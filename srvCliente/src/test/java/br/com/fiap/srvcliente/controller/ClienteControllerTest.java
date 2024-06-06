package br.com.fiap.srvcliente.controller;

import br.com.fiap.srvcliente.dto.ClienteDto;
import br.com.fiap.srvcliente.exception.GlobalExceptionHandler;
import br.com.fiap.srvcliente.service.ClienteService;
import br.com.fiap.srvcliente.util.ClienteHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ClienteControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ClienteService clienteService;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        ClienteController clienteController = new ClienteController(clienteService);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Nested
    class RegistrarCliente {
        @Test
        void deveRegistrarCliente() throws Exception {
            var cliente = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(cliente);
            when(clienteService.cadastrarCliente(any(ClienteDto.class))).thenAnswer(i -> i.getArgument(0));

            mockMvc.perform(post("/clientes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(dto)))
                    .andExpect(status().isCreated());

            verify(clienteService, times(1)).cadastrarCliente(any(ClienteDto.class));
        }
    }

    @Nested
    class RemoverCliente {
        @Test
        void devePermetirExcluirClientePorId() throws Exception {
            var cliente = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(cliente);

            when(clienteService.excluirCliente(any(ClienteDto.class)))
                    .thenReturn(true);

            mockMvc.perform(delete("/clientes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(dto)))
                    .andExpect(status().isOk());

            verify(clienteService, times(1)).excluirCliente(any(ClienteDto.class));
        }
    }

    @Nested
    class AlterarCliente {
        @Test
        void deveAlterarCliente() throws Exception {
            var cliente = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(cliente);

            when(clienteService.atualizarCliente(any(ClienteDto.class))).thenReturn(any(ClienteDto.class));

            mockMvc.perform(put("/clientes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(dto)))
                    .andExpect(status().isOk());

            verify(clienteService, times(1)).atualizarCliente(any(ClienteDto.class));
        }
    }

    @Nested
    class ListarClientes {
        @Test
        void devePermitirListarClientes() throws Exception {
            Pageable pageable = PageRequest.of(1, 10);
            Page<ClienteDto> clientes = clienteService.listarClientes(pageable);

            when(clienteService.listarClientes(pageable)).thenReturn(clientes);

            mockMvc.perform(get("/clientes")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(clienteService, times(1)).listarClientes(pageable);
        }
    }

    @Nested
    class BuscarClientePorId {
        @Test
        void devePermitirBuscarPorId() throws Exception {
            var id = UUID.randomUUID();
            var cliente = ClienteHelper.gerarRegistro();
            cliente.setIdCliente(id);
            var dto = ClienteDto.fromEntity(cliente);

            when(clienteService.buscarClientePorId(any(UUID.class))).thenReturn(any(ClienteDto.class));

            mockMvc.perform(get("/clientes/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(clienteService, times(1)).buscarClientePorId(any(UUID.class));
        }
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
