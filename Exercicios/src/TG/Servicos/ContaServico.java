package TG.Servicos;

import java.math.BigDecimal;
import java.util.List;

import TG.Modelos.Conta;
import TG.Repositorios.ContaRepositorio;

/**
 * Classe responsável por gerenciar os serviços relacionados a contas.
 * Esta classe pode incluir métodos para adicionar, buscar e listar contas.
 */
public class ContaServico {

    private ContaRepositorio contaRepositorio;

    public ContaServico() {
        this.contaRepositorio = new ContaRepositorio();
    }
    
    public void adicionarConta() {
        String numero = obterProximoNumeroConta();
        Conta conta = new Conta(numero, BigDecimal.ZERO);        

        contaRepositorio.adicionarConta(conta);
    }

    public void adicionarConta(Conta conta) {
        if (conta == null) {
            throw new IllegalArgumentException("Conta não pode ser nula.");
        }
        if (conta.getNumero() == null || conta.getNumero().isEmpty()) {
            throw new IllegalArgumentException("Número da conta não pode ser nulo ou vazio.");
        }
        if (conta.getNumero().length() != 8) {
            throw new IllegalArgumentException("Número da conta deve ter exatamente 8 dígitos.");
        }
        if (!conta.getNumero().matches("\\d{8}")) {
            throw new IllegalArgumentException("Número da conta deve conter apenas números.");
        }
        if (conta.getSaldo() == null) {
            throw new IllegalArgumentException("Saldo da conta não pode ser nulo.");
        }
        if (contaRepositorio.buscarContaPorNumero(conta.getNumero()) != null) {
            throw new IllegalArgumentException("Já existe uma conta cadastrada com este número.");
        }

        contaRepositorio.adicionarConta(conta);
    }

    public Conta buscarContaPorNumero(String numero) {
        if (numero == null || numero.isEmpty()) {
            throw new IllegalArgumentException("Número da conta não pode ser nulo ou vazio.");
        }
        return contaRepositorio.buscarContaPorNumero(numero);
    }
    
    public List<Conta> listarContas() {
        return contaRepositorio.listarContas();
    }

    public void depositar(String numeroConta, BigDecimal valor) {
        if (numeroConta == null || numeroConta.isEmpty()) {
            throw new IllegalArgumentException("Número da conta não pode ser nulo ou vazio.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do depósito deve ser maior que zero.");
        }

        Conta conta = buscarContaPorNumero(numeroConta);
        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada.");
        }

        conta.creditar(valor);
        contaRepositorio.atualizarConta(conta);
    }

    public BigDecimal consultarSaldo(String numeroConta) {
        if (numeroConta == null || numeroConta.isEmpty()) {
            throw new IllegalArgumentException("Número da conta não pode ser nulo ou vazio.");
        }

        Conta conta = buscarContaPorNumero(numeroConta);
        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada.");
        }

        return conta.getSaldo();
    }

    public void transferir(String numeroContaOrigem, String numeroContaDestino, BigDecimal valor) {
        if (numeroContaOrigem == null || numeroContaOrigem.isEmpty()) {
            throw new IllegalArgumentException("Número da conta de origem não pode ser nulo ou vazio.");
        }
        if (numeroContaDestino == null || numeroContaDestino.isEmpty()) {
            throw new IllegalArgumentException("Número da conta de destino não pode ser nulo ou vazio.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da transferência deve ser maior que zero.");
        }

        Conta contaOrigem = buscarContaPorNumero(numeroContaOrigem);
        Conta contaDestino = buscarContaPorNumero(numeroContaDestino);

        if (contaOrigem == null) {
            throw new IllegalArgumentException("Conta de origem não encontrada.");
        }
        if (contaDestino == null) {
            throw new IllegalArgumentException("Conta de destino não encontrada.");
        }

        contaOrigem.debitar(valor);
        contaDestino.creditar(valor);
        contaRepositorio.atualizarConta(contaOrigem);
        contaRepositorio.atualizarConta(contaDestino);
    }

    public void debitar(String numeroConta, BigDecimal valor) {
        if (numeroConta == null || numeroConta.isEmpty()) {
            throw new IllegalArgumentException("Número da conta não pode ser nulo ou vazio.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do saque deve ser maior que zero.");
        }

        Conta conta = buscarContaPorNumero(numeroConta);
        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada.");
        }

        conta.debitar(valor);
        contaRepositorio.atualizarConta(conta);
    }

    private String obterProximoNumeroConta() {
        List<Conta> contas = listarContas();
        if (contas.isEmpty()) {
            return "0001"; // Retorna o primeiro código se não houver vendas
        }
        // Obtém o último código de venda e incrementa para o próximo
        // Assumindo que os códigos de venda são numéricos e formatados como "0001", "0002", etc.
        contas.sort((v1, v2) -> v1.getNumero().compareTo(v2.getNumero()));
        if (contas.size() < 1) {
            return "0001"; // Retorna o primeiro código se não houver vendas
        }        

        String ultimoCodigo = contas.get(contas.size() - 1).getNumero();
        int proximoNumero = Integer.parseInt(ultimoCodigo) + 1;
        return String.format("%04d", proximoNumero); // Formata para 4 dígitos
    }
}
