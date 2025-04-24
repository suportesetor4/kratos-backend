package com.suporte.demo.LAYERS.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suporte.demo.LAYERS.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

  Usuario getUsuarioPorLogin(String login);

  boolean existsByLogin(String login);

  Optional<Usuario> findByLogin(String login);

  Optional<Usuario> findByEmail(String email);

}


