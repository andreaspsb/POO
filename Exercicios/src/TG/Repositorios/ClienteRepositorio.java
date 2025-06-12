package TG.Repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import TG.Modelos.Cliente;
import TG.Modelos.Conta;

/**
 * Classe responsável por gerenciar o repositório de clientes.
 * Permite adicionar e buscar clientes, além de persistir essas informações em um arquivo.
 */
public class ClienteRepositorio {
    
    private String caminhoArquivo = "Exercicios/src/TG/Arquivos/clientes.txt";
    
    public void adicionarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
          
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            writer.append(cliente.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Cliente buscarClientePorCPF(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }

        List<Cliente> clientes = listarClientes();
        
        for (Cliente cliente : clientes) {
             if (cliente.getCpf().equals(cpf)) {
                 return cliente;
             }
        }
        return null; // Retornar o cliente encontrado ou null se não encontrado
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Cliente cliente = parseCliente(linha);
                if (cliente != null) {
                    clientes.add(cliente);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        Conta conta = contaRepositorio.buscarContaPorNumero(contaAux);
        return new Cliente(cpf, nome, email, conta);
    }
    
}
