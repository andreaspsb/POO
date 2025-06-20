package TG.Servicos;

import java.math.BigDecimal;
import java.util.List;

import TG.Modelos.Conta;
import TG.Repositorios.ContaRepositorio;
import TG.Repositorios.OperacoesMonetizacaoRepositorio;
import TG.Excecoes.ContaNaoEncontradaException;
import TG.Excecoes.SaldoInsuficienteException;
import TG.Excecoes.OperacaoNaoPermitidaException;
import TG.Excecoes.ArquivoRepositorioException;
import TG.Excecoes.DadoInvalidoException;
import TG.DTOs.OperacaoMonetizacaoDTO;
import TG.DTOs.OperacaoMonetizacaoDTO.TipoOperacao;
import java.time.LocalDateTime;

/**
 * Classe responsável por gerenciar os serviços relacionados a contas.
 * Esta classe pode incluir métodos para adicionar, buscar e listar contas.
 */
public class ContaServico {

    private ContaRepositorio contaRepositorio;
    private OperacoesMonetizacaoRepositorio operacoesMonetizacaoRepositorio;    

    public ContaServico() {
        this.contaRepositorio = new ContaRepositorio();
        this.operacoesMonetizacaoRepositorio = new OperacoesMonetizacaoRepositorio();
    }
    
    public void adicionarConta() throws ArquivoRepositorioException, DadoInvalidoException {
        String numero = obterProximoNumeroConta();
        Conta conta = new Conta(numero, BigDecimal.ZERO);        

        contaRepositorio.adicionarConta(conta);
    }

    public void adicionarConta(Conta conta) throws ContaNaoEncontradaException, ArquivoRepositorioException, DadoInvalidoException {
        if (conta == null) {
            throw new DadoInvalidoException("Conta não pode ser nula.");
        }
        if (conta.getNumero() == null || conta.getNumero().isEmpty()) {
            throw new DadoInvalidoException("Número da conta não pode ser nulo ou vazio.");
        }
        if (conta.getNumero().length() != 8) {
            throw new DadoInvalidoException("Número da conta deve ter exatamente 8 dígitos.");
        }
        if (!conta.getNumero().matches("\\d{8}")) {
            throw new DadoInvalidoException("Número da conta deve conter apenas números.");
        }
        if (conta.getSaldo() == null) {
            throw new DadoInvalidoException("Saldo da conta não pode ser nulo.");
        }
        if (contaRepositorio.buscarContaPorNumero(conta.getNumero()) != null) {
            throw new ContaNaoEncontradaException("Já existe uma conta cadastrada com este número.");
        }

        contaRepositorio.adicionarConta(conta);
    }

    public Conta buscarContaPorNumero(String numero) throws ArquivoRepositorioException, DadoInvalidoException {
        if (numero == null || numero.isEmpty()) {
            throw new DadoInvalidoException("Número da conta não pode ser nulo ou vazio.");
        }
        return contaRepositorio.buscarContaPorNumero(numero);
    }
    
    public List<Conta> listarContas() throws ArquivoRepositorioException, DadoInvalidoException {
        return contaRepositorio.listarContas();
    }

    public void depositar(String numeroConta, BigDecimal valor) throws ContaNaoEncontradaException, OperacaoNaoPermitidaException, ArquivoRepositorioException, DadoInvalidoException {
        if (numeroConta == null || numeroConta.isEmpty()) {
            throw new OperacaoNaoPermitidaException("Número da conta não pode ser nulo ou vazio.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OperacaoNaoPermitidaException("Valor do depósito deve ser maior que zero.");
        }
        Conta conta = buscarContaPorNumero(numeroConta);
        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta não encontrada.");
        }
        conta.creditar(valor);
        contaRepositorio.atualizarConta(conta);
        // Registrar a operação de depósito no repositório de operações monetárias
        operacoesMonetizacaoRepositorio.registrarOperacao(
            new OperacaoMonetizacaoDTO(TipoOperacao.DEPOSITO, valor.doubleValue(), numeroConta, null, LocalDateTime.now())
        );
    }

    public BigDecimal consultarSaldo(String numeroConta) throws ContaNaoEncontradaException, ArquivoRepositorioException, DadoInvalidoException {
        if (numeroConta == null || numeroConta.isEmpty()) {
            throw new ContaNaoEncontradaException("Número da conta não pode ser nulo ou vazio.");
        }
        Conta conta = buscarContaPorNumero(numeroConta);
        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta não encontrada.");
        }
        return conta.getSaldo();
    }

    public void transferir(String numeroContaOrigem, String numeroContaDestino, BigDecimal valor) throws ContaNaoEncontradaException, SaldoInsuficienteException, OperacaoNaoPermitidaException, ArquivoRepositorioException, DadoInvalidoException {
        if (numeroContaOrigem == null || numeroContaOrigem.isEmpty()) {
            throw new OperacaoNaoPermitidaException("Número da conta de origem não pode ser nulo ou vazio.");
        }
        if (numeroContaDestino == null || numeroContaDestino.isEmpty()) {
            throw new OperacaoNaoPermitidaException("Número da conta de destino não pode ser nulo ou vazio.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OperacaoNaoPermitidaException("Valor da transferência deve ser maior que zero.");
        }
        Conta contaOrigem = buscarContaPorNumero(numeroContaOrigem);
        Conta contaDestino = buscarContaPorNumero(numeroContaDestino);
        if (contaOrigem == null) {
            throw new ContaNaoEncontradaException("Conta de origem não encontrada.");
        }
        if (contaDestino == null) {
            throw new ContaNaoEncontradaException("Conta de destino não encontrada.");
        }
        if (contaOrigem.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para transferência.");
        }
        contaOrigem.debitar(valor);
        contaDestino.creditar(valor);
        contaRepositorio.atualizarConta(contaOrigem);
        contaRepositorio.atualizarConta(contaDestino);
        // Registrar a operação de transferência no repositório de operações monetárias
        operacoesMonetizacaoRepositorio.registrarOperacao(
            new OperacaoMonetizacaoDTO(TipoOperacao.TRANSFERENCIA, valor.doubleValue(), numeroContaOrigem, numeroContaDestino, LocalDateTime.now())
        );
    }

    public void debitar(String numeroConta, BigDecimal valor) throws ContaNaoEncontradaException, SaldoInsuficienteException, OperacaoNaoPermitidaException, ArquivoRepositorioException, DadoInvalidoException {
        if (numeroConta == null || numeroConta.isEmpty()) {
            throw new OperacaoNaoPermitidaException("Número da conta não pode ser nulo ou vazio.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OperacaoNaoPermitidaException("Valor do saque deve ser maior que zero.");
        }
        Conta conta = buscarContaPorNumero(numeroConta);
        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta não encontrada.");
        }
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para saque.");
        }
        conta.debitar(valor);
        contaRepositorio.atualizarConta(conta);
    }

    private String obterProximoNumeroConta() throws ArquivoRepositorioException, DadoInvalidoException {
        List<Conta> contas = listarContas();
        if (contas.isEmpty()) {
            return "0001";
        }
        contas.sort((v1, v2) -> v1.getNumero().compareTo(v2.getNumero()));
        if (contas.size() < 1) {
            return "0001";
        }
        String ultimoCodigo = contas.get(contas.size() - 1).getNumero();
        int proximoNumero = Integer.parseInt(ultimoCodigo) + 1;
        return String.format("%04d", proximoNumero);
    }

    public List<OperacaoMonetizacaoDTO> listarOperacoesMonetizacao() throws ArquivoRepositorioException {
        return operacoesMonetizacaoRepositorio.listarOperacoesMonetizacao();
    }
}
