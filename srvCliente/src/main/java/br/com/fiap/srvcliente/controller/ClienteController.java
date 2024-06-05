package br.com.fiap.srvcliente.controller;

import br.com.fiap.srvcliente.dto.ClienteDto;
import br.com.fiap.srvcliente.dto.EnderecoDto;
import br.com.fiap.srvcliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteDto> cadastrarCliente (@Valid @RequestBody ClienteDto clienteDto) {
        var cliente = clienteService.cadastrarCliente(clienteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @PutMapping
    public ResponseEntity<ClienteDto> alterarCliente (@Valid @RequestBody ClienteDto clienteDto) {
        var cliente = clienteService.atualizarCliente(clienteDto);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @DeleteMapping
    public ResponseEntity<?> excluirCliente (@Valid @RequestBody ClienteDto clienteDto) {
        boolean excluido = clienteService.excluirCliente(clienteDto);
        if (excluido) {
            return ResponseEntity.status(HttpStatus.OK).body("Cliente excluido com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Falha ao excluir cliente.");
        }
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDto>> listarClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClienteDto> clientes = clienteService.listarClientes(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> buscarClientePorId(@PathVariable UUID id) {
        var cliente = clienteService.buscarClientePorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @GetMapping("/buscaendereco/{cep}")
    public ResponseEntity<EnderecoDto> buscarEnderecoPorCep(@PathVariable String cep) {
        var endereco = clienteService.buscarEnderecoPorCep(cep);
        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }
}
