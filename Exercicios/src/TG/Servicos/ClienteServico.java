package TG.Servicos;

import java.math.BigDecimal;
import java.util.List;

import TG.Modelos.Cliente;
import TG.Repositorios.ClienteRepositorio;

/**
 * Classe responsável por gerenciar os serviços relacionados a clientes.
 * Esta classe pode incluir métodos para adicionar, buscar e listar clientes.
 */
public class ClienteServico {

    private ClienteRepositorio clienteRepositorio;

    public ClienteServico() {
        this.clienteRepositorio = new ClienteRepositorio();
    }

    /**
     * Adiciona um novo cliente ao repositório.
     *
     * @param cliente O cliente a ser adicionado.
     */
    public void adicionarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        if (cliente.getCpf() == null || cliente.getNome() == null || cliente.getEmail() == null) {
            throw new IllegalArgumentException("CPF, nome e email do cliente não podem ser nulos.");
        }
        if (clienteRepositorio.buscarClientePorCPF(cliente.getCpf()) != null) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este CPF.");
        }
        if (cliente.getConta() == null) {
            throw new IllegalArgumentException("Conta do cliente não pode ser nula.");
        }
        if (cliente.getConta().getNumero() == null || cliente.getConta().getSaldo() == null) {
            throw new IllegalArgumentException("Número da conta e saldo não podem ser nulos.");
        }
        if (cliente.getConta().getSaldo().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Saldo da conta não pode ser negativo.");
        }
        if (cliente.getConta().getNumero().isEmpty()) {
            throw new IllegalArgumentException("Número da conta não pode ser vazio.");
        }
        if (cliente.getCpf().length() != 11) {
            throw new IllegalArgumentException("CPF do cliente deve ter 11 dígitos.");
        }
        if (!cliente.getCpf().matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF do cliente deve conter apenas números.");
        }
        if (cliente.getNome().length() < 3) {
            throw new IllegalArgumentException("Nome do cliente deve ter pelo menos 3 caracteres.");
        }
        if (!cliente.getNome().matches("[a-zA-Z\\s]+")) {
            throw new IllegalArgumentException("Nome do cliente deve conter apenas letras e espaços.");
        }
        if (cliente.getEmail().length() < 5) {
            throw new IllegalArgumentException("Email do cliente deve ter pelo menos 5 caracteres.");
        }
        if (!cliente.getEmail().matches("^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Email do cliente deve ser válido.");
        }
        if (cliente.getConta().getNumero().length() < 5) {
            throw new IllegalArgumentException("Número da conta deve ter pelo menos 5 caracteres.");
        }
        if (!cliente.getConta().getNumero().matches("\\d+")) {
            throw new IllegalArgumentException("Número da conta deve conter apenas números.");
        }
        // Verifica se a conta já existe no repositório, se não existir, adiciona a conta
        ContaServico contaServico = new ContaServico();
        if (contaServico.buscarContaPorNumero(cliente.getConta().getNumero()) == null) {
            contaServico.adicionarConta(cliente.getConta());
        }

        // Verifica se a conta já existe
        if (clienteRepositorio.listarClientes().stream()
                .anyMatch(c -> c.getConta().getNumero().equals(cliente.getConta().getNumero()))) {
            throw new IllegalArgumentException("Já existe uma conta cadastrada com este número.");
        }


        clienteRepositorio.adicionarCliente(cliente);
    }

    /**
     * Busca um cliente pelo CPF.
     *
     * @param cpf O CPF do cliente a ser buscado.
     * @return O cliente encontrado ou null se não encontrado.
     */
    public Cliente buscarClientePorCPF(String cpf) {
        return clienteRepositorio.buscarClientePorCPF(cpf);
    }

    /**
     * Lista todos os clientes cadastrados.
     *
     * @return Uma lista de clientes.
     */
    public List<Cliente> listarClientes() {
        return clienteRepositorio.listarClientes();
    }
}
