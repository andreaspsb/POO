package TG.Excecoes;

public class ArquivoRepositorioException extends Exception {
    public ArquivoRepositorioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    public ArquivoRepositorioException(String mensagem) {
        super(mensagem);
    }
}
