package br.com.fiap.srvcliente.service;

import br.com.fiap.srvcliente.dto.ClienteDto;
import br.com.fiap.srvcliente.dto.EnderecoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ClienteService {
    ClienteDto cadastrarCliente(ClienteDto dto);
    ClienteDto atualizarCliente(ClienteDto dto);
    boolean excluirCliente(ClienteDto dto);
    ClienteDto buscarClientePorId(UUID id);
    Page<ClienteDto> listarClientes(Pageable pageable);
    EnderecoDto buscarEnderecoPorCep(String cep);
}
