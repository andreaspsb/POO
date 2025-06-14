package TG.Servicos;

import java.util.List;

import TG.Excecoes.ArquivoRepositorioException;
import TG.Excecoes.DadoInvalidoException;
import TG.Excecoes.ProdutoJaCadastradoException;
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
    public void adicionarProduto(Produto produto) throws ProdutoJaCadastradoException, ArquivoRepositorioException, DadoInvalidoException {
        if (produto == null) {
            throw new DadoInvalidoException("Produto não pode ser nulo.");
        }
        if (produto.getCodigo() == null || produto.getNome() == null) {
            throw new DadoInvalidoException("Código e nome do produto não podem ser nulos.");
        }
        if (produto.getCodigo().isEmpty()) {
            throw new DadoInvalidoException("Código do produto não pode ser vazio.");
        }
        if (produtoRepositorio.buscarProdutoPorId(produto.getCodigo()) != null) {
            throw new ProdutoJaCadastradoException("Já existe um produto cadastrado com este código.");
        }
        if (produto.getCodigo().length() < 3) {
            throw new DadoInvalidoException("Código do produto deve ter pelo menos 3 caracteres.");
        }
        if (!produto.getCodigo().matches("[a-zA-Z0-9]+")) {
            throw new DadoInvalidoException("Código do produto deve conter apenas letras e números.");
        }
        if (produto.getNome().length() < 3) {
            throw new DadoInvalidoException("Nome do produto deve ter pelo menos 3 caracteres.");
        }
        if (produto.getPreco() < 0) {
            throw new DadoInvalidoException("Preço do produto não pode ser negativo.");
        }
        if (produto.getPreco() == 0) {
            throw new DadoInvalidoException("Preço do produto não pode ser zero.");
        }

        produtoRepositorio.adicionarProduto(produto);
    }

    public Produto buscarProdutoPorCodigo(String codigo) throws ArquivoRepositorioException, DadoInvalidoException {
        if (codigo == null || codigo.isEmpty()) {
            throw new DadoInvalidoException("Código do produto não pode ser nulo ou vazio.");
        }
        return produtoRepositorio.buscarProdutoPorId(codigo);
    }

    public List<Produto> listarProdutos() throws ArquivoRepositorioException, DadoInvalidoException {
        return produtoRepositorio.listarProdutos();
    }    
    
}
