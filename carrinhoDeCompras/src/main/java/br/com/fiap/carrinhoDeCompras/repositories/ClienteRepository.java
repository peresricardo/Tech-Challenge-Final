package br.com.fiap.carrinhoDeCompras.repositories;

import br.com.fiap.carrinhoDeCompras.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
