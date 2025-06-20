package TG.Views;

import TG.DTOs.ProdutoVendidoDTO;
import TG.Modelos.Venda;
import TG.Servicos.VendaServico;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioProdutosVendidosView extends JFrame {
    private JTable tabela;
    private ProdutosVendidosTableModel tableModel;
    private VendaServico vendaServico = new VendaServico();

    public RelatorioProdutosVendidosView() {
        setTitle("Relatório de Produtos Vendidos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Produtos Vendidos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        tableModel = new ProdutosVendidosTableModel();
        tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        // Carrega automaticamente os produtos vendidos ao abrir a janela
        carregarProdutosVendidos();
    }

    // Método para atualizar os dados da tabela
    public void atualizarTabela(List<ProdutoVendidoDTO> produtosVendidos) {
        tableModel.setProdutosVendidos(produtosVendidos);
    }

    /**
     * Atualiza a tabela com todos os produtos vendidos em todas as vendas.
     */
    public void carregarProdutosVendidos() {
        List<ProdutoVendidoDTO> produtosVendidos = new ArrayList<>();
        try {
            List<Venda> vendas = vendaServico.listarVendas();
            // Mapeia código do produto para DTO acumulando quantidade e valor
            java.util.Map<String, ProdutoVendidoDTO> mapa = new java.util.HashMap<>();
            for (Venda venda : vendas) {
                for (java.util.Map.Entry<TG.Modelos.Produto, Integer> entry : venda.getProdutos().entrySet()) {
                    TG.Modelos.Produto produto = entry.getKey();
                    int quantidade = entry.getValue();
                    double valorTotal = produto.getPreco() * quantidade;
                    ProdutoVendidoDTO dto = mapa.get(produto.getCodigo());
                    if (dto == null) {
                        dto = new ProdutoVendidoDTO(produto.getCodigo(), produto.getNome(), quantidade, valorTotal);
                        mapa.put(produto.getCodigo(), dto);
                    } else {
                        dto.setQuantidadeVendida(dto.getQuantidadeVendida() + quantidade);
                        dto.setValorTotal(dto.getValorTotal() + valorTotal);
                    }
                }
            }
            produtosVendidos.addAll(mapa.values());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos vendidos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        atualizarTabela(produtosVendidos);
    }

    // TableModel interno para produtos vendidos
    private static class ProdutosVendidosTableModel extends AbstractTableModel {
        private String[] colunas = {"Código", "Nome", "Quantidade Vendida", "Valor Total"};
        private List<ProdutoVendidoDTO> produtosVendidos;

        public void setProdutosVendidos(List<ProdutoVendidoDTO> produtosVendidos) {
            this.produtosVendidos = produtosVendidos;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return produtosVendidos == null ? 0 : produtosVendidos.size();
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
            ProdutoVendidoDTO produto = produtosVendidos.get(rowIndex);
            switch (columnIndex) {
                case 0: return produto.getCodigo();
                case 1: return produto.getNome();
                case 2: return produto.getQuantidadeVendida();
                case 3: return String.format("R$ %.2f", produto.getValorTotal());
                default: return null;
            }
        }
    }
}
