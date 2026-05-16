package controle;

import java.util.ArrayList;
import modelo.Tipo_usuario;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tipo_usuarioControle {

    public Tipo_usuarioControle() {
    }

    public ArrayList<Tipo_usuario> consultarTipos() {
        ArrayList<Tipo_usuario> lista = new ArrayList<>();
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT * FROM tipo_usuario";
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);

            while (resultado.next()) {
                Tipo_usuario tipo = new Tipo_usuario();
                tipo.setID_TipoUsuario(resultado.getInt("ID_TipoUsuario"));
                tipo.setDescricao_usuario(resultado.getString("descricao_usuario"));
                lista.add(tipo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Tipo_usuarioControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public Tipo_usuario consultarID(int ID_TipoUsuario) {
        Tipo_usuario tipo = new Tipo_usuario();
        String consulta = "SELECT * FROM tipo_usuario WHERE ID_TipoUsuario = ?";
        
        // O try abaixo abre a conexão e o PreparedStatement. 
        // Ao chegar no fim do bloco }, o Java FECHA tudo automaticamente.
        try (Connection conn = new ConexaoMySQL().conectar();
             PreparedStatement stm = conn.prepareStatement(consulta)) {
            
            if (conn == null) return tipo;

            stm.setInt(1, ID_TipoUsuario);
            try (ResultSet resultado = stm.executeQuery()) {
                if (resultado.next()) {
                    tipo.setID_TipoUsuario(resultado.getInt("ID_TipoUsuario"));
                    tipo.setDescricao_usuario(resultado.getString("descricao_usuario"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Tipo_usuarioControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipo;
    }

    public String inserirTipo(Tipo_usuario tipo) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "INSERT INTO tipo_usuario (descricao_usuario) VALUES (?)";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, tipo.getDescricao_usuario());
            stm.executeUpdate();
            resultado = "inserido";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }

    public String alterarTipo(Tipo_usuario tipo) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "UPDATE tipo_usuario SET descricao_usuario = ? WHERE ID_TipoUsuario = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, tipo.getDescricao_usuario());
            stm.setInt(2, tipo.getID_TipoUsuario());
            stm.executeUpdate();
            resultado = "alterado";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }

    public String deletarTipo(int id) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "DELETE FROM tipo_usuario WHERE ID_TipoUsuario = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, id);
            stm.executeUpdate();
            resultado = "removido";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }

    public static void main(String[] args) {
        Tipo_usuarioControle tc = new Tipo_usuarioControle();

        // Exemplo: Inserir
        // Tipo_usuario novo = new Tipo_usuario(0, "Administrador");
        // tc.inserirTipo(novo);

        // Listar todos
        for (Tipo_usuario t : tc.consultarTipos()) {
            System.out.println(t.toString());
        }
    }
}