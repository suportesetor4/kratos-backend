package com.suporte.demo.LAYERS.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Equipamento {
    
    public enum TipoEnum{
        COMPUTADOR,
        MONITOR,
        NOTEBOOK,
        NOBREAK_ESTABILIZADOR,
        PROJETOR,
        ROTEADOR,
        SWITCH,
        OUTRO
    }   

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String tombamento;

    @Enumerated(EnumType.STRING)
    private TipoEnum tipo;


    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
     this.id = id;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getTombamento(){
        return tombamento;
    }

    public void setTombamento(String tombamento){
        this.tombamento = tombamento;
    }

    public TipoEnum getTipo(){
        return tipo;
    }
    public void setTipo(TipoEnum tipo){
        this.tipo = tipo;
    }

}



