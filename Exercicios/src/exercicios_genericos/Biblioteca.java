package exercicios_genericos;

import java.util.HashSet;
import java.util.Set;

public class Biblioteca<T> {
    private Set<T> itens;
    private int quantidadeLivros;
    
    public Biblioteca(int capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser maior que zero.");
        }
        itens = new HashSet<>(capacidade);
        quantidadeLivros = 0;
    }

    public void adicionarLivro(T livro) {
        if (livro == null) {
            throw new IllegalArgumentException("Livro não pode ser nulo.");
        }
        if (itens.add(livro)) {
            quantidadeLivros++;
            System.out.println("Livro adicionado: " + livro);
        } else {
            System.out.println("Livro já existe na biblioteca: " + livro);
        }
    }

    public void listarLivros() {
        System.out.println("Livros na biblioteca:");
        if (quantidadeLivros == 0) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            for (T livro : itens) {
                System.out.println(livro);
            }
        }
    }
    public int getQuantidadeLivros() {
        return quantidadeLivros;
    }

    public static class Livro {
        private String titulo;
        
        public Livro(String titulo) {
            this.titulo = titulo;
        }
        
        @Override
        public String toString() {
            return titulo;
        }
    }

    public static class Revista {
        private String titulo;
        
        public Revista(String titulo) {
            this.titulo = titulo;
        }
        
        @Override
        public String toString() {
            return titulo;
        }
    }

    // Método para testar a biblioteca
    public static void main(String[] args) {
        Biblioteca<Livro> biblioteca = new Biblioteca<>(5);
        
        Livro livro1 = new Livro("O Senhor dos Anéis");
        Livro livro2 = new Livro("1984");
        Livro livro3 = new Livro("Dom Casmurro");
        
        biblioteca.adicionarLivro(livro1);
        biblioteca.adicionarLivro(livro2);
        biblioteca.adicionarLivro(livro3);
        biblioteca.adicionarLivro(livro1); // Tentativa de adicionar um livro duplicado
        
        biblioteca.listarLivros();
        
        System.out.println("Quantidade de livros na biblioteca: " + biblioteca.getQuantidadeLivros());
    }

}
