package br.com.fiap.carrinhoDeCompras.service.impl;

import br.com.fiap.carrinhoDeCompras.dto.ItemDto;
import br.com.fiap.carrinhoDeCompras.entities.Carrinho;
import br.com.fiap.carrinhoDeCompras.entities.Cliente;
import br.com.fiap.carrinhoDeCompras.entities.Endereco;
import br.com.fiap.carrinhoDeCompras.entities.ItemCarrinho;
import br.com.fiap.carrinhoDeCompras.handler.NotFoundException;
import br.com.fiap.carrinhoDeCompras.repositories.CarrinhoRepository;
import br.com.fiap.carrinhoDeCompras.repositories.ItemCarrinhoRepository;
import br.com.fiap.carrinhoDeCompras.service.CarrinhoService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ClienteEndpointService clienteEndpointService;

    @Autowired
    public CarrinhoServiceImpl(CarrinhoRepository carrinhoRepository, ItemCarrinhoRepository itemCarrinhoRepository,
                               RestTemplate restTemplate, ObjectMapper objectMapper,
                               ClienteEndpointService clienteEndpointService) {
        this.carrinhoRepository = carrinhoRepository;
        this.itemCarrinhoRepository = itemCarrinhoRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.clienteEndpointService = clienteEndpointService;
    }

    @Override
    public List<Carrinho> listarCarrinhos() {
        return carrinhoRepository.findAll();
    }

    @Override
    public Carrinho buscarCarrinhoPorId(Long id) {
        return carrinhoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("carrinho não encontrado para o ID: " + id));
    }

    @Override
    public Carrinho criarCarrinho(Carrinho carrinho) {
        if (carrinho.getIdCliente() != null) {
            Cliente cliente = verificaCliente(carrinho.getIdCliente());
            if (cliente == null) {
                throw new RuntimeException("Cliente não cadastrado...");
            }
        }

        //recupera itens
        List<ItemDto> itens = this.recuperaItens(carrinho.getItens());

        //verificação de estoque chamando gestão de itens
        this.verificaEstoque(itens, carrinho.getItens());

        itemCarrinhoRepository.saveAll(carrinho.getItens());

        return carrinhoRepository.save(carrinho);
    }

    @Override
    public Carrinho atualizarCarrinho(Long id, Carrinho carrinho) {
        Carrinho carrinhoExistente = this.buscarCarrinhoPorId(id);

        itemCarrinhoRepository.deleteAll();

        //recupera itens
        List<ItemDto> itens = this.recuperaItens(carrinho.getItens());

        //verificação de estoque chamando gestão de itens
        this.verificaEstoque(itens, carrinho.getItens());

        itemCarrinhoRepository.saveAll(carrinho.getItens());

        if (carrinho.getIdCliente() != null) {
            Cliente cliente = verificaCliente(carrinho.getIdCliente());
            if (cliente == null) {
                throw new RuntimeException("Cliente não cadastrado...");
            }
        }

        carrinhoExistente.setItens(carrinho.getItens());

        return carrinhoRepository.save(carrinhoExistente);
    }

    @Override
    public boolean deletarCarrinho(Long id) {
        carrinhoRepository.deleteById(id);
        return true;
    }

    private Cliente verificaCliente(UUID id)  {
        System.out.println("Accessando endpoint cliente.");

        var cliente = clienteEndpointService.buscarClientePorId(id);
        if (cliente.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Cliente não encontrado");
        }
        return cliente.getBody();
    }

    private List<ItemDto> recuperaItens(List<ItemCarrinho> itensCarrinho) {
        List<ItemDto> itens = new ArrayList<>();

        if (itensCarrinho == null) {
            throw new RuntimeException("Carrinho deve conter itens");
        }

        for (ItemCarrinho itemCarrinho : itensCarrinho) {
            ResponseEntity<String> response = restTemplate.getForEntity(
                    "http://srv-item:9511/itens/{id}",
                    String.class,
                    itemCarrinho.getIdItem()
            );

            if (response == null) break;

            ItemDto item = new ItemDto();

            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NoSuchElementException("Item não encontrado");
            } else {
                try {
                    JsonNode produtoJson = objectMapper.readTree(response.getBody());
                    item.setId(UUID.fromString(produtoJson.get("id").asText()));
                    item.setNome(produtoJson.get("nome").asText());
                    item.setDescricao(produtoJson.get("descricao").asText());
                    item.setCategoria(produtoJson.get("categoria").asText());
                    item.setPreco(produtoJson.get("preco").decimalValue());
                    item.setUrlImagem(produtoJson.get("urlImagem").asText());
                    item.setQuantidade(produtoJson.get("quantidade").asInt());

                } catch (IOException e) {
                    throw new RuntimeException("não foi possível fazer a conexão com o app de itens");
                }
            }
            itens.add(item);
        }
        return itens;
    }

    private void verificaEstoque(List<ItemDto> itens, List<ItemCarrinho> itensCarrinho) {
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getQuantidade() < itensCarrinho.get(i).getQuantidade()) {
                throw new IllegalArgumentException("O item " + itens.get(i).getNome()
                        + " não tem a quantidade desejada (" + itensCarrinho.get(i).getQuantidade() + ") no estoque." +
                        " Total: " + itens.get(i).getQuantidade());
            }
        }

    }
}