/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rj.macae.femass.receitas.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author anamm
 */
public class Categoria {
    private int id;
    private String nome;
    private String descricao;
    private List<Receita> receitas = new ArrayList<Receita>();

    public List<Receita> getReceitas() {
        return Collections.unmodifiableList(receitas);
    }

    public void setReceita(Receita receita) {
        if(receita.getCategoria().equals(this)){
            if(!this.receitas.contains(receita)){this.receitas.add(receita);}
        }else{
            receita.setCategoria(this);
        }
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Categoria other = (Categoria) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return " " + id + " - " + nome + "  (" + descricao + ')';
    }
    
    
    
    
}
