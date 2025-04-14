import java.util.Random;

public class MenorOuMaior implements Jogo {

    public Resultado executar() {

        System.out.println("Executando o jogo Menor ou Maior...");
        // Lógica do jogo Menor ou Maior

        Random gerador = new Random();
        int numeroAleatorio = gerador.nextInt(100) + 1; // Número aleatório entre 1 e 100
        int tentativas = 0;
        int palpite = 0;
        boolean acertou = false; desistiu = false;
        while (!acertou) {
            System.out.print("Digite seu palpite (1 a 100): ");
            palpite = new java.util.Scanner(System.in).nextInt();
            tentativas++;

            if (palpite < 1 || palpite > 100) {
                System.out.println("Palpite inválido! Digite um número entre 1 e 100.");
                tentativas--; // Não conta como tentativa
                continue;
            }

            if (palpite == 0) {
                //System.out.println("Você desistiu do jogo.");
                desistiu = true;
                break;
            }

            if (palpite < numeroAleatorio) {
                System.out.println("Seu palpite é menor que o número aleatório.");
            } else if (palpite > numeroAleatorio) {
                System.out.println("Seu palpite é maior que o número aleatório.");
            } else {
                acertou = true;
                //System.out.println("Parabéns! Você acertou o número em " + tentativas + " tentativas.");
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

        Resultado resultado = new Resultado("Menor ou Maior", resultadoEnum, tentativas, numeroAleatorio);
        return resultado;
        
        /*System.out.println(resultado);
        System.out.println("Você ganhou o jogo Menor ou Maior!");
        System.out.println("Número aleatório era: " + numeroAleatorio);
        System.out.println("Você fez " + tentativas + " tentativas.");
        System.out.println("Obrigado por jogar!");
        System.out.println("Deseja jogar novamente? (s/n)");
        String resposta = new java.util.Scanner(System.in).next();
        if (resposta.equalsIgnoreCase("s")) {
            executar(); // Reinicia o jogo
        } else {
            System.out.println("Saindo do jogo Menor ou Maior...");
        }*/
    }
}
