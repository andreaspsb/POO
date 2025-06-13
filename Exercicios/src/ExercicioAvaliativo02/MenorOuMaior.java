package ExercicioAvaliativo02;

import java.util.Random;
import java.util.Scanner;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class MenorOuMaior implements Jogo {

    public Resultado executar(Scanner scanner) {

        System.out.println("Executando o jogo Menor ou Maior...");
        // Lógica do jogo Menor ou Maior
        // ...

        Random gerador = new Random();
        int numeroAleatorio = gerador.nextInt(100) + 1; // Número aleatório entre 1 e 100
        int tentativas = 0;
        int palpite = 0;
        boolean acertou = false;
        boolean desistiu = false;
        while (!acertou && !desistiu) {
            System.out.print("Digite seu palpite (1 a 100): ");
            palpite = scanner.nextInt();
            tentativas++;

            if (palpite == 0) {
                System.out.println("Você desistiu do jogo.");
                desistiu = true;
                break;
            }

            if (palpite < 1 || palpite > 100) {
                System.out.println("Palpite inválido! Digite um número entre 1 e 100.");
                tentativas--; // Não conta como tentativa
                continue;
            }

            if (palpite < numeroAleatorio) {
                System.out.println("Seu palpite é menor que o número aleatório.");
            } else if (palpite > numeroAleatorio) {
                System.out.println("Seu palpite é maior que o número aleatório.");
            } else {
                acertou = true;
            }
        }

        EResultado resultadoEnum;
        if (acertou) {
            resultadoEnum = EResultado.VITORIA;
        } else {
            resultadoEnum = EResultado.DERROTA;
        }

        if (desistiu) {
            resultadoEnum = EResultado.DESISTENCIA;
        }

        Resultado resultado = new Resultado("Menor ou Maior", resultadoEnum, tentativas, String.valueOf(numeroAleatorio));
        return resultado;
    }
}
