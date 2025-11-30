package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Aluno;  

public class AlunoJDBCDAO  {
    private static final String URL = "jdbc:mysql://localhost:3306/projeto_database?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";  
     
     private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
     }
  
     public void adicionar(Aluno aluno) {

        String sql = "INSERT INTO alunos (nome, cpf, data_nascimento, telefone, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
              pstmt.setString (1, aluno.getNome());
              pstmt.setString (2, aluno.getCpf());
              pstmt.setDate   (3, Date.valueOf(aluno.getDataNascimento()));
              pstmt.setString (4, aluno.getTelefone());
              pstmt.setString (5, aluno.getEmail());

                pstmt.executeUpdate();

             }catch (SQLException e) {
            
            }

    }

    
    public void atualizar(Aluno aluno) {
        String sql = "UPDATE alunos SET nome = ?, cpf = ?, data_nascimento = ?, telefone = ?, email = ? WHERE id = ?";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement (sql)) {

             pstmt.setString (1, aluno.getNome());
             pstmt.setString (2, aluno.getCpf());
             pstmt.setDate   (3, Date.valueOf(aluno.getDataNascimento()));
             pstmt.setString (4, aluno.getTelefone());
             pstmt.setString (5, aluno.getEmail());
             pstmt.setInt    (6, aluno.getId());

             pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    
    public void remover(int id) {
        String sql = "DELETE FROM alunos WHERE id = ?";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

             pstmt.setInt(1, id);
             pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    
    
      public Aluno buscarPorId(int id) {
       String sql = "SELECT * FROM alunos WHERE id = ?";

        try (Connection conn = conectar();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

          pstmt.setInt(1, id);
          ResultSet rs = pstmt.executeQuery();

           if (rs.next()) {
            return new Aluno(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getDate("data_nascimento").toLocalDate(),
                rs.getString("telefone"),
                rs.getString("email")
            );
        }

        } catch (SQLException e) {
           e.printStackTrace();
    }

    return null; 
}

    
    public List<Aluno> listarTodos() {
        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alunos";
        try (Connection conn = conectar();
              PreparedStatement pstmt = conn.prepareStatement(sql);
              ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()){
                    Aluno aluno = new Aluno(
                    rs.getInt("id"),
                    rs.getString("nome"),  
                    rs.getString("cpf"),
                    rs.getDate("data_nascimento").toLocalDate(),
                    rs.getString("telefone"),
                    rs.getString("email")

                    );

                    lista.add(aluno);

                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    

}