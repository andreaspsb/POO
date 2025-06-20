package TG.Views;

import TG.Modelos.Cliente;
import TG.Modelos.Venda;
import TG.Servicos.VendaServico;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class RelatorioClientesMaisCompramView extends JFrame {
    private JTable tabela;
    private ClientesMaisCompramTableModel tableModel;
    private VendaServico vendaServico = new VendaServico();

    public RelatorioClientesMaisCompramView() {
        setTitle("Relatório de Clientes que Mais Compram");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Clientes que Mais Compram", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        tableModel = new ClientesMaisCompramTableModel();
        tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        carregarClientesMaisCompram();
    }

    private void carregarClientesMaisCompram() {
        try {
            List<Venda> vendas = vendaServico.listarVendas();
            Map<String, ClienteResumo> mapa = new HashMap<>();
            for (Venda venda : vendas) {
                if (venda.getStatus() != null && venda.getStatus().name().equals("CONCLUIDA")) {
                    Cliente cliente = venda.getCliente();
                    if (cliente != null) {
                        String cpf = cliente.getCpf();
                        ClienteResumo resumo = mapa.get(cpf);
                        if (resumo == null) {
                            resumo = new ClienteResumo(cliente.getNome(), cpf, 1);
                            mapa.put(cpf, resumo);
                        } else {
                            resumo.quantidadeVendas++;
                        }
                    }
                }
            }
            List<ClienteResumo> lista = new ArrayList<>(mapa.values());
            lista.sort((a, b) -> Integer.compare(b.quantidadeVendas, a.quantidadeVendas));
            tableModel.setClientes(lista);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar relatório: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class ClienteResumo {
        String nome;
        String cpf;
        int quantidadeVendas;
        ClienteResumo(String nome, String cpf, int quantidadeVendas) {
            this.nome = nome;
            this.cpf = cpf;
            this.quantidadeVendas = quantidadeVendas;
        }
    }

    private static class ClientesMaisCompramTableModel extends AbstractTableModel {
        private String[] colunas = {"Nome", "CPF", "Quantidade de Compras"};
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
                case 2: return cliente.quantidadeVendas;
                default: return null;
            }
        }
    }
}
