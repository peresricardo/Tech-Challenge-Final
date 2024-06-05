package br.com.fiap.srvcliente.dto;

import br.com.fiap.srvcliente.model.ClienteModel;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record ClienteDto(
        UUID id,
        @NotBlank(message = "Nome é preenchimento obrigatório")
        @Length(min = 5, max = 60, message = "Nome dever ser preenchido entre 5 a 60 caracteres")
        String nome,
        @Length(min = 5, max = 80, message = "Email dever ser preenchido entre 5 a 80 caracteres")
        String email,
        EnderecoDto endereco
) {
    public static ClienteDto fromEntity(ClienteModel cliente) {
        return new ClienteDto(
                cliente.getIdCliente(),
                cliente.getNome(),
                cliente.getEmail(),
                new EnderecoDto(
                        cliente.getEndereco().getLogradouro(),
                        cliente.getEndereco().getNumero(),
                        cliente.getEndereco().getComplemento(),
                        cliente.getEndereco().getBairro(),
                        cliente.getEndereco().getCidade(),
                        cliente.getEndereco().getUf(),
                        cliente.getEndereco().getCep())
        );
    }
}
