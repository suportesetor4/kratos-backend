package com.suporte.demo.LAYERS.controllers;

import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/Equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoService equipamentoService;

    
}
