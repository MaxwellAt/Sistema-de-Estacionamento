package Frontend.EntradaESaida;

import backend.*;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class EntradaJFrame extends JFrame {
    private GerenciadorEstacionamento gerenciador;
    private String tipoVeiculo;
    private String placa;

    public EntradaJFrame(GerenciadorEstacionamento gerenciador, String tipoVeiculo, String placa) {
        this.gerenciador = gerenciador;
        this.tipoVeiculo = tipoVeiculo;
        this.placa = placa;
        setTitle("Registrar Entrada de Veículos");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));

        JTextField marcaField = new JTextField();
        JTextField modeloField = new JTextField();
        JTextField corField = new JTextField();
        JTextField cargaMaximaField = new JTextField();
        JTextField comprimentoField = new JTextField();
        JTextField cilindradasField = new JTextField();

        addFieldsBasedOnVehicleType(panel, marcaField, modeloField, corField, cargaMaximaField, comprimentoField, cilindradasField);

        JButton entradaButton = new JButton("Registrar Entrada");
        entradaButton.addActionListener(e -> registerVehicleEntry(marcaField, modeloField, corField, cargaMaximaField, comprimentoField, cilindradasField));

        panel.add(new JLabel()); // Filler for alignment
        panel.add(entradaButton);

        getContentPane().add(panel);
    }

    private void addFieldsBasedOnVehicleType(JPanel panel, JTextField marcaField, JTextField modeloField, JTextField corField, JTextField cargaMaximaField, JTextField comprimentoField, JTextField cilindradasField) {
        switch (tipoVeiculo) {
            case "Carro":
                addFieldToPanel(panel, "Marca:", marcaField);
                addFieldToPanel(panel, "Modelo:", modeloField);
                addFieldToPanel(panel, "Cor:", corField);
                break;
            case "Caminhao":
                addFieldToPanel(panel, "Carga Máxima:", cargaMaximaField);
                addFieldToPanel(panel, "Comprimento:", comprimentoField);
                break;
            case "Moto":
                addFieldToPanel(panel, "Marca:", marcaField);
                addFieldToPanel(panel, "Cilindradas:", cilindradasField);
                break;
        }
    }

    private void addFieldToPanel(JPanel panel, String label, JTextField field) {
        panel.add(new JLabel(label));
        panel.add(field);
    }

    private void registerVehicleEntry(JTextField marcaField, JTextField modeloField, JTextField corField, JTextField cargaMaximaField, JTextField comprimentoField, JTextField cilindradasField) {
        try {
            Veiculo veiculo = createVehicleBasedOnType(marcaField, modeloField, corField, cargaMaximaField, comprimentoField, cilindradasField);
            if (gerenciador.entradaVeiculo(veiculo, new Date())) {
                JOptionPane.showMessageDialog(null, "Entrada do veículo registrada com sucesso!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Não há vagas disponíveis.");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Veiculo createVehicleBasedOnType(JTextField marcaField, JTextField modeloField, JTextField corField, JTextField cargaMaximaField, JTextField comprimentoField, JTextField cilindradasField) throws IllegalArgumentException {
        switch (tipoVeiculo) {
            case "Carro":
                return new Carro(placa, corField.getText(), marcaField.getText(), modeloField.getText());
            case "Caminhao":
                try {
                    double cargaMaxima = Double.parseDouble(cargaMaximaField.getText());
                    double comprimento = Double.parseDouble(comprimentoField.getText());
                    return new Caminhao(placa, cargaMaxima, comprimento);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Carga Máxima e Comprimento devem ser números válidos.");
                }
            case "Moto":
                try {
                    int cilindradas = Integer.parseInt(cilindradasField.getText());
                    return new Moto(placa, marcaField.getText(), cilindradas);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Cilindradas deve ser um número inteiro válido.");
                }
            default:
                throw new IllegalArgumentException("Tipo de veículo inválido: " + tipoVeiculo);
        }
    }
}
