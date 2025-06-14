package TG.Repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import TG.Excecoes.ArquivoRepositorioException;
import TG.Excecoes.DadoInvalidoException;
import TG.Modelos.Cliente;
import TG.Modelos.Conta;

/**
 * Classe responsável por gerenciar o repositório de clientes.
 * Permite adicionar e buscar clientes, além de persistir essas informações em um arquivo.
 */
public class ClienteRepositorio {
    
    private String caminhoArquivo = "Exercicios/src/TG/Arquivos/clientes.txt";
    
    public void adicionarCliente(Cliente cliente) throws ArquivoRepositorioException, DadoInvalidoException {
        if (cliente == null) {
            throw new DadoInvalidoException("Cliente não pode ser nulo");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            writer.append(cliente.toString());
            writer.newLine();
        } catch (IOException e) {
            throw new ArquivoRepositorioException("Erro ao adicionar cliente ao arquivo.", e);
        }
    }
    
    public Cliente buscarClientePorCPF(String cpf) throws ArquivoRepositorioException, DadoInvalidoException {
        if (cpf == null || cpf.isEmpty()) {
            throw new DadoInvalidoException("CPF não pode ser nulo ou vazio");
        }
        List<Cliente> clientes = listarClientes();
        for (Cliente cliente : clientes) {
             if (cliente.getCpf().equals(cpf)) {
                 return cliente;
             }
        }
        return null;
    }

    public List<Cliente> listarClientes() throws ArquivoRepositorioException, DadoInvalidoException {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Cliente cliente;
                try {
                    cliente = parseCliente(linha);
                } catch (Exception e) {
                    throw new DadoInvalidoException("Erro ao fazer parsing do cliente: " + e.getMessage());
                }
                if (cliente != null) {
                    clientes.add(cliente);
                }
            }
        } catch (IOException e) {
            throw new ArquivoRepositorioException("Erro ao ler clientes do arquivo.", e);
        }
        return clientes;
    }

    private Cliente parseCliente(String linha) {
        linha = linha.replace("Cliente{", "").replace("}", "").trim();
        
        String[] partes = linha.split(", ");
        if (partes.length != 4) {
            System.err.println("Formato inválido: " + linha);
            return null;
        }
        String cpf = partes[0].split("=")[1].trim().replace("'", "");
        String nome = partes[1].split("=")[1].trim().replace("'", "");
        String email = partes[2].split("=")[1].trim().replace("'", "");
        String contaAux = partes[3].split("=")[1].trim(); // Se necessário, pode ser processado
        
        ContaRepositorio contaRepositorio = new ContaRepositorio();
        Conta conta = null;
        try {
            conta = contaRepositorio.buscarContaPorNumero(contaAux);
        } catch (Exception e) {
            System.err.println("Erro ao buscar conta: " + e.getMessage());
        }
        try {
            return new Cliente(cpf, nome, email, conta);
        } catch (Exception e) {
            System.err.println("Erro ao criar cliente: " + e.getMessage());
            return null;
        }
    }
    
}
