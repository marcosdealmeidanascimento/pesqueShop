package com.les.pesqueshop.controller;

import com.les.pesqueshop.dominio.EntidadeDominio;
import com.les.pesqueshop.facade.LogFacade;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
@CrossOrigin(origins = "*")
public class LogController {
    private final LogFacade logFacade;

    public LogController() throws SQLException {
        this.logFacade = new LogFacade();
    }

    @GetMapping("/")
    public List<EntidadeDominio> getAll(@PathParam("limit") int limit, @PathParam("offset") int offset) throws SQLException {
        return logFacade.getAll(limit, offset);
    }
}
