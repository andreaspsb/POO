public class Resultado {

    private String nomeJogo;
    private EResultado resultado;
    private int tentativas;
    private int sorteado;

    public Resultado(String nomeJogo, EResultado resultado, int tentativas, int sorteado) {
        this.nomeJogo = nomeJogo;
        this.resultado = resultado;
        this.tentativas = tentativas;
        this.sorteado = sorteado;
    }
    
    public String imprimirResultado() {
        return "Resultado{" +
                "nomeJogo='" + nomeJogo + '\'' +
                ", resultado=" + resultado +
                ", tentativas=" + tentativas +
                ", sorteado=" + sorteado +
                '}';
    }

}
