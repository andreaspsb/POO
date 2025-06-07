package TG.Repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import TG.Modelos.Produto;
import TG.Modelos.Venda;

/**
 * Classe responsável por gerenciar o repositório de itens de vendas.
 * Permite adicionar e buscar itens de vendas, além de persistir essas
 * informações em um arquivo.
 */
public class VendaItemRepositorio {
    private String caminhoArquivo;

    // Método para adicionar um item de venda
    public void adicionarItensVenda(Venda venda) {
        if (venda == null || venda.getCodigo() == null || venda.getCodigo().isEmpty()) {
            throw new IllegalArgumentException("Venda ou código da venda não pode ser nulo ou vazio.");
        }
        if (venda.getProdutos() == null || venda.getProdutos().isEmpty()) {
            throw new IllegalArgumentException("Venda deve conter pelo menos um produto.");
        }
        String codigoVenda = venda.getCodigo();
        String codigoProduto;
        int quantidade;
        Map<Produto, Integer> produtos = venda.getProdutos();

        // percorrer o mapa de produtos e adicionar cada item de venda
        for (Map.Entry<Produto, Integer> entry : produtos.entrySet()) {
            codigoProduto = entry.getKey().getCodigo(); // Pega o código do produto
            quantidade = entry.getValue(); // Pega a quantidade do produto
            this.adicionarItemVenda(codigoVenda, codigoProduto, quantidade);
        }

    }

    public void adicionarItemVenda(String codigoVenda, String codigoProduto, int quantidade) {
        if (codigoVenda == null || codigoVenda.isEmpty()) {
            throw new IllegalArgumentException("Código da venda não pode ser nulo ou vazio.");
        }
        if (codigoProduto == null || codigoProduto.isEmpty()) {
            throw new IllegalArgumentException("Código do produto não pode ser nulo ou vazio.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            String itemVenda = String.format("%s,%s,%d", codigoVenda, codigoProduto, quantidade);
            writer.append(itemVenda);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar itens de venda por código de venda
    public Map<Produto, Integer> buscarItensPorCodigoVenda(String codigoVenda) {
        if (codigoVenda == null || codigoVenda.isEmpty()) {
            throw new IllegalArgumentException("Código da venda não pode ser nulo ou vazio.");
        }

        // Lógica para buscar os itens de venda no repositório
        Map<Produto, Integer> itensVenda = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length < 3) {
                    continue; // Ignorar linhas mal formatadas
                }
                String codigoVendaLinha = partes[0].trim();
                if (codigoVendaLinha.equals(codigoVenda)) {
                    String codigoProduto = partes[1].trim();
                    int quantidade = Integer.parseInt(partes[2].trim());
                    ProdutoRepositorio produtoRepositorio = new ProdutoRepositorio();
                    Produto produto = produtoRepositorio.buscarProdutoPorId(codigoProduto);
                    if (produto == null) {
                        throw new IllegalArgumentException("Produto com código " + codigoProduto + " não encontrado.");
                    }
                    itensVenda.put(produto, quantidade);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itensVenda;
    }    

}
