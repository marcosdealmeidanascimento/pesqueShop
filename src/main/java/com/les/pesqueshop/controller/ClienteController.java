package com.les.pesqueshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.les.pesqueshop.dao.ClienteDAO;
import com.les.pesqueshop.dominio.Cliente;
import com.les.pesqueshop.dominio.EntidadeDominio;
import com.les.pesqueshop.facade.ClienteFacade;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
    private final ClienteFacade clienteFacade;

    public ClienteController() throws SQLException, Exception {
        this.clienteFacade = new ClienteFacade();
    }

    @GetMapping("/")
    public ResponseEntity<List<EntidadeDominio>> getAll(@PathParam("limit") int limit, @PathParam("offset") int offset) throws SQLException {
        try {
            return ResponseEntity.ok(clienteFacade.getAll(limit, offset));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntidadeDominio> get(@PathVariable int id) throws SQLException {
        try {
            return ResponseEntity.ok(clienteFacade.get(id));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<List<EntidadeDominio>> filter(@RequestBody Cliente cliente, @PathParam("limit") int limit, @PathParam("offset") int offset) throws SQLException {
        try {
            return ResponseEntity.ok(clienteFacade.filter(cliente, limit, offset));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> save(@RequestBody Cliente cliente) throws SQLException {
        try {
            return ResponseEntity.ok(clienteFacade.save(cliente));
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Cliente cliente) throws SQLException {
        try {
            return ResponseEntity.ok(clienteFacade.update(id, cliente));
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntidadeDominio> delete(@PathVariable int id) throws SQLException {
        try {
            return ResponseEntity.ok(clienteFacade.delete(id));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
