package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Sistema Academia - Menu Principal");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(5, 1, 5, 5));

        JLabel titulo = new JLabel("Selecione uma opção:", JLabel.CENTER);

        JButton btnAlunos   = new JButton("Gerenciar Alunos");
        JButton btnTreinos  = new JButton("Gerenciar Treinos");
        JButton btnSair     = new JButton("Sair");

    
        btnAlunos.addActionListener(e -> new MenuAlunos());

        btnTreinos.addActionListener(e -> new MenuTreinos());

        btnSair.addActionListener(e -> System.exit(0));

        painel.add(titulo);
        painel.add(btnAlunos);
        painel.add(btnTreinos);
        painel.add(btnSair);

        add(painel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}
