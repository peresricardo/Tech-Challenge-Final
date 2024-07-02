package br.com.fiap.carrinhoDeCompras.service.impl;

import br.com.fiap.carrinhoDeCompras.entities.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.UUID;

@FeignClient(name = "cliente-service", url = "http://srv-cliente:9510/clientes")
public interface ClienteEndpointService {

    @GetMapping("/{id}")
    ResponseEntity<Cliente> buscarClientePorId(@PathVariable UUID id);
}
