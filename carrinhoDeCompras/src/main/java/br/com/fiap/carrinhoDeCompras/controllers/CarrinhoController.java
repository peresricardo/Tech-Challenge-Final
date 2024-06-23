package br.com.fiap.carrinhoDeCompras.controllers;

import br.com.fiap.carrinhoDeCompras.entities.Carrinho;
import br.com.fiap.carrinhoDeCompras.service.CarrinhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carrinho")
@Tag(name = "Gestão do Carrinho de Compras", description = "Controller para manutenção na Gestão do Carrinho de Compras")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping(value = "/listarItens", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista todos os itens no carrinho", method = "GET")
    public ResponseEntity<List<Carrinho>> listarItensNoCarrinho() {
        return new ResponseEntity<>(carrinhoService.listarItens(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Criar um novo Carrinho", method = "POST")
    public ResponseEntity<Carrinho> criarCarrinho(@RequestBody Carrinho carrinho, @RequestParam Long idCliente) {
        Carrinho carrinhoResponse = carrinhoService.criarCarrinho(carrinho, idCliente);
        return new ResponseEntity<>(carrinhoResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um carrinho cadastrado", method = "PUT")
    public ResponseEntity<Carrinho> atualizarCarrinho(@PathVariable Long id, @RequestBody Carrinho carrinhoNovo) {
        Carrinho carrinhoNovoResponse = carrinhoService.atualizarCarrinho(id, carrinhoNovo);
        return new ResponseEntity<>(carrinhoNovoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um carrinho cadastrado", method = "DELETE")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carrinhoService.deletarCarrinho(id);
        return ResponseEntity.noContent().build();
    }

}
