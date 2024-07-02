package br.com.fiap.carrinhoDeCompras.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tb_cliente")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idCliente;
    @Column(nullable = false, length = 60)
    private String nome;
    @Column(length = 80)
    private String email;

    @Embedded
    private Endereco endereco;
}
