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
                // System.out.println("Parabéns! Você acertou o número em " + tentativas + "
                // tentativas.");
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

        Resultado resultado = new Resultado("Menor ou Maior", resultadoEnum, tentativas, Integer.toString(numeroAleatorio));
        return resultado;

        /*
         * System.out.println(resultado);
         * System.out.println("Você ganhou o jogo Menor ou Maior!");
         * System.out.println("Número aleatório era: " + numeroAleatorio);
         * System.out.println("Você fez " + tentativas + " tentativas.");
         * System.out.println("Obrigado por jogar!");
         * System.out.println("Deseja jogar novamente? (s/n)");
         * String resposta = new java.util.Scanner(System.in).next();
         * if (resposta.equalsIgnoreCase("s")) {
         * executar(); // Reinicia o jogo
         * } else {
         * System.out.println("Saindo do jogo Menor ou Maior...");
         * }
         */
    }

    public String pesquisarNaInternetSantoDoDia() {
        // Utilize uma biblioteca HTTP para realizar a requisição, como HttpClient

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.copilot.microsoft.com/santo-do-dia"))
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer YOUR_API_KEY") // Substitua pela sua chave de API
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Processar a resposta JSON
                String jsonResponse = response.body();
                // Aqui você pode usar uma biblioteca como Jackson ou Gson para processar o JSON
                System.out.println("Resposta da API: " + jsonResponse);
                // Exemplo de processamento simples
                // String santoDoDia = jsonResponse.substring(jsonResponse.indexOf("santo do
                // dia") + 30, jsonResponse.indexOf("santo do dia") + 60);
                // vamos tratar com Jackson
                // ObjectMapper objectMapper = new ObjectMapper();
                // JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                // String santoDoDia = jsonNode.get("santo").asText();
                // System.out.println("Santo do dia: " + santoDoDia);
                return jsonResponse; // Retorna o santo do dia
            } else {
                System.out.println("Erro ao buscar o santo do dia. Código de status: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Erro ao realizar a requisição: " + e.getMessage());
        }
        return "https://www.google.com/search?q=santo+do+dia";
    }

}

// Arquivo movido para /src/ExercicioAvaliativo02/MenorOuMaior.java
