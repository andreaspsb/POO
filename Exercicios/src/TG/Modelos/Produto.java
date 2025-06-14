package TG.Modelos;

import TG.Excecoes.DadoInvalidoException;

public class Produto {
    private String codigo;
    private String nome;
    private double preco;

    public Produto(String codigo, String nome, double preco) throws DadoInvalidoException {
        if (codigo == null || nome == null) {
            throw new DadoInvalidoException("Código e nome do produto não podem ser nulos.");
        }
        if (preco < 0) {
            throw new DadoInvalidoException("Preço do produto não pode ser negativo.");
        }
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                '}';
    }
}
