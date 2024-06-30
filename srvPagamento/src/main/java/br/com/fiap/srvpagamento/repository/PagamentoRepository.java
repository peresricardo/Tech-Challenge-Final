package br.com.fiap.srvpagamento.repository;

import br.com.fiap.srvpagamento.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
