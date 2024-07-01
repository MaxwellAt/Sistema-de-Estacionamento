package Frontend.Vagas;

import backend.GerenciadorEstacionamento;
import backend.Vaga;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VagasJFrame extends JFrame {
    private GerenciadorEstacionamento gerenciador;
    private JTable table;
    private DefaultTableModel tableModel;

    public VagasJFrame(GerenciadorEstacionamento gerenciador) {
        this.gerenciador = gerenciador;
        setupUI();
        updateTable();
    }

    private void setupUI() {
        setTitle("Gerenciamento de Vagas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        setupTable(panel);
        setupButtons(panel);

        getContentPane().add(panel);
    }

    private void setupTable(JPanel panel) {
        tableModel = new DefaultTableModel(new Object[]{"Número", "Localização", "Status", "Tipo de Veículo"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    private void setupButtons(JPanel panel) {
        JButton adicionarVagaButton = createButton("Adicionar Vaga", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdicionarVagaJFrame(gerenciador, VagasJFrame.this).setVisible(true);
            }
        });
        JButton removerVagaButton = createButton("Remover Vaga", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemoverVagaJFrame(gerenciador, VagasJFrame.this).setVisible(true);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(adicionarVagaButton);
        buttonPanel.add(removerVagaButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    public void updateTable() {
        tableModel.setRowCount(0);
        List<Vaga> vagas = gerenciador.getVagas();
        for (Vaga vaga : vagas) {
            if (vaga != null) {
                tableModel.addRow(new Object[]{vaga.getNumero(), vaga.getLocalizacao(), vaga.getStatus(), vaga.getTipoVeiculo()});
            }
        }
    }
}