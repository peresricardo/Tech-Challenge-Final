package br.com.fiap.carrinhoDeCompras.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "item_carrinho")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemCarrinho {

    @Id
    private Long idItem;
    private String nomeItem;
    private Integer quantidade;
    private BigDecimal precoUnitario;

}
