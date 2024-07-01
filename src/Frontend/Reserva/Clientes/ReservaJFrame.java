package Frontend.Reserva.Clientes;

import backend.GerenciadorEstacionamento;
import backend.Cliente;
import backend.Vaga;
import backend.Reserva;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ReservaJFrame extends JFrame {
    private GerenciadorEstacionamento gerenciador;
    private Cliente cliente;
    private JComboBox<Vaga> vagaComboBox;
    private JTextField tempoTextField;

    public ReservaJFrame(GerenciadorEstacionamento gerenciador, Cliente cliente) {
        this.gerenciador = gerenciador;
        this.cliente = cliente;
        setupUI();
    }

    private void setupUI() {
        setTitle("Reserva de Vagas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("Cliente:"+ cliente.getNome()));
        panel.add(new JLabel(""));

        vagaComboBox = new JComboBox<>(gerenciador.getVagasDisponiveis().toArray(new Vaga[0]));
        panel.add(new JLabel("Vaga:"));
        panel.add(vagaComboBox);

        tempoTextField = new JTextField();
        panel.add(new JLabel("Tempo de Reserva (minutos):"));
        panel.add(tempoTextField);

        panel.add(createButton("Confirmar Reserva", e -> confirmarReserva()));

        getContentPane().add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void confirmarReserva() {
        Vaga vaga = (Vaga) vagaComboBox.getSelectedItem();
        int tempoReserva = Integer.parseInt(tempoTextField.getText());

        Reserva reserva = gerenciador.criarReserva(cliente, vaga, tempoReserva);
        if (reserva != null) {
            JOptionPane.showMessageDialog(null, "Reserva criada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível criar a reserva.");
        }
    }
}