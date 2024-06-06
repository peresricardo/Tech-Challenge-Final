package br.com.fiap.srvcliente.model;

import br.com.fiap.srvcliente.dto.ClienteDto;
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
public class ClienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idCliente;
    @Column(nullable = false, length = 60)
    private String nome;
    @Column(length = 80)
    private String email;

    @Embedded
    private EnderecoModel endereco;

    public ClienteModel(ClienteDto clienteDto) {
        this.nome = clienteDto.nome();
        this.email = clienteDto.email();
        this.endereco = new EnderecoModel();
        this.endereco.setLogradouro(clienteDto.endereco().logradouro());
        this.endereco.setNumero(clienteDto.endereco().numero());
        this.endereco.setComplemento(clienteDto.endereco().complemento());
        this.endereco.setBairro(clienteDto.endereco().bairro());
        this.endereco.setCidade(clienteDto.endereco().cidade());
        this.endereco.setUf(clienteDto.endereco().uf());
        this.endereco.setCep(clienteDto.endereco().cep());
    }

}
