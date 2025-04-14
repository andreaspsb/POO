public class App {

    public static void main(String[] args) {
        menu();
        //Jogo jogo1 = new MenorOuMaior();
        //Jogo jogo2 = new CaraOuCoroa();

        //jogo1.iniciar();
        //jogo2.iniciar();
    }

    public static void menu() {
        Jogo jogo = null;
        System.out.println("Escolha um jogo:");
        System.out.println("1. Menor ou Maior");
        System.out.println("2. Cara ou Coroa");
        System.out.println("3. Sair");
        System.out.print("Opção: ");
        int opcao = new java.util.Scanner(System.in).nextInt();
        switch (opcao) {
            case 1:
                System.out.println("Você escolheu Menor ou Maior.");
                jogo = new MenorOuMaior();
                break;
            case 2:
                System.out.println("Você escolheu Cara ou Coroa.");
                jogo = new CaraOuCoroa();
                break;
            case 3:
                sair();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                menu();
                break;
        }

        if (jogo != null) {
            Resultado resultado = jogo.executar();
            System.out.println(resultado.imprimirResultado());
        } 

        System.out.println("Deseja jogar novamente? (s/n)");
        String resposta = new java.util.Scanner(System.in).next();
        if (resposta.equalsIgnoreCase("s")) {
            menu(); // Reinicia o menu
        } else {
            sair();
        }
        
    }
    public static void sair() {
        System.out.println("Saindo do jogo...");
    }
    
}
