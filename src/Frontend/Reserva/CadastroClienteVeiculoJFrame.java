package Frontend.Reserva;

import backend.Cliente;
import backend.Veiculo;
import backend.GerenciadorEstacionamento;
import Frontend.Reserva.Clientes.Veiculos.ListadeVeiculosPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CadastroClienteVeiculoJFrame extends JFrame {
    private GerenciadorEstacionamento gerenciador;
    private JTextField nomeField;
    private JTextField telefoneField;
    private JTextField emailField;
    private ListadeVeiculosPanel listadeVeiculosPanel;

    public CadastroClienteVeiculoJFrame(GerenciadorEstacionamento gerenciador, ClienteJaExistenteJPanel parent) {
        this.gerenciador = gerenciador;
        setupUI(parent);
    }

    private void setupUI(ClienteJaExistenteJPanel parent) {
        setTitle("Cadastro de Cliente e Veículo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        nomeField = new JTextField();
        telefoneField = new JTextField();
        emailField = new JTextField();
        listadeVeiculosPanel = new ListadeVeiculosPanel();

        JPanel camposComunsPanel = new JPanel(new GridLayout(3, 2));
        camposComunsPanel.add(new JLabel("Nome:"));
        camposComunsPanel.add(nomeField);
        camposComunsPanel.add(new JLabel("Telefone:"));
        camposComunsPanel.add(telefoneField);
        camposComunsPanel.add(new JLabel("Email:"));
        camposComunsPanel.add(emailField);

        panel.add(camposComunsPanel);
        panel.add(listadeVeiculosPanel);

        JButton cadastrarButton = createButton("Cadastrar", e -> cadastrarCliente(parent));

        panel.add(cadastrarButton);

        add(panel);
        pack(); // Ajusta o tamanho da janela baseado nos componentes
        setLocationRelativeTo(null); // Centraliza a janela
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void cadastrarCliente(ClienteJaExistenteJPanel parent) {
        String nome = nomeField.getText();
        String telefone = telefoneField.getText();
        String email = emailField.getText();

        Cliente cliente = new Cliente(nome, telefone, email);
        Veiculo veiculo = listadeVeiculosPanel.getVeiculo();

        cliente.setVeiculo(veiculo);
        try {
            gerenciador.cadastrarCliente(cliente);
            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        parent.atualizarClientes();
        dispose();
    }
}