package TG.Views;

import TG.Modelos.Cliente;
import TG.Servicos.ClienteServico;
import TG.Servicos.ContaServico;
import TG.DTOs.OperacaoMonetizacaoDTO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class RelatorioClientesMaisMonetizamView extends JFrame {
    private JTable tabela;
    private ClientesMaisMonetizamTableModel tableModel;
    private ContaServico contaServico = new ContaServico();
    private ClienteServico clienteServico = new ClienteServico();

    public RelatorioClientesMaisMonetizamView() {
        setTitle("Relatório de Clientes que Mais Monetizam");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Clientes que Mais Monetizam", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        tableModel = new ClientesMaisMonetizamTableModel();
        tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        carregarClientesMaisMonetizam();
    }

    private void carregarClientesMaisMonetizam() {
        try {
            List<OperacaoMonetizacaoDTO> operacoes = contaServico.listarOperacoesMonetizacao();
            Map<String, ClienteResumo> mapa = new HashMap<>();
            List<Cliente> clientes = clienteServico.listarClientes();
            Map<String, Cliente> clientesPorConta = new HashMap<>();
            for (Cliente c : clientes) {
                if (c.getConta() != null) {
                    clientesPorConta.put(c.getConta().getNumero(), c);
                }
            }
            for (OperacaoMonetizacaoDTO op : operacoes) {
                String conta = op.getContaOrigem();
                if (conta != null && clientesPorConta.containsKey(conta)) {
                    Cliente cli = clientesPorConta.get(conta);
                    ClienteResumo resumo = mapa.get(cli.getCpf());
                    if (resumo == null) {
                        resumo = new ClienteResumo(cli.getNome(), cli.getCpf(), 1);
                        mapa.put(cli.getCpf(), resumo);
                    } else {
                        resumo.quantidadeOperacoes++;
                    }
                }
            }
            List<ClienteResumo> lista = new ArrayList<>(mapa.values());
            lista.sort((a, b) -> Integer.compare(b.quantidadeOperacoes, a.quantidadeOperacoes));
            tableModel.setClientes(lista);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar relatório: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class ClienteResumo {
        String nome;
        String cpf;
        int quantidadeOperacoes;
        ClienteResumo(String nome, String cpf, int quantidadeOperacoes) {
            this.nome = nome;
            this.cpf = cpf;
            this.quantidadeOperacoes = quantidadeOperacoes;
        }
    }

    private static class ClientesMaisMonetizamTableModel extends AbstractTableModel {
        private String[] colunas = {"Nome", "CPF", "Quantidade de Operações"};
        private List<ClienteResumo> clientes = new ArrayList<>();

        public void setClientes(List<ClienteResumo> clientes) {
            this.clientes = clientes;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return clientes == null ? 0 : clientes.size();
        }

        @Override
        public int getColumnCount() {
            return colunas.length;
        }

        @Override
        public String getColumnName(int column) {
            return colunas[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ClienteResumo cliente = clientes.get(rowIndex);
            switch (columnIndex) {
                case 0: return cliente.nome;
                case 1: return cliente.cpf;
                case 2: return cliente.quantidadeOperacoes;
                default: return null;
            }
        }
    }
}
