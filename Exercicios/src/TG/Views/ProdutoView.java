package TG.Views;

import javax.swing.*;
import TG.Modelos.Produto;
import TG.Servicos.ProdutoServico;

public class ProdutoView extends JFrame {

    private JTextField txtCodigo;
    private JTextField txtDescricao;
    private JTextField txtPreco;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public ProdutoView() {
        setTitle("Cadastro de Produto");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(30, 30, 80, 25);
        panel.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(120, 30, 200, 25);
        panel.add(txtCodigo);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setBounds(30, 70, 80, 25);
        panel.add(lblDescricao);

        txtDescricao = new JTextField();
        txtDescricao.setBounds(120, 70, 200, 25);
        panel.add(txtDescricao);

        JLabel lblPreco = new JLabel("Preço:");
        lblPreco.setBounds(30, 110, 80, 25);
        panel.add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setBounds(120, 110, 200, 25);
        panel.add(txtPreco);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(50, 180, 100, 30);
        btnSalvar.addActionListener(e -> salvarProduto());
        panel.add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(200, 180, 100, 30);
        btnSalvar.addActionListener(e -> cancelar());
        panel.add(btnCancelar);

        add(panel);
    }        

    private void salvarProduto() {
        Produto produto = obterProduto();
        if (produto != null) {
            ProdutoServico produtoServico = new ProdutoServico();
            try {
                produtoServico.adicionarProduto(produto);
                exibirSucesso("Produto cadastrado com sucesso!");
                limparCampos();
            } catch (Exception e) {
                exibirErro("Erro ao cadastrar produto: " + e.getMessage());
            }
        } else {
            exibirErro("Preencha todos os campos corretamente.");
        }
    }

    public Produto obterProduto() {
        String codigo = txtCodigo.getText();
        String descricao = txtDescricao.getText();
        double preco;
        try {
            preco = Double.parseDouble(txtPreco.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido. Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return new Produto(codigo, descricao, preco);
    }

    private void cancelar() {
        limparCampos();
        exibirMensagem("Cadastro cancelado.");
        fechar();
    }

    
    
    
    public void limparCampos() {
        txtCodigo.setText("");
        txtDescricao.setText("");
        txtPreco.setText("");
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

    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    public void exibirErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public void exibirSucesso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    
    
    public static void main(String[] args) {
        ProdutoView produtoView = new ProdutoView();
        produtoView.mostrar();        
        
        produtoView.adicionarListenerSalvar(e -> {
            produtoView.salvarProduto();
        });
        
        produtoView.adicionarListenerCancelar(e -> {
            produtoView.cancelar();
        });
    }

    public void adicionarListenerSalvar(java.awt.event.ActionListener listener) {
        btnSalvar.addActionListener(listener);
    }
    public void adicionarListenerCancelar(java.awt.event.ActionListener listener) {
        btnCancelar.addActionListener(listener);
    }
}
