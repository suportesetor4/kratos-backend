package com.suporte.demo.LAYERS.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {
       
    @GetMapping
    public String getStatus() throws Exception {
        	

        return "O Servidor está ativo!";


        
    }    


}

