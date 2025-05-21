package com.suporte.demo.LAYERS.controllers;

import org.springframework.stereotype.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suporte.demo.LAYERS.services.EquipamentoService;
import com.suporte.demo.LAYERS.entities.Equipamento;


@RestController
@RequestMapping("/equipamento")
public class EquipamentoController {

    @Autowired
    private EquipamentoService equipamentoService;



    @GetMapping
    @Secured(value = { "ROLE_ADMIN","ROLE_CLIENTE" })
     public ResponseEntity<List<Equipamento>> listarTodos() {
        List<Equipamento> equipamentos = equipamentoService.listarTodos();
        return ResponseEntity.ok(equipamentos);
    }

    @PostMapping
    @Secured(value = { "ROLE_ADMIN","ROLE_CLIENTE" })
    public ResponseEntity<Equipamento> criarcliente(@RequestBody Equipamento equipamento ){
        Equipamento equipamentoCriado = equipamentoService.salvar(equipamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(equipamentoCriado);
    }

    
}
