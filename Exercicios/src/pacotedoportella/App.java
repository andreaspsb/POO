import java.util.List;

public class App {
    //sistema para cadastrasr histórias em quadrinhos
    public static void main(String[] args) {
        // Criação do objeto Scanner para ler a entrada do usuário
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        // Exibe o menu de opções
        System.out.println("Escolha uma opção:");
        System.out.println("1. Cadastrar História em Quadrinhos");
        System.out.println("2. Pesquisar História em Quadrinhos");
        System.out.println("3. Sair");

        // Lê a opção escolhida pelo usuário
        int opcao = scanner.nextInt();

        // Verifica a opção escolhida e executa a ação correspondente
        switch (opcao) {
            case 1:
                // Chama o método para cadastrar história em quadrinhos
                cadastrarHistoriaQuadrinhos();
                break;
            case 2:
                // Chama o método para pesquisar história em quadrinhos
                pesquisarHistoriaQuadrinhos();
                break;
            case 3:
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
    public static void cadastrarHistoriaQuadrinhos() {
        // Lógica para cadastrar história em quadrinhos
        System.out.println("Cadastrar História em Quadrinhos");
        // Aqui você pode adicionar a lógica para cadastrar a história em quadrinhos
        // Exemplo: solicitar informações ao usuário, armazenar em uma lista, etc.
        // Exemplo de informações a serem solicitadas
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("Digite o título da história: ");
        String titulo = scanner.nextLine();
        System.out.print("Digite o autor da história: ");
        String autor = scanner.nextLine();
        System.out.print("Digite o ano de publicação: ");
        int anoPublicacao = scanner.nextInt();
        System.out.print("Digite o gênero da história: ");
        String genero = scanner.nextLine();
        // Aqui você pode adicionar a lógica para armazenar as informações em uma lista ou banco de dados
        List<HistoriaQuadrinhos> historias = new java.util.ArrayList<>();
        HistoriaQuadrinhos historia = new HistoriaQuadrinhos(titulo, autor, anoPublicacao, genero);
        historias.add(historia);
        System.out.println("História em quadrinhos cadastrada com sucesso!");

        // Exemplo de impressão das informações cadastradas
    }
}
