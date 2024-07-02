package br.com.fiap.srvpagamento.controller;


import br.com.fiap.srvpagamento.dto.ResumoPagamentoDTO;
import br.com.fiap.srvpagamento.model.Pagamento;
import br.com.fiap.srvpagamento.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/pagamento")
    public ResumoPagamentoDTO realizarPagamento(@RequestBody Pagamento pagamento) {
        return pagamentoService.realizarPagamento(pagamento, pagamento.getIdCarrinho());
    }

}