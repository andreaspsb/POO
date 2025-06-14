package TG.Repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import TG.Excecoes.ArquivoRepositorioException;
import TG.Excecoes.DadoInvalidoException;
import TG.Modelos.Conta;

/**
 * Classe responsável por gerenciar o repositório de clientes.
 * Permite adicionar e buscar clientes, além de persistir essas informações em um arquivo.
 */
public class ContaRepositorio{

    private String caminhoArquivo = "Exercicios/src/TG/Arquivos/contas.txt";    

    public void adicionarConta(Conta conta) throws ArquivoRepositorioException, DadoInvalidoException {
        if (conta == null) {
            throw new DadoInvalidoException("Conta não pode ser nula");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            writer.append(conta.toString());
            writer.newLine();
        } catch (IOException e) {
            throw new ArquivoRepositorioException("Erro ao adicionar conta ao arquivo.", e);
        }
    }

    public Conta buscarContaPorNumero(String numero) throws ArquivoRepositorioException, DadoInvalidoException {
        if (numero == null || numero.isEmpty()) {
            throw new DadoInvalidoException("Número da conta não pode ser nulo ou vazio");
        }
        List<Conta> contas;
        try {
            contas = listarContas();
        } catch (DadoInvalidoException e) {
            throw new DadoInvalidoException("Erro ao listar contas: " + e.getMessage());
        }
        for (Conta conta : contas) {
            if (conta.getNumero().equals(numero)) {
                return conta;
            }
        }
        return null;
    }

    public List<Conta> listarContas() throws ArquivoRepositorioException, DadoInvalidoException {
        List<Conta> contas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Conta conta;
                try {
                    conta = parseConta(linha);
                } catch (Exception e) {
                    throw new DadoInvalidoException("Erro ao fazer parsing da conta: " + e.getMessage());
                }
                if (conta != null) {
                    contas.add(conta);
                }
            }
        } catch (IOException e) {
            throw new ArquivoRepositorioException("Erro ao ler contas do arquivo.", e);
        }
        return contas;
    }

    private Conta parseConta(String linha) {
        linha = linha.replace("Conta{", "").replace("}", "").trim();

        String[] partes = linha.split(",");
        if (partes.length < 2) {
            return null; // Retornar null se a linha não estiver no formato esperado
        }
        String numero = partes[0].split("=")[1].trim().replace("'", "");
        BigDecimal saldo;
        try {
            saldo = new BigDecimal(partes[1].split("=")[1].trim());
        } catch (NumberFormatException e) {
            return null; // Retornar null se o saldo não for um número válido
        }
        try {
            return new Conta(numero, saldo);
        } catch (Exception e) {
            System.err.println("Erro ao criar conta: " + e.getMessage());
            return null;
        }
    }

    public void atualizarConta(Conta conta) throws ArquivoRepositorioException, DadoInvalidoException {
        if (conta == null) {
            throw new DadoInvalidoException("Conta não pode ser nula");
        }
        List<Conta> contas;
        try {
            contas = listarContas();
        } catch (DadoInvalidoException e) {
            throw new DadoInvalidoException("Erro ao listar contas: " + e.getMessage());
        }
        boolean encontrado = false;
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getNumero().equals(conta.getNumero())) {
                contas.set(i, conta);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new DadoInvalidoException("Conta com número " + conta.getNumero() + " não encontrada.");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Conta c : contas) {
                writer.append(c.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ArquivoRepositorioException("Erro ao atualizar conta no arquivo.", e);
        }
    }
    

}