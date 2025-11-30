package view;

import dao.TreinoJDBCDAO;
import model.Treino;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

public class MenuTreinos {

	private JFrame frame;
	private DefaultTableModel tableModel;
	private JTable table;
	private TreinoJDBCDAO dao = new TreinoJDBCDAO();
	private int idAluno;

	public MenuTreinos(int idAluno) {
		this.idAluno = idAluno;
		initialize();
		carregarTreinos();
	}

	public MenuTreinos() {
		String idStr = JOptionPane.showInputDialog(null, "Informe o ID do aluno:");
		if (idStr == null || idStr.isEmpty()) return;
		try {
			this.idAluno = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "ID inválido.");
			return;
		}
		initialize();
		carregarTreinos();
	}

	private void initialize() {
		frame = new JFrame("Treinos do Aluno " + idAluno);
		frame.setSize(700, 450);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		tableModel = new DefaultTableModel(new Object[]{"ID","Tipo","Descricao","Duracao(min)","Data Inicio"},0) {
			public boolean isCellEditable(int row, int col) { return false; }
		};
		table = new JTable(tableModel);
		JScrollPane scroll = new JScrollPane(table);

		JPanel form = new JPanel(new GridLayout(5,2,6,6));
		JTextField tfTipo = new JTextField();
		JTextField tfDescricao = new JTextField();
		JTextField tfDuracao = new JTextField();
		JTextField tfDataInicio = new JTextField();

		form.add(new JLabel("Tipo de Treino:")); form.add(tfTipo);
		form.add(new JLabel("Descricao:")); form.add(tfDescricao);
		form.add(new JLabel("Duracao (min):")); form.add(tfDuracao);
		form.add(new JLabel("Data Inicio (DD-MM-YYYY):")); form.add(tfDataInicio);

		JButton btnAdicionar = new JButton("Adicionar Treino");
		JPanel botoes = new JPanel(new FlowLayout());
		botoes.add(btnAdicionar);

		btnAdicionar.addActionListener(e -> {
			try {
				String dataStr = tfDataInicio.getText().trim();
				if (dataStr.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Por favor, informe a data de início (DD-MM-YYYY).");
					return;
				}
				if (!isValidDate(dataStr)) {
					JOptionPane.showMessageDialog(frame, "Data inválida. Use o formato DD-MM-YYYY.");
					return;
				}

				Treino t = new Treino();
				t.setIdAluno(idAluno);
				t.setTreino(tfTipo.getText());
				t.setDescricao(tfDescricao.getText());
				t.setDuracaoMinutos(Integer.parseInt(tfDuracao.getText()));
				t.setDataInicio(LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT)));
				dao.adicionar(t);
				carregarTreinos();
				tfTipo.setText(""); tfDescricao.setText(""); tfDuracao.setText(""); tfDataInicio.setText("");
				JOptionPane.showMessageDialog(frame, "Treino adicionado.");
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(frame, "Duração inválida. Informe um número inteiro.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Erro ao adicionar treino: " + ex.getMessage());
			}
		});


		JPanel top = new JPanel(new BorderLayout());
		top.add(form, BorderLayout.CENTER);
		top.add(botoes, BorderLayout.SOUTH);

		frame.getContentPane().add(scroll, BorderLayout.CENTER);
		frame.getContentPane().add(top, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

	private void carregarTreinos() {
		tableModel.setRowCount(0);
		try {
			List<Treino> lista = dao.listarPorAluno(idAluno);
			for (Treino t : lista) {
				String dataFormatada = "";
				if (t.getDataInicio() != null) {
					dataFormatada = t.getDataInicio().format(DateTimeFormatter.ofPattern("dd-MM-uuuu"));
				}
				tableModel.addRow(new Object[]{t.getId(), t.getTreino(), t.getDescricao(), t.getDuracaoMinutos(), dataFormatada});
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame, "Erro ao carregar treinos: " + ex.getMessage());
		}
	}

	private boolean isValidDate(String dateStr) {
		if (dateStr == null) return false;
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);
		try {
			LocalDate.parse(dateStr, fmt);
			return true;
		} catch (DateTimeParseException ex) {
			return false;
		}
	}
}
