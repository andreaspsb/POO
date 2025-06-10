package TG.Views;

import javax.swing.*;
import TG.Modelos.Venda;
import TG.Servicos.VendaServico;

public class VendaView extends JFrame {

    private JTextField txtCodigo;
    private JTextField txtCliente;
    private JButton btnAdicionarProduto;
    private JButton btnFinalizarVenda;
    private JButton btnCancelarVenda;

    public VendaView() {
        setTitle("Venda");
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

        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(30, 70, 80, 25);
        panel.add(lblCliente);

        txtCliente = new JTextField();
        txtCliente.setBounds(120, 70, 200, 25);
        panel.add(txtCliente);

        btnAdicionarProduto = new JButton("Adicionar Produto");
        btnAdicionarProduto.setBounds(50, 120, 150, 30);
        btnAdicionarProduto.addActionListener(e -> adicionarProduto());
        panel.add(btnAdicionarProduto);

        btnFinalizarVenda = new JButton("Finalizar Venda");
        btnFinalizarVenda.setBounds(220, 120, 150, 30);        
        btnFinalizarVenda.addActionListener(e -> finalizarVenda());
        panel.add(btnFinalizarVenda);

        btnCancelarVenda = new JButton("Cancelar Venda");
        btnCancelarVenda.setBounds(135, 180, 150, 30);
        btnCancelarVenda.addActionListener(e -> cancelar());
        panel.add(btnCancelarVenda);

        add(panel);
    }    

    private void adicionarProduto() {
        // Aqui você pode implementar a lógica para adicionar produtos à venda.
        // Por exemplo, abrir uma nova janela para selecionar produtos e adicioná-los à
        // venda.
        // Por enquanto, apenas exibe uma mensagem informando que a funcionalidade não
        // está implementada.
        exibirMensagem("Funcionalidade de adicionar produto ainda não implementada.");

        // Exemplo de como você poderia abrir uma nova janela para adicionar produtos:
        // ProdutoView produtoView = new ProdutoView();
        // produtoView.mostrar();
        // produtoView.adicionarListenerSalvar(e -> {
        //     Produto produto = produtoView.obterProduto();
        //     if (produto != null) {
        //         VendaServico vendaServico = new VendaServico();
        //         try {
        //             vendaServico.adicionarProdutoAVenda(produto);
        //             exibirSucesso("Produto adicionado à venda com sucesso.");
        //         } catch (Exception e) {
        //             exibirErro("Erro ao adicionar produto à venda: " + e.getMessage());
        //         }
        //     }
        // });
    }

    public Venda obterVenda() {
        String codigo = txtCodigo.getText();
        String cliente = txtCliente.getText();

        if (codigo.isEmpty() || cliente.isEmpty()) {
            exibirErro("Todos os campos devem ser preenchidos.");
            return null;
        }

        Venda venda = new Venda(codigo, cliente);
        return venda;
    }

    private void finalizarVenda() {
        Venda venda = obterVenda();
        if (venda != null) {
            VendaServico vendaServico = new VendaServico();
            try {
                vendaServico.finalizarVenda(venda);
                exibirMensagem("Venda finalizada com sucesso.");
                limparCampos();
            } catch (Exception e) {
                exibirErro("Erro ao finalizar a venda: " + e.getMessage());
            }
        }
    }

    private void cancelar() {
        limparCampos();
        exibirMensagem("Cadastro cancelado.");
        fechar();
    }

    public void limparCampos() {
        txtCodigo.setText("");
        txtCliente.setText("");
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
        VendaView vendaView = new VendaView();
        vendaView.mostrar();

        vendaView.adicionarListenerAdicionarProduto(e -> {
            vendaView.adicionarProduto();
        });

        vendaView.adicionarListenerFinalizarVenda(e -> {
            vendaView.finalizarVenda();
        });

        vendaView.adicionarListenerCancelarVenda(e -> {
            vendaView.cancelar();
        });
    }

    public void adicionarListenerAdicionarProduto(java.awt.event.ActionListener listener) {
        btnAdicionarProduto.addActionListener(listener);
    }

    public void adicionarListenerFinalizarVenda(java.awt.event.ActionListener listener) {
        btnFinalizarVenda.addActionListener(listener);
    }

    public void adicionarListenerCancelarVenda(java.awt.event.ActionListener listener) {
        btnCancelarVenda.addActionListener(listener);
    }

}
