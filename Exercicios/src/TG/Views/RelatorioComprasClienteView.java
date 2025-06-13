package TG.Views;

import javax.swing.*;
import java.awt.*;

public class RelatorioComprasClienteView extends JFrame {
    public RelatorioComprasClienteView() {
        setTitle("Relatório de Compras por Cliente");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Compras por Cliente", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        JTable tabela = new JTable(); // Dados e modelo serão preenchidos depois
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);
    }
}
