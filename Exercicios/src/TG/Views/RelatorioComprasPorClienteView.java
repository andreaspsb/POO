package TG.Views;

import TG.Modelos.Cliente;
import TG.Modelos.Venda;
import TG.Servicos.ClienteServico;
import TG.Servicos.VendaServico;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RelatorioComprasPorClienteView extends JFrame {
    private JFormattedTextField campoCpf;
    private JButton btnBuscar;
    private JTable tabela;
    private ComprasClienteTableModel tableModel;
    private ClienteServico clienteServico = new ClienteServico();
    private VendaServico vendaServico = new VendaServico();

    public RelatorioComprasPorClienteView() {
        setTitle("Relatório de Compras por Cliente");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelFiltro = new JPanel(new FlowLayout());
        painelFiltro.add(new JLabel("CPF do Cliente:"));
        try {
            MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
            cpfMask.setPlaceholderCharacter('_');
            campoCpf = new JFormattedTextField(cpfMask);
            campoCpf.setColumns(15);
        } catch (Exception e) {
            campoCpf = new JFormattedTextField();
            campoCpf.setColumns(15);
        }
        painelFiltro.add(campoCpf);
        btnBuscar = new JButton("Buscar");
        painelFiltro.add(btnBuscar);
        add(painelFiltro, BorderLayout.NORTH);

        tableModel = new ComprasClienteTableModel();
        tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarComprasPorCliente();
            }
        });
    }

    private void buscarComprasPorCliente() {
 String cpf = campoCpf.getText().replaceAll("[.\\-]", "").trim();
        if (cpf.isEmpty() || cpf.contains("_")) {
            JOptionPane.showMessageDialog(this, "Informe o CPF do cliente.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Cliente cliente = clienteServico.buscarClientePorCPF(cpf);
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<Venda> vendas = vendaServico.listarVendas();
            java.util.List<Venda> vendasDoCliente = new java.util.ArrayList<>();
            for (Venda venda : vendas) {
 if (venda.getCliente() != null && cpf.equals(venda.getCliente().getCpf().replaceAll("[.\\-]", ""))) {
                    vendasDoCliente.add(venda);
                }
            }
            if (vendasDoCliente.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O cliente não possui compras.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                tableModel.setVendas(null);
                return;
            }
            tableModel.setVendas(vendasDoCliente);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar compras: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class ComprasClienteTableModel extends AbstractTableModel {
        private String[] colunas = {"Código Venda", "Data", "Status", "Total"};
        private List<Venda> vendas;

        public void setVendas(List<Venda> vendas) {
            this.vendas = vendas;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return vendas == null ? 0 : vendas.size();
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
            Venda venda = vendas.get(rowIndex);
            switch (columnIndex) {
                case 0: return venda.getCodigo();
                case 1: return venda.getDateTimeConclusao() != null ? venda.getDateTimeConclusao().toString() : "-";
                case 2: return venda.getStatus();
                case 3: return String.format("R$ %.2f", venda.calcularTotal());
                default: return null;
            }
        }
    }
}
