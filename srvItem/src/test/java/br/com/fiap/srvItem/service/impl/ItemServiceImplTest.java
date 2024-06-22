package br.com.fiap.srvItem.service.impl;

import br.com.fiap.srvItem.exception.MensagemNotFoundException;
import br.com.fiap.srvItem.model.Item;
import br.com.fiap.srvItem.repository.ItemRepository;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    private EasyRandom easyRandom;

    @BeforeEach
    public void setUp() {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .stringLengthRange(5, 10)
                .collectionSizeRange(1, 5)
                .randomize(UUID.class, UUID::randomUUID);
        easyRandom = new EasyRandom(parameters);
    }

    @Test
    public void listarItens_DeveRetornarListaDeItens() {
        List<Item> itens = Arrays.asList(easyRandom.nextObject(Item.class), easyRandom.nextObject(Item.class));

        when(itemRepository.findAll()).thenReturn(itens);

        List<Item> result = itemService.listarItens();

        assertEquals(itens, result);
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    public void buscarItemPorId_DeveRetornarItem_QuandoItemExistir() {
        Item item = easyRandom.nextObject(Item.class);
        UUID id = item.getId();

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        Item result = itemService.buscarItemPorId(id);

        assertEquals(item, result);
        verify(itemRepository, times(1)).findById(id);
    }

    @Test
    public void buscarItemPorId_DeveLancarExcecao_QuandoItemNaoExistir() {
        UUID id = UUID.randomUUID();

        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MensagemNotFoundException.class, () -> itemService.buscarItemPorId(id));
        verify(itemRepository, times(1)).findById(id);
    }

    @Test
    void buscarItemPorCategoria_quandoItensExistem_retornaItens() {
        String categoria = "Eletrônicos";
        List<Item> itens = Arrays.asList(new Item(), new Item());
        when(itemRepository.findByCategoria(categoria)).thenReturn(itens);

        List<Item> result = itemService.buscarItemPorCategoria(categoria);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void buscarItemPorCategoria_quandoNenhumItemEncontrado_lancaExcecao() {
        String categoria = "Móveis";
        when(itemRepository.findByCategoria(categoria)).thenReturn(List.of());

        assertThrows(MensagemNotFoundException.class, () -> itemService.buscarItemPorCategoria(categoria));
    }

    @Test
    public void cadastrarItem_DeveRetornarItemCriado_QuandoItemValido() {
        Item item = easyRandom.nextObject(Item.class);

        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item result = itemService.cadastrarItem(item);

        assertEquals(item, result);
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    public void atualizarItem_DeveRetornarItemAtualizado_QuandoItemExistir() {
        Item itemAtualizado = easyRandom.nextObject(Item.class);
        UUID id = itemAtualizado.getId();

        when(itemRepository.findById(id)).thenReturn(Optional.of(itemAtualizado));
        when(itemRepository.save(any(Item.class))).thenReturn(itemAtualizado);

        Item result = itemService.atualizarItem(id, itemAtualizado);

        assertEquals(itemAtualizado, result);
        verify(itemRepository, times(1)).findById(id);
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    public void atualizarItem_DeveLancarExcecao_QuandoItemNaoExistir() {
        UUID id = UUID.randomUUID();
        Item itemAtualizado = easyRandom.nextObject(Item.class);

        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MensagemNotFoundException.class, () -> itemService.atualizarItem(id, itemAtualizado));
        verify(itemRepository, times(1)).findById(id);
    }

    @Test
    public void deletarItem_DeveDeletarItem_QuandoItemExistir() {
        UUID id = UUID.randomUUID();
        Item item = easyRandom.nextObject(Item.class);

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        doNothing().when(itemRepository).deleteById(id);

        itemService.deletarItem(id);

        verify(itemRepository, times(1)).findById(id);
        verify(itemRepository, times(1)).deleteById(id);
    }

    @Test
    public void deletarItem_DeveLancarExcecao_QuandoItemNaoExistir() {
        UUID id = UUID.randomUUID();

        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MensagemNotFoundException.class, () -> itemService.deletarItem(id));
        verify(itemRepository, times(1)).findById(id);
        verify(itemRepository, never()).deleteById(id);
    }
}