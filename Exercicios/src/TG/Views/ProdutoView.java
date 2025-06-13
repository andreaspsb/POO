package TG.Views;

import javax.swing.*;
import TG.Modelos.Produto;
import TG.Servicos.ProdutoServico;
import javax.swing.text.*;
import java.text.NumberFormat;
import java.util.Locale;

public class ProdutoView extends JFrame {

    private JTextField txtCodigo;
    private JTextField txtNome;
    private JFormattedTextField txtPreco;
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
        ((AbstractDocument) txtCodigo.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string != null) {
                    string = string.toUpperCase().replaceAll("[^A-Z0-9]", "");
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text != null) {
                    text = text.toUpperCase().replaceAll("[^A-Z0-9]", "");
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        txtCodigo.setBounds(120, 30, 200, 25);
        panel.add(txtCodigo);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 70, 80, 25);
        panel.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(120, 70, 200, 25);
        panel.add(txtNome);

        JLabel lblPreco = new JLabel("Preço:");
        lblPreco.setBounds(30, 110, 80, 25);
        panel.add(lblPreco);

        NumberFormat nf = NumberFormat
                .getNumberInstance(new Locale.Builder().setLanguage("pt").setRegion("BR").build());
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        NumberFormatter precoFormatter = new NumberFormatter(nf);
        precoFormatter.setValueClass(Double.class);
        precoFormatter.setAllowsInvalid(false);
        precoFormatter.setMinimum(0.0);
        txtPreco = new JFormattedTextField(precoFormatter);
        txtPreco.setBounds(120, 110, 200, 25);
        panel.add(txtPreco);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(50, 180, 100, 30);
        btnSalvar.addActionListener(e -> salvarProduto());
        panel.add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(200, 180, 100, 30);
        btnCancelar.addActionListener(e -> cancelar());
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
        String nome = txtNome.getText();
        double preco;
        try {
            preco = ((Number) txtPreco.getValue()).doubleValue();
        } catch (Exception e) {
            exibirErro("Preço inválido. Por favor, insira um número válido.");
            return null;
        }
        if (codigo.isEmpty() || nome.isEmpty() || preco <= 0) {
            exibirErro("Todos os campos devem ser preenchidos corretamente.");
            return null;
        }
        if (codigo.length() < 3) {
            exibirErro("Código do produto deve ter pelo menos 3 caracteres.");
            return null;
        }
        if (!codigo.matches("[a-zA-Z0-9]+")) {
            exibirErro("Código do produto deve conter apenas letras e números.");
            return null;
        }
        if (nome.length() < 3) {
            exibirErro("Nome do produto deve ter pelo menos 3 caracteres.");
            return null;
        }
        return new Produto(codigo, nome, preco);
    }

    private void cancelar() {
        boolean preenchido = !txtCodigo.getText().trim().isEmpty() ||
                             !txtNome.getText().trim().isEmpty() ||
                             txtPreco.getValue() != null;
        if (preenchido) {
            int resp = JOptionPane.showConfirmDialog(this, "Há campos preenchidos. Deseja realmente cancelar?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (resp != JOptionPane.YES_OPTION) {
                return;
            }
        }
        limparCampos();
        exibirMensagem("Cadastro cancelado.");
        fechar();
    }

    public void limparCampos() {
        txtCodigo.setText("");
        txtNome.setText("");
        txtPreco.setValue(null);
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

    /*
     * public static void main(String[] args) {
     * ProdutoView produtoView = new ProdutoView();
     * produtoView.mostrar();
     * 
     * produtoView.adicionarListenerSalvar(e -> {
     * produtoView.salvarProduto();
     * });
     * 
     * produtoView.adicionarListenerCancelar(e -> {
     * produtoView.cancelar();
     * });
     * }
     * 
     * 
     * public void adicionarListenerCancelar(java.awt.event.ActionListener listener)
     * {
     * btnCancelar.addActionListener(listener);
     * }
     */

    public void adicionarListenerSalvar(java.awt.event.ActionListener listener) {
        btnSalvar.addActionListener(listener);
    }
}
