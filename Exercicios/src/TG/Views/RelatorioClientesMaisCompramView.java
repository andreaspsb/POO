package TG.Views;

import javax.swing.*;
import java.awt.*;

public class RelatorioClientesMaisCompramView extends JFrame {
    public RelatorioClientesMaisCompramView() {
        setTitle("Relatório de Clientes que Mais Compram");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Clientes que Mais Compram", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        JTable tabela = new JTable(); // Dados e modelo serão preenchidos depois
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);
    }
}
