package br.com.fiap.srvItem.service.impl;

import br.com.fiap.srvItem.exception.MensagemNotFoundException;
import br.com.fiap.srvItem.model.Item;
import br.com.fiap.srvItem.repository.ItemRepository;
import br.com.fiap.srvItem.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public List<Item> listarItens() {
        return itemRepository.findAll();
    }

    @Override
    public Item buscarItemPorId(UUID id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new MensagemNotFoundException("Item não encontrado para o ID: " + id));
    }

    @Override
    public Item cadastrarItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item atualizarItem(UUID id, Item itemAtualizado) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setNome(itemAtualizado.getNome());
                    item.setDescricao(itemAtualizado.getDescricao());
                    item.setCategoria(itemAtualizado.getCategoria());
                    item.setPreco(itemAtualizado.getPreco());
                    item.setUrlImagem(itemAtualizado.getUrlImagem());
                    item.setQuantidade(itemAtualizado.getQuantidade());
                    return itemRepository.save(item);
                }).orElseThrow(() -> new MensagemNotFoundException("Item não encontrado com o id " + id));
    }

    @Override
    public void deletarItem(UUID id) {
        itemRepository.findById(id)
                .orElseThrow(() -> new MensagemNotFoundException("Item não encontrado com o id " + id));
        itemRepository.deleteById(id);
    }
}
