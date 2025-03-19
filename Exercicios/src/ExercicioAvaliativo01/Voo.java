package ExercicioAvaliativo01;

public class Voo {

    private Aeroporto origem;
    private Aeroporto destino;

    private Integer numero, vagas;
    private String horarioPartida, horarioChegada;

    public Voo(Integer numero, Integer vagas, String horarioPartida, String horarioChegada) {
        this.numero = numero;
        this.vagas = vagas;
        this.horarioPartida = horarioPartida;
        this.horarioChegada = horarioChegada;
    }

    public Integer getNumero() {
        return this.numero;
    }

}
