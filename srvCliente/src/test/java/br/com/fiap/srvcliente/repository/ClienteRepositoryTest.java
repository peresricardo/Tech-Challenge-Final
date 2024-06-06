package br.com.fiap.srvcliente.repository;

import br.com.fiap.srvcliente.model.ClienteModel;
import br.com.fiap.srvcliente.util.ClienteHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;


public class ClienteRepositoryTest {
    @Mock
    private ClienteRepository clienteRepository;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class RegistrarCliente {
        @Test
        void deveRegistrarCliente() {
            var cliente = ClienteHelper.gerarRegistro();
            when(clienteRepository.save(any(ClienteModel.class))).thenReturn(cliente);

            var resultado = clienteRepository.save(cliente);

            assertThat(resultado).isNotNull().isEqualTo(cliente);
            verify(clienteRepository, times(1)).save(any(ClienteModel.class));
        }
    }

    @Nested
    class RemoverCliente {
        @Test
        void deveRemoverCliente() {
            var id = UUID.fromString("51fa607a-1e61-11ee-be56-0242ac120002");
            doNothing()
                    .when(clienteRepository).deleteById(id);

            clienteRepository.deleteById(id);

            verify(clienteRepository, times(1)).deleteById(any(UUID.class));
        }
    }

    @Nested
    class ListarClientes {
        @Test
        void devePermitirListarClientes() {
            // Arrange
            var reg1 = ClienteHelper.gerarRegistro();
            var reg2 = ClienteHelper.gerarRegistro();
            var lista = Arrays.asList(reg1, reg2);
            when(clienteRepository.findAll()).thenReturn(lista);

            var listaRecebida = clienteRepository.findAll();

            assertThat(listaRecebida).hasSizeGreaterThan(1);
            verify(clienteRepository, times(1)).findAll();
        }
    }

    @Nested
    class BuscarClientePorId {
        @Test
        void deveBuscarClientePorId() {
            var id = UUID.fromString("2c888bee-5498-4f06-abd5-091f1f22a605");
            var cliente = ClienteHelper.gerarRegistro();
            when(clienteRepository.findById(any(UUID.class))).thenReturn(Optional.of(cliente));

            var resultado = clienteRepository.findById(id);

            Assertions.assertThat(resultado).isPresent().containsSame(cliente);
            resultado.ifPresent(entityRecebida -> {
                Assertions.assertThat(entityRecebida.getNome()).isEqualTo(cliente.getNome());
                Assertions.assertThat(entityRecebida.getEmail()).isEqualTo(cliente.getEmail());
            });
            verify(clienteRepository, times(1)).findById(any(UUID.class));
        }
    }
}
