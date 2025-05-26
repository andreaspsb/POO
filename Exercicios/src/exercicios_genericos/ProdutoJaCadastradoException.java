package exercicios_genericos;

public class ProdutoJaCadastradoException extends Exception {
    private static final long serialVersionUID = 1L;

    public ProdutoJaCadastradoException(String mensagem) {
        super(mensagem);
    }

}
