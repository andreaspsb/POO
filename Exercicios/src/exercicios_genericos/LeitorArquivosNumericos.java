package exercicios_genericos;

import java.util.List;

public class LeitorArquivosNumericos {

    public static void main(String[] args) {
        String caminhoArquivo = "Exercicios/src/exercicios_genericos/numeros.txt";
        
        try {
            LeitorArquivosNumericos leitor = new LeitorArquivosNumericos();
            leitor.lerArquivoNumerico(caminhoArquivo);
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public void lerArquivoNumerico(String caminho) throws Exception {
        List<Double> numeros;
        try {
            numeros = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(caminho))
                .stream()
                .map(Double::parseDouble)
                .toList();
        } catch (java.io.IOException e) {
            throw new Exception("Erro ao ler o arquivo: " + e.getMessage());
        }
        catch (NumberFormatException e) {
            throw new Exception("Erro ao converter números do arquivo: " + e.getMessage());
        }

        if (numeros.isEmpty()) {
            throw new Exception("O arquivo está vazio ou não contém números válidos.");
        }

        System.out.println("Números lidos do arquivo:");
        for (Double numero : numeros) {
            System.out.println(numero);
        }              

    }

}
