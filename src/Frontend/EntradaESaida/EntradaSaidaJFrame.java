package Frontend.EntradaESaida;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class EntradaSaidaJFrame extends JFrame {
    private GerenciadorEstacionamento gerenciador;

    public EntradaSaidaJFrame(GerenciadorEstacionamento gerenciador) {
        this.gerenciador = gerenciador;
        setTitle("Registrar Entrada e Saída de Veículos");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(8, 1));

        JTextField placaField = new JTextField();
        JComboBox<String> tipoVeiculoComboBox = new JComboBox<>(new String[]{"Carro", "Caminhao", "Moto"});

        panel.add(new JLabel("Placa:"));
        panel.add(placaField);
        panel.add(new JLabel("Tipo de Veículo:"));
        panel.add(tipoVeiculoComboBox);

        JButton entradaButton = new JButton("Registrar Entrada");
        entradaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String placa = placaField.getText();
                    if (placa.isEmpty()) {
                        throw new IllegalArgumentException("A placa não pode estar vazia.");
                    }
                    String tipoVeiculo = (String) tipoVeiculoComboBox.getSelectedItem();
                    if (tipoVeiculo != null) {
                        EntradaJFrame entradaJFrame = new EntradaJFrame(gerenciador, tipoVeiculo, placa);
                        entradaJFrame.setVisible(true);
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton saidaButton = new JButton("Registrar Saída");
        saidaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String placa = placaField.getText();
                Veiculo veiculo = null;
                for(Vaga vaga: gerenciador.getVagas()){
                    if(vaga.getVeiculo() != null && vaga.getVeiculo().getPlaca().equals(placa)){
                        veiculo = vaga.getVeiculo();
                        break;
                    }
                }
                if (veiculo == null) {
                    JOptionPane.showMessageDialog(null, "Veículo não encontrado no estacionamento.");
                    return;
                }
                double valor = gerenciador.saidaVeiculo(veiculo, new Date());
                PagamentoJFrame pagamentoDialog = new PagamentoJFrame(valor);
                pagamentoDialog.setVisible(true);
            }
        });

        panel.add(entradaButton);
        panel.add(saidaButton);

        getContentPane().add(panel);
    }


}