/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rj.macae.femass.receitas.controle;

import br.rj.macae.femass.receitas.dao.CategoriaDAO;

import br.rj.macae.femass.receitas.modelo.Categoria;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JList;
/**
 *
 * @author anamm
 */
public class CategoriaControle {
    public void gravar(Categoria c, JList listaReceitas) throws SQLException{
        if(c.getId()<=0){
            cadastrar(c, listaReceitas);
        }else{
            alterar(c, listaReceitas);
        }
    }
    private void cadastrar(Categoria c, JList lista) throws SQLException{
        CategoriaDAO dao = new CategoriaDAO();
        dao.cadastrar(c);
        List categorias = dao.listarTodos();
        lista.setListData(categorias.toArray());
    }
    private void alterar(Categoria c, JList lista) throws SQLException{
        CategoriaDAO dao = new CategoriaDAO();
        dao.alterar(c);
        List categorias = dao.listarTodos();
        lista.setListData(categorias.toArray());
    }
    public void atualizarListaCategorias(JList lista) throws SQLException{
        CategoriaDAO dao = new CategoriaDAO();        
        List categorias = dao.listarTodos();
        lista.setListData(categorias.toArray());
    }
    
}
