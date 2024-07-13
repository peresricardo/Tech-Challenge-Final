package br.com.fiap.srvpagamento.controller;

import br.com.fiap.srvpagamento.dto.CarrinhoDTO;
import br.com.fiap.srvpagamento.dto.ItemCarrinhoDTO;
import br.com.fiap.srvpagamento.dto.ResumoPagamentoDTO;
import br.com.fiap.srvpagamento.model.Pagamento;
import br.com.fiap.srvpagamento.service.PagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PagamentoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PagamentoService pagamentoService;

    @InjectMocks
    private PagamentoController pagamentoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController).build();
    }

    @Test
    void testRealizarPagamento() throws Exception {
        Pagamento pagamento = new Pagamento();
        pagamento.setIdCarrinho(1L);

        ItemCarrinhoDTO item1 = new ItemCarrinhoDTO();
        item1.setPreco(BigDecimal.valueOf(50.0));
        item1.setNome("tenis");
        item1.setQuantidade(1);

        ItemCarrinhoDTO item2 = new ItemCarrinhoDTO();
        item2.setPreco(BigDecimal.valueOf(50.0));
        item2.setNome("calca");
        item2.setQuantidade(1);

        CarrinhoDTO carrinho = new CarrinhoDTO();
        carrinho.setItens(Arrays.asList(item1, item2));

        ResumoPagamentoDTO resumoPagamento = new ResumoPagamentoDTO(pagamento, carrinho.getItens(), carrinho.getTotalPedido());

        when(pagamentoService.realizarPagamento(any(Pagamento.class), any(Long.class))).thenReturn(resumoPagamento);

        mockMvc.perform(post("/pagamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pagamento)))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
