/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rj.macae.femass.dsi.cookbook.dao;

import br.rj.macae.femass.dsi.cookbook.modelo.Categoria;
import br.rj.macae.femass.dsi.cookbook.modelo.Ingrediente;
import br.rj.macae.femass.dsi.cookbook.modelo.Receita;
import br.rj.macae.femass.dsi.cookbook.modelo.Receita_Ingrediente;
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
public class ReceitaDAO implements IDAO{

    @Override
    public void adicionar(Object o) throws SQLException {
        Receita receita = (Receita) o;
        Connection conn = null;
        //cadastra uma nova receita
        try {
            conn = FabricaConexao.getConexao();            
            String sql = "INSERT INTO public.receita(\n" +
"	nome, preparo, modoservir, categoria)\n" +
"	VALUES (?, ?, ?, ?);";
            PreparedStatement stmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);                    
            stmt.setString(1, receita.getNome());
            stmt.setString(2, receita.getModoPreparo());
            stmt.setString(3, receita.getModoServir());
            stmt.setLong(4, receita.getCategoria().getId());
            stmt.executeUpdate();            
           //recupera o id da nova receita gerado pelo banco
           ResultSet rs = stmt.getGeneratedKeys();  
           rs.next();
           Long id = rs.getLong(1);
           receita.setId(id);
           rs.close();            
           stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Eroo ao tentar cadastrar a receita. \n" + e.getMessage());
        }
        //cadastra os ingredientes da nova receita
        
        if(receita.getIngredientes().size()>0){
            try{
                PreparedStatement stmt=null;
            for(Receita_Ingrediente ingrediente : receita.getIngredientes()){
                
                String sql = "INSERT INTO public.receita_ingrediente(\n" +
"	receita, ingrediente, quantidade, unidade)\n" +
"	VALUES (?, ?, ?, ?);";
            stmt = conn.prepareStatement(sql);                    
            stmt.setLong(1, receita.getId());
            stmt.setLong(2, ingrediente.getIngrediente().getId());
            stmt.setString(4, ingrediente.getUnidade());            
            stmt.setFloat(3, ingrediente.getQuantidade());
            stmt.executeUpdate();   
            
            }
            stmt.close();
            }catch(SQLException ex){
                ex.printStackTrace();
                throw new SQLException("Eroo ao tentar cadastrar ingrediente a nova receita. \n" + ex.getMessage());
            }
        }
        
    }

    @Override
    public void alterar(Object o) throws SQLException {
        Receita receita = (Receita) o;
        Connection conn = null;
        try {
            conn = FabricaConexao.getConexao();

            String sql = "UPDATE public.receita\n" +
"	SET nome=?, preparo=?, modoservir=?, categoria=?\n" +
"	WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // preenche os valores         
            stmt.setString(1, receita.getNome());
            stmt.setString(2, receita.getModoPreparo());
            stmt.setString(3, receita.getModoServir());
            stmt.setLong(4, receita.getCategoria().getId());
            stmt.setLong(5, receita.getId());
            stmt.executeUpdate();
            stmt.close();
            
        } catch (SQLException e) {
            throw new SQLException("Eroo ao tentar alterar a receita. \n" + e.getMessage());
        }
    }

    @Override
    public void excluir(Object o) throws SQLException {
        Receita receita = (Receita) o;
        Connection conn = null;
        try {
            conn = FabricaConexao.getConexao();

            String sql = "DELETE FROM public.receita\n" +
"	WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // preenche os valores                     
            stmt.setLong(1, receita.getId());
            
            stmt.executeUpdate();
            stmt.close();
            
        } catch (SQLException e) {
            throw new SQLException("Eroo ao tentar remover a receita. \n" + e.getMessage());
        }
    }
    
    @Override
    public void excluir(Long id) throws SQLException {
        
        Connection conn = null;
        try {
            conn = FabricaConexao.getConexao();

            String sql = "DELETE FROM public.receita\n" +
"	WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // preenche os valores                     
            stmt.setLong(1, id);
            
            stmt.executeUpdate();
            stmt.close();
            
        } catch (SQLException e) {
            throw new SQLException("Eroo ao tentar remover a receita. \n" + e.getMessage());
        }
    }

    @Override
    public List listarTodos() throws SQLException{
        List lista = new ArrayList();
        Connection conn = null;
        CategoriaDAO dao = new CategoriaDAO();
        try {
            conn = FabricaConexao.getConexao();
            String sql = "SELECT *\n" +
"	FROM public.receita;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Receita r = new Receita();
                r.setNome(rs.getString("nome"));
                r.setModoPreparo(rs.getString("preparo"));                
                r.setModoServir(rs.getString("modoservir"));                
                r.setCategoria((Categoria)dao.listarPorId(rs.getLong("categoria")));
                r.setId(rs.getLong("id"));
                recuperarIngredientes(r);
                lista.add(r);
            }

            rs.close();
            stmt.close();
            
            return lista;
        } catch (SQLException e) {
            throw new SQLException("Eroo ao recuperar a lista de receitas. \n" + e.getMessage());
        }
    }

    @Override
    public Object listarPorId(Long id) throws SQLException {
        Connection conn = null;
        CategoriaDAO dao = new CategoriaDAO();
        try {
            conn = FabricaConexao.getConexao();
            String sql = "SELECT id, nome, preparo, modoservir, categoria\n" +
"	FROM public.receita WHERE id=?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Receita r = new Receita();
            r.setNome(rs.getString("nome"));
            r.setModoPreparo(rs.getString("preparo"));
            r.setModoServir(rs.getString("modoservir"));                
            r.setCategoria((Categoria)dao.listarPorId(rs.getLong("categoria")));
            r.setId(rs.getLong("id"));
            recuperarIngredientes(r);

            rs.close();
            stmt.close();
            
            return r;
        } catch (SQLException e) {
            throw new SQLException("Eroo ao recuperar a receita. \n" + e.getMessage());
        }
    }

    private void recuperarIngredientes(Receita r) throws SQLException{
        Connection conn = null;
        IngredienteDAO dao = new IngredienteDAO();
        try {
            conn = FabricaConexao.getConexao();
            String sql = "SELECT receita, ingrediente, quantidade, unidade\n" +
"	FROM public.receita_ingrediente WHERE receita=?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, r.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){            
                Receita_Ingrediente ingrediente = new Receita_Ingrediente();
                ingrediente.setReceita(r);
                ingrediente.setQuantidade(rs.getFloat("quantidade"));
                ingrediente.setUnidade(rs.getString("unidade"));
                ingrediente.setIngrediente((Ingrediente)dao.listarPorId(rs.getLong("ingrediente")));
                r.setIngrediente(ingrediente);
            }

            rs.close();
            stmt.close();
            
            
        } catch (SQLException e) {
            throw new SQLException("Eroo ao recuperar ingrediente da receita. \n" + e.getMessage());
        }
    }
    
    
}
