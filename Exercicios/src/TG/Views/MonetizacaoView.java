package TG.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import TG.Servicos.ContaServico;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.util.Locale;
import TG.Excecoes.ContaNaoEncontradaException;
import TG.Excecoes.SaldoInsuficienteException;
import TG.Excecoes.OperacaoNaoPermitidaException;
import TG.Excecoes.ArquivoRepositorioException;
import TG.Excecoes.DadoInvalidoException;
import TG.Util.MensagensUtil;

public class MonetizacaoView extends JFrame {
    private JFormattedTextField txtConta;
    private JFormattedTextField txtValor;
    private JFormattedTextField txtContaDestino;
    private JButton btnDepositar;
    private JButton btnTransferir;
    private JButton btnVerSaldo;
    private JLabel lblResultado;
    private ContaServico contaServico;

    public MonetizacaoView() {
        setTitle("Operações de Monetização");
        setSize(420, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        contaServico = new ContaServico();
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblConta = new JLabel("Conta:");
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblConta, gbc);
        try {
            MaskFormatter contaMask = new MaskFormatter("########");
            contaMask.setPlaceholderCharacter('_');
            txtConta = new JFormattedTextField(contaMask);
        } catch (Exception ex) {
            txtConta = new JFormattedTextField();
        }
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(txtConta, gbc);
        gbc.gridwidth = 1;

        JLabel lblValor = new JLabel("Valor:");
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblValor, gbc);
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale.Builder().setLanguage("pt").setRegion("BR").build());
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        NumberFormatter valorFormatter = new NumberFormatter(nf);
        valorFormatter.setValueClass(Double.class);
        valorFormatter.setAllowsInvalid(false);
        valorFormatter.setMinimum(0.0);
        txtValor = new JFormattedTextField(valorFormatter);
        txtValor.setColumns(10);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(txtValor, gbc);
        gbc.gridwidth = 1;

        JLabel lblContaDestino = new JLabel("Conta Destino:");
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblContaDestino, gbc);
        try {
            MaskFormatter contaDestinoMask = new MaskFormatter("########");
            contaDestinoMask.setPlaceholderCharacter('_');
            txtContaDestino = new JFormattedTextField(contaDestinoMask);
        } catch (Exception ex) {
            txtContaDestino = new JFormattedTextField();
        }
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(txtContaDestino, gbc);
        gbc.gridwidth = 1;

        btnDepositar = new JButton("Depositar");
        btnDepositar.addActionListener(this::depositar);
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(btnDepositar, gbc);

        btnTransferir = new JButton("Transferir");
        btnTransferir.addActionListener(this::transferir);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(btnTransferir, gbc);

        btnVerSaldo = new JButton("Ver Saldo");
        btnVerSaldo.addActionListener(this::verSaldo);
        gbc.gridx = 2; gbc.gridy = 3;
        panel.add(btnVerSaldo, gbc);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> fechar());
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3;
        panel.add(btnVoltar, gbc);
        gbc.gridwidth = 1;

        lblResultado = new JLabel("");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 3;
        panel.add(lblResultado, gbc);
        gbc.gridwidth = 1;

        add(panel);
    }

    private void depositar(ActionEvent e) {
        try {
            String conta = txtConta.getText();
            double valor = ((Number) txtValor.getValue()).doubleValue();
            contaServico.depositar(conta, BigDecimal.valueOf(valor));
            lblResultado.setText("Depósito realizado com sucesso!");
        } catch (ContaNaoEncontradaException ex) {
            lblResultado.setText("Conta não encontrada.");
        } catch (OperacaoNaoPermitidaException ex) {
            lblResultado.setText(ex.getMessage());
        } catch (ArquivoRepositorioException ex) {
            lblResultado.setText(MensagensUtil.ERRO_ARQUIVO + ex.getMessage());
        } catch (DadoInvalidoException ex) {
            lblResultado.setText(MensagensUtil.DADOS_INVALIDOS + ex.getMessage());
        } catch (Exception ex) {
            lblResultado.setText("Erro: " + ex.getMessage());
        }
    }

    private void transferir(ActionEvent e) {
        try {
            String contaOrigem = txtConta.getText();
            String contaDestino = txtContaDestino.getText();
            double valor = ((Number) txtValor.getValue()).doubleValue();
            contaServico.transferir(contaOrigem, contaDestino, BigDecimal.valueOf(valor));
            lblResultado.setText("Transferência realizada com sucesso!");
        } catch (ContaNaoEncontradaException ex) {
            lblResultado.setText("Conta de origem ou destino não encontrada.");
        } catch (SaldoInsuficienteException ex) {
            lblResultado.setText("Saldo insuficiente para transferência.");
        } catch (OperacaoNaoPermitidaException ex) {
            lblResultado.setText(ex.getMessage());
        } catch (ArquivoRepositorioException ex) {
            lblResultado.setText(MensagensUtil.ERRO_ARQUIVO + ex.getMessage());
        } catch (DadoInvalidoException ex) {
            lblResultado.setText(MensagensUtil.DADOS_INVALIDOS + ex.getMessage());
        } catch (Exception ex) {
            lblResultado.setText("Erro: " + ex.getMessage());
        }
    }

    private void verSaldo(ActionEvent e) {
        try {
            String conta = txtConta.getText();
            BigDecimal saldo = contaServico.consultarSaldo(conta);
            lblResultado.setText("Saldo: R$ " + saldo);
        } catch (ContaNaoEncontradaException ex) {
            lblResultado.setText("Conta não encontrada.");
        } catch (ArquivoRepositorioException ex) {
            lblResultado.setText(MensagensUtil.ERRO_ARQUIVO + ex.getMessage());
        } catch (DadoInvalidoException ex) {
            lblResultado.setText(MensagensUtil.DADOS_INVALIDOS + ex.getMessage());
        } catch (Exception ex) {
            lblResultado.setText("Erro: " + ex.getMessage());
        }
    }

    private void fechar() {
        setVisible(false);
        dispose();
    }
}
