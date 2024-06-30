package br.com.fiap.srvpagamento.controller;

import br.com.fiap.srvpagamento.exception.GlobalExceptionHandler;
import br.com.fiap.srvpagamento.service.PagamentoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ControllerAdvice;

public class PagamentoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PagamentoService pagamentoService;


    AutoCloseable openMocks;

    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        PagamentoController pagamentoController = new PagamentoController(pagamentoService);
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    
}
