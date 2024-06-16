package br.com.fiap.srvItem.service;

import br.com.fiap.srvItem.model.Item;

import java.util.List;
import java.util.UUID;

public interface ItemService {

    List<Item> listarItens();
    Item buscarItemPorId(UUID id);
    Item cadastrarItem(Item item);
    Item atualizarItem(UUID id, Item itemAtualizado);
    void deletarItem(UUID id);
}
