package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Curso;

public class CursoDAO {

    private Connection con;

    public CursoDAO(Connection con) {
        this.con = con;
    }

    public void inserir(Curso curso) {
        try {
            String sql = "INSERT INTO curso (nome, carga_horaria) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.execute();
            System.out.println("Curso inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir curso: " + e.getMessage());
        }
    }

    public ArrayList<Curso> listar() {
        ArrayList<Curso> cursos = new ArrayList<Curso>();
        try {
            String sql = "SELECT * FROM curso ORDER BY id";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Curso curso = new Curso();
                curso.setId(rs.getInt("id"));
                curso.setNome(rs.getString("nome"));
                curso.setCargaHoraria(rs.getInt("carga_horaria"));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar cursos: " + e.getMessage());
        }
        return cursos;
    }

    public void atualizar(Curso curso) {
        try {
            String sql = "UPDATE curso SET nome = ?, carga_horaria = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setInt(3, curso.getId());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Curso atualizado com sucesso!");
            } else {
                System.out.println("Curso nao encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar curso: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        try {
            String sql = "DELETE FROM curso WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Curso deletado com sucesso!");
            } else {
                System.out.println("Curso nao encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar curso: " + e.getMessage());
        }
    }
}
