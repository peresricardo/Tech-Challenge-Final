package br.com.fiap.srvItem.repository;

import br.com.fiap.srvItem.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
}
