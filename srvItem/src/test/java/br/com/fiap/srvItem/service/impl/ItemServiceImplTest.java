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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        // Gera uma lista de itens de teste
        List<Item> itens = Arrays.asList(easyRandom.nextObject(Item.class), easyRandom.nextObject(Item.class));

        // Configura o mock para retornar a lista de itens
        when(itemRepository.findAll()).thenReturn(itens);

        // Executa o método do serviço
        List<Item> result = itemService.listarItens();

        // Verifica o resultado
        assertEquals(itens, result);
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    public void buscarItemPorId_DeveRetornarItem_QuandoItemExistir() {
        // Gera um item de teste com um ID aleatório
        Item item = easyRandom.nextObject(Item.class);
        UUID id = item.getId();

        // Configura o mock para retornar o item de teste
        when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        // Executa o método do serviço
        Item result = itemService.buscarItemPorId(id);

        // Verifica o resultado
        assertEquals(item, result);
        verify(itemRepository, times(1)).findById(id);
    }

    @Test
    public void buscarItemPorId_DeveLancarExcecao_QuandoItemNaoExistir() {
        // Gera um ID aleatório para o teste
        UUID id = UUID.randomUUID();

        // Configura o mock para retornar um Optional vazio
        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        // Executa o método do serviço e verifica se a exceção é lançada
        assertThrows(MensagemNotFoundException.class, () -> itemService.buscarItemPorId(id));
        verify(itemRepository, times(1)).findById(id);
    }

    @Test
    public void cadastrarItem_DeveRetornarItemCriado_QuandoItemValido() {
        // Gera um item de teste
        Item item = easyRandom.nextObject(Item.class);

        // Configura o mock para retornar o item de teste
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        // Executa o método do serviço
        Item result = itemService.cadastrarItem(item);

        // Verifica o resultado
        assertEquals(item, result);
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    public void atualizarItem_DeveRetornarItemAtualizado_QuandoItemExistir() {
        // Gera um item de teste com um ID aleatório
        Item itemAtualizado = easyRandom.nextObject(Item.class);
        UUID id = itemAtualizado.getId();

        // Configura o mock para retornar o item encontrado e depois o atualizado
        when(itemRepository.findById(id)).thenReturn(Optional.of(itemAtualizado));
        when(itemRepository.save(any(Item.class))).thenReturn(itemAtualizado);

        // Executa o método do serviço
        Item result = itemService.atualizarItem(id, itemAtualizado);

        // Verifica o resultado
        assertEquals(itemAtualizado, result);
        verify(itemRepository, times(1)).findById(id);
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    public void atualizarItem_DeveLancarExcecao_QuandoItemNaoExistir() {
        // Gera um ID aleatório para o teste
        UUID id = UUID.randomUUID();
        Item itemAtualizado = easyRandom.nextObject(Item.class);

        // Configura o mock para retornar um Optional vazio
        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        // Executa o método do serviço e verifica se a exceção é lançada
        assertThrows(MensagemNotFoundException.class, () -> itemService.atualizarItem(id, itemAtualizado));
        verify(itemRepository, times(1)).findById(id);
    }

    @Test
    public void deletarItem_DeveDeletarItem_QuandoItemExistir() {
        // Gera um item de teste com um ID aleatório
        UUID id = UUID.randomUUID();
        Item item = easyRandom.nextObject(Item.class);

        // Configura o mock para retornar o item encontrado
        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        doNothing().when(itemRepository).deleteById(id);

        // Executa o método do serviço
        itemService.deletarItem(id);

        // Verifica se o método de deleção foi chamado
        verify(itemRepository, times(1)).findById(id);
        verify(itemRepository, times(1)).deleteById(id);
    }

    @Test
    public void deletarItem_DeveLancarExcecao_QuandoItemNaoExistir() {
        // Gera um ID aleatório para o teste
        UUID id = UUID.randomUUID();

        // Configura o mock para retornar um Optional vazio
        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        // Executa o método do serviço e verifica se a exceção é lançada
        assertThrows(MensagemNotFoundException.class, () -> itemService.deletarItem(id));
        verify(itemRepository, times(1)).findById(id);
        verify(itemRepository, never()).deleteById(id);
    }
}