package ExercicioAvaliativo01;

public class Aeroporto {

    private String nome, codigo, cidade;

    public Aeroporto(String nome, String codigo, String cidade) {
        this.nome = nome;
        this.codigo = codigo;
        this.cidade = cidade;
    }

    public String getNome() {
        return this.nome;
    }
    public String getCodigo() {
        return this.codigo;
    }
    public String getCidade() {
        return this.cidade;
    }

}
