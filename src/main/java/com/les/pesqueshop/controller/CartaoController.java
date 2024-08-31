package com.les.pesqueshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.les.pesqueshop.facade.CartaoFacade;
import com.les.pesqueshop.dominio.Cartao;
import com.les.pesqueshop.dominio.EntidadeDominio;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cartoes")
@CrossOrigin(origins = "*")
public class CartaoController {
    private final CartaoFacade cartaoFacade;

    public CartaoController() throws SQLException {
        this.cartaoFacade = new CartaoFacade();
    }

    @GetMapping("/")
    public ResponseEntity<List<EntidadeDominio>> getAll(@PathParam("limit") int limit, @PathParam("offset") int offset) throws SQLException {
        try {
            return ResponseEntity.ok(cartaoFacade.getAll(limit, offset));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntidadeDominio> get(@PathVariable int id) throws SQLException {
        try {
            return ResponseEntity.ok(cartaoFacade.get(id));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<EntidadeDominio>> getCartoesCliente(@PathVariable int id) throws SQLException {
        try {
            return ResponseEntity.ok(cartaoFacade.getCartoesCliente(id));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<List<EntidadeDominio>> filter(@RequestBody Cartao cartao, @PathParam("limit") int limit, @PathParam("offset") int offset) throws SQLException {
        try {
            return ResponseEntity.ok(cartaoFacade.filter(cartao, limit, offset));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> save(@RequestBody Cartao cartao) throws SQLException {
        try {
            return ResponseEntity.ok(cartaoFacade.save(cartao));
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Cartao cartao) throws SQLException {
        try {
            return ResponseEntity.ok(cartaoFacade.update(id, cartao));
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntidadeDominio> delete(@PathVariable int id) throws SQLException {
        try {
            return ResponseEntity.ok(cartaoFacade.delete(id));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
