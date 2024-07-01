package Frontend.Reserva;

import backend.GerenciadorEstacionamento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginJFrame extends JFrame {
    private GerenciadorEstacionamento gerenciador;

    public LoginJFrame(GerenciadorEstacionamento gerenciador) {
        this.gerenciador = gerenciador;
        setupUI();
    }

    private void setupUI() {
        setTitle("Reserva");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));

        ClienteJaExistenteJPanel clientePanel = new ClienteJaExistenteJPanel(gerenciador);
        panel.add(clientePanel);

        JButton newClientButton = createButton("Cadastrar novo cliente", e -> openNewClientFrame(clientePanel));

        panel.add(newClientButton);

        getContentPane().add(panel);
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void openNewClientFrame(ClienteJaExistenteJPanel clientePanel) {
        CadastroClienteVeiculoJFrame newClientFrame = new CadastroClienteVeiculoJFrame(gerenciador, clientePanel);
        newClientFrame.setVisible(true);
    }
}