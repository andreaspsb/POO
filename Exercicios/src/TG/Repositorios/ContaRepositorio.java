package TG.Repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import TG.Modelos.Conta;

/**
 * Classe responsável por gerenciar o repositório de clientes.
 * Permite adicionar e buscar clientes, além de persistir essas informações em um arquivo.
 */
public class ContaRepositorio{

    private String caminhoArquivo;    

    public void adicionarConta(Conta conta) {
        if (conta == null) {
            throw new IllegalArgumentException("Conta não pode ser nula");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            writer.append(conta.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Conta buscarContaPorNumero(String numero) {
        if (numero == null || numero.isEmpty()) {
            throw new IllegalArgumentException("Número da conta não pode ser nulo ou vazio");
        }

        List<Conta> contas = listarContas();

        for (Conta conta : contas) {
            if (conta.getNumero().equals(numero)) {
                return conta;
            }
        }
        return null; // Retornar a conta encontrada ou null se não encontrada
    }

    public List<Conta> listarContas() {
        List<Conta> contas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Conta conta = parseConta(linha);
                if (conta != null) {
                    contas.add(conta);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contas;
    }

    private Conta parseConta(String linha) {
        String[] partes = linha.split(",");
        if (partes.length < 2) {
            return null; // Retornar null se a linha não estiver no formato esperado
        }
        String numero = partes[0].trim();
        BigDecimal saldo;
        try {
            saldo = new BigDecimal(partes[1].trim());
        } catch (NumberFormatException e) {
            return null; // Retornar null se o saldo não for um número válido
        }
        return new Conta(numero, saldo);
    }

    public void atualizarConta(Conta conta) {
        if (conta == null) {
            throw new IllegalArgumentException("Conta não pode ser nula");
        }

        List<Conta> contas = listarContas();
        boolean encontrado = false;

        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getNumero().equals(conta.getNumero())) {
                contas.set(i, conta);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new IllegalArgumentException("Conta com número " + conta.getNumero() + " não encontrada.");
        }

        // Reescrever o arquivo com as contas atualizadas
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Conta c : contas) {
                writer.append(c.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    

}