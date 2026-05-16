package controle;

import java.util.ArrayList;
import modelo.Filme_avaliados;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Filme_avaliadosControle {

    public Filme_avaliadosControle() {
    }

    public ArrayList<Filme_avaliados> consultarFilme_avaliados() {
        ArrayList<Filme_avaliados> vFilme_avaliados = new ArrayList<>();
        Connection conn = null;
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            conn = conexao.conectar();

            String consulta = "SELECT * FROM Filme_avaliados";
            PreparedStatement stm = conn.prepareStatement(consulta);
            ResultSet resultado = stm.executeQuery();

            while (resultado.next()) {
                Filme_avaliados fa = new Filme_avaliados();
                fa.setID_Avaliacao(resultado.getInt("ID_Avaliacao"));
                fa.setResenha(resultado.getString("resenha"));
                fa.setEstrelas(resultado.getInt("estrelas"));
                fa.setDataAvaliacao(resultado.getDate("data_avaliacao"));

                fa.setID_Filme(new FilmeControle().consultarFilmeCodigo(resultado.getInt("ID_Filme")));
                fa.setID_Perfil(new PerfilControle().consultarPerfilCodigo(resultado.getInt("ID_Perfil")));

                vFilme_avaliados.add(fa);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vFilme_avaliados;
    }

    public Filme_avaliados consultarID_Filme_avaliados(int ID_Avaliacao) {
        Filme_avaliados fa = new Filme_avaliados();
        Connection conn = null;
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            conn = conexao.conectar();

            String consulta = "SELECT * FROM Filme_avaliados WHERE ID_Avaliacao = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ID_Avaliacao);
            ResultSet resultado = stm.executeQuery();

            if (resultado.next()) {
                fa.setID_Avaliacao(resultado.getInt("ID_Avaliacao"));
                fa.setResenha(resultado.getString("resenha"));
                fa.setEstrelas(resultado.getInt("estrelas"));
                fa.setDataAvaliacao(resultado.getDate("data_avaliacao"));

                fa.setID_Filme(new FilmeControle().consultarFilmeCodigo(resultado.getInt("ID_Filme")));
                fa.setID_Perfil(new PerfilControle().consultarPerfilCodigo(resultado.getInt("ID_Perfil")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fa;
    }

    public String inserirFilme_avaliados(Filme_avaliados fa) {
        String resultado = "";
        Connection conn = null;
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            conn = conexao.conectar();
            String consulta = "INSERT INTO Filme_avaliados "
                    + "(resenha, estrelas, data_avaliacao, ID_Filme, ID_Perfil) VALUES (?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, fa.getResenha());
            stm.setInt(2, fa.getEstrelas());
            stm.setDate(3, new java.sql.Date(fa.getDataAvaliacao().getTime()));
            stm.setInt(4, fa.getID_Filme().getID_Filme());
            stm.setInt(5, fa.getID_Perfil().getID_Perfil());
            stm.executeUpdate();
            resultado = "inserido";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    public String alterarFilme_avaliados(Filme_avaliados fa) {
        String resultado = "";
        Connection conn = null;
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            conn = conexao.conectar();
            String consulta = "UPDATE Filme_avaliados SET "
                    + "resenha = ?, estrelas = ?, data_avaliacao = ?, ID_Filme = ?, ID_Perfil = ? WHERE ID_Avaliacao = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, fa.getResenha());
            stm.setInt(2, fa.getEstrelas());
            stm.setDate(3, new java.sql.Date(fa.getDataAvaliacao().getTime()));
            stm.setInt(4, fa.getID_Filme().getID_Filme());
            stm.setInt(5, fa.getID_Perfil().getID_Perfil());
            stm.setInt(6, fa.getID_Avaliacao());
            stm.executeUpdate();
            resultado = "alterado";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    public String deletarFilme_avaliados(int ID_Avaliacao) {
        String resultado = "";
        Connection conn = null;
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            conn = conexao.conectar();
            String consulta = "DELETE FROM Filme_avaliados WHERE ID_Avaliacao = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ID_Avaliacao);
            stm.executeUpdate();
            resultado = "removido";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    // ========== NOVOS MÉTODOS ==========

    /**
     * Consulta quantos filmes foram avaliados por um perfil específico
     * @param idPerfil ID do perfil
     * @return quantidade de filmes avaliados
     */
    public int consultarQuantidadeFilmesAvaliadosPorPerfil(int idPerfil) {
        int quantidade = 0;
        Connection conn = null;
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            conn = conexao.conectar();

            String consulta = "SELECT COUNT(*) as total FROM Filme_avaliados WHERE ID_Perfil = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, idPerfil);
            ResultSet resultado = stm.executeQuery();

            if (resultado.next()) {
                quantidade = resultado.getInt("total");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return quantidade;
    }

    /**
     * Consulta quantos filmes foram assistidos por um perfil
     * @param idPerfil ID do perfil
     * @return quantidade de filmes assistidos
     */
    public int consultarQuantidadeFilmesAssistidosPorPerfil(int idPerfil) {
        int quantidade = 0;
        Connection conn = null;
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            conn = conexao.conectar();

            String consulta = "SELECT COUNT(DISTINCT ID_Filme) as total FROM Filme_avaliados WHERE ID_Perfil = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, idPerfil);
            ResultSet resultado = stm.executeQuery();

            if (resultado.next()) {
                quantidade = resultado.getInt("total");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return quantidade;
    }

    /**
     * Busca a média de estrelas de um filme específico
     * @param idFilme ID do filme
     * @return média de estrelas do filme
     */
    public double buscarMediaEstrelasPorFilme(int idFilme) {
        double media = 0;
        Connection conn = null;
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            conn = conexao.conectar();

            String consulta = "SELECT AVG(estrelas) as media_estrelas FROM Filme_avaliados WHERE ID_Filme = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, idFilme);
            ResultSet resultado = stm.executeQuery();

            if (resultado.next()) {
                media = resultado.getDouble("media_estrelas");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return media;
    }

    /**
     * Busca todas as resenhas de um filme específico
     * @param idFilme ID do filme
     * @return ArrayList com as resenhas
     */
    public ArrayList<String> buscarResenhasPorFilme(int idFilme) {
        ArrayList<String> resenhas = new ArrayList<>();
        Connection conn = null;
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            conn = conexao.conectar();

            String consulta = "SELECT resenha FROM Filme_avaliados WHERE ID_Filme = ? AND resenha IS NOT NULL AND resenha != ''";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, idFilme);
            ResultSet resultado = stm.executeQuery();

            while (resultado.next()) {
                resenhas.add(resultado.getString("resenha"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resenhas;
    }

    /**
     * Busca a quantidade total de estrelas por filme
     * @param idFilme ID do filme
     * @return soma total de estrelas
     */
    public int buscarTotalEstrelasPorFilme(int idFilme) {
        int total = 0;
        Connection conn = null;
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            conn = conexao.conectar();

            String consulta = "SELECT SUM(estrelas) as total_estrelas FROM Filme_avaliados WHERE ID_Filme = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, idFilme);
            ResultSet resultado = stm.executeQuery();

            if (resultado.next()) {
                total = resultado.getInt("total_estrelas");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return total;
    }

    /**
     * Busca detalhes completos de avaliação por filme (estrelas + resenhas)
     * @param idFilme ID do filme
     * @return ArrayList com objetos contendo avaliação e resenha
     */
    public ArrayList<AvaliacaoDetalhe> buscarDetalhesAvaliacaoPorFilme(int idFilme) {
        ArrayList<AvaliacaoDetalhe> detalhes = new ArrayList<>();
        Connection conn = null;
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            conn = conexao.conectar();

            String consulta = "SELECT estrelas, resenha, data_avaliacao, ID_Perfil " +
                             "FROM Filme_avaliados WHERE ID_Filme = ? ORDER BY data_avaliacao DESC";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, idFilme);
            ResultSet resultado = stm.executeQuery();

            while (resultado.next()) {
                AvaliacaoDetalhe detalhe = new AvaliacaoDetalhe();
                detalhe.setEstrelas(resultado.getInt("estrelas"));
                detalhe.setResenha(resultado.getString("resenha"));
                detalhe.setDataAvaliacao(resultado.getDate("data_avaliacao"));
                detalhe.setIdPerfil(resultado.getInt("ID_Perfil"));
                detalhes.add(detalhe);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return detalhes;
    }

    /**
     * Busca IDs dos filmes avaliados por um perfil
     * @param idPerfil ID do perfil
     * @return ArrayList com IDs dos filmes
     */
    public ArrayList<Integer> buscarIdsFilmesAvaliadosPorPerfil(int idPerfil) {
        ArrayList<Integer> idsFilmes = new ArrayList<>();
        Connection conn = null;
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            conn = conexao.conectar();

            String consulta = "SELECT DISTINCT ID_Filme FROM Filme_avaliados WHERE ID_Perfil = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, idPerfil);
            ResultSet resultado = stm.executeQuery();

            while (resultado.next()) {
                idsFilmes.add(resultado.getInt("ID_Filme"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return idsFilmes;
    }

    /**
     * Relatório completo: filmes avaliados por perfil com estatísticas
     * @param idPerfil ID do perfil
     */
    public void relatorioFilmesPorPerfil(int idPerfil) {
        Connection conn = null;
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            conn = conexao.conectar();

            String consulta = "SELECT f.ID_Filme, f.nome_filme, " +
                             "COUNT(fa.ID_Avaliacao) as total_avaliacoes, " +
                             "AVG(fa.estrelas) as media_estrelas, " +
                             "SUM(fa.estrelas) as soma_estrelas, " +
                             "MAX(fa.data_avaliacao) as ultima_avaliacao " +
                             "FROM Filme_avaliados fa " +
                             "INNER JOIN Filme f ON fa.ID_Filme = f.ID_Filme " +
                             "WHERE fa.ID_Perfil = ? " +
                             "GROUP BY f.ID_Filme, f.nome_filme " +
                             "ORDER BY ultima_avaliacao DESC";
           
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, idPerfil);
            ResultSet resultado = stm.executeQuery();

            System.out.println("=== RELATÓRIO DE FILMES POR PERFIL (ID: " + idPerfil + ") ===");
            System.out.println("");
           
            int totalFilmes = 0;
            double mediaGeral = 0;
            int totalEstrelas = 0;
           
            while (resultado.next()) {
                String nomeFilme = resultado.getString("nome_filme");
                int totalAvaliacoes = resultado.getInt("total_avaliacoes");
                double mediaEstrelas = resultado.getDouble("media_estrelas");
                int somaEstrelas = resultado.getInt("soma_estrelas");
                Date ultimaAvaliacao = resultado.getDate("ultima_avaliacao");
               
                System.out.println("Filme: " + nomeFilme);
                System.out.println("  Total Avaliações: " + totalAvaliacoes);
                System.out.println("  Soma de Estrelas: " + somaEstrelas);
                System.out.println("  Média Estrelas: " + String.format("%.2f", mediaEstrelas));
                System.out.println("  Última Avaliação: " + ultimaAvaliacao);
                System.out.println("---");
               
                totalFilmes++;
                mediaGeral += mediaEstrelas;
                totalEstrelas += somaEstrelas;
            }
           
            System.out.println("");
            System.out.println("RESUMO:");
            System.out.println("Total de filmes avaliados: " + totalFilmes);
            System.out.println("Total de estrelas concedidas: " + totalEstrelas);
            if (totalFilmes > 0) {
                System.out.println("Média geral de estrelas: " + String.format("%.2f", mediaGeral / totalFilmes));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Classe auxiliar para detalhes de avaliação
     */
    public static class AvaliacaoDetalhe {
        private int estrelas;
        private String resenha;
        private java.sql.Date dataAvaliacao;
        private int idPerfil;

        public int getEstrelas() {
            return estrelas;
        }
       
        public void setEstrelas(int estrelas) {
            this.estrelas = estrelas;
        }
       
        public String getResenha() {
            return resenha;
        }
       
        public void setResenha(String resenha) {
            this.resenha = resenha;
        }
       
        public java.sql.Date getDataAvaliacao() {
            return dataAvaliacao;
        }
       
        public void setDataAvaliacao(java.sql.Date dataAvaliacao) {
            this.dataAvaliacao = dataAvaliacao;
        }
       
        public int getIdPerfil() {
            return idPerfil;
        }
       
        public void setIdPerfil(int idPerfil) {
            this.idPerfil = idPerfil;
        }

        @Override
        public String toString() {
            return "AvaliacaoDetalhe{" +
                   "estrelas=" + estrelas +
                   ", resenha='" + resenha + '\'' +
                   ", dataAvaliacao=" + dataAvaliacao +
                   ", idPerfil=" + idPerfil +
                   '}';
        }
    }

    public static void main(String[] args) {
        Filme_avaliadosControle fac = new Filme_avaliadosControle();
       
        System.out.println("=== TESTE DOS NOVOS MÉTODOS ===\n");
       
        // TESTE 1: Quantidade de filmes avaliados por perfil
        System.out.println("=== TESTE 1: Quantidade de filmes avaliados por perfil ===");
        for (int i = 1; i <= 3; i++) {
            int qtdAvaliados = fac.consultarQuantidadeFilmesAvaliadosPorPerfil(i);
            System.out.println("Perfil " + i + " avaliou " + qtdAvaliados + " filmes");
        }
       
        System.out.println("\n=== TESTE 2: Quantidade de filmes assistidos por perfil ===");
        for (int i = 1; i <= 3; i++) {
            int qtdAssistidos = fac.consultarQuantidadeFilmesAssistidosPorPerfil(i);
            System.out.println("Perfil " + i + " assistiu " + qtdAssistidos + " filmes");
        }
       
        System.out.println("\n=== TESTE 3: Média de estrelas por filme ===");
        for (int i = 1; i <= 5; i++) {
            double media = fac.buscarMediaEstrelasPorFilme(i);
            if (media > 0) {
                System.out.println("Filme " + i + " tem média de " + String.format("%.2f", media) + " estrelas");
            }
        }
       
        System.out.println("\n=== TESTE 4: Total de estrelas por filme ===");
        for (int i = 1; i <= 5; i++) {
            int total = fac.buscarTotalEstrelasPorFilme(i);
            if (total > 0) {
                System.out.println("Filme " + i + " totalizou " + total + " estrelas");
            }
        }
       
        System.out.println("\n=== TESTE 5: Resenhas do filme 1 ===");
        ArrayList<String> resenhas = fac.buscarResenhasPorFilme(1);
        if (resenhas.isEmpty()) {
            System.out.println("Nenhuma resenha encontrada para o filme 1");
        } else {
            for (int i = 0; i < resenhas.size(); i++) {
                System.out.println("Resenha " + (i+1) + ": " + resenhas.get(i));
            }
        }
       
        System.out.println("\n=== TESTE 6: Detalhes das avaliações do filme 1 ===");
        ArrayList<AvaliacaoDetalhe> detalhes = fac.buscarDetalhesAvaliacaoPorFilme(1);
        if (detalhes.isEmpty()) {
            System.out.println("Nenhuma avaliação encontrada para o filme 1");
        } else {
            for (AvaliacaoDetalhe detalhe : detalhes) {
                System.out.println(detalhe.toString());
            }
        }
       
        System.out.println("\n=== TESTE 7: IDs dos filmes avaliados pelo perfil 1 ===");
        ArrayList<Integer> idsFilmes = fac.buscarIdsFilmesAvaliadosPorPerfil(1);
        if (idsFilmes.isEmpty()) {
            System.out.println("Nenhum filme encontrado para o perfil 1");
        } else {
            System.out.print("IDs dos filmes: ");
            for (int id : idsFilmes) {
                System.out.print(id + " ");
            }
            System.out.println("");
        }
       
        System.out.println("\n=== TESTE 8: Relatório completo do perfil 1 ===");
        fac.relatorioFilmesPorPerfil(1);
    }
}