package Frontend.Vagas;

import backend.GerenciadorEstacionamento;
import backend.Vaga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdicionarVagaJFrame extends JFrame {
    private GerenciadorEstacionamento gerenciador;
    private JTextField localizacaoField;
    private JComboBox<String> tipoVeiculoComboBox;
    private VagasJFrame parent;

    public AdicionarVagaJFrame(GerenciadorEstacionamento gerenciador, VagasJFrame parent) {
        this.gerenciador = gerenciador;
        this.parent = parent;
        setupUI();
    }

    private void setupUI() {
        setTitle("Adicionar Vaga");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1));

        localizacaoField = new JTextField();
        tipoVeiculoComboBox = new JComboBox<>(new String[]{"Carro", "Caminhao", "Moto"});

        panel.add(new JLabel("Localização:"));
        panel.add(localizacaoField);
        panel.add(new JLabel("Tipo de Veículo:"));
        panel.add(tipoVeiculoComboBox);
        panel.add(createButton("Salvar", e -> saveVaga()));

        getContentPane().add(panel);
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void saveVaga() {
        try {
            int numero = gerenciador.proximoNumeroVaga();
            String localizacao = localizacaoField.getText();
            String tipoVeiculo = (String) tipoVeiculoComboBox.getSelectedItem();

            if (localizacao == null || localizacao.isEmpty()) {
                throw new IllegalArgumentException("Por favor, insira a localização.");
            }

            if (tipoVeiculo == null) {
                throw new IllegalArgumentException("Por favor, selecione o tipo de veículo.");
            }

            boolean vagaExistenteEncontrada = false;
            try {
                for (Vaga vaga : gerenciador.getVagas()) {
                    if (vaga.getLocalizacao().equals(localizacao) && vaga.getTipoVeiculo().equals(tipoVeiculo)) {
                        vagaExistenteEncontrada = true;
                        break;
                    }
                }
            } catch (NullPointerException ex) {
                // Ignora se não houver vagas cadastradas
            }

            if (vagaExistenteEncontrada) {
                throw new IllegalArgumentException("A vaga com a mesma localização e tipo de veículo já existe.");
            }

            gerenciador.cadastrarVaga(numero, localizacao, tipoVeiculo);
            JOptionPane.showMessageDialog(null, "Vaga cadastrada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            parent.updateTable();
            dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}