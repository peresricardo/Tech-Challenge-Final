package br.com.fiap.srvpagamento.controller;


import br.com.fiap.srvpagamento.model.Pagamento;
import br.com.fiap.srvpagamento.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping("/pagamento")
    public String realizarPagamento(@RequestBody Pagamento pagamento) {
        return pagamentoService.realizarPagamento(pagamento);
    }

}