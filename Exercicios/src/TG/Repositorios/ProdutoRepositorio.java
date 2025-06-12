package TG.Repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import TG.Modelos.Produto;

/**
 * Classe responsável por gerenciar o repositório de clientes.
 * Permite adicionar e buscar clientes, além de persistir essas informações em um arquivo.
 */
public class ProdutoRepositorio {

    private String caminhoArquivo = "Exercicios/src/TG/Arquivos/produtos.txt";

    public void adicionarProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            writer.append(produto.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar produto ao repositório", e);
        }
    }

    // Exemplo de método para buscar um produto por ID
    public Produto buscarProdutoPorId(String codigo) {
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("ID do produto não pode ser nulo ou vazio");
        }
        // Lógica para buscar o produto no repositório
        List<Produto> produtos = listarProdutos();
        for (Produto produto : produtos) {
            if (produto.getCodigo().equals(codigo)) {
                return produto; // Retornar o produto encontrado
            }
        }

        return null; // Retornar o produto encontrado ou null se não encontrado
    }

    // Exemplo de método para listar todos os produtos
    public List<Produto> listarProdutos() {
        // Lógica para listar todos os produtos no repositório
        List<Produto> produtos = new ArrayList<>();
        // Aqui você pode ler do arquivo e popular a lista de produtos
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Produto produto = parseProduto(linha);
                if (produto != null) {
                    produtos.add(produto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return produtos;
    }

    private Produto parseProduto(String linha) {
        linha = linha.replace("Produto{", "").replace("}", "").trim();

        String[] partes = linha.split(",");
        if (partes.length < 3) {
            return null; // Retornar null se a linha não estiver no formato esperado
        }        
        
        String codigo = partes[0].split("=")[1].trim().replace("\'", "");
        String nome = partes[1].split("=")[1].trim().replace("\'", "");
        double preco = Double.parseDouble(partes[2].split("=")[1].trim());
        return new Produto(codigo, nome, preco);
    }

}
