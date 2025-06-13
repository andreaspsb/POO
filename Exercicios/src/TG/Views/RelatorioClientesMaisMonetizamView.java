package TG.Views;

import javax.swing.*;
import java.awt.*;

public class RelatorioClientesMaisMonetizamView extends JFrame {
    public RelatorioClientesMaisMonetizamView() {
        setTitle("Relatório de Clientes que Mais Monetizam");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Clientes que Mais Monetizam", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        JTable tabela = new JTable(); // Dados e modelo serão preenchidos depois
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);
    }
}
