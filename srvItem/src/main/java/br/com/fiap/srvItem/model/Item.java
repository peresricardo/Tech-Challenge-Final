package br.com.fiap.srvItem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "tb_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;
    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria;
    @Column(name = "preco", nullable = false)
    private BigDecimal preco;
    @Column(name = "url_imagem", nullable = true, length = 100)
    private String urlImagem;
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;


}
