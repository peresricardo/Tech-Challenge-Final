package br.com.fiap.carrinhoDeCompras.repositories;

import br.com.fiap.carrinhoDeCompras.entities.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
}
