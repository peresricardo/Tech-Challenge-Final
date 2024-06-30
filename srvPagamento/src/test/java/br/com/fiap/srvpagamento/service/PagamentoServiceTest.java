package br.com.fiap.srvpagamento.service;

import br.com.fiap.srvpagamento.dto.CarrinhoDTO;
import br.com.fiap.srvpagamento.dto.ResumoPagamentoDTO;
import br.com.fiap.srvpagamento.model.Pagamento;
import br.com.fiap.srvpagamento.repository.PagamentoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PagamentoServiceTest {

    @InjectMocks
    private PagamentoService pagamentoService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PagamentoRepository pagamentoRepository;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() throws Exception {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarPagamento() {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(1);

        Long idCarrinho = 1L;

        CarrinhoDTO carrinhoDTO = new CarrinhoDTO();
        carrinhoDTO.setTotalPedido(BigDecimal.valueOf(100));

        when(restTemplate.getForObject(anyString(), eq(CarrinhoDTO.class))).thenReturn(carrinhoDTO);
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);

        ResumoPagamentoDTO resumoPagamentoDTO = pagamentoService.realizarPagamento(pagamento, idCarrinho);

        assertNotNull(resumoPagamentoDTO);
        assertEquals(pagamento.getId(), resumoPagamentoDTO.getPagamento().getId());
        assertEquals(carrinhoDTO.getTotalPedido(), resumoPagamentoDTO.getTotal());
        verify(pagamentoRepository, times(1)).save(pagamento);
    }

    @Test
    void deveLancarExcecaoQuandoCarrinhoInvalido() {
        Pagamento pagamento = new Pagamento();
        Long idCarrinho = 1L;

        when(restTemplate.getForObject(anyString(), eq(CarrinhoDTO.class))).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            pagamentoService.realizarPagamento(pagamento, idCarrinho);
        });
    }
}
