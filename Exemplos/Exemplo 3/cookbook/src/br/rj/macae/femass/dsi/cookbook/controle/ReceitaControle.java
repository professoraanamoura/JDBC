/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rj.macae.femass.dsi.cookbook.controle;

import br.rj.macae.femass.dsi.cookbook.dao.CategoriaDAO;
import br.rj.macae.femass.dsi.cookbook.dao.IngredienteDAO;
import br.rj.macae.femass.dsi.cookbook.dao.ReceitaDAO;
import br.rj.macae.femass.dsi.cookbook.modelo.Receita;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author anamm
 */
public class ReceitaControle {
    public void gravar(Receita i)throws SQLException{
        
        ReceitaDAO dao = new ReceitaDAO();
        try{
        if(i.getId()==null || i.getId()<=0)
            dao.adicionar(i);
        else
            dao.alterar(i);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Erro ao salvar as informações: \n"+ex.getMessage());
            
        }
    }
    public void excluir(Receita i)throws SQLException{
        if(JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir este ingrediente?")==JOptionPane.YES_OPTION){
            ReceitaDAO dao = new ReceitaDAO();    
            dao.excluir(i);
        }
        
    }
    public void excluir(Long id)throws SQLException{
        if(JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir este ingrediente?")==JOptionPane.YES_OPTION){
            ReceitaDAO dao = new ReceitaDAO();    
            dao.excluir(id);
        }
    }
    public void atualizarLista(JTable tabela) throws SQLException{
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        //limpa as linhas da tabela.
        for(int i=0; i<model.getRowCount(); i++)
            model.removeRow(i);
        
        ReceitaDAO dao = new ReceitaDAO();
        List categorias = dao.listarTodos();
        //Adiciona as linhas
        for (Object o : categorias){
            Receita r = (Receita) o;
            model.addRow(new Object[]{r.getId(),r.getNome(),r.getCategoria().getNome(),r.getModoPreparo()});
        }
        
    }

    public Receita getReceitaPorId(Long id) throws SQLException {
        ReceitaDAO dao = new ReceitaDAO();
        Receita r = (Receita)dao.listarPorId(id);
        return r;       
    }

    public List listarCategorias() throws SQLException {
        CategoriaDAO dao = new CategoriaDAO();
        return dao.listarTodos();
    }

    public List listarIngredientes() throws SQLException {
        IngredienteDAO dao = new IngredienteDAO();
        return dao.listarTodos();
    }
}
