package br.com.fiap.srvpagamento.dto;

import br.com.fiap.srvpagamento.model.Pagamento;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class ResumoPagamentoDTO {

    private Pagamento pagamento;
    private List<ItemCarrinhoDTO> itens;
    private BigDecimal valorTotal;

    public ResumoPagamentoDTO(Pagamento pagamento, List<ItemCarrinhoDTO> itens, BigDecimal valorTotal) {
        this.pagamento = pagamento;
        this.itens = itens;
        this.valorTotal = valorTotal;
    }

    public BigDecimal getTotal() {
        return valorTotal;
    }
}