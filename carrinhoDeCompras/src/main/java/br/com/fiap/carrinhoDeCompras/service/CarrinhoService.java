package br.com.fiap.carrinhoDeCompras.service;

import br.com.fiap.carrinhoDeCompras.entities.Carrinho;

import java.util.List;

public interface CarrinhoService {

    List<Carrinho> listarItens();

    Carrinho criarCarrinho(Carrinho carrinho, Long idCliente);

    Carrinho atualizarCarrinho(Long id, Carrinho carrinhoNovo);

    boolean deletarCarrinho(Long id);

}
