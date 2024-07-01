package Frontend.Reserva.Clientes.Veiculos;

import backend.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RemoverVeiculoJFrame extends JFrame {
    private Cliente cliente;
    private JTextField placaField;

    public RemoverVeiculoJFrame(Cliente cliente) {
        this.cliente = cliente;
        setupUI();
    }

    private void setupUI() {
        setTitle("Remover Veículo");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));

        placaField = new JTextField("");
        panel.add(new JLabel("Placa do Veículo:"));
        panel.add(placaField);

        panel.add(createButton("Remover Veículo", e -> removeVehicle()));

        getContentPane().add(panel);
        setVisible(true);
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void removeVehicle() {
        String placa = placaField.getText();
        boolean removido = cliente.removerVeiculo(placa);

        if (removido) {
            JOptionPane.showMessageDialog(this, "Veículo removido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Veículo não encontrado!");
        }

        dispose();
    }
}
