package ExercicioAvaliativo02;

import java.util.Scanner;

public class CaraOuCoroa implements Jogo {

    @Override
    public Resultado executar(Scanner scanner) {
        System.out.println("Executando o jogo Cara ou Coroa...");
        // Lógica do jogo Cara ou Coroa
        // ...
        // Exemplo de implementação simples
        String[] opcoes = { "Cara", "Coroa" };
        String escolha = "";
        int resultado = (int) (Math.random() * 2); // 0 ou 1
        String resultadoJogo = opcoes[resultado];
        boolean acertou;
        boolean jogoValido = true;

        while (jogoValido) {
            System.out.println("Escolha: Cara ou Coroa?");
            escolha = scanner.next();
            if (escolha.equalsIgnoreCase("Cara") || escolha.equalsIgnoreCase("Coroa")) {
                jogoValido = false; // Sai do loop se a escolha for válida
            } else {
                System.out.println("Escolha inválida! Tente novamente.");
            }
        }

        if (escolha.equalsIgnoreCase(resultadoJogo)) {
            acertou = true;
        } else {
            acertou = false;
        }

        EResultado resultadoEnum;

        if (acertou) {
            resultadoEnum = EResultado.VITORIA;
        } else {
            resultadoEnum = EResultado.DERROTA;
        }

        int tentativas = 1; // Exemplo de contagem de tentativas
        Resultado resultadoObj = new Resultado("Cara ou Coroa", resultadoEnum, tentativas, resultadoJogo);
        return resultadoObj;
    }
}
