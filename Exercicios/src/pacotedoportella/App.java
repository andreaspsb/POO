package pacotedoportella;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    static List<HistoriaQuadrinhos> historias;

    // sistema para cadastrar histórias em quadrinhos
    public static void main(String[] args) {
        historias = new ArrayList<>();

        // Criação do objeto Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Exibe o menu de opções
        System.out.println("Escolha uma opção:");
        System.out.println("1. Cadastrar História em Quadrinhos");
        System.out.println("2. Pesquisar História em Quadrinhos");
        System.out.println("3. Exibir Todas as Histórias Cadastradas");
        System.out.println("4. Sair");
        System.out.println("Escolha uma opção:");

        // Lê a opção escolhida pelo usuário
        int opcao = scanner.nextInt();

        // Verifica a opção escolhida e executa a ação correspondente
        switch (opcao) {
            case 1:
                // Chama o método para cadastrar história em quadrinhos
                cadastrarHistoriaQuadrinhos(scanner);
                break;
            case 2:
                // Chama o método para pesquisar história em quadrinhos
                pesquisarHistoriaQuadrinhos(scanner);
                break;
            case 3:
                // Chama o método para exibir todas as histórias cadastradas
                exibirHistoriasCadastradas();
                break;
            case 4:
                // Encerra o programa
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }

        // Fecha o scanner
        scanner.close();
    }

    // Método para cadastrar história em quadrinhos
    public static void cadastrarHistoriaQuadrinhos(Scanner scanner) {
        // Lógica para cadastrar história em quadrinhos
        System.out.println("Cadastrar História em Quadrinhos");
        // Aqui você pode adicionar a lógica para cadastrar a história em quadrinhos
        // Exemplo: solicitar informações ao usuário, armazenar em uma lista, etc.
        // Exemplo de informações a serem solicitadas        
        System.out.print("Digite o título da história: ");
        String titulo = scanner.nextLine();
        System.out.print("Digite o autor da história: ");
        String autor = scanner.nextLine();
        System.out.print("Digite o ano de publicação: ");
        int anoPublicacao = scanner.nextInt();
        System.out.print("Digite o gênero da história: ");
        String genero = scanner.nextLine();
        // Aqui você pode adicionar a lógica para armazenar as informações em uma lista
        // ou banco de dados
        // List<HistoriaQuadrinhos> historias = new java.util.ArrayList<>();
        HistoriaQuadrinhos historia = new HistoriaQuadrinhos(titulo, autor, anoPublicacao, genero);
        historias.add(historia);
        System.out.println("História em quadrinhos cadastrada com sucesso!");

        // Exemplo de impressão das informações cadastradas
    }

    public static void pesquisarHistoriaQuadrinhos(Scanner scanner) {
        // Lógica para pesquisar história em quadrinhos
        System.out.println("Pesquisar História em Quadrinhos");
        // Aqui você pode adicionar a lógica para pesquisar a história em quadrinhos
        // Exemplo: solicitar informações ao usuário, buscar na lista, etc.
        System.out.print("Digite o título da história que deseja pesquisar: ");
        String titulo = scanner.nextLine();
        // Aqui você pode adicionar a lógica para buscar as informações na lista ou
        // banco de dados
        // Exemplo de busca na lista (supondo que você tenha uma lista de histórias
        // cadastradas)
        for (HistoriaQuadrinhos historia : historias) {
            if (historia.toString().contains(titulo)) {
                System.out.println("História em quadrinhos encontrada: " + titulo);
                System.out.println(historia);
            }
        }
        // Se a história não for encontrada, exibe uma mensagem
        if (historias.stream().noneMatch(h -> h.toString().contains(titulo))) {
            System.out.println("História em quadrinhos não encontrada.");
        }
    }

    // Método para exibir todas as histórias cadastradas
    public static void exibirHistoriasCadastradas() {
        System.out.println("Histórias em quadrinhos cadastradas:");
        for (HistoriaQuadrinhos historia : historias) {
            System.out.println(historia);
        }
    }

    // Classe para representar uma história em quadrinhos
    static class HistoriaQuadrinhos {
        private String titulo;
        private String autor;
        private int anoPublicacao;
        private String genero;

        public HistoriaQuadrinhos(String titulo, String autor, int anoPublicacao, String genero) {
            this.titulo = titulo;
            this.autor = autor;
            this.anoPublicacao = anoPublicacao;
            this.genero = genero;
        }

        @Override
        public String toString() {
            return "Título: " + titulo + ", Autor: " + autor + ", Ano de Publicação: " + anoPublicacao + ", Gênero: "
                    + genero;
        }
    }
}
