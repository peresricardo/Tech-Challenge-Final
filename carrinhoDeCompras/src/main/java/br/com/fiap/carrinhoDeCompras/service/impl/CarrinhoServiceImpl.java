package br.com.fiap.carrinhoDeCompras.service.impl;

import br.com.fiap.carrinhoDeCompras.dto.ItemDto;
import br.com.fiap.carrinhoDeCompras.entities.Carrinho;
import br.com.fiap.carrinhoDeCompras.entities.Cliente;
import br.com.fiap.carrinhoDeCompras.entities.Endereco;
import br.com.fiap.carrinhoDeCompras.entities.ItemCarrinho;
import br.com.fiap.carrinhoDeCompras.repositories.CarrinhoRepository;
import br.com.fiap.carrinhoDeCompras.service.CarrinhoService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public CarrinhoServiceImpl(CarrinhoRepository carrinhoRepository, RestTemplate restTemplate,
                             ObjectMapper objectMapper) {
        this.carrinhoRepository = carrinhoRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Carrinho> listarItens() {
        return carrinhoRepository.findAll();
    }

    @Override
    public Carrinho criarCarrinho(Carrinho carrinho, Long idCliente) {
        Cliente cliente = getClienteById(idCliente);
        carrinho.setCliente(cliente);

        //recupera itens
        List<ItemDto> itens = this.recuperaItens(carrinho.getItens());

        //verificação de estoque chamando gestão de itens
        this.verificaEstoque(itens, carrinho.getItens());

        //total do carrinho
        BigDecimal totalPedido = calcularTotalPedido(carrinho.getItens(), itens);
        carrinho.setTotalPedido(totalPedido);

        Carrinho carrinhoSalvo = carrinhoRepository.save(carrinho);

        return carrinhoSalvo;
    }

    @Override
    public Carrinho atualizarCarrinho(Long id, Carrinho carrinho) {
        Carrinho carrinhoExistente = new Carrinho(); //buscarPedidoPorId(id);

        carrinhoExistente.setCliente(carrinho.getCliente());
        carrinhoExistente.setItens(carrinho.getItens());
//        carrinhoExistente.setTotalPedido(calcularTotalPedido(carrinho.getItens()));

        return carrinhoRepository.save(carrinhoExistente);
    }

    @Override
    public boolean deletarCarrinho(Long id) {
        carrinhoRepository.deleteById(id);
        return true;
    }

    private Cliente getClienteById(Long idCliente) {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:8090/clientes/{id}",
                String.class,
                idCliente
        );

        if (response == null || response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException("Cliente não encontrado com ID: " + idCliente);
        }
        try {
            JsonNode clienteJson = objectMapper.readTree(response.getBody());
            Endereco endereco = new Endereco(
                    clienteJson.get("endereco").get("logradouro").asText(),
                    clienteJson.get("endereco").get("bairro").asText(),
                    clienteJson.get("endereco").get("cep").asText(),
                    clienteJson.get("endereco").get("cidade").asText(),
                    clienteJson.get("endereco").get("uf").asText(),
                    clienteJson.get("endereco").get("complemento").asText(),
                    clienteJson.get("endereco").get("numero").asText()
            );
            return Cliente.builder()
                    .idCliente(clienteJson.get("idCliente").asLong())
                    .nome(clienteJson.get("nome").asText())
                    .telefone(clienteJson.get("telefone").asText())
                    .cpf(clienteJson.get("cpf").asText())
                    .email(clienteJson.get("email").asText())
                    .endereco(endereco)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível processar o cliente", e);
        }
    }

    private List<ItemDto> recuperaItens(List<ItemCarrinho> itensCarrinho) {
        List<ItemDto> itens = new ArrayList<>();

        if (itensCarrinho == null) {
            throw new RuntimeException("Pedido deve ter algum item");
        }

        for (ItemCarrinho itemCarrinho : itensCarrinho) {
            ResponseEntity<String> response = restTemplate.getForEntity(
                    "http://localhost:8080/itens/getItens/{id}",
                    String.class,
                    itemCarrinho.getIdItem()
            );

            ItemDto item = new ItemDto();
            if (response == null) break;

            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NoSuchElementException("Item não encontrado");
            } else {
                try {
                    JsonNode produtoJson = objectMapper.readTree(response.getBody());
                    item.setId(produtoJson.get("id").asLong());
                    item.setNome(produtoJson.get("nome").asText());
                    item.setDescricao(produtoJson.get("descricao").asText());
                    item.setQuantidadeEstoque(produtoJson.get("quantidadeEstoque").asInt());
                    item.setPreco(produtoJson.get("preco").decimalValue());

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
            if (itens.get(i).getQuantidadeEstoque() < itensCarrinho.get(i).getQuantidade()) {
                throw new IllegalArgumentException("O item " + itens.get(i).getNome()
                        + " não tem " + itensCarrinho.get(i).getQuantidade() + " no estoque." +
                        " Total: " + itens.get(i).getQuantidadeEstoque());
            }
        }

        for (int i = 0; i < itens.size(); i++) {
            this.removeEstoque(itens.get(i).getId(), itensCarrinho.get(i).getQuantidade());
        }
    }

    private void removeEstoque(Long id, Integer quantidade) {
        try {
            String url = "http://localhost:8080/itens/removeEstoque/"+id+"/"+quantidade;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

            restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class);

            Map<String, String> params = new HashMap<>();
            params.put("id", id.toString());
            params.put("quantidade", quantidade.toString());

            restTemplate.put(
                    "http://localhost:8080/itens/removeEstoque/{id}/{quantidade}",
                    String.class,
                    params);
        } catch (Exception e) {
            throw new RuntimeException("não foi possível remover do estoque. Erro: " + e.getMessage());
        }
    }

    private BigDecimal calcularTotalPedido(List<ItemCarrinho> itensCarrinho, List<ItemDto> itens) {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < itens.size(); i++) {
            total = total.add(
                    itens.get(i).getPreco().multiply(
                            BigDecimal.valueOf(itensCarrinho.get(i).getQuantidade())
                    )
            );
        }
        return total;
    }

}