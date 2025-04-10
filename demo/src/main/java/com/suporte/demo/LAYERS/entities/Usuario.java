package com.suporte.demo.LAYERS.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.suporte.demo.LAYERS.entities.enums.Papel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

@NamedQuery(name = "Usuario.getUsuarioPorLogin", query = "SELECT u FROM Usuario u WHERE u.login=:login")

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefone;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // A senha será apenas aceitada na entrada, mas não será mostrada na resposta
    @Column(nullable = false, updatable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, updatable = false)
    private Papel papel;

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }


    public String getNome(){
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getTelefone(){
        return telefone;
    }

    public void setTelefone(String telefone){
        this.telefone = telefone;
    }


    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

}

