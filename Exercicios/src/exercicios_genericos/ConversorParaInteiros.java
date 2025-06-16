package exercicios_genericos;

import java.util.ArrayList;
import java.util.List;

public class ConversorParaInteiros<T> {
    public List<Integer> converter(T valor) throws TipoNaoSuportadoException {
        List<Integer> listaInteiros = new ArrayList<>();

        if (valor instanceof Integer) {
            listaInteiros.add((Integer) valor);
            System.out.println("Valor convertido: " + valor);
        } else if (valor instanceof String) {
            try {
                Integer inteiro = Integer.parseInt((String) valor);
                listaInteiros.add(inteiro);
                System.out.println("String convertida para inteiro: " + inteiro);
            } catch (NumberFormatException e) {
                System.err.println("Erro ao converter String para inteiro: " + valor);
            }
        } else {
            throw new TipoNaoSuportadoException("Tipo não suportado para conversão: " + valor.getClass().getName());
        }

        return listaInteiros;
    }

    // Método para testar a conversão
    public static void main(String[] args) {
        ConversorParaInteiros<Object> conversor = new ConversorParaInteiros<>();

        try {
            // Testando com um Integer
            List<Integer> resultado1 = conversor.converter(42);
            System.out.println("Resultado da conversão de Integer: " + resultado1);

            // Testando com uma String válida
            List<Integer> resultado2 = conversor.converter("123");
            System.out.println("Resultado da conversão de String: " + resultado2);

            // Testando com uma String inválida
            List<Integer> resultado3 = conversor.converter("abc");
            System.out.println("Resultado da conversão de String inválida: " + resultado3);

            // Testando com um tipo não suportado
            List<Integer> resultado4 = conversor.converter(3.14);
            System.out.println("Resultado da conversão de tipo não suportado: " + resultado4);
            
        } catch (TipoNaoSuportadoException e) {
            System.err.println(e.getMessage());
        }

    }

}
