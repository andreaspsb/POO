package TG.Views;

import java.math.BigDecimal;

import javax.swing.*;
import TG.Modelos.Cliente;
import TG.Modelos.Conta;
import TG.Servicos.ClienteServico;
import javax.swing.text.*;

public class ClienteView extends JFrame {

    private JFormattedTextField txtCpf;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JFormattedTextField txtConta;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public ClienteView() {
        setTitle("Cadastro de Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(8, 8, 8, 8);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.anchor = java.awt.GridBagConstraints.WEST;

        JLabel lblCpf = new JLabel("CPF:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblCpf, gbc);

        try {
            javax.swing.text.MaskFormatter cpfMask = new javax.swing.text.MaskFormatter("###.###.###-##");
            cpfMask.setPlaceholderCharacter('_');
            txtCpf = new JFormattedTextField(cpfMask);
        } catch (java.text.ParseException e) {
            txtCpf = new JFormattedTextField();
        }
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        panel.add(txtCpf, gbc);
        gbc.weightx = 0;

        JLabel lblNome = new JLabel("Nome:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblNome, gbc);

        txtNome = new JTextField();
        ((AbstractDocument) txtNome.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string != null) {
                    string = string.toUpperCase().replaceAll("[^A-Z ]", "");
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text != null) {
                    text = text.toUpperCase().replaceAll("[^A-Z ]", "");
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        panel.add(txtNome, gbc);
        gbc.weightx = 0;

        JLabel lblEmail = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblEmail, gbc);

        txtEmail = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        panel.add(txtEmail, gbc);
        gbc.weightx = 0;

        JLabel lblConta = new JLabel("Conta:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblConta, gbc);

        try {
            javax.swing.text.MaskFormatter contaMask = new javax.swing.text.MaskFormatter("########");
            contaMask.setPlaceholderCharacter('_');
            txtConta = new JFormattedTextField(contaMask);
        } catch (java.text.ParseException e) {
            txtConta = new JFormattedTextField();
        }
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        panel.add(txtConta, gbc);
        gbc.weightx = 0;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 0));
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarCliente());
        buttonPanel.add(btnSalvar);
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> cancelar());
        buttonPanel.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = java.awt.GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        add(panel);
    }    

    private void salvarCliente() {
        Cliente cliente = obterCliente();
        if (cliente == null) {
            exibirErro("Verifique os campos preenchidos.");
            return;
        }
        
        ClienteServico clienteServico = new ClienteServico();
        
        try {
            clienteServico.adicionarCliente(cliente);
            exibirSucesso("Cliente cadastrado com sucesso!");
            limparCampos();
        } catch (Exception ex) {
            exibirErro("Erro ao cadastrar cliente: " + ex.getMessage());
        }
    }

    public Cliente obterCliente() {
        String cpf = txtCpf.getText().replaceAll("[.\\-]", "");
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String numeroConta = txtConta.getText();

        if (cpf.isEmpty() || nome.isEmpty() || email.isEmpty() || numeroConta.isEmpty()) {
            exibirErro("Todos os campos devem ser preenchidos.");
            return null;
        }
        if (!cpf.matches("\\d{11}")) {
            exibirErro("CPF deve conter 11 dígitos numéricos.");
            return null;
        }
        if (!nome.matches("[a-zA-Z\\s]+")) {
            exibirErro("Nome deve conter apenas letras e espaços.");
            return null;
        }
        if (nome.length() < 3) {
            exibirErro("Nome deve ter pelo menos 3 caracteres.");
            return null;
        }
        if (!email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            exibirErro("Email inválido.");
            return null;
        }
        if (!numeroConta.matches("\\d{8}")) {
            exibirErro("Número da conta deve ter exatamente 8 dígitos numéricos.");
            return null;
        }
        Conta conta = new Conta(numeroConta, BigDecimal.ZERO);
        return new Cliente(cpf, nome, email, conta);
    }

    private void cancelar() {
        boolean preenchido = !txtCpf.getText().trim().isEmpty() ||
                             !txtNome.getText().trim().isEmpty() ||
                             !txtEmail.getText().trim().isEmpty() ||
                             !txtConta.getText().trim().isEmpty();
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

    private void limparCampos() {
        txtCpf.setValue(null);
        txtNome.setText("");
        txtEmail.setText("");
        txtConta.setValue(null);
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
    public static void main(String[] args) {
        ClienteView clienteView = new ClienteView();
        clienteView.mostrar();

        clienteView.adicionarListenerSalvar(e -> {
            clienteView.salvarCliente();
        });
        clienteView.adicionarListenerCancelar(e -> {
            clienteView.cancelar();
        });
        
    }

    public void adicionarListenerSalvar(java.awt.event.ActionListener listener) {
        btnSalvar.addActionListener(listener);
    }
    public void adicionarListenerCancelar(java.awt.event.ActionListener listener) {
        btnCancelar.addActionListener(listener);
    }
        */

}
