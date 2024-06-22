package br.com.fiap.srvItem.controller;

import br.com.fiap.srvItem.model.Item;
import br.com.fiap.srvItem.service.ItemService;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private EasyRandom easyRandom;

    @BeforeEach
    public void setup() {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .stringLengthRange(5, 10)
                .collectionSizeRange(1, 5)
                .randomize(UUID.class, UUID::randomUUID);
        easyRandom = new EasyRandom(parameters);
    }

    @Test
    public void listarItens_DeveRetornarListaDeItens() {
        List<Item> itens = Arrays.asList(easyRandom.nextObject(Item.class), easyRandom.nextObject(Item.class));

        when(itemService.listarItens()).thenReturn(itens);

        List<Item> result = itemController.listarItens();

        assertEquals(itens, result);
        verify(itemService, times(1)).listarItens();
    }

    @Test
    public void buscarItemPorId_DeveRetornarItem_QuandoItemExistir() {
        Item item = easyRandom.nextObject(Item.class);
        UUID id = item.getId();

        when(itemService.buscarItemPorId(id)).thenReturn(item);

        ResponseEntity<Item> response = itemController.buscarItemPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(item, response.getBody());
        verify(itemService, times(1)).buscarItemPorId(id);
    }

    @Test
    public void cadastrarItem_DeveRetornarItemCriado_QuandoItemValido() {
        Item item = easyRandom.nextObject(Item.class);

        when(itemService.cadastrarItem(any(Item.class))).thenReturn(item);

        ResponseEntity<Item> response = itemController.cadastrarItem(item);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(item, response.getBody());
        verify(itemService, times(1)).cadastrarItem(any(Item.class));
    }

    @Test
    public void atualizarItem_DeveRetornarItemAtualizado_QuandoItemExistir() {
        Item itemAtualizado = easyRandom.nextObject(Item.class);
        UUID id = itemAtualizado.getId();

        when(itemService.atualizarItem(eq(id), any(Item.class))).thenReturn(itemAtualizado);

        ResponseEntity<Item> response = itemController.atualizarItem(id, itemAtualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemAtualizado, response.getBody());
        verify(itemService, times(1)).atualizarItem(eq(id), any(Item.class));
    }

    @Test
    public void deletarItem_DeveRetornarNoContent_QuandoItemExistir() {
        UUID id = UUID.randomUUID();

        doNothing().when(itemService).deletarItem(id);

        ResponseEntity<Void> response = itemController.deletarItem(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(itemService, times(1)).deletarItem(id);
    }
}