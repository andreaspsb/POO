

import java.util.List;

public class App {
    public static void main(String[] args) {
        GerenciadorDeChamados gerenciador = new GerenciadorDeChamados();
        String caminhoArquivo = "chamados.txt";

        // limpar o arquivo antes de começar
        try {
            new java.io.PrintWriter(caminhoArquivo).close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }


        // Criar um novo chamado
        Chamado chamado1 = new Chamado("Cliente A", "Problema com o produto", Urgencia.ALTA, Status.ABERTO);
        gerenciador.salvarChamado(chamado1, caminhoArquivo);

        // Criar outro chamado
        Chamado chamado2 = new Chamado("Cliente B", "Dúvida sobre o serviço", Urgencia.MEDIA, Status.EM_ATENDIMENTO);
        gerenciador.salvarChamado(chamado2, caminhoArquivo);
        
        // Criar mais um chamado
        Chamado chamado3 = new Chamado("Cliente C", "Reclamação sobre o atendimento", Urgencia.BAIXA, Status.RESOLVIDO);
        gerenciador.salvarChamado(chamado3, caminhoArquivo);

        // Carregar chamados do arquivo
        List<Chamado> chamados = gerenciador.carregarChamados(caminhoArquivo);
        for (Chamado chamado : chamados) {
            System.out.println(chamado);
        }

        System.out.println();

        // Listar chamados de alta urgência
        System.out.println("Chamados de alta urgência:");
        gerenciador.listarChamadosUrgenciaAlta(caminhoArquivo);
    }

}
