package TG.Repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import TG.Excecoes.ArquivoRepositorioException;
import TG.Excecoes.DadoInvalidoException;
import TG.Modelos.Produto;

/**
 * Classe responsável por gerenciar o repositório de clientes.
 * Permite adicionar e buscar clientes, além de persistir essas informações em um arquivo.
 */
public class ProdutoRepositorio {

    private String caminhoArquivo = "Exercicios/src/TG/Arquivos/produtos.txt";

    public void adicionarProduto(Produto produto) throws ArquivoRepositorioException, DadoInvalidoException {
        if (produto == null) {
            throw new DadoInvalidoException("Produto não pode ser nulo");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            writer.append(produto.toString());
            writer.newLine();
        } catch (IOException e) {
            throw new ArquivoRepositorioException("Erro ao adicionar produto ao arquivo.", e);
        }
    }

    // Exemplo de método para buscar um produto por ID
    public Produto buscarProdutoPorId(String codigo) throws ArquivoRepositorioException, DadoInvalidoException {
        if (codigo == null || codigo.isEmpty()) {
            throw new DadoInvalidoException("ID do produto não pode ser nulo ou vazio");
        }
        List<Produto> produtos = listarProdutos();
        for (Produto produto : produtos) {
            if (produto.getCodigo().equals(codigo)) {
                return produto; // Retornar o produto encontrado
            }
        }
        return null; // Retornar o produto encontrado ou null se não encontrado
    }

    // Exemplo de método para listar todos os produtos
    public List<Produto> listarProdutos() throws ArquivoRepositorioException, DadoInvalidoException {
        List<Produto> produtos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Produto produto;
                try {
                    produto = parseProduto(linha);
                } catch (Exception e) {
                    throw new DadoInvalidoException("Erro ao fazer parsing do produto: " + e.getMessage());
                }
                if (produto != null) {
                    produtos.add(produto);
                }
            }
        } catch (IOException e) {
            throw new ArquivoRepositorioException("Erro ao ler produtos do arquivo.", e);
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
        try {
            return new Produto(codigo, nome, preco);
        } catch (Exception e) {
            System.err.println("Erro ao criar produto: " + e.getMessage());
            return null;
        }
    }

}
