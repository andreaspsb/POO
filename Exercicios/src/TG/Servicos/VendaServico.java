package TG.Servicos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import TG.Modelos.Cliente;
import TG.Modelos.EnumStatusVenda;
import TG.Modelos.Venda;
import TG.Repositorios.VendaRepositorio;

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
    public void adicionarVenda(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        //obter o próximo código de venda de um repositório ou serviço
        String codigo = this.obterProximoCodigoVenda();

        Venda venda = new Venda(codigo, cliente);
        vendaRepositorio.adicionarVenda(venda);

    }

    //método para finalizar a venda
    public void finalizarVenda(Venda venda) {
        if (venda == null) {
            throw new IllegalArgumentException("Venda não pode ser nula.");
        }
        if (venda.getProdutos().isEmpty()) {
            throw new IllegalStateException("Não é possível finalizar a venda sem produtos.");
        }

        double total = venda.calcularTotal();
        if (total <= 0) {
            throw new IllegalStateException("O total da venda deve ser maior que zero.");
        }
        if (venda.getCliente().getConta().getSaldo().compareTo(BigDecimal.valueOf(total)) < 0) {
            throw new IllegalStateException("Saldo insuficiente na conta do cliente para finalizar a venda.");              
        }

        ContaServico contaServico = new ContaServico();
        contaServico.debitar(venda.getCliente().getConta().getNumero(), BigDecimal.valueOf(total));

        venda.setDateTimeConclusao(LocalDateTime.now());
        venda.setStatus(new EnumStatusVenda(EnumStatusVenda.CONCLUIDA));

        vendaRepositorio.atualizarVenda(venda);
        
    }

    public Venda buscarVendaPorCodigo(String codigo) {
        return vendaRepositorio.buscarVendaPorCodigo(codigo);
    }

    public List<Venda> listarVendas() {
        return vendaRepositorio.listarVendas();
    }

    private String obterProximoCodigoVenda() {
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

}
