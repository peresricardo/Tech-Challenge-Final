package br.com.fiap.srvcliente.util;

import br.com.fiap.srvcliente.model.ClienteModel;
import br.com.fiap.srvcliente.model.EnderecoModel;

public abstract class ClienteHelper {
    public static ClienteModel gerarRegistro() {
        var endereco = new EnderecoModel();
        endereco.setLogradouro("Endereco Automatizado");
        endereco.setNumero("1010");
        endereco.setUf("SP");
        endereco.setCep("00000-000");
        endereco.setBairro("Bairro Auto");
        endereco.setCidade("Cidade Auto");
        endereco.setComplemento("Comple");
        var entity = ClienteModel.builder()
                .nome("Cliente Automatizado")
                .email("clienteautomatizado@gmail.com")
                .endereco(endereco)
                .build();
        return entity;
    }
}
