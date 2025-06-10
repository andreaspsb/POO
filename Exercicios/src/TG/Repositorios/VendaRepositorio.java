package TG.Repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import TG.Modelos.Cliente;
import TG.Modelos.EnumStatusVenda;
import TG.Modelos.Venda;

/**
 * Classe responsável por gerenciar o repositório de clientes.
 * Permite adicionar e buscar clientes, além de persistir essas informações em um arquivo.
 */
public class VendaRepositorio {
    
    private String caminhoArquivo = "/Exercicios/src/TG/Arquivos/vendas.txt";

    public void adicionarVenda(Venda venda) {
        if (venda == null) {
            throw new IllegalArgumentException("Venda não pode ser nula.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            writer.append(venda.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        VendaItemRepositorio vendaItemRepositorio = new VendaItemRepositorio();
        vendaItemRepositorio.adicionarItensVenda(venda);
    }

    public Venda buscarVendaPorCodigo(String codigo) {
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("Código da venda não pode ser nulo ou vazio.");
        }

        List<Venda> vendas = listarVendas();
        for (Venda venda : vendas) {
            if (venda.getCodigo().equals(codigo)) {
                return venda;
            }
        }
        return null;
    }

    public List<Venda> listarVendas() {
        List<Venda> vendas = new ArrayList<>();
        VendaItemRepositorio vendaItemRepositorio = new VendaItemRepositorio();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Venda venda = parseVenda(linha);
                if (venda != null) {
                    vendas.add(venda);
                    venda.setProdutos(vendaItemRepositorio.buscarItensPorCodigoVenda(venda.getCodigo()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vendas;
    }

    private Venda parseVenda(String linha) {
        String[] partes = linha.split(",");
        if (partes.length < 5) {
            return null; 
        }
        String codigo = partes[0].trim();
        LocalDateTime dataHoraAbertura = LocalDateTime.parse(partes[1].trim());
        LocalDateTime dataHoraConclusao = LocalDateTime.parse(partes[2].trim());
        EnumStatusVenda status = new EnumStatusVenda(partes[3].trim().toUpperCase());
        String clienteId = partes[4].trim();
        ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
        Cliente cliente = clienteRepositorio.buscarClientePorCPF(clienteId);
        Venda venda = new Venda(codigo, dataHoraAbertura, dataHoraConclusao, status, cliente);
        return venda;
    }

    public void atualizarVenda(Venda venda) {
        if (venda == null) {
            throw new IllegalArgumentException("Venda não pode ser nula.");
        }

        List<Venda> vendas = listarVendas();
        for (int i = 0; i < vendas.size(); i++) {
            if (vendas.get(i).getCodigo().equals(venda.getCodigo())) {
                vendas.set(i, venda);
                break;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Venda v : vendas) {
                writer.append(v.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

}
