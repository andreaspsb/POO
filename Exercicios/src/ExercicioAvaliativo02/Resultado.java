
public class Resultado {

    private String nomeJogo;
    private EResultado resultado;
    private int tentativas;
    private String sorteado;

    public Resultado(String nomeJogo, EResultado resultado, int tentativas, String sorteado) {
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
    
    public String getNomeJogo() {
        return nomeJogo;
    }
    public void setNomeJogo(String nomeJogo) {
        this.nomeJogo = nomeJogo;
    }
    public EResultado getResultado() {
        return resultado;
    }
    public void setResultado(EResultado resultado) {
        this.resultado = resultado;
    }
    public int getTentativas() {
        return tentativas;
    }
    public void setTentativas(int tentativas) {
        this.tentativas = tentativas;
    }
    public String getSorteado() {
        return sorteado;
    }
    public void setSorteado(String sorteado) {
        this.sorteado = sorteado;
    }
    @Override
    public String toString() {
        return "Resultado{" +
                "nomeJogo='" + nomeJogo + '\'' +
                ", resultado=" + resultado +
                ", tentativas=" + tentativas +
                ", sorteado=" + sorteado +
                '}';
    }
}
