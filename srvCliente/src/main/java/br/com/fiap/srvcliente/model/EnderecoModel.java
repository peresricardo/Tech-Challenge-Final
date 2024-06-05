package br.com.fiap.srvcliente.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoModel {

    @Column(nullable = false, length = 80)
    private String logradouro;
    @Column(nullable = false, length = 10)
    private String numero;
    @Column(length = 40)
    private String complemento;
    @Column(nullable = false, length = 60)
    private String bairro;
    @Column(nullable = false, length = 60)
    private String cidade;
    @Column(nullable = false, length = 2)
    private String uf;
    @Column(nullable = false, length = 9)
    private String cep;
}
