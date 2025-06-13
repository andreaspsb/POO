public class SocioAtleta {

    private String nome;

    private int idade;

    private double altura;

    private int categoria;

    public SocioAtleta(String nome, int idade, double altura) {
        this.nome = nome;
        this.idade = idade;
        this.altura = altura;
        this.categoria = this.calcularCategoria();
    }

    public int calcularCategoria() {
        int categoria;
        if (this.idade <= 12) {
            if (this.altura <= 1.55) {
                categoria = 10;
            } else {
                categoria = 20;
            }
        } else {
            if (this.altura <= 1.68) {
                categoria = 30;
            } else {
                categoria = 40;
            }
        }

        return categoria;
    }

    public void revisarAltura(int altura) {
        this.altura = altura;
        this.categoria = this.calcularCategoria();
    }

    public String getNome() {
        return this.nome;
    }

    public int getIdade() {
        return this.idade;
    }

    public double getAltura() {
        return this.altura;
    }

    public int getCategoria() {
        return this.categoria;
    }

}
