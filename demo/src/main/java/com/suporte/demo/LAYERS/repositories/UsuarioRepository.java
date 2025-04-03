package com.suporte.demo.LAYERS.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suporte.demo.LAYERS.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

  Usuario getUsuarioPorLogin(String login);
  
}
