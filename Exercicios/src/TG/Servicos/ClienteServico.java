package TG.Servicos;

import java.util.List;

import TG.Modelos.Cliente;
import TG.Repositorios.ClienteRepositorio;

/**
 * Classe responsável por gerenciar os serviços relacionados a clientes.
 * Esta classe pode incluir métodos para adicionar, buscar e listar clientes.
 */
public class ClienteServico {

    private ClienteRepositorio clienteRepositorio;

    public ClienteServico(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
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
