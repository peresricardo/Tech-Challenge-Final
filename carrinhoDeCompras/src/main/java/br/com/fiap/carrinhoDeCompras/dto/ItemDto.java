package br.com.fiap.carrinhoDeCompras.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Long id;
    private String nome;
    private String descricao;
    private Integer quantidadeEstoque;
    private BigDecimal preco;

}
