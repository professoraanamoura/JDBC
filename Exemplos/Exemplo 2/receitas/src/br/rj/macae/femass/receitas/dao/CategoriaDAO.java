/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rj.macae.femass.receitas.dao;

import br.rj.macae.femass.receitas.modelo.Categoria;
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
public class CategoriaDAO implements IDAO{

    @Override
    public void cadastrar(Object o) throws SQLException {
        Categoria categoria = (Categoria) o;
        Connection conn = null;
        try {
            conn = FabricaConexao.getConexao();

            String sql = "insert into categoria (nome,descricao) "
                    + "values (?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // preenche os valores         
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            
            
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new SQLException("Eroo ao tentar cadastrar a categoria. \n" + e.getMessage());
        }
    }

    @Override
    public void alterar(Object o) throws SQLException {
        Categoria categoria = (Categoria) o;
        Connection conn = null;
        try {
            conn = FabricaConexao.getConexao();

            String sql = "update categoria  set "
                    + "nome = ?, "
                    + "descricao = ?,"                    
                    + "where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // preenche os valores         
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());            
            stmt.setInt(3, categoria.getId());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new SQLException("Eroo ao tentar alterar a categoria. \n" + e.getMessage());
        }
    }

    @Override
    public void excluir(Object o) throws SQLException {
        Categoria categoria = (Categoria) o;
        Connection conn = null;
        try {
            conn = FabricaConexao.getConexao();

            String sql = "delete from categoria "
                    + "where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // preenche os valores                     
            stmt.setInt(1, categoria.getId());
            //executa o sql (DML) para atualizar o banco. Pode ser um insert, update ou delete
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new SQLException("Eroo ao tentar remover a categoria. \n" + e.getMessage());
        }
    }

    @Override
    public List listarTodos() throws SQLException {
        List lista = new ArrayList();
        Connection conn = null;
        try {
            conn = FabricaConexao.getConexao();
            String sql = "select * from categoria";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setNome(rs.getString("nome"));
                categoria.setDescricao(rs.getString("descricao"));                
                categoria.setId(rs.getInt("id"));
                lista.add(categoria);
            }

            rs.close();
            stmt.close();
            conn.close();
            return lista;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Eroo ao recuperar a lista de categorias. \n" + e.getMessage());
        }
    }

    @Override
    public Object listarPorId(int id) throws SQLException {
        Connection conn = null;
        try {
            conn = FabricaConexao.getConexao();
            String sql = "select * from categoria where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Categoria categoria = new Categoria();
            categoria.setNome(rs.getString("nome"));
            categoria.setDescricao(rs.getString("descricao"));            
            categoria.setId(rs.getInt("id"));
            

            rs.close();
            stmt.close();
            conn.close();
            return categoria;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Eroo ao recuperar a categoria. \n" + e.getMessage());
        }
    }
    
}
