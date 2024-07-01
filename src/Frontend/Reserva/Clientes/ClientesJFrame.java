package Frontend.Reserva.Clientes;

import Frontend.Reserva.ClienteJaExistenteJPanel;
import backend.Cliente;
import backend.GerenciadorEstacionamento;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;

public class ClientesJFrame extends JFrame {
    private GerenciadorEstacionamento gerenciador;

    public ClientesJFrame(GerenciadorEstacionamento gerenciador, Cliente cliente, ClienteJaExistenteJPanel parentPanel) {
        this.gerenciador = gerenciador;
        setupUI(cliente, parentPanel);
    }

    private void setupUI(Cliente cliente, ClienteJaExistenteJPanel parentPanel) {
        setTitle("Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(300, 200);

        JPanel panel = new JPanel(new GridLayout(4, 1));

        panel.add(new JLabel("Bem-vindo, " + cliente.getNome()));
        panel.add(createButton("Atualizar", e -> openUpdateFrame(cliente)));
        panel.add(createButton("Reservar Vaga", e -> reserveSpot(cliente)));
        panel.add(createButton("Excluir", e -> deleteClient(cliente, parentPanel)));

        getContentPane().add(panel);
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void openUpdateFrame(Cliente cliente) {
        AtualizarJFrame atualizarJFrame = new AtualizarJFrame(gerenciador, cliente);
        atualizarJFrame.setVisible(true);
    }

    private void reserveSpot(Cliente cliente) {
        if (gerenciador.getVagasDisponiveis().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há vagas disponíveis no momento.");
        } else {
            ReservaJFrame reservaJFrame = new ReservaJFrame(gerenciador, cliente);
            reservaJFrame.setVisible(true);
        }
    }

    private void deleteClient(Cliente cliente, ClienteJaExistenteJPanel parentPanel) {
        int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o cliente?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            gerenciador.removerCliente(cliente);
            parentPanel.atualizarClientes();
            dispose();
        }
    }
}