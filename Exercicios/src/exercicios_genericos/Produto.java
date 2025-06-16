package exercicios_genericos;

public class Produto {
    private String nome;
    private String codigo;
    private int qtdEstoque;

    public Produto(String nome, String codigo, int qtdEstoque) {
        this.nome = nome;
        this.codigo = codigo;
        this.qtdEstoque = qtdEstoque;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }
}
