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
        setSize(500, 320); // Ajustado para comportar todos os textos
        setMinimumSize(new Dimension(500, 320));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Painel de Cadastros
        JPanel panelCadastros = new JPanel();
        panelCadastros.setLayout(new GridLayout(4, 1, 5, 5));
        panelCadastros.setBorder(BorderFactory.createTitledBorder("Cadastros e Operações"));

        btnCliente = new JButton("Gerenciar Clientes");
        btnCliente.addActionListener(e -> new ClienteView().setVisible(true));
        panelCadastros.add(btnCliente);

        btnProduto = new JButton("Gerenciar Produtos");
        btnProduto.addActionListener(e -> new ProdutoView().setVisible(true));
        panelCadastros.add(btnProduto);

        btnVenda = new JButton("Realizar Venda");
        btnVenda.addActionListener(e -> new VendaView().setVisible(true));
        panelCadastros.add(btnVenda);

        btnMonetizacao = new JButton("Operações de Monetização");
        btnMonetizacao.addActionListener(e -> new MonetizacaoView().setVisible(true));
        panelCadastros.add(btnMonetizacao);

        // Painel de Relatórios
        JPanel panelRelatorios = new JPanel();
        panelRelatorios.setLayout(new GridLayout(4, 1, 5, 5));
        panelRelatorios.setBorder(BorderFactory.createTitledBorder("Relatórios"));

        btnRelatorioProdutosVendidos = new JButton("Produtos Vendidos");
        btnRelatorioProdutosVendidos.addActionListener(e -> new RelatorioProdutosVendidosView().setVisible(true));
        panelRelatorios.add(btnRelatorioProdutosVendidos);

        btnRelatorioComprasCliente = new JButton("Compras por Cliente");
        btnRelatorioComprasCliente.addActionListener(e -> new RelatorioComprasPorClienteView().setVisible(true));
        panelRelatorios.add(btnRelatorioComprasCliente);

        btnRelatorioClientesMaisCompram = new JButton("Clientes que Mais Compram");
        btnRelatorioClientesMaisCompram.addActionListener(e -> new RelatorioClientesMaisCompramView().setVisible(true));
        panelRelatorios.add(btnRelatorioClientesMaisCompram);

        btnRelatorioClientesMonetizacao = new JButton("Clientes que Mais Monetizam");
        btnRelatorioClientesMonetizacao.addActionListener(e -> new RelatorioClientesMaisMonetizamView().setVisible(true));
        panelRelatorios.add(btnRelatorioClientesMonetizacao);

        // Painel principal com os dois grupos
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));
        panelCentral.add(panelCadastros);
        panelCentral.add(panelRelatorios);

        // Painel inferior para o botão sair
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> System.exit(0));
        panelInferior.add(btnSair);

        panel.add(panelCentral, BorderLayout.CENTER);
        panel.add(panelInferior, BorderLayout.SOUTH);

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
