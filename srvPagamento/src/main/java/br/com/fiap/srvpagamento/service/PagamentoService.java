package br.com.fiap.srvpagamento.service;

import br.com.fiap.srvpagamento.dto.CarrinhoDTO;
import br.com.fiap.srvpagamento.dto.ResumoPagamentoDTO;
import br.com.fiap.srvpagamento.model.Pagamento;
import br.com.fiap.srvpagamento.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class PagamentoService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    private static final String CARRINHO_SERVICE_URL = "http://localhost:9515/carrinhos/porId/{idCarrinho}";

    public ResumoPagamentoDTO realizarPagamento(Pagamento pagamento, Long idCarrinho) {
        // Corrigindo a construção da URL
        CarrinhoDTO carrinho = restTemplate.getForObject(CARRINHO_SERVICE_URL, CarrinhoDTO.class, idCarrinho);
        carrinho.setTotalPedido(BigDecimal.valueOf(100.0));
        if (carrinho == null || carrinho.getTotalPedido().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Carrinho inválido ou valor de pagamento inválido");
        }

        pagamento.setValor(carrinho.getTotalPedido().doubleValue());

        pagamentoRepository.save(pagamento);

        ResumoPagamentoDTO resumoPagamento = new ResumoPagamentoDTO(pagamento, carrinho.getItens(), carrinho.getTotalPedido());

        return resumoPagamento;
    }
}
