package TG.Excecoes;

public class OperacaoNaoPermitidaException extends Exception {
    public OperacaoNaoPermitidaException(String mensagem) {
        super(mensagem);
    }
}
