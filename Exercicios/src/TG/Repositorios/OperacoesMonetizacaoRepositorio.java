package TG.Repositorios;

import TG.DTOs.OperacaoMonetizacaoDTO;
import TG.DTOs.OperacaoMonetizacaoDTO.TipoOperacao;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import TG.Excecoes.ArquivoRepositorioException;
import TG.Excecoes.DadoInvalidoException;

public class OperacoesMonetizacaoRepositorio {

    private String caminhoArquivo = "Exercicios/src/TG/Arquivos/operacoesMonetizacao.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public void registrarOperacao(OperacaoMonetizacaoDTO dto) throws DadoInvalidoException, ArquivoRepositorioException {
        if (dto == null) {
            throw new DadoInvalidoException("Operação não pode ser nula");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            // Formato: tipo;valor;contaOrigem;contaDestino;dataHora
            writer.append(dto.getTipo().name() + ";" + dto.getValor() + ";" +
                (dto.getContaOrigem() != null ? dto.getContaOrigem() : "") + ";" +
                (dto.getContaDestino() != null ? dto.getContaDestino() : "") + ";" +
                dto.getDataHora().format(FORMATTER));
            writer.newLine();
        } catch (IOException e) {
            throw new ArquivoRepositorioException("Erro ao registrar operação no arquivo.", e);
        }
    }

    public List<OperacaoMonetizacaoDTO> listarOperacoesMonetizacao() throws ArquivoRepositorioException {
        List<OperacaoMonetizacaoDTO> operacoes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 5) {
                    TipoOperacao tipo = TipoOperacao.valueOf(partes[0]);
                    double valor = Double.parseDouble(partes[1]);
                    String contaOrigem = partes[2].isEmpty() ? null : partes[2];
                    String contaDestino = partes[3].isEmpty() ? null : partes[3];
                    LocalDateTime dataHora = LocalDateTime.parse(partes[4], FORMATTER);
                    operacoes.add(new OperacaoMonetizacaoDTO(tipo, valor, contaOrigem, contaDestino, dataHora));
                }
            }
        } catch (IOException e) {
            throw new ArquivoRepositorioException("Erro ao ler operações do arquivo.", e);
        }
        return operacoes;
    }
}
