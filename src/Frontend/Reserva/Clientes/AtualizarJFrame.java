package Frontend.Reserva.Clientes;
import backend.*;
import Frontend.Reserva.Clientes.Veiculos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AtualizarJFrame extends JFrame {
    private Cliente cliente;
    private JTextField emailField;
    private JTextField telefoneField;

    public AtualizarJFrame(GerenciadorEstacionamento gerenciador, Cliente cliente) {
        this.cliente = cliente;
        emailField = new JTextField(cliente.getEmail(), 15);
        telefoneField = new JTextField(cliente.getTelefone(), 15);
        setSize(300, 200);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Bem-vindo, " + cliente.getNome()));
        panel.add(new JLabel(""));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Telefone:"));
        panel.add(telefoneField);

        panel.add(createButton("Adicionar Veiculo", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdicionarVeiculoJFrame(cliente).setVisible(true);
            }
        }));
        panel.add(createButton("Remover Veiculo", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemoverVeiculoJFrame( cliente).setVisible(true);
            }
        }));
        panel.add(createButton("Salvar Alterações", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges(gerenciador);
            }
        }));

        getContentPane().add(panel);
        setVisible(true);
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void saveChanges(GerenciadorEstacionamento gerenciador) {
        gerenciador.atualizarCliente(cliente);
        cliente.setEmail(emailField.getText());
        cliente.setTelefone(telefoneField.getText());
        JOptionPane.showMessageDialog(null, "Alterações salvas com sucesso");
        dispose();
    }
}