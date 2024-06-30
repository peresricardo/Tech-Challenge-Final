package br.com.fiap.carrinhoDeCompras.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private UUID id;
    private String nome;
    private String descricao;
    private String categoria;
    private BigDecimal preco;
    private String urlImagem;
    private Integer quantidade;

}
