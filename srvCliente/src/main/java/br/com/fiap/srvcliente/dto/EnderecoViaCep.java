package br.com.fiap.srvcliente.dto;

public record EnderecoViaCep(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf,
        String ibge,
        String gia,
        String ddd,
        String siafi)
{}