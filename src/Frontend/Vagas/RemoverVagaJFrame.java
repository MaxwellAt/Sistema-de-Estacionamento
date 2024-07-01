package Frontend.Vagas;

import backend.GerenciadorEstacionamento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RemoverVagaJFrame extends JFrame {
    private GerenciadorEstacionamento gerenciador;
    private JTextField numeroField;
    private VagasJFrame parent;

    public RemoverVagaJFrame(GerenciadorEstacionamento gerenciador, VagasJFrame parent) {
        this.gerenciador = gerenciador;
        this.parent = parent;
        setupUI();
    }

    private void setupUI() {
        setTitle("Remover Vaga");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));

        numeroField = new JTextField();

        panel.add(new JLabel("Número da Vaga:"));
        panel.add(numeroField);
        panel.add(createButton("Remover", e -> removeVaga()));

        getContentPane().add(panel);
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void removeVaga() {
        int numero = Integer.parseInt(numeroField.getText());
        gerenciador.removerVaga(numero);
        JOptionPane.showMessageDialog(null, "Vaga removida com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        parent.updateTable();
        dispose();
    }
}