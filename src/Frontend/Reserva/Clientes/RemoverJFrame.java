package Frontend.Reserva.Clientes;

import backend.GerenciadorEstacionamento;
import backend.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RemoverJFrame extends JFrame {
    private GerenciadorEstacionamento gerenciador;
    private JTextField nomeField;

    public RemoverJFrame(GerenciadorEstacionamento gerenciador) {
        this.gerenciador = gerenciador;
        setupUI();
    }

    private void setupUI() {
        setTitle("Remover Cliente");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));

        nomeField = new JTextField();
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);

        panel.add(createButton("Remover", e -> removerCliente()));

        getContentPane().add(panel);
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void removerCliente() {
        String nome = nomeField.getText();
        Cliente cliente = gerenciador.getClientePorNome(nome);
        if (cliente != null) {
            gerenciador.removerCliente(cliente);
            JOptionPane.showMessageDialog(null, "Cliente removido com sucesso.");
        } else {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado.");
        }
    }
}