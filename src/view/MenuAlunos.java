package view;

import dao.AlunoJDBCDAO;
import model.Aluno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

public class MenuAlunos {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfNome, tfCpf, tfDataNascimento, tfTelefone, tfEmail, tfId;
    private AlunoJDBCDAO dao = new AlunoJDBCDAO();

    public MenuAlunos() {
        initialize();
        carregarAlunos();
    }

    private void initialize() {
        frame = new JFrame("Gerenciar Alunos");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout(8,8));

        tableModel = new DefaultTableModel(new Object[]{"ID","Nome","CPF","Data Nasc","Telefone","Email"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        painel.add(scroll, BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(7,2,6,6));

        tfId = new JTextField(); tfId.setEditable(false);
        tfNome = new JTextField();
        tfCpf = new JTextField();
        tfDataNascimento = new JTextField();
        tfTelefone = new JTextField();
        tfEmail = new JTextField();

        form.add(new JLabel("ID:")); form.add(tfId);
        form.add(new JLabel("Nome:")); form.add(tfNome);
        form.add(new JLabel("CPF (000.000.000-00):")); form.add(tfCpf);
        form.add(new JLabel("Data Nascimento (DD-MM-YYYY):")); form.add(tfDataNascimento);
        form.add(new JLabel("Telefone ((00) 00000-0000):")); form.add(tfTelefone);
        form.add(new JLabel("Email:")); form.add(tfEmail);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER,8,8));
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnRemover = new JButton("Remover");
        JButton btnAbrirTreinos = new JButton("Abrir Treinos");

        botoes.add(btnAdicionar); botoes.add(btnAtualizar); botoes.add(btnRemover); botoes.add(btnAbrirTreinos);

        JPanel south = new JPanel(new BorderLayout());
        south.add(form, BorderLayout.CENTER);
        south.add(botoes, BorderLayout.SOUTH);

        painel.add(south, BorderLayout.SOUTH);

        frame.getContentPane().add(painel);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                tfId.setText(table.getValueAt(row,0).toString());
                tfNome.setText(table.getValueAt(row,1).toString());
                tfCpf.setText(table.getValueAt(row,2).toString());
                tfDataNascimento.setText(table.getValueAt(row,3).toString());
                tfTelefone.setText(table.getValueAt(row,4).toString());
                tfEmail.setText(table.getValueAt(row,5).toString());
            }
        });

        btnAdicionar.addActionListener(e -> adicionarAluno());
        btnAtualizar.addActionListener(e -> atualizarAluno());
        btnRemover.addActionListener(e -> removerAluno());
        btnAbrirTreinos.addActionListener(e -> abrirTreinos());

        frame.setVisible(true);
    }

    private void carregarAlunos() {
        tableModel.setRowCount(0);
        try {
            List<Aluno> lista = dao.listarTodos();
            for (Aluno a : lista) {
                String dataFormatada = "";
                if (a.getDataNascimento() != null) {
                    dataFormatada = a.getDataNascimento().format(DateTimeFormatter.ofPattern("dd-MM-uuuu"));
                }
                tableModel.addRow(new Object[]{a.getId(), a.getNome(), a.getCpf(), dataFormatada, a.getTelefone(), a.getEmail()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao carregar alunos: " + ex.getMessage());
        }
    }

    private void adicionarAluno() {
        try {
            if (tfNome.getText().isEmpty() || tfCpf.getText().isEmpty() || tfDataNascimento.getText().isEmpty() ||
                    tfTelefone.getText().isEmpty() || tfEmail.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos.");
                return;
            }

            String dataStr = tfDataNascimento.getText().trim();
            if (!isValidISODate(dataStr)) {
                JOptionPane.showMessageDialog(frame, "Data inválida. Use o formato DD-MM-AAAA.");
                return;
            }

            Aluno a = new Aluno();
            a.setNome(tfNome.getText());
            a.setCpf(tfCpf.getText());
            a.setDataNascimento(LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT)));
            a.setTelefone(tfTelefone.getText());
            a.setEmail(tfEmail.getText());

            dao.adicionar(a);
            carregarAlunos();
            limparForm();
            JOptionPane.showMessageDialog(frame, "Aluno adicionado.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao adicionar: " + ex.getMessage());
        }
    }

    private void atualizarAluno() {
        try {
            if (tfId.getText().isEmpty()) { JOptionPane.showMessageDialog(frame, "Selecione um aluno para atualizar."); return; }

            String dataStr = tfDataNascimento.getText().trim();
            if (dataStr.isEmpty() || !isValidISODate(dataStr)) {
                JOptionPane.showMessageDialog(frame, "Data inválida. Use o formato DD-MM-YYYY.");
                return;
            }

            Aluno a = new Aluno();
            a.setId(Integer.parseInt(tfId.getText()));
            a.setNome(tfNome.getText());
            a.setCpf(tfCpf.getText());
            a.setDataNascimento(LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT)));
            a.setTelefone(tfTelefone.getText());
            a.setEmail(tfEmail.getText());
            dao.atualizar(a);
            carregarAlunos();
            JOptionPane.showMessageDialog(frame, "Aluno atualizado.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void removerAluno() {
        try {
            if (tfId.getText().isEmpty()) { JOptionPane.showMessageDialog(frame, "Selecione um aluno para remover."); return; }
            int id = Integer.parseInt(tfId.getText());
            int confirm = JOptionPane.showConfirmDialog(frame, "Confirma remoção do aluno ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dao.remover(id);
                carregarAlunos();
                limparForm();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao remover: " + ex.getMessage());
        }
    }

    private void abrirTreinos() {
        String idText = tfId.getText();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Selecione um aluno para abrir seus treinos.");
            return;
        }
        int idAluno = Integer.parseInt(idText);
        new MenuTreinos(idAluno);
    }

    private void limparForm() {
        tfId.setText(""); tfNome.setText(""); tfCpf.setText(""); tfDataNascimento.setText(""); tfTelefone.setText(""); tfEmail.setText("");
    }

    private boolean isValidISODate(String dateStr) {
        if (dateStr == null) return false;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(dateStr, fmt);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuAlunos());
    }

}
