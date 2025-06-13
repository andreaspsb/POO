

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeChamados {

    public void salvarChamado(Chamado chamado, String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            writer.append(chamado.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Chamado> carregarChamados(String caminhoArquivo) {
        List<Chamado> chamados = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Chamado chamado = parseChamado(linha);
                if (chamado != null) {
                    chamados.add(chamado);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return chamados;
    }

    private Chamado parseChamado(String linha) {
        linha = linha.replace("Chamado{", "").replace("}", "");

        String[] partes = linha.split(",");
        if (partes.length != 4) {
            System.err.println("Formato inválido: " + linha);
            return null;
        }

        String cliente = partes[0].split("=")[1].trim();
        String descricao = partes[1].split("=")[1].trim();
        Urgencia urgencia = Urgencia.valueOf(partes[2].split("=")[1].trim());
        Status status = Status.valueOf(partes[3].split("=")[1].trim());
        return new Chamado(cliente, descricao, urgencia, status);
    }

    public void listarChamadosUrgenciaAlta(String caminhoArquivo) {
        List<Chamado> chamados = carregarChamados(caminhoArquivo);
        for (Chamado chamado : chamados) {
            if (chamado.getUrgencia() == Urgencia.ALTA) {
                System.out.println("Chamado de alta urgência: " + chamado);
            }
        }
    }

}
