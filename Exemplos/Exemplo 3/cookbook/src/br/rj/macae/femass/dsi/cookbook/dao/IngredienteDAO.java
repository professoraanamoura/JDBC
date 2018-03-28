/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rj.macae.femass.dsi.cookbook.dao;


import br.rj.macae.femass.dsi.cookbook.modelo.Ingrediente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anamm
 */
public class IngredienteDAO implements IDAO{

    @Override
    public void adicionar(Object o) throws SQLException {
        Ingrediente ingrediente = (Ingrediente) o;
        Connection conn = null;
        try {
            conn = FabricaConexao.getConexao();

            String sql = "INSERT INTO public.ingrediente(\n" +
"	nome, descricao)\n" +
"	VALUES (?, ?);";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // preenche os valores         
            stmt.setString(1, ingrediente.getNome());
            stmt.setString(2, ingrediente.getDescricao());
            
            
            stmt.executeUpdate();
            stmt.close();
            
        } catch (SQLException e) {
            throw new SQLException("Eroo ao tentar cadastrar a ingrediente. \n" + e.getMessage());
        }
    }

    @Override
    public void alterar(Object o) throws SQLException {
        Ingrediente ingrediente = (Ingrediente) o;
        Connection conn = null;
        try {
            conn = FabricaConexao.getConexao();

            String sql = "UPDATE public.ingrediente\n" +
"	SET nome=?, descricao=?\n" +
"	WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // preenche os valores         
            stmt.setString(1, ingrediente.getNome());
            stmt.setString(2, ingrediente.getDescricao());
            stmt.setLong(3, ingrediente.getId());
            stmt.executeUpdate();
            stmt.close();
            
        } catch (SQLException e) {
            throw new SQLException("Eroo ao tentar alterar a ingrediente. \n" + e.getMessage());
        }
    }

    @Override
    public void excluir(Object o) throws SQLException {
        Ingrediente ingrediente = (Ingrediente) o;
        Connection conn = null;
        try {
            conn = FabricaConexao.getConexao();

            String sql = "DELETE FROM public.ingrediente\n" +
"	WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // preenche os valores                     
            stmt.setLong(1, ingrediente.getId());
            
            stmt.executeUpdate();
            stmt.close();
            
        } catch (SQLException e) {
            throw new SQLException("Eroo ao tentar remover a ingrediente. \n" + e.getMessage());
        }
    }
    
    @Override
    public void excluir(Long id) throws SQLException {
        
        Connection conn = null;
        try {
            conn = FabricaConexao.getConexao();

            String sql = "DELETE FROM public.ingrediente\n" +
"	WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // preenche os valores                     
            stmt.setLong(1, id);
            
            stmt.executeUpdate();
            stmt.close();
            
        } catch (SQLException e) {
            throw new SQLException("Eroo ao tentar remover a ingrediente. \n" + e.getMessage());
        }
    }

    @Override
    public List listarTodos() throws SQLException{
        List lista = new ArrayList();
        Connection conn = null;
        try {
            conn = FabricaConexao.getConexao();
            String sql = "SELECT id, nome, descricao\n" +
"	FROM public.ingrediente;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Ingrediente c = new Ingrediente();
                c.setNome(rs.getString("nome"));
                c.setDescricao(rs.getString("descricao"));                
                c.setId(rs.getLong("id"));
                lista.add(c);
            }

            rs.close();
            stmt.close();
            
            return lista;
        } catch (SQLException e) {
            throw new SQLException("Eroo ao recuperar a lista de ingredientes. \n" + e.getMessage());
        }
    }

    @Override
    public Object listarPorId(Long id) throws SQLException {
        Connection conn = null;
        try {
            conn = FabricaConexao.getConexao();
            String sql = "SELECT id, nome, descricao\n" +
"	FROM public.ingrediente WHERE id=?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Ingrediente c = new Ingrediente();
            c.setNome(rs.getString("nome"));
            c.setDescricao(rs.getString("descricao"));
            c.setId(rs.getLong("id"));
            

            rs.close();
            stmt.close();
            
            return c;
        } catch (SQLException e) {
            throw new SQLException("Eroo ao recuperar a ingrediente. \n" + e.getMessage());
        }
    }
    
    
}
