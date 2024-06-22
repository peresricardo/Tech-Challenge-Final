package br.com.fiap.srvpagamento.service;

import br.com.fiap.srvpagamento.model.Pagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PagamentoService {

    @Autowired
    private RestTemplate restTemplate;

    public String realizarPagamento(Pagamento pagamento) {
        return "Pagamento realizado com sucesso";
    }
}
