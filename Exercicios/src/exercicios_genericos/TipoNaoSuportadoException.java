package exercicios_genericos;

public class TipoNaoSuportadoException extends Exception {
    private static final long serialVersionUID = 1L;

    public TipoNaoSuportadoException(String mensagem) {
        super(mensagem);
    }

}
