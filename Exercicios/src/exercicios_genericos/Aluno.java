package exercicios_genericos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aluno<T> {
    private String nome;
    private List<T> notas;

    public Aluno(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }
        
        this.nome = nome;

        this.notas = new java.util.ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public double calcularMedia() {
        double soma = 0;
        if (notas.isEmpty()) {
            throw new IllegalArgumentException("Não há notas para calcular a média.");
        }
        for (T nota : notas) {
            if (nota instanceof Number) {
                soma += ((Number) nota).doubleValue();
            } else {
                throw new IllegalArgumentException("Notas devem ser do tipo numérico.");
            }
        }
        double media = soma / notas.size();
        return Math.round(media * 100.0) / 100.0; // Arredonda para duas casas decimais
    }

    public static void main(String[] args) {
        Map<String, Aluno<?>> alunos = new HashMap<>();

        // Exemplo de uso da classe Aluno com notas Double
        Aluno<Double> aluno = new Aluno<>("Maria");
        alunos.put(aluno.nome, aluno);
        aluno.notas.add(8.5);
        aluno.notas.add(7.0);
        aluno.notas.add(9.2);

        System.out.println("Aluno: " + aluno.getNome());
        System.out.println("Média: " + aluno.calcularMedia());

        Aluno<Integer> aluno2 = new Aluno<>("João");
        alunos.put(aluno2.nome, aluno2);
        aluno2.notas.add(8);
        aluno2.notas.add(7);
        aluno2.notas.add(9);

        System.out.println("Aluno2: " + aluno2.getNome());
        System.out.println("Média: " + aluno2.calcularMedia());

    }

}
