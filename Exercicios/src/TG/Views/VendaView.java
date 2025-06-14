package TG.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.util.List;

import TG.Modelos.Cliente;
import TG.Modelos.Produto;
import TG.Modelos.Venda;
import TG.Modelos.EnumStatusVenda;
import TG.Servicos.ProdutoServico;
import TG.Servicos.VendaServico;
import TG.Servicos.ClienteServico;
import TG.Excecoes.VendaInvalidaException;
import TG.Excecoes.SaldoInsuficienteException;
import TG.Excecoes.ArquivoRepositorioException;
import TG.Excecoes.DadoInvalidoException;
import TG.Util.MensagensUtil;

public class VendaView extends JFrame {

    private JTextField txtCliente;
    private JTextField txtQuantidade;
    private JButton btnAdicionarProduto;
    private JButton btnFinalizarVenda;
    private JButton btnCancelarVenda;
    private JButton btnIniciarVenda;
    private JTable tabelaProdutos;
    private DefaultTableModel tabelaModel;
    private JComboBox<Produto> comboProdutos;
    private JLabel lblTotal;
    private ProdutoServico produtoServico = new ProdutoServico();
    private Venda vendaAtual = null;

    public VendaView() {
        setTitle("Venda");
        setSize(700, 500); // Tamanho aumentado
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(8, 8, 8, 8);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.anchor = java.awt.GridBagConstraints.WEST;

        JLabel lblCliente = new JLabel("Cliente (CPF):");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblCliente, gbc);
        try {
            MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
            cpfMask.setPlaceholderCharacter('_');
            txtCliente = new JFormattedTextField(cpfMask);
        } catch (Exception ex) {
            txtCliente = new JTextField();
        }
        txtCliente.setColumns(15); // Garante espaço para todo o CPF
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(txtCliente, gbc);
        gbc.gridwidth = 1;

        btnIniciarVenda = new JButton("Iniciar Venda");
        btnIniciarVenda.addActionListener(e -> iniciarVenda(e));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(btnIniciarVenda, gbc);

        btnCancelarVenda = new JButton("Cancelar");
        btnCancelarVenda.addActionListener(e -> cancelar());
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(btnCancelarVenda, gbc);

        JLabel lblProduto = new JLabel("Produto:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblProduto, gbc);
        comboProdutos = new JComboBox<>();
        carregarProdutosCombo();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(comboProdutos, gbc);
        gbc.gridwidth = 1;

        JLabel lblQuantidade = new JLabel("Quantidade:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblQuantidade, gbc);
        txtQuantidade = new JTextField();
        ((AbstractDocument) txtQuantidade.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr)
                    throws javax.swing.text.BadLocationException {
                if (string != null) {
                    string = string.replaceAll("[^0-9]", "");
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text,
                    javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
                if (text != null) {
                    text = text.replaceAll("[^0-9]", "");
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        txtQuantidade.setColumns(8);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(txtQuantidade, gbc);

        btnAdicionarProduto = new JButton("Adicionar Produto");
        btnAdicionarProduto.addActionListener(e -> adicionarProduto());
        btnAdicionarProduto.setPreferredSize(new java.awt.Dimension(180, 30));
        gbc.gridx = 2;
        gbc.gridy = 3;
        panel.add(btnAdicionarProduto, gbc);

        tabelaModel = new DefaultTableModel(new Object[] { "Código", "Quantidade", "Preço Unitário", "Preço Total" },
                0);
        tabelaProdutos = new JTable(tabelaModel);
        tabelaProdutos.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        scrollPane.setPreferredSize(new java.awt.Dimension(600, 200));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;

        btnFinalizarVenda = new JButton("Finalizar Venda");
        btnFinalizarVenda.addActionListener(e -> finalizarVenda());
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(btnFinalizarVenda, gbc);

        lblTotal = new JLabel("Total: R$ 0.00");
        gbc.gridx = 2;
        gbc.gridy = 5;
        panel.add(lblTotal, gbc);

        add(panel);
        setCamposProdutosVisiveis(false);
    }

    private void carregarProdutosCombo() {
        comboProdutos.removeAllItems();
        try {
            List<Produto> produtos = produtoServico.listarProdutos();
            for (Produto p : produtos) {
                comboProdutos.addItem(p);
            }
        } catch (ArquivoRepositorioException | DadoInvalidoException ex) {
            exibirErro("Erro ao carregar produtos: " + ex.getMessage());
        }
        comboProdutos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                java.awt.Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Produto) {
                    Produto p = (Produto) value;
                    setText(p.getCodigo() + " - " + p.getNome() + " (R$ " + String.format("%.2f", p.getPreco()) + ")");
                }
                return c;
            }
        });
    }

    private void setCamposProdutosVisiveis(boolean visivel) {
        comboProdutos.setVisible(visivel);
        txtQuantidade.setVisible(visivel);
        tabelaProdutos.setVisible(visivel);
        btnAdicionarProduto.setVisible(visivel);
        btnFinalizarVenda.setVisible(visivel);
        lblTotal.setVisible(visivel);
        for (Component c : getContentPane().getComponents()) {
            if (c instanceof JPanel) {
                for (Component comp : ((JPanel) c).getComponents()) {
                    if (comp instanceof JLabel) {
                        JLabel label = (JLabel) comp;
                        if (label.getText().contains("Produto") || label.getText().contains("Quantidade")
                                || label.getText().contains("Total")) {
                            label.setVisible(visivel);
                        }
                    }
                    if (comp instanceof JScrollPane) {
                        comp.setVisible(visivel);
                    }
                }
            }
        }
    }

    private void iniciarVenda(ActionEvent e) {
        String cpf = txtCliente.getText().replaceAll("[._-]", "").trim();
        if (cpf.isEmpty()) {
            exibirErro("O campo Cliente (CPF) não pode estar vazio.");
            return;
        }
        try {
            ClienteServico clienteServico = new ClienteServico();
            if (clienteServico.buscarClientePorCPF(cpf) == null) {
                exibirErro(MensagensUtil.ERRO_CLIENTE_NAO_ENCONTRADO);
                return;
            }
            Cliente cliente;
            try {
                cliente = new Cliente(cpf);
            } catch (DadoInvalidoException ex) {
                exibirErro(MensagensUtil.CAMPOS_OBRIGATORIOS);
                return;
            }
            VendaServico vendaServico = new VendaServico();
            vendaServico.adicionarVenda(cliente);
            List<Venda> vendas = vendaServico.listarVendas();
            vendaAtual = vendas.get(vendas.size() - 1);
            vendaAtual = vendaServico.buscarVendaPorCodigo(
                    vendaServico.listarVendas().get(vendaServico.listarVendas().size() - 1).getCodigo());
            exibirSucesso("Venda iniciada com sucesso.");
        } catch (VendaInvalidaException ex) {
            exibirErro(ex.getMessage());
            return;
        } catch (ArquivoRepositorioException ex) {
            exibirErro(MensagensUtil.ERRO_ARQUIVO + ex.getMessage());
            return;
        } catch (Exception ex) {
            exibirErro("Erro ao iniciar a venda: " + ex.getMessage());
            return;
        }
        txtCliente.setEditable(false);
        btnIniciarVenda.setVisible(false);
        setCamposProdutosVisiveis(true);
    }

    private void adicionarProduto() {
        Produto produto = (Produto) comboProdutos.getSelectedItem();
        String quantidadeStr = txtQuantidade.getText().trim();
        if (produto == null || quantidadeStr.isEmpty()) {
            exibirErro("Selecione um produto e informe a quantidade.");
            return;
        }
        int quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeStr);
            if (quantidade <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            exibirErro("Quantidade inválida.");
            return;
        }
        double precoUnitario = produto.getPreco();
        double precoTotal = precoUnitario * quantidade;
        tabelaModel.addRow(new Object[] { produto.getCodigo(), quantidade, String.format("%.2f", precoUnitario),
                String.format("%.2f", precoTotal) });
        atualizarTotal();
        txtQuantidade.setText("");
    }

    private void atualizarTotal() {
        double total = 0.0;
        for (int i = 0; i < tabelaModel.getRowCount(); i++) {
            total += Double.parseDouble(tabelaModel.getValueAt(i, 3).toString().replace(",", "."));
        }
        lblTotal.setText("Total: R$ " + String.format("%.2f", total));
    }

    public Venda obterVenda() {
        String cliente = txtCliente.getText();
        Cliente clienteObj;
        try {
            clienteObj = new Cliente(cliente);
        } catch (DadoInvalidoException ex) {
            exibirErro("Dados inválidos: " + ex.getMessage());
            return null;
        }
        if (cliente.isEmpty()) {
            exibirErro("Todos os campos devem ser preenchidos.");
            return null;
        }
        // Gera um código simples para a venda (pode ser melhorado conforme a lógica do
        // sistema)
        String codigoVenda = "VENDA-" + System.currentTimeMillis();
        Venda venda = new Venda(codigoVenda, clienteObj);
        // Aqui você pode adicionar os produtos da tabela à venda, se desejar
        return venda;
    }

    private void finalizarVenda() {
        if (vendaAtual == null) {
            exibirErro("Nenhuma venda em aberto para finalizar.");
            return;
        }
        if (tabelaModel.getRowCount() == 0) {
            exibirErro("Adicione produtos à venda antes de finalizar.");
            return;
        }
        for (int i = 0; i < tabelaModel.getRowCount(); i++) {
            String codigoProduto = tabelaModel.getValueAt(i, 0).toString();
            int quantidade = Integer.parseInt(tabelaModel.getValueAt(i, 1).toString());
            Produto produto = null;
            try {
                produto = produtoServico.buscarProdutoPorCodigo(codigoProduto);
            } catch (ArquivoRepositorioException | DadoInvalidoException ex) {
                exibirErro("Erro ao buscar produto: " + ex.getMessage());
                return;
            }
            if (produto != null) {
                vendaAtual.adicionarProduto(produto, quantidade);
            } else {
                exibirErro(String.format(MensagensUtil.ERRO_PRODUTO_NAO_ENCONTRADO, codigoProduto));
                return;
            }
        }
        VendaServico vendaServico = new VendaServico();
        try {
            vendaServico.finalizarVenda(vendaAtual);
            exibirMensagem("Venda finalizada com sucesso.");
            limparCampos();
            vendaAtual = null;
            fechar();
        } catch (SaldoInsuficienteException ex) {
            exibirErro(MensagensUtil.ERRO_SALDO);
        } catch (VendaInvalidaException ex) {
            exibirErro(ex.getMessage());
        } catch (ArquivoRepositorioException | DadoInvalidoException ex) {
            exibirErro("Erro ao finalizar venda: " + ex.getMessage());
        } catch (Exception e) {
            exibirErro("Erro inesperado ao finalizar a venda: " + e.getMessage());
        }
    }

    private void cancelar() {
        // Se estiver na segunda tela (venda em aberto), pede confirmação e atualiza
        // status
        if (vendaAtual != null && vendaAtual.getStatus() == EnumStatusVenda.ABERTA) {
            int resp = JOptionPane.showConfirmDialog(this, "Deseja realmente cancelar esta venda?", "Confirmação",
                    JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                try {
                    vendaAtual.setStatus(EnumStatusVenda.CANCELADA);
                    VendaServico vendaServico = new VendaServico();
                    vendaServico.atualizarVenda(vendaAtual);
                    exibirMensagem("Venda cancelada com sucesso.");
                } catch (Exception ex) {
                    exibirErro("Erro ao cancelar venda: " + ex.getMessage());
                }
                limparCampos();
                vendaAtual = null;
                fechar();
            }
        } else {
            limparCampos();
            exibirMensagem("Cadastro cancelado.");
            fechar();
        }
    }

    public void limparCampos() {
        txtCliente.setText("");
        txtQuantidade.setText("");
        tabelaModel.setRowCount(0);
        lblTotal.setText("Total: R$ 0.00");
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

}

    /*
     * public static void main(String[] args) {
     * VendaView vendaView = new VendaView();
     * vendaView.mostrar();
     * 
     * vendaView.adicionarListenerAdicionarProduto(e -> {
     * vendaView.adicionarProduto();
     * });
     * 
     * vendaView.adicionarListenerFinalizarVenda(e -> {
     * vendaView.finalizarVenda();
     * });
     * 
     * vendaView.adicionarListenerCancelarVenda(e -> {
     * vendaView.cancelar();
     * });
     * }
     * 
     * public void adicionarListenerAdicionarProduto(java.awt.event.ActionListener
     * listener) {
     * btnAdicionarProduto.addActionListener(listener);
     * }
     * 
     * public void adicionarListenerFinalizarVenda(java.awt.event.ActionListener
     * listener) {
     * btnFinalizarVenda.addActionListener(listener);
     * }
     * 
     * public void adicionarListenerCancelarVenda(java.awt.event.ActionListener
     * listener) {
     * btnCancelarVenda.addActionListener(listener);
     * }
     */
