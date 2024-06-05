package br.com.fiap.srvcliente.service.serviceImpl;

import br.com.fiap.srvcliente.dto.EnderecoViaCep;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${appviacep.receive.name}", url = "${appviacep.receive.url}")
public interface ViacepClient {

    @GetMapping("{cep}/json")
    EnderecoViaCep getEndereco(@PathVariable String cep);
}
