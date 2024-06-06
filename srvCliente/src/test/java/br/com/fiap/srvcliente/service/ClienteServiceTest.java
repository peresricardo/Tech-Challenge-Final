package br.com.fiap.srvcliente.service;

import br.com.fiap.srvcliente.dto.ClienteDto;
import br.com.fiap.srvcliente.exception.MensagemNotFoundException;
import br.com.fiap.srvcliente.model.ClienteModel;
import br.com.fiap.srvcliente.repository.ClienteRepository;
import br.com.fiap.srvcliente.service.serviceImpl.ClienteServiceImpl;
import br.com.fiap.srvcliente.service.serviceImpl.ViacepClient;
import br.com.fiap.srvcliente.util.ClienteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;


public class ClienteServiceTest {
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ViacepClient viacepClient;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        clienteService = new ClienteServiceImpl(clienteRepository, viacepClient);
    }

    @AfterEach
    void tearDow() throws Exception {
        openMocks.close();
    }

    @Nested
    class RegistrarCliente {
        @Test
        void deveRegistrarCliente() {
            var cliente = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(cliente);

            when(clienteRepository.save(any(ClienteModel.class)))
                    .thenAnswer(i -> i.getArgument(0));

            var clienteRegistrado = clienteService.cadastrarCliente(dto);

            assertThat(clienteRegistrado).isInstanceOf(ClienteDto.class).isNotNull();
            assertThat(clienteRegistrado.nome()).isEqualTo(cliente.getNome());
            verify(clienteRepository, times(1)).save(any(ClienteModel.class));
        }
    }

    @Nested
    class RemoverCliente {
        @Test
        void deveRemoverCliente() {
            var id = UUID.fromString("51fa607a-1e61-11ee-be56-0242ac120002");
            var cliente = ClienteHelper.gerarRegistro();
            cliente.setIdCliente(id);
            var dto = ClienteDto.fromEntity(cliente);

            when(clienteRepository.findById(id))
                    .thenReturn(Optional.of(cliente));

            doNothing()
                    .when(clienteRepository).deleteById(id);

            var resultado = clienteService.excluirCliente(dto);

            assertThat(resultado).isTrue();
            verify(clienteRepository, times(1)).findById(any(UUID.class));
            verify(clienteRepository, times(1)).delete(any(ClienteModel.class));
        }
    }

    @Nested
    class AlterarCliente {
        @Test
        void deveAlterarCliente() {
            var cliente = ClienteHelper.gerarRegistro();
            var id = UUID.fromString("51fa607a-1e61-11ee-be56-0242ac120002");
            cliente.setIdCliente(id);

            var clienteAlterado = cliente;
            clienteAlterado.setNome("Nome Alterado");
            clienteAlterado.setEmail("email Alterado");

            when(clienteRepository.findById(id))
                    .thenReturn(Optional.of(cliente));

            when(clienteRepository.save(any(ClienteModel.class)))
                    .thenAnswer(i -> i.getArgument(0));

            var dto = ClienteDto.fromEntity(clienteAlterado);
            var clienteRegistrado = clienteService.atualizarCliente(dto);
            var resultado = new ClienteModel(clienteRegistrado);

            assertThat(resultado).isInstanceOf(ClienteModel.class).isNotNull();
            assertThat(resultado.getNome()).isEqualTo(clienteAlterado.getNome());
            assertThat(resultado.getEmail()).isEqualTo(clienteAlterado.getEmail());
            verify(clienteRepository, times(1)).save(any(ClienteModel.class));
        }
    }

    @Nested
    class ListarClientes {
        @Test
        void devePermitirListarClientes() {
            var reg1 = ClienteHelper.gerarRegistro();
            var reg2 = ClienteHelper.gerarRegistro();

            Page<ClienteModel> lista = new PageImpl<>(Arrays.asList(reg1, reg2));

            when(clienteRepository.findAll(any(Pageable.class))).thenReturn(lista);

            var listaRecebida = clienteService.listarClientes(Pageable.unpaged());

            assertThat(listaRecebida).hasSizeGreaterThan(1);
            verify(clienteRepository, times(1)).findAll(any(Pageable.class));
        }
    }

    @Nested
    class BuscarClientePorId {
        @Test
        void devePermitirBuscarPorId() {
            var id = UUID.randomUUID();
            var cliente = ClienteHelper.gerarRegistro();
            cliente.setIdCliente(id);
            var dto = ClienteDto.fromEntity(cliente);

            when(clienteRepository.findById(any(UUID.class)))
                    .thenReturn(Optional.of(cliente));

            var resultado = clienteService.buscarClientePorId(id);

            assertThat(resultado).isEqualTo(dto);
            verify(clienteRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        void deveGerarExcecao_QuandoBuscarPorId_NaoExistente() {
            var id = UUID.randomUUID();

            when(clienteRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> clienteService.buscarClientePorId(id))
                    .isInstanceOf(MensagemNotFoundException.class)
                    .hasMessage("Cliente não encontrado.");
            verify(clienteRepository, times(1)).findById(id);
        }
    }
}
