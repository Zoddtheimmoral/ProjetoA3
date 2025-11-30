package util;

import dao.AlunoJDBCDAO;
import model.Aluno;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorAlunosAleatorios {

    private static final String[] NOMES = {
            "Ana Silva", "Bruno Costa", "Carlos Mendes", "Daniela Rocha", "Eduardo Santos",
            "Fernanda Oliveira", "Gabriel Ferreira", "Helena Martins", "Igor Pereira", "Juliana Lima",
            "Kaio Sousa", "Larissa Alves", "Matheus Gomes", "Nathalia Ribeiro", "Otavio Dias",
            "Patricia Barros", "Quentin Chaves", "Raissa Cardoso", "Samuel Teixeira", "Taisa Vargas"
    };

    private static final String[] SOBRENOMES = {
            "Silva", "Santos", "Oliveira", "Souza", "Costa",
            "Ferreira", "Gomes", "Martins", "Pereira", "Alves"
    };

    private static final String[] DOMINIO_EMAIL = {
            "gmail.com", "hotmail.com", "yahoo.com", "outlook.com", "mail.com"
    };

    public static void main(String[] args) {
        List<Aluno> alunos = gerarAlunosAleatorios(20);
        inserirAlunosNoBanco(alunos);
    }

    public static List<Aluno> gerarAlunosAleatorios(int quantidade) {
        List<Aluno> alunos = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < quantidade; i++) {
            String nome = NOMES[random.nextInt(NOMES.length)] + " " + SOBRENOMES[random.nextInt(SOBRENOMES.length)];
            String cpf = gerarCPFAleatorio(random);
            LocalDate dataNascimento = gerarDataNascimentoAleatoria(random);
            String telefone = gerarTelefoneAleatorio(random);
            String email = gerarEmailAleatorio(nome, random);

            Aluno aluno = new Aluno();
            aluno.setNome(nome);
            aluno.setCpf(cpf);
            aluno.setDataNascimento(dataNascimento);
            aluno.setTelefone(telefone);
            aluno.setEmail(email);

            alunos.add(aluno);
        }

        return alunos;
    }

    public static void inserirAlunosNoBanco(List<Aluno> alunos) {
        AlunoJDBCDAO dao = new AlunoJDBCDAO();

        int sucesso = 0;
        int erro = 0;

        for (Aluno aluno : alunos) {
            try {
                dao.adicionar(aluno);
                sucesso++;
                System.out.println("✓ Aluno inserido: " + aluno.getNome());
            } catch (Exception ex) {
                erro++;
                System.err.println("✗ Erro ao inserir " + aluno.getNome() + ": " + ex.getMessage());
            }
        }

        System.out.println("\n=== RESUMO ===");
        System.out.println("Total gerado: " + alunos.size());
        System.out.println("Inseridos com sucesso: " + sucesso);
        System.out.println("Erros: " + erro);
    }

    private static String gerarCPFAleatorio(Random random) {
        StringBuilder cpf = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            cpf.append(random.nextInt(10));
        }
        // Gerar dígitos verificadores simples (não validação real de CPF)
        cpf.append(random.nextInt(10));
        cpf.append(random.nextInt(10));
        return cpf.toString().replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    private static LocalDate gerarDataNascimentoAleatoria(Random random) {
        // Gerar data entre 1990 e 2010 (alunos entre 14 e 34 anos)
        int ano = 1990 + random.nextInt(21);
        int mes = 1 + random.nextInt(12);
        int dia = 1 + random.nextInt(28); // Usar até dia 28 para evitar problemas com fevereiro

        return LocalDate.of(ano, mes, dia);
    }

    private static String gerarTelefoneAleatorio(Random random) {
        StringBuilder telefone = new StringBuilder("(");
        for (int i = 0; i < 2; i++) {
            telefone.append(random.nextInt(10));
        }
        telefone.append(") ");
        for (int i = 0; i < 5; i++) {
            telefone.append(random.nextInt(10));
        }
        telefone.append("-");
        for (int i = 0; i < 4; i++) {
            telefone.append(random.nextInt(10));
        }
        return telefone.toString();
    }

    private static String gerarEmailAleatorio(String nome, Random random) {
        String nomeFormatado = nome.toLowerCase().replaceAll(" ", ".");
        String dominio = DOMINIO_EMAIL[random.nextInt(DOMINIO_EMAIL.length)];
        return nomeFormatado + random.nextInt(100) + "@" + dominio;
    }

}
