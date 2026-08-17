import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import controller.CursoDAO;
import model.*;


public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection con = null;

        try {
            con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/faculdade",
                    "postgres",
                    "sua_senha_aqui"
            );
            System.out.println("Conectado ao banco de dados!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return;
        }

        CursoDAO dao = new CursoDAO(con);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- MENU CURSOS ---");
            System.out.println("1 - Inserir curso");
            System.out.println("2 - Listar cursos");
            System.out.println("3 - Atualizar curso");
            System.out.println("4 - Deletar curso");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");
            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Nome do curso: ");
                    String nome = sc.nextLine();
                    System.out.print("Carga horaria: ");
                    int carga = Integer.parseInt(sc.nextLine());

                    Curso novoCurso = new Curso();
                    novoCurso.setNome(nome);
                    novoCurso.setCargaHoraria(carga);
                    dao.inserir(novoCurso);
                    break;

                case 2:
                    ArrayList<Curso> cursos = dao.listar();
                    if (cursos.size() == 0) {
                        System.out.println("Nenhum curso cadastrado.");
                    } else {
                        for (int i = 0; i < cursos.size(); i++) {
                            Curso c = cursos.get(i);
                            System.out.println(c.getId() + " - " + c.getNome() + " (" + c.getCargaHoraria() + "h)");
                        }
                    }
                    break;

                case 3:
                    System.out.print("ID do curso a atualizar: ");
                    int idAtualizar = Integer.parseInt(sc.nextLine());
                    System.out.print("Novo nome: ");
                    String novoNome = sc.nextLine();
                    System.out.print("Nova carga horaria: ");
                    int novaCarga = Integer.parseInt(sc.nextLine());

                    Curso cursoAtualizado = new Curso();
                    cursoAtualizado.setId(idAtualizar);
                    cursoAtualizado.setNome(novoNome);
                    cursoAtualizado.setCargaHoraria(novaCarga);
                    dao.atualizar(cursoAtualizado);
                    break;

                case 4:
                    System.out.print("ID do curso a deletar: ");
                    int idDeletar = Integer.parseInt(sc.nextLine());
                    dao.deletar(idDeletar);
                    break;

                case 0:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opcao invalida.");
            }
        }

        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexao: " + e.getMessage());
        }

        sc.close();
    }
}