package br.com.fiap.srvcliente.service.serviceImpl;

import br.com.fiap.srvcliente.dto.ClienteDto;
import br.com.fiap.srvcliente.dto.EnderecoDto;
import br.com.fiap.srvcliente.exception.MensagemFoundException;
import br.com.fiap.srvcliente.exception.MensagemNotFoundException;
import br.com.fiap.srvcliente.model.ClienteModel;
import br.com.fiap.srvcliente.repository.ClienteRepository;
import br.com.fiap.srvcliente.service.ClienteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;
    private final ViacepClient viacepClient;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ViacepClient viacepClient) {
        this.clienteRepository = clienteRepository;
        this.viacepClient = viacepClient;
    }


    @Override
    public ClienteDto cadastrarCliente(ClienteDto dto) {
        boolean existe = clienteRepository.existsByNome(dto.nome());
        if (existe) {
            throw new MensagemFoundException("Cliente já cadastrado com esse nome.");
        }
        ClienteModel entity = new ClienteModel(dto);
        clienteRepository.save(entity);
        return ClienteDto.fromEntity(entity);
    }

    @Override
    public ClienteDto atualizarCliente(ClienteDto dto) {
        ClienteModel entity = clienteRepository.findById(dto.id())
                .orElseThrow(() -> new MensagemNotFoundException("Cliente não encontrado."));
        ClienteModel cliente = new ClienteModel(dto);
        cliente.setIdCliente(entity.getIdCliente());
        clienteRepository.save(cliente);
        return ClienteDto.fromEntity(cliente);
    }

    @Override
    public boolean excluirCliente(ClienteDto dto) {
        var dtoSearch = buscarClientePorId(dto.id());
        ClienteModel cliente = new ClienteModel(dtoSearch);
        clienteRepository.delete(cliente);
        return true;
    }

    @Override
    public ClienteDto buscarClientePorId(UUID id) {
        var cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new MensagemNotFoundException("Cliente não encontrado."));
        return ClienteDto.fromEntity(cliente);
    }

    @Override
    public Page<ClienteDto> listarClientes(Pageable pageable) {
        Page<ClienteModel> lista = clienteRepository.findAll(pageable);
        return lista.map(ClienteDto::fromEntity);
    }

    @Override
    public EnderecoDto buscarEnderecoPorCep(String cep) {
        var endereco = viacepClient.getEndereco(cep);
        if (endereco != null) {
            EnderecoDto dto = new EnderecoDto(
                    endereco.logradouro(),
                    "",
                    "",
                    endereco.bairro(),
                    endereco.localidade(),
                    endereco.uf(),
                    endereco.cep());
            return dto;
        } else {
            return null;
        }
    }
}
