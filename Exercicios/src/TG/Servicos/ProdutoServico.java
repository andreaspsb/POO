package TG.Servicos;

import java.util.List;

import TG.Modelos.Produto;
import TG.Repositorios.ProdutoRepositorio;

/**
 * Classe responsável por gerenciar os serviços relacionados a contas.
 * Esta classe pode incluir métodos para adicionar, buscar e listar contas.
 */
public class ProdutoServico {

    private ProdutoRepositorio produtoRepositorio;

    public ProdutoServico() {
        this.produtoRepositorio = new ProdutoRepositorio();
    }

    /**
     * Adiciona um novo produto ao repositório.
     *
     * @param produto O produto a ser adicionado.
     */
    public void adicionarProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo.");
        }
        if (produto.getCodigo() == null || produto.getNome() == null) {
            throw new IllegalArgumentException("Código e nome do produto não podem ser nulos.");
        }
        if (produto.getCodigo().isEmpty()) {
            throw new IllegalArgumentException("Código do produto não pode ser vazio.");
        }
        if (produtoRepositorio.buscarProdutoPorId(produto.getCodigo()) != null) {
            throw new IllegalArgumentException("Já existe um produto cadastrado com este código.");
        }
        if (produto.getCodigo().length() < 3) {
            throw new IllegalArgumentException("Código do produto deve ter pelo menos 3 caracteres.");
        }
        if (!produto.getCodigo().matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("Código do produto deve conter apenas letras e números.");
        }        
        if (produto.getNome().length() < 3) {
            throw new IllegalArgumentException("Nome do produto deve ter pelo menos 3 caracteres.");
        }
        if (!produto.getNome().matches("[a-zA-Z\\s]+")) {
            throw new IllegalArgumentException("Nome do produto deve conter apenas letras e espaços.");
        }        
        if (produto.getPreco() < 0) {
            throw new IllegalArgumentException("Preço do produto não pode ser negativo.");
        }
        if (produto.getPreco() == 0) {
            throw new IllegalArgumentException("Preço do produto não pode ser zero.");
        }

        produtoRepositorio.adicionarProduto(produto);
    }

    public Produto buscarProdutoPorId(String codigo) {
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("Código do produto não pode ser nulo ou vazio.");
        }
        return produtoRepositorio.buscarProdutoPorId(codigo);
    }

    public List<Produto> listarProdutos() {
        return produtoRepositorio.listarProdutos();
    }
    
}
