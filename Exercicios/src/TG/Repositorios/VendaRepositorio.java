package TG.Repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import TG.Excecoes.ArquivoRepositorioException;
import TG.Excecoes.DadoInvalidoException;
import TG.Excecoes.ProdutoNaoEncontradoException;
import TG.Modelos.Cliente;
import TG.Modelos.EnumStatusVenda;
import TG.Modelos.Venda;

/**
 * Classe responsável por gerenciar o repositório de clientes.
 * Permite adicionar e buscar clientes, além de persistir essas informações em um arquivo.
 */
public class VendaRepositorio {
    
    private String caminhoArquivo = "Exercicios/src/TG/Arquivos/vendas.txt";

    public void adicionarVenda(Venda venda) throws ArquivoRepositorioException, DadoInvalidoException {
        if (venda == null) {
            throw new DadoInvalidoException("Venda não pode ser nula.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            writer.append(venda.toString());
            writer.newLine();
        } catch (IOException e) {
            throw new ArquivoRepositorioException("Erro ao adicionar venda no arquivo.", e);
        }
        
    }

    public void adicionarItensVenda(Venda venda) throws ArquivoRepositorioException, DadoInvalidoException {
        if (venda == null || venda.getProdutos() == null || venda.getProdutos().isEmpty()) {
            throw new DadoInvalidoException("Venda ou produtos não podem ser nulos ou vazios.");
        }

        VendaItemRepositorio vendaItemRepositorio = new VendaItemRepositorio();
        vendaItemRepositorio.adicionarItensVenda(venda);
    }

    public Venda buscarVendaPorCodigo(String codigo) throws ArquivoRepositorioException, DadoInvalidoException {
        if (codigo == null || codigo.isEmpty()) {
            throw new DadoInvalidoException("Código da venda não pode ser nulo ou vazio.");
        }

        List<Venda> vendas = listarVendas();
        for (Venda venda : vendas) {
            if (venda.getCodigo().equals(codigo)) {
                return venda;
            }
        }
        return null;
    }

    public List<Venda> listarVendas() throws ArquivoRepositorioException, DadoInvalidoException {
        List<Venda> vendas = new ArrayList<>();
        VendaItemRepositorio vendaItemRepositorio = new VendaItemRepositorio();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Venda venda;
                try {
                    venda = parseVenda(linha);
                } catch (Exception e) {
                    throw new DadoInvalidoException("Erro ao fazer parsing da venda: " + e.getMessage());
                }
                if (venda != null) {
                    vendas.add(venda);
                    try {
                        venda.setProdutos(vendaItemRepositorio.buscarItensPorCodigoVenda(venda.getCodigo()));
                    } catch (ProdutoNaoEncontradoException e) {
                        throw new DadoInvalidoException("Produto não encontrado ao listar itens da venda: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            throw new ArquivoRepositorioException("Erro ao listar vendas do arquivo.", e);
        }
        return vendas;
    }

    private Venda parseVenda(String linha) throws DadoInvalidoException, ArquivoRepositorioException {
        try {
            linha = linha.replace("Venda{", "").replace("}", "").trim();
            String[] partes = linha.split(",");
            if (partes.length < 5) {
                throw new DadoInvalidoException("Linha de venda mal formatada.");
            }
            String codigo = partes[0].split("=")[1].trim().replace("'", "");
            LocalDateTime dataHoraAbertura = LocalDateTime.parse(partes[1].split("=")[1].trim());

            LocalDateTime dataHoraConclusao;
            if (partes[2].split("=").length < 2 || partes[2].split("=")[1].trim().isEmpty() || partes[2].split("=")[1].trim().equals("null")) {
                dataHoraConclusao = null;
            } else {
                dataHoraConclusao = LocalDateTime.parse(partes[2].split("=")[1].trim());
            }

            EnumStatusVenda status = EnumStatusVenda.valueOf(partes[3].split("=")[1].trim().toUpperCase());

            String clienteId = partes[4].split("=")[1].trim();

            ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
            Cliente cliente = clienteRepositorio.buscarClientePorCPF(clienteId);
            if (cliente == null) {
                throw new DadoInvalidoException("Cliente com CPF " + clienteId + " não encontrado.");
            }
            return new Venda(codigo, dataHoraAbertura, dataHoraConclusao, status, cliente);
        } catch (Exception e) {
            throw new DadoInvalidoException("Erro ao fazer parsing da venda: " + e.getMessage());
        }
    }

    public void atualizarVenda(Venda venda) throws ArquivoRepositorioException, DadoInvalidoException {
        if (venda == null) {
            throw new DadoInvalidoException("Venda não pode ser nula.");
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
            throw new ArquivoRepositorioException("Erro ao atualizar venda no arquivo.", e);
        }
    }

    public Venda buscarVendaEmAberto() throws ArquivoRepositorioException, DadoInvalidoException {
        List<Venda> vendas = listarVendas();
        for (Venda venda : vendas) {
            if (venda.getStatus() == EnumStatusVenda.ABERTA) {
                return venda;
            }
        }
        return null;
    }    

}
