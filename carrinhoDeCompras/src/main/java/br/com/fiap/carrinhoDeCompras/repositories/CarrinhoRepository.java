package br.com.fiap.carrinhoDeCompras.repositories;

import br.com.fiap.carrinhoDeCompras.entities.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
}
