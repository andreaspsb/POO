import java.util.Random;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class MenorOuMaior implements Jogo {

    public Resultado executar() {

        System.out.println("Executando o jogo Menor ou Maior...");
        // Lógica do jogo Menor ou Maior
        // ...

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

        int idade = null; // Aqui você pode adicionar lógica para obter a idade do jogador, se necessário
        System.out.println("Digite sua idade: ");
        idade = new java.util.Scanner(System.in).nextInt();
        Resultado resultado = new Resultado("Menor ou Maior", resultadoEnum, tentativas, numeroAleatorio, idade);
        // Aqui você pode adicionar lógica para imprimir o resultado, se necessário
        // Exemplo de impressão do resultado
        // System.out.println(resultado.imprimirResultado());
        Resultado resultado = new Resultado("Menor ou Maior", resultadoEnum, tentativas, numeroAleatorio,);
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
    
    public String pesquisarNaInternetSantoDoDia () {
        httpreq = new HttpRequest();
        httpreq.setUrl("https://www.google.com/search?q=santo+do+dia");
        httpreq.setMethod("GET");
        httpreq.setHeaders("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
        httpreq.setHeaders("Accept-Language", "en-US,en;q=0.5");
        httpreq.setHeaders("Accept-Encoding", "gzip, deflate, br");
        httpreq.setHeaders("Connection", "keep-alive");
        httpreq.setHeaders("Upgrade-Insecure-Requests", "1");
        httpreq.setHeaders("Cache-Control", "max-age=0");
        httpreq.setHeaders("Pragma", "no-cache");
        httpreq.setHeaders("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpreq.setHeaders("DNT", "1");
        //retornar os dados do santo do dia
        // Aqui você pode adicionar lógica para processar a resposta da requisição
        // Exemplo de impressão da resposta
        // System.out.println(httpreq.getResponse());
        //capturar os 30 caracteres seguintes à palavra "santo do dia"
        // String resposta = httpreq.getResponse();
        // String santoDoDia = resposta.substring(resposta.indexOf("santo do dia") + 30, resposta.indexOf("santo do dia") + 60);
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
                // String santoDoDia = jsonResponse.substring(jsonResponse.indexOf("santo do dia") + 30, jsonResponse.indexOf("santo do dia") + 60);
                //vamos tratar com Jackson
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
