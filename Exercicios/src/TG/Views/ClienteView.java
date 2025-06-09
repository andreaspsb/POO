package TG.Views;

import javax.swing.*;
import TG.Modelos.Cliente;
import TG.Servicos.ClienteServico;

public class ClienteView extends JFrame {

    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtTelefone;
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
        panel.setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 30, 80, 25);
        panel.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(120, 30, 200, 25);
        panel.add(txtNome);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 70, 80, 25);
        panel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(120, 70, 200, 25);
        panel.add(txtEmail);

        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setBounds(30, 110, 80, 25);
        panel.add(lblTelefone);

        txtTelefone = new JTextField();
        txtTelefone.setBounds(120, 110, 200, 25);
        panel.add(txtTelefone);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(50, 180, 100, 30);
        btnSalvar.addActionListener(e -> salvarCliente());
        panel.add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(200, 180, 100, 30);
        btnCancelar.addActionListener(e -> cancelar());
        panel.add(btnCancelar);

        add(panel);
    }

    private void salvarCliente() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String telefone = txtTelefone.getText();

        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(nome, email, telefone);
        
        ClienteServico clienteServico = new ClienteServico();
        
        try {
            clienteServico.adicionarCliente(cliente);
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelar() {
        limparCampos();
        dispose();
    }

    private void limparCampos() {
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
    }

    public void mostrar() {
        setVisible(true);
    }
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
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }
    public Cliente obterCliente() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String telefone = txtTelefone.getText();

        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            throw new IllegalArgumentException("Todos os campos devem ser preenchidos.");
        }

        return new Cliente(nome, email, telefone);
    }
    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }    

}
