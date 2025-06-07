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

    /**
     * Adiciona uma nova conta ao repositório.
     *
     * @param conta A conta a ser adicionada.
     */
    public void adicionarConta(Conta conta) {
        if (conta == null) {
            throw new IllegalArgumentException("Conta não pode ser nula.");
        }
        if (conta.getNumero() == null || conta.getSaldo() == null) {
            throw new IllegalArgumentException("Número da conta e saldo não podem ser nulos.");
        }
        if (conta.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Saldo da conta não pode ser negativo.");
        }
        if (conta.getNumero().isEmpty()) {
            throw new IllegalArgumentException("Número da conta não pode ser vazio.");
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

        conta.depositar(valor);
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

        contaOrigem.sacar(valor);
        contaDestino.depositar(valor);
    }
}
