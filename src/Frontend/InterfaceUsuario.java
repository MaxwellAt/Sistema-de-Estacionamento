package Frontend;

import Frontend.EntradaESaida.*;
import Frontend.RelatoriosEConsultas.RelatoriosEConsultasJFrame;
import Frontend.Vagas.*;
import Frontend.Reserva.*;
import backend.GerenciadorEstacionamento;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceUsuario {
    private GerenciadorEstacionamento gerenciador;
    private JTextField precoPorHoraField;
    public InterfaceUsuario(GerenciadorEstacionamento gerenciador) {
        this.gerenciador = gerenciador;
        this.precoPorHoraField = gerenciador.getTaxaPorHora() == 0 ? new JTextField() : new JTextField(String.valueOf(gerenciador.getTaxaPorHora()));
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Sistema de Gerenciamento de Estacionamento");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));

        // Adicione um campo de texto para inserir o preço por hora
        panel.add(new JLabel("Preço por hora:"));
        panel.add(precoPorHoraField);


        precoPorHoraField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                atualizarPreco();
            }
            public void removeUpdate(DocumentEvent e) {
                atualizarPreco();
            }
            public void insertUpdate(DocumentEvent e) {
                atualizarPreco();
            }

            public void atualizarPreco() {
                try {
                    double precoPorHora = Double.parseDouble(precoPorHoraField.getText());
                    gerenciador.setTaxaPorHora(precoPorHora);
                } catch (NumberFormatException ex) {
                    // Ignora se o texto não for um número válido

                    new JOptionPane().showMessageDialog(null, "Por favor, insira um número válido para o preço por hora.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addButton(panel, "Vagas", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VagasJFrame(gerenciador).setVisible(true);
            }
        });
        addButton(panel, "Registrar Entrada e Saida", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EntradaSaidaJFrame(gerenciador).setVisible(true);
            }
        });
        addButton(panel, "Relatorios e Consultas", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RelatoriosEConsultasJFrame(gerenciador).setVisible(true);
            }
        });
        addButton(panel, "Reserva", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginJFrame(gerenciador).setVisible(true);
            }
        });
        addButton(panel, "Sair", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void addButton(JPanel panel, String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        panel.add(button);
    }
    private void confirmarPreco() {
        try {
            double precoPorHora = Double.parseDouble(precoPorHoraField.getText());
            gerenciador.setTaxaPorHora(precoPorHora);
            JOptionPane.showMessageDialog(null, "Preço por hora atualizado com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, insira um número válido para o preço por hora.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
        new InterfaceUsuario(new GerenciadorEstacionamento("src/backend/estacionamento.json"));
    }
}