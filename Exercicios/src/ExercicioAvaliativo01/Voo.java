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
    public Integer getVagas() {
        return this.vagas;
    }
    public String getHorarioPartida() {
        return this.horarioPartida;
    }
    public String getHorarioChegada() {
        return this.horarioChegada;
    }
    public Aeroporto getOrigem() {
        return this.origem;
    }
    public Aeroporto getDestino() {
        return this.destino;
    }
    

}
