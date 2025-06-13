package ExercicioAvaliativo02;

public enum EResultado {

    VITORIA("Vitoria"),
    DERROTA("Derrota"),
    EMPATE("Empate"),
    DESISTENCIA("Desistencia");

    private String resultado;

    EResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    @Override
    public String toString() {
        return resultado;
    }
}
