public class Resultado {

    private String nomeJogo;
    private EResultado resultado;
    private int tentativas;
    private int sorteado;
    private int idade;

    public Resultado(String nomeJogo, EResultado resultado, int tentativas, int sorteado, int idade) {
        this.nomeJogo = nomeJogo;
        this.resultado = resultado;
        this.tentativas = tentativas;
        this.sorteado = sorteado;
        this.idade = idade;

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
    public int getSorteado() {
        return sorteado;
    }
    public void setSorteado(int sorteado) {
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resultado)) return false;
        Resultado resultado1 = (Resultado) o;
        return tentativas == resultado1.tentativas && sorteado == resultado1.sorteado && nomeJogo.equals(resultado1.nomeJogo) && resultado == resultado1.resultado;
    }
    @Override
    public int hashCode() {
        return Objects.hash(nomeJogo, resultado, tentativas, sorteado);
    }
    @Override

    public int multiplicarPelaIdade(int idade) {
        return this.tentativas * idade;
    }
    public int somarComSorteado(int numero) {
        return this.sorteado + numero;
    }
}
