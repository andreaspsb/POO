package TG.Views;

import javax.swing.*;
import java.awt.*;

/**
 * Classe responsável por gerenciar a interface do usuário para a tela inicial.
 */

public class HomeView extends JFrame {

    private JButton btnCliente;
    private JButton btnProduto;
    private JButton btnVenda;

    public HomeView() {
        setTitle("Tela Inicial");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        btnCliente = new JButton("Gerenciar Clientes");
        btnCliente.addActionListener(e -> new ClienteView().setVisible(true));
        panel.add(btnCliente);

        btnProduto = new JButton("Gerenciar Produtos");
        btnProduto.addActionListener(e -> new ProdutoView().setVisible(true));
        panel.add(btnProduto);

        btnVenda = new JButton("Realizar Venda");
        btnVenda.addActionListener(e -> new VendaView().setVisible(true));
        panel.add(btnVenda);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeView homeView = new HomeView();
            homeView.mostrar();            
        });
    }

    public void mostrar() {
        setVisible(true);
    }
    
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }
    
    public void fechar() {
        setVisible(false);
        dispose();
    }

}
