package br.com.fiap.carrinhoDeCompras.service;

import br.com.fiap.carrinhoDeCompras.entities.Carrinho;

import java.util.List;

public interface CarrinhoService {

    List<Carrinho> listarCarrinhos();

    Carrinho buscarCarrinhoPorId(Long id);

    Carrinho criarCarrinho(Carrinho carrinho);

    Carrinho atualizarCarrinho(Long id, Carrinho carrinhoNovo);

    boolean deletarCarrinho(Long id);

}
