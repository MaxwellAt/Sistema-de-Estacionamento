package Frontend.Reserva.Clientes.Veiculos;

import backend.Veiculo;
import backend.Carro;
import backend.Moto;
import backend.Caminhao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListadeVeiculosPanel extends JPanel {
    private JTextField placaField;
    private JComboBox<String> tipoVeiculoBox;
    private JTextField corField;
    private JTextField marcaField;
    private JTextField modeloField;
    private JTextField cargaMaximaField;
    private JTextField comprimentoField;
    private JTextField cilindradasField;
    private JPanel camposVariaveisPanel;

    public ListadeVeiculosPanel() {
        setLayout(new BorderLayout());

        placaField = new JTextField();
        tipoVeiculoBox = new JComboBox<>(new String[]{"Carro", "Moto", "Caminhão"});
        corField = new JTextField();
        marcaField = new JTextField();
        modeloField = new JTextField();
        cargaMaximaField = new JTextField();
        comprimentoField = new JTextField();
        cilindradasField = new JTextField();
        camposVariaveisPanel = new JPanel(new CardLayout());

        JPanel mainPanel = new JPanel(new GridLayout(2, 2));
        mainPanel.add(new JLabel("Placa:"));
        placaField = new JTextField();
        mainPanel.add(placaField);

        mainPanel.add(new JLabel("Tipo de Veículo:"));
        tipoVeiculoBox = new JComboBox<>(new String[]{"Carro", "Moto", "Caminhão"});
        mainPanel.add(tipoVeiculoBox);

        camposVariaveisPanel = new JPanel(new CardLayout());

        JPanel carroPanel = new JPanel();
        carroPanel.setLayout(new GridLayout(3, 2));
        carroPanel.add(new JLabel("Marca:"));
        marcaField = new JTextField();
        carroPanel.add(marcaField);
        carroPanel.add(new JLabel("Modelo:"));
        modeloField = new JTextField();
        carroPanel.add(modeloField);
        carroPanel.add(new JLabel("Cor:"));
        corField = new JTextField();
        carroPanel.add(corField);

        JPanel motoPanel = new JPanel();
        motoPanel.setLayout(new GridLayout(2, 2));
        motoPanel.add(new JLabel("Marca:"));
        marcaField = new JTextField();
        motoPanel.add(marcaField);
        motoPanel.add(new JLabel("Cilindradas:"));
        cilindradasField = new JTextField();
        motoPanel.add(cilindradasField);

        JPanel caminhaoPanel = new JPanel();
        caminhaoPanel.setLayout(new GridLayout(2, 2));
        caminhaoPanel.add(new JLabel("Carga Máxima:"));
        cargaMaximaField = new JTextField();
        caminhaoPanel.add(cargaMaximaField);
        caminhaoPanel.add(new JLabel("Comprimento:"));
        comprimentoField = new JTextField();
        caminhaoPanel.add(comprimentoField);

        camposVariaveisPanel.add(carroPanel, "Carro");
        camposVariaveisPanel.add(motoPanel, "Moto");
        camposVariaveisPanel.add(caminhaoPanel, "Caminhão");

        tipoVeiculoBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (camposVariaveisPanel.getLayout());
                cl.show(camposVariaveisPanel, (String) tipoVeiculoBox.getSelectedItem());
            }
        });

        add(mainPanel, BorderLayout.NORTH);
        add(camposVariaveisPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public Veiculo getVeiculo() {
        String placa = placaField.getText();
        String tipoVeiculo = (String) tipoVeiculoBox.getSelectedItem();
        Veiculo veiculo = null;

        try {
            switch (tipoVeiculo) {
                case "Carro":
                    String cor = corField.getText();
                    String marca = marcaField.getText();
                    String modelo = modeloField.getText();
                    veiculo = new Carro(placa, marca, modelo, cor);
                    break;
                case "Moto":
                    int cilindradas = Integer.parseInt(cilindradasField.getText());
                    String marcaMoto = marcaField.getText();
                    veiculo = new Moto(placa, marcaMoto, cilindradas);
                    break;
                case "Caminhão":
                    double cargaMaxima = Double.parseDouble(cargaMaximaField.getText());
                    double comprimento = Double.parseDouble(comprimentoField.getText());
                    veiculo = new Caminhao(placa, cargaMaxima, comprimento);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de veículo desconhecido: " + tipoVeiculo);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }

        return veiculo;
    }
}
