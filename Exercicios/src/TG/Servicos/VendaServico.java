package TG.Servicos;

import java.time.LocalDateTime;
import java.util.List;

import TG.Modelos.Cliente;
import TG.Modelos.EnumStatusVenda;
import TG.Modelos.Venda;
import TG.Repositorios.VendaRepositorio;
import TG.Excecoes.VendaInvalidaException;
import TG.Excecoes.SaldoInsuficienteException;
import TG.Excecoes.DadoInvalidoException;
import TG.Excecoes.ArquivoRepositorioException;
import TG.Excecoes.ContaNaoEncontradaException;
import TG.Excecoes.OperacaoNaoPermitidaException;

/**
 * Classe responsável por gerenciar os serviços relacionados a vendas.
 * Esta classe pode incluir métodos para adicionar, buscar e listar vendas.
 */
public class VendaServico {

    private VendaRepositorio vendaRepositorio;

    public VendaServico() {
        this.vendaRepositorio = new VendaRepositorio();
    }    

    /**
     * Adiciona uma nova venda ao repositório.
     *
     * @param venda A venda a ser adicionada.
     */
    public void adicionarVenda(Cliente cliente) throws VendaInvalidaException, DadoInvalidoException, ArquivoRepositorioException {
        if (cliente == null) {
            throw new DadoInvalidoException("Cliente não pode ser nulo.");
        }
        String codigo = this.obterProximoCodigoVenda();
        Venda venda = new Venda(codigo, cliente);
        vendaRepositorio.adicionarVenda(venda);
    }

    //método para finalizar a venda
    public void finalizarVenda(Venda venda) throws VendaInvalidaException, SaldoInsuficienteException, DadoInvalidoException, ArquivoRepositorioException, ContaNaoEncontradaException, OperacaoNaoPermitidaException {
        if (venda == null) {
            throw new DadoInvalidoException("Venda não pode ser nula.");
        }
        if (venda.getProdutos().isEmpty()) {
            throw new VendaInvalidaException("Não é possível finalizar a venda sem produtos.");
        }
        double total = venda.calcularTotal();
        if (total <= 0) {
            throw new VendaInvalidaException("O total da venda deve ser maior que zero.");
        }
        if (venda.getCliente().getConta().getSaldo().compareTo(java.math.BigDecimal.valueOf(total)) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente na conta do cliente para finalizar a venda.");
        }
        ContaServico contaServico = new ContaServico();
        contaServico.debitar(venda.getCliente().getConta().getNumero(), java.math.BigDecimal.valueOf(total));
        venda.setDateTimeConclusao(LocalDateTime.now());
        venda.setStatus(EnumStatusVenda.CONCLUIDA);
        vendaRepositorio.adicionarItensVenda(venda);
        vendaRepositorio.atualizarVenda(venda);
    }

    public Venda buscarVendaPorCodigo(String codigo) throws DadoInvalidoException, ArquivoRepositorioException {
        if (codigo == null || codigo.isEmpty()) {
            throw new DadoInvalidoException("Código da venda não pode ser nulo ou vazio.");
        }
        return vendaRepositorio.buscarVendaPorCodigo(codigo);
    }

    public List<Venda> listarVendas() throws DadoInvalidoException, ArquivoRepositorioException {
        return vendaRepositorio.listarVendas();
    }

    private String obterProximoCodigoVenda() throws ArquivoRepositorioException, DadoInvalidoException {
        List<Venda> vendas = listarVendas();
        if (vendas.isEmpty()) {
            return "0001"; // Retorna o primeiro código se não houver vendas
        }
        // Obtém o último código de venda e incrementa para o próximo
        // Assumindo que os códigos de venda são numéricos e formatados como "0001", "0002", etc.
        vendas.sort((v1, v2) -> v1.getCodigo().compareTo(v2.getCodigo()));
        if (vendas.size() < 1) {
            return "0001"; // Retorna o primeiro código se não houver vendas
        }        

        String ultimoCodigo = vendas.get(vendas.size() - 1).getCodigo();
        int proximoNumero = Integer.parseInt(ultimoCodigo) + 1;
        return String.format("%04d", proximoNumero); // Formata para 4 dígitos
    }         

    // Adiciona método público para atualizar venda
    public void atualizarVenda(Venda venda) throws DadoInvalidoException, ArquivoRepositorioException {
        if (venda == null) {
            throw new DadoInvalidoException("Venda não pode ser nula.");
        }
        vendaRepositorio.atualizarVenda(venda);
    }

    public Venda buscarVendaEmAberto() throws VendaInvalidaException, ArquivoRepositorioException, DadoInvalidoException {
        Venda vendaEmAberto = vendaRepositorio.buscarVendaEmAberto();
        if (vendaEmAberto == null) {
            throw new VendaInvalidaException("Não há venda em aberto.");
        }
        return vendaEmAberto;
    }

}
