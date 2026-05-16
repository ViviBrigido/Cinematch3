package controle;

import java.util.ArrayList;
import modelo.Plano_filme;
import modelo.Filme;
import modelo.Plano;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Plano_filmeControle {

    public ArrayList<Plano_filme> consultarPlano_filme() {
        ArrayList<Plano_filme> lista = new ArrayList();
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
       
        try {
            conn = new ConexaoMySQL().conectar();
            stm = conn.createStatement();
            rs = stm.executeQuery("SELECT * FROM Plano_filme");
           
            while (rs.next()) {
                Plano_filme pf = new Plano_filme();
                pf.setID_Plano_Filme(rs.getInt("ID_Plano_Filme"));
                pf.setID_Filme(new FilmeControle().consultarFilmeCodigo(rs.getInt("ID_Filme")));
                pf.setID_Plano(new PlanoControle().consultarPlanoCodigo(rs.getInt("ID_Plano")));
                lista.add(pf);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Plano_filmeControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return lista;
    }

    public Plano_filme consultarID_Plano_filme(int id) {
        Plano_filme pf = new Plano_filme();
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
       
        try {
            conn = new ConexaoMySQL().conectar();
            stm = conn.createStatement();
            rs = stm.executeQuery("SELECT * FROM Plano_filme WHERE ID_Plano_Filme = " + id);
           
            if (rs.next()) {
                pf.setID_Plano_Filme(rs.getInt("ID_Plano_Filme"));
                pf.setID_Filme(new FilmeControle().consultarFilmeCodigo(rs.getInt("ID_Filme")));
                pf.setID_Plano(new PlanoControle().consultarPlanoCodigo(rs.getInt("ID_Plano")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Plano_filmeControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return pf;
    }

    public String inserirPlano_filme(Plano_filme pf) {
        Connection conn = null;
        PreparedStatement stm = null;
       
        try {
            conn = new ConexaoMySQL().conectar();
            stm = conn.prepareStatement("INSERT INTO Plano_filme (ID_Filme, ID_Plano) VALUES (?,?)");
            stm.setInt(1, pf.getID_Filme().getID_Filme());
            stm.setInt(2, pf.getID_Plano().getID_plano());
            stm.executeUpdate();
            return "inserido";
        } catch (SQLException ex) {
            return ex.getSQLState();
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    public String alterarPlano_filme(Plano_filme pf) {
        Connection conn = null;
        PreparedStatement stm = null;
       
        try {
            conn = new ConexaoMySQL().conectar();
            stm = conn.prepareStatement("UPDATE Plano_filme SET ID_Filme=?, ID_Plano=? WHERE ID_Plano_Filme=?");
            stm.setInt(1, pf.getID_Filme().getID_Filme());
            stm.setInt(2, pf.getID_Plano().getID_plano());
            stm.setInt(3, pf.getID_Plano_Filme());
            stm.executeUpdate();
            return "alterado";
        } catch (SQLException ex) {
            return ex.getSQLState();
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    public String deletarPlano_filme(int id) {
        Connection conn = null;
        PreparedStatement stm = null;
       
        try {
            conn = new ConexaoMySQL().conectar();
            stm = conn.prepareStatement("DELETE FROM Plano_filme WHERE ID_Plano_Filme=?");
            stm.setInt(1, id);
            stm.executeUpdate();
            return "removido";
        } catch (SQLException ex) {
            return ex.getSQLState();
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // NOVAS CONSULTAS:
    public ArrayList<Plano> consultarPlanosPorFilme(int idFilme) {
        ArrayList<Plano> lista = new ArrayList();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
       
        try {
            conn = new ConexaoMySQL().conectar();
            stm = conn.prepareStatement("SELECT ID_Plano FROM Plano_filme WHERE ID_Filme=?");
            stm.setInt(1, idFilme);
            rs = stm.executeQuery();
           
            PlanoControle pc = new PlanoControle();
            while (rs.next()) {
                lista.add(pc.consultarPlanoCodigo(rs.getInt("ID_Plano")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Plano_filmeControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return lista;
    }

    public ArrayList<Filme> consultarFilmesPorPlano(int idPlano) {
        ArrayList<Filme> lista = new ArrayList();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
       
        try {
            conn = new ConexaoMySQL().conectar();
            stm = conn.prepareStatement("SELECT ID_Filme FROM Plano_filme WHERE ID_Plano=?");
            stm.setInt(1, idPlano);
            rs = stm.executeQuery();
           
            FilmeControle fc = new FilmeControle();
            while (rs.next()) {
                lista.add(fc.consultarFilmeCodigo(rs.getInt("ID_Filme")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Plano_filmeControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return lista;
    }

    public static void main(String[] args) {
    Plano_filmeControle pfc = new Plano_filmeControle();
    FilmeControle fc = new FilmeControle();
    PlanoControle pc = new PlanoControle();

    System.out.println("=== LISTA DE PLANO_FILME ===");
    for (Plano_filme pf : pfc.consultarPlano_filme()) {
        System.out.println(pf.toString());
    }

    System.out.println("\n=== INSERINDO PLANO_FILME ===");
    Plano_filme novoPf = new Plano_filme();
   
    // Verificar se o filme ID 1 existe
    Filme filme1 = fc.consultarFilmeCodigo(1);
    Plano plano1 = pc.consultarPlanoCodigo(1);
   
    if (filme1.getID_Filme() != 0 && plano1.getID_plano() != 0) {
        novoPf.setID_Filme(filme1);
        novoPf.setID_Plano(plano1);
        String resultadoInsert = pfc.inserirPlano_filme(novoPf);
        System.out.println("Resultado Inserir: " + resultadoInsert);
    } else {
        System.out.println("Erro: Filme ID 1 ou Plano ID 1 não existe!");
    }

    System.out.println("\n=== CONSULTA POR ID ===");
    Plano_filme pfConsultado = pfc.consultarID_Plano_filme(1);
    if (pfConsultado.getID_Plano_Filme() != 0) {
        System.out.println(pfConsultado.toString());
    } else {
        System.out.println("Plano_filme com ID 1 não encontrado.");
    }

    System.out.println("\n=== ALTERANDO PLANO_FILME ===");
    Plano_filme pfEdit = pfc.consultarID_Plano_filme(1);

    if (pfEdit.getID_Plano_Filme() != 0) {
        Filme filme2 = fc.consultarFilmeCodigo(2);
        Plano plano2 = pc.consultarPlanoCodigo(2);
       
        if (filme2.getID_Filme() != 0 && plano2.getID_plano() != 0) {
            pfEdit.setID_Filme(filme2);
            pfEdit.setID_Plano(plano2);
            String resultadoUpdate = pfc.alterarPlano_filme(pfEdit);
            System.out.println("Resultado alterar: " + resultadoUpdate);
        } else {
            System.out.println("Erro: Filme ID 2 ou Plano ID 2 nao existe!");
        }
    } else {
        System.out.println("Plano_filme com ID 1 não encontrado para alteração.");
    }

    System.out.println("\n=== DELETANDO PLANO_FILME ===");
    String resultadoDelete = pfc.deletarPlano_filme(1);
    System.out.println("Resultado deletar: " + resultadoDelete);
}
}