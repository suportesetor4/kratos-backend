package com.suporte.demo.LAYERS.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import com.suporte.demo.LAYERS.entities.Equipamento;
import com.suporte.demo.LAYERS.repositories.EquipamentoRepository;

@Service
public class EquipamentoService {


@Autowired 
EquipamentoRepository equipamentoRepository;

public List<Equipamento> listarTodos() {
    return equipamentoRepository.findAll();
}

public Equipamento salvar(Equipamento equipamento) {

    if(equipamento.getTombamento() == null || equipamento.getTombamento().isBlank()){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tombamento está nulo ou branco");
    }
    if(equipamento.getNome() == null || equipamento.getNome().isBlank()){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome está nulo ou branco");
    }
    Optional<Equipamento>equipamentoExistente = equipamentoRepository.findByTombamento(equipamento.getTombamento());
    if(equipamentoExistente.isPresent()){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"O equipamento já existe");
    }

    return equipamentoRepository.save(equipamento);
}



}
