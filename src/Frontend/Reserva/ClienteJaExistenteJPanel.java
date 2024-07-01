package Frontend.Reserva;

import Frontend.Reserva.Clientes.ClientesJFrame;
import backend.Cliente;
import backend.GerenciadorEstacionamento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClienteJaExistenteJPanel extends JPanel {
    private GerenciadorEstacionamento gerenciador;
    private JTextField nomeField;
    private JComboBox<String> clienteComboBox;
    private JRadioButton radioButtonNome;

    public ClienteJaExistenteJPanel(GerenciadorEstacionamento gerenciador) {
        this.gerenciador = gerenciador;
        setupUI();
    }

    private void setupUI() {
        setLayout(new GridLayout(6, 2));

        clienteComboBox = createComboBox("Clientes:");
        nomeField = createTextField("Nome:", false);
        radioButtonNome = createRadioButton("Buscar por nome", e -> toggleFields());

        atualizarClientes();
        createButton("Entrar", e -> entrar());
    }

    private JTextField createTextField(String label, boolean enabled) {
        JTextField textField = new JTextField();
        textField.setEnabled(enabled);
        add(new JLabel(label));
        add(textField);
        return textField;
    }

    private JRadioButton createRadioButton(String text, ActionListener action) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.addActionListener(action);
        add(radioButton);
        return radioButton;
    }

    private JComboBox<String> createComboBox(String label) {
        JComboBox<String> comboBox = new JComboBox<>();
        add(new JLabel(label));
        add(comboBox);
        return comboBox;
    }

    private void createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        add(button);
    }

    private void toggleFields() {
        nomeField.setEnabled(radioButtonNome.isSelected());
        clienteComboBox.setEnabled(!radioButtonNome.isSelected());
    }

    private void entrar() {
        String nome = radioButtonNome.isSelected() ? nomeField.getText() : (String) clienteComboBox.getSelectedItem();
        Cliente cliente = gerenciador.getClientePorNome(nome);
        if (cliente != null) {
            new ClientesJFrame(gerenciador, cliente, this).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado: " + nome);
        }
    }

    public void atualizarClientes() {
        clienteComboBox.removeAllItems();
        for (Cliente cliente : gerenciador.getClientes()) {
            clienteComboBox.addItem(cliente.getNome());
        }
    }
}