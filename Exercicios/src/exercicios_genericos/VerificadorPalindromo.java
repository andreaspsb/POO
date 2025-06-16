package exercicios_genericos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class VerificadorPalindromo {
    public static boolean isPalindromo(String texto) throws PalavraInvalidaException {
        if (texto == null || texto.isEmpty()) {
            return false;
        }

        if (!texto.matches("[a-zA-Z0-9\\s]+")) {
            throw new PalavraInvalidaException("Texto inválido para verificação de palíndromo.");
        }
        
        String textoLimpo = texto.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        int inicio = 0;
        int fim = textoLimpo.length() - 1;

        while (inicio < fim) {
            if (textoLimpo.charAt(inicio) != textoLimpo.charAt(fim)) {
                return false;
            }
            inicio++;
            fim--;
        }
        
        return true;
    }

    public static void main(String[] args) {
        Set<String> palavras = new HashSet<>();

        // leitura de um arquivo de texto
        String texto = "Exercicios/src/exercicios_genericos/palavras.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(texto))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                try {
                    if (isPalindromo(linha)) {
                        palavras.add(linha);                    
                    }
                } catch (PalavraInvalidaException e) {
                    System.err.println("Erro ao verificar palíndromo: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Erro inesperado: " + e.getMessage());
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        if (palavras.isEmpty()) {
            System.out.println("Nenhuma palavra palíndroma encontrada.");
        } else {
            System.out.println("Palavras palíndromas encontradas: " + palavras);
        }

    }       

}
