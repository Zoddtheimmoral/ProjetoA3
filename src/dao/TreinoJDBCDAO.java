package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.Treino;


public class TreinoJDBCDAO  {

    private static final String URL = "jdbc:mysql://localhost:3306/projeto_database?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void adicionar(Treino treino) {
        String sql = "INSERT INTO treinos (id_aluno, treino, descricao, duracao_minutos, data_inicio) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, treino.getIdAluno());
            pstmt.setString(2, treino.getTreino());
            pstmt.setString(3, treino.getDescricao());
            pstmt.setInt(4, treino.getDuracaoMinutos());

            if (treino.getDataInicio() != null) {
                pstmt.setDate(5, Date.valueOf(treino.getDataInicio()));
            } else {
                pstmt.setNull(5, Types.DATE);
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Treino> listarPorAluno(int idAluno) {
        List<Treino> lista = new ArrayList<>();
        String sql = "SELECT * FROM treinos WHERE id_aluno = ?";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idAluno);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Treino treino = new Treino(
                    rs.getInt("id"),
                    rs.getInt("id_aluno"),
                    rs.getString("treino"),
                    rs.getString("descricao"),
                    rs.getInt("duracao_minutos"),
                    rs.getDate("data_inicio") != null
                        ? rs.getDate("data_inicio").toLocalDate()
                        : null
                );
                lista.add(treino);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
