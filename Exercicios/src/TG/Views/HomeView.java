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
    private JButton btnMonetizacao;
    private JButton btnSair;
    private JButton btnRelatorioProdutosVendidos;
    private JButton btnRelatorioComprasCliente;
    private JButton btnRelatorioClientesMaisCompram;
    private JButton btnRelatorioClientesMonetizacao;

    public HomeView() {
        setTitle("Tela Inicial");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1));

        btnCliente = new JButton("Gerenciar Clientes");
        btnCliente.addActionListener(e -> new ClienteView().setVisible(true));
        panel.add(btnCliente);

        btnProduto = new JButton("Gerenciar Produtos");
        btnProduto.addActionListener(e -> new ProdutoView().setVisible(true));
        panel.add(btnProduto);

        btnVenda = new JButton("Realizar Venda");
        btnVenda.addActionListener(e -> new VendaView().setVisible(true));
        panel.add(btnVenda);

        btnMonetizacao = new JButton("Operações de Monetização");
        btnMonetizacao.addActionListener(e -> new MonetizacaoView().setVisible(true));
        panel.add(btnMonetizacao);

        btnRelatorioProdutosVendidos = new JButton("Relatório: Produtos Vendidos");
        btnRelatorioProdutosVendidos.addActionListener(e -> new RelatorioProdutosVendidosView().setVisible(true));
        panel.add(btnRelatorioProdutosVendidos);

        btnRelatorioComprasCliente = new JButton("Relatório: Compras por Cliente");
        btnRelatorioComprasCliente.addActionListener(e -> new RelatorioComprasClienteView().setVisible(true));
        panel.add(btnRelatorioComprasCliente);

        btnRelatorioClientesMaisCompram = new JButton("Relatório: Clientes que Mais Compram");
        btnRelatorioClientesMaisCompram.addActionListener(e -> new RelatorioClientesMaisCompramView().setVisible(true));
        panel.add(btnRelatorioClientesMaisCompram);

        btnRelatorioClientesMonetizacao = new JButton("Relatório: Clientes que Mais Monetizam");
        btnRelatorioClientesMonetizacao.addActionListener(e -> new RelatorioClientesMaisMonetizamView().setVisible(true));
        panel.add(btnRelatorioClientesMonetizacao);

        btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> System.exit(0));
        panel.add(btnSair);

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

    //SAIR
    public void sair() {
        int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Obrigado por usar o sistema!", "Saída", JOptionPane.INFORMATION_MESSAGE);
            fechar();
            System.exit(0);
        }
    }

}
