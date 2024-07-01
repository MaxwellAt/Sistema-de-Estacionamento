package Frontend.EntradaESaida;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PagamentoJFrame extends JDialog {
    public PagamentoJFrame(double valor) {
        setTitle("Pagamento");
        setSize(300, 200);

        JPanel panel = new JPanel(new GridLayout(5, 1));

        JComboBox<String> formaPagamentoComboBox = new JComboBox<>(new String[]{"Pix", "Cartão", "Dinheiro"});
        JTextField valorField = new JTextField(String.valueOf(valor));

        panel.add(new JLabel("Forma de Pagamento:"));
        panel.add(formaPagamentoComboBox);
        panel.add(new JLabel("Valor:"+valor));
        panel.add(valorField);

        JButton pagarButton = new JButton("Pagar");
        pagarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implementar a lógica para registrar o pagamento
                // Mostrar tela com o recibo de pagamento
                JDialog reciboDialog = new JDialog();
                reciboDialog.setTitle("Recibo de Pagamento");
                reciboDialog.setSize(300, 200);
                reciboDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                JPanel reciboPanel = new JPanel(new GridLayout(5, 1));
                reciboPanel.add(new JLabel("Pagamento efetuado com sucesso!"));
                reciboPanel.add(new JLabel("Forma de Pagamento: " + formaPagamentoComboBox.getSelectedItem()));
                reciboPanel.add(new JLabel("Valor: " + valorField.getText()));
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        reciboDialog.dispose();
                        dispose();
                    }
                });
                reciboPanel.add(okButton);

                reciboDialog.getContentPane().add(reciboPanel);
                reciboDialog.setVisible(true);
                dispose();
            }
        });

        panel.add(pagarButton);

        getContentPane().add(panel);
    }
}