package br.com.fiap.srvItem.controller;

import br.com.fiap.srvItem.model.Item;
import br.com.fiap.srvItem.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/itens")
@Tag(name = "Gestão de Itens", description = "Endpoint para manutenção no cadastro de itens")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    @Operation(summary = "Obtem uma lista de todos os itens cadastrados", method = "GET")
    public List<Item> listarItens() {
        return itemService.listarItens();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtem dados de um item existente por id", method = "GET")
    public ResponseEntity<Item> buscarItemPorId(@PathVariable UUID id) {
        var item = itemService.buscarItemPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @PostMapping
    @Operation(summary = "Efetua a inclusão de um novo Item", method = "POST")
    public ResponseEntity<Item> cadastrarItem(@Valid @RequestBody Item item) {
        var itemNovo = itemService.cadastrarItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemNovo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Efetua a alteração de um item existente", method = "PUT")
    public ResponseEntity<Item> atualizarItem(@PathVariable UUID id, @Valid @RequestBody Item itemAtualizado) {
        var item = itemService.atualizarItem(id, itemAtualizado);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Efetua a exclusão de um item existente", method = "DELETE")
    public ResponseEntity<Void> deletarItem(@PathVariable UUID id) {
        itemService.deletarItem(id);
        return ResponseEntity.noContent().build();
    }
}
