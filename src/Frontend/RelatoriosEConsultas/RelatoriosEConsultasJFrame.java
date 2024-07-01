package Frontend.RelatoriosEConsultas;

import backend.GerenciadorEstacionamento;
import backend.Repositorio;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class RelatoriosEConsultasJFrame extends JFrame {
    public RelatoriosEConsultasJFrame(GerenciadorEstacionamento gerenciador) {
        setTitle("Relatórios e Consultas");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));

        JButton ocupacoesButton = new JButton("Relatório de Ocupações");
        ocupacoesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implementar a lógica para gerar o relatório de ocupações
                try {
                    // Carregar o objeto Repositorio do arquivo JSON
                    Repositorio repositorio = Repositorio.loadFromJson("src/backend/estacionamento.json");

                    // Obter a lista de histórico de ocupação
                    List<String> todoHistoricoOcupacao = repositorio.getRelatorios().getTodoHistoricoOcupacao();

                    // Converter a lista de histórico de ocupação em uma única string
                    String content = String.join("\n", todoHistoricoOcupacao);

                    // Criar uma área de texto com o conteúdo do histórico de ocupação
                    JTextArea textArea = new JTextArea(content);

                    // Permitir quebra de linha automática
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);

                    // Adicionar rolagem à área de texto
                    JScrollPane scrollPane = new JScrollPane(textArea);

                    // Criar uma caixa de diálogo redimensionável
                    JOptionPane optionPane = new JOptionPane(scrollPane, JOptionPane.INFORMATION_MESSAGE);
                    JDialog dialog = optionPane.createDialog("Relatório de Ocupações");
                    dialog.setResizable(true);
                    dialog.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo de ocupações.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton historicoButton = new JButton("Histórico de Entrada e Saída de Veículo");
        historicoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Solicitar ao usuário a placa do veículo
                    String placa = JOptionPane.showInputDialog("Digite a placa do veículo:");

                    // Carregar o objeto Repositorio do arquivo JSON
                    Repositorio repositorio = Repositorio.loadFromJson("src/backend/estacionamento.json");

                    // Obter o histórico do veículo
                    List<String> historicoVeiculo = repositorio.getRelatorios().getPlacas().get(placa);

                    if (historicoVeiculo == null || historicoVeiculo.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nenhum histórico encontrado para o veículo com placa: " + placa, "Informação", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    // Converter a lista de histórico do veículo em uma única string
                    String content = String.join("\n", historicoVeiculo);

                    // Criar uma área de texto com o conteúdo do histórico do veículo
                    JTextArea textArea = new JTextArea(content);

                    // Permitir quebra de linha automática
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);

                    // Adicionar rolagem à área de texto
                    JScrollPane scrollPane = new JScrollPane(textArea);

                    // Criar uma caixa de diálogo redimensionável
                    JOptionPane optionPane = new JOptionPane(scrollPane, JOptionPane.INFORMATION_MESSAGE);
                    JDialog dialog = optionPane.createDialog("Histórico de Entrada e Saída de Veículo");
                    dialog.setResizable(true);
                    dialog.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo de histórico de veículos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton financeiroButton = new JButton("Relatório Financeiro Diário");
        financeiroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obter o histórico de transações financeiras
                    List<String> historicoTransacoes = gerenciador.getRelatorioFinanceiroDiario(new Date());
                    // Obter o saldo atual
                    double saldo = gerenciador.getRepositorio().getRelatorios().getTransacoesFinanceiras().getSaldo();

                    // Converter a lista de transações em uma única string
                    String content = String.join("\n", historicoTransacoes);
                    content += "\nSaldo atual: " + saldo;

                    // Criar uma área de texto com o conteúdo do relatório financeiro
                    JTextArea textArea = new JTextArea(content);

                    // Permitir quebra de linha automática
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);

                    // Adicionar rolagem à área de texto
                    JScrollPane scrollPane = new JScrollPane(textArea);

                    // Criar uma caixa de diálogo redimensionável
                    JOptionPane optionPane = new JOptionPane(scrollPane, JOptionPane.INFORMATION_MESSAGE);
                    JDialog dialog = optionPane.createDialog("Relatório Financeiro Diário");
                    dialog.setResizable(true);
                    dialog.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo de transações financeiras.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(ocupacoesButton);
        panel.add(historicoButton);
        panel.add(financeiroButton);

        getContentPane().add(panel);
    }
}