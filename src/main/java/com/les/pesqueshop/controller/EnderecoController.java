package com.les.pesqueshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.les.pesqueshop.facade.EnderecoFacade;
import com.les.pesqueshop.dominio.Endereco;
import com.les.pesqueshop.dominio.EntidadeDominio;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/enderecos")
@CrossOrigin(origins = "*")
public class EnderecoController {
    private final EnderecoFacade enderecoFacade;
    public EnderecoController() throws SQLException {
        this.enderecoFacade = new EnderecoFacade();
    }

    @GetMapping("/")
    public ResponseEntity<List<EntidadeDominio>> getAll(@PathParam("limit") int limit, @PathParam("offset") int offset) throws SQLException {
        try {
            return ResponseEntity.ok(enderecoFacade.getAll(limit, offset));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntidadeDominio> get(@PathVariable int id) throws SQLException {
        try {
            return ResponseEntity.ok(enderecoFacade.get(id));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<EntidadeDominio>> getEnderecosCliente(@PathVariable int id) throws SQLException {
        try {
            return ResponseEntity.ok(enderecoFacade.getEnderecosCliente(id));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<List<EntidadeDominio>> filter(@RequestBody Endereco endereco, @PathParam("limit") int limit, @PathParam("offset") int offset) throws SQLException {
        try {
            return ResponseEntity.ok(enderecoFacade.filter(endereco, limit, offset));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> save(@RequestBody Endereco endereco) throws SQLException {
        try {
            return ResponseEntity.ok(enderecoFacade.save(endereco));
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Endereco endereco) throws SQLException {
        try {
            return ResponseEntity.ok(enderecoFacade.update(id, endereco));
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntidadeDominio> delete(@PathVariable int id) throws SQLException {
        try {
            return ResponseEntity.ok(enderecoFacade.delete(id));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
