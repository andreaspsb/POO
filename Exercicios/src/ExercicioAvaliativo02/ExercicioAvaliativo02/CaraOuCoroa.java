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
            // System.out.println("Você ganhou! O resultado foi: " + resultadoJogo);
        } else {
            acertou = false;
            // System.out.println("Você perdeu! O resultado foi: " + resultadoJogo);
        }

        EResultado resultadoEnum;

        if (acertou) {
            resultadoEnum = EResultado.VITORIA;
        } else {
            resultadoEnum = EResultado.DERROTA;
        }

        // System.out.println("Resultado: " + resultadoJogo);
        // Aqui você pode adicionar lógica para contar tentativas, etc.
        // Exemplo de contagem de tentativas
        int tentativas = 1; // Exemplo de contagem de tentativas
        Resultado resultadoObj = new Resultado("Cara ou Coroa", resultadoEnum, tentativas, resultadoJogo);
        return resultadoObj;

        /*
         * System.out.println(resultadoObj);
         * System.out.println("Você ganhou o jogo Cara ou Coroa!");
         * System.out.println("Você fez " + tentativas + " tentativas.");
         * System.out.println("Obrigado por jogar!");
         * System.out.println("Deseja jogar novamente? (s/n)");
         * String resposta = new java.util.Scanner(System.in).next();
         * if (resposta.equalsIgnoreCase("s")) {
         * executar(); // Reinicia o jogo
         * } else {
         * System.out.println("Saindo do jogo Cara ou Coroa...");
         * }
         */

    }

}
