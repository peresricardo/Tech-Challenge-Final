package br.com.fiap.carrinhoDeCompras.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_carrinho")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrinho;
//    @ManyToOne
//    @JoinColumn(name = "id_cliente")
//    private Cliente cliente;
    @OneToMany
    private List<ItemCarrinho> itens;

}
