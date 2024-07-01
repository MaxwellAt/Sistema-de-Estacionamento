package Frontend.Reserva.Clientes.Veiculos;

import backend.*;

import javax.swing.*;

public class AdicionarVeiculoJFrame extends JFrame {
    private Cliente cliente;
    private ListadeVeiculosPanel listadeVeiculosPanel;

    public AdicionarVeiculoJFrame(Cliente cliente) {
        this.cliente = cliente;
        this.listadeVeiculosPanel = new ListadeVeiculosPanel();

        setupUI();
    }

    private void setupUI() {
        setTitle("Adicionar Veículo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(listadeVeiculosPanel);

        JButton adicionarButton = new JButton("Adicionar");
        adicionarButton.addActionListener(e -> addVehicle());

        panel.add(adicionarButton);

        add(panel);
    }

    private void addVehicle() {
        Veiculo veiculo = listadeVeiculosPanel.getVeiculo();
        if (veiculo != null) {
            cliente.setVeiculo(veiculo);
            JOptionPane.showMessageDialog(this, "Veículo adicionado com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar veículo. Por favor, preencha os campos corretamente.");
        }
    }
}