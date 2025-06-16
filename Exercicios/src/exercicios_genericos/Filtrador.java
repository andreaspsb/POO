package exercicios_genericos;

import java.util.List;
import java.util.function.Predicate;

public class Filtrador<T> {
    private T filtro;

    public Filtrador(T filtro) {
        this.filtro = filtro;
    }

    public T getFiltro() {
        return filtro;
    }

    public void setFiltro(T filtro) {
        this.filtro = filtro;
    }

    public boolean filtrar(T item) {
        return item.equals(filtro);
    }

    public List<T> filtrar(List<T> lista, Predicate<T> criterio) {
        if (lista == null || criterio == null) {
            throw new IllegalArgumentException("Lista e critério de filtragem não podem ser nulos.");
        }
        if (lista.isEmpty()) {
            return List.of(); // Retorna uma lista vazia se a lista original estiver vazia
        }
        return lista.stream()
                    .filter(criterio)
                    .toList();
    }

    @Override
    public String toString() {
        return "Filtrador{" +
                "filtro=" + filtro +
                '}';
    }

    public static void main(String[] args) {
        // Exemplo de uso do Filtrador
        Filtrador<String> filtrador = new Filtrador<>("teste");
        System.out.println(filtrador.filtrar("teste")); // Deve retornar true
        System.out.println(filtrador.filtrar("outro")); // Deve retornar false

        // Exemplo de filtragem de lista
        List<String> lista = List.of("teste", "outro", "teste2");
        List<String> resultado = filtrador.filtrar(lista, item -> item.startsWith("t"));
        System.out.println(resultado); // Deve imprimir ["teste", "teste2"]

        // filtrar palavras maiores que 5 letras, e números pares.
        Filtrador<Integer> filtradorNumeros = new Filtrador<>(0);
        List<Integer> numeros = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> numerosPares = filtradorNumeros.filtrar(numeros, numero -> numero % 2 == 0);
        System.out.println(numerosPares); // Deve imprimir [2, 4, 6, 8, 10]
        
        List<String> palavras = List.of("teste", "exemplo", "palavra", "java");
        List<String> palavrasMaioresQueCinco = filtrador.filtrar(palavras, palavra -> palavra.length() > 5);
        System.out.println(palavrasMaioresQueCinco); // Deve imprimir ["exemplo", "palavra"]
    }

}
