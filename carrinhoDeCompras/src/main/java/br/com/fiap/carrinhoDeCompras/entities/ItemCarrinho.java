package br.com.fiap.carrinhoDeCompras.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "item_carrinho")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID idItem;
    private Integer quantidade;

}
