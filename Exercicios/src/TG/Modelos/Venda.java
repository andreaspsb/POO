package TG.Modelos;

import java.time.LocalDateTime;
import java.util.Map;

public class Venda {
    private String codigo;
    private LocalDateTime dateTimeAbertura;
    private LocalDateTime dateTimeConclusao;
    private EnumStatusVenda status;
    private Cliente cliente;
    private Map<Produto, Integer> produtos; // Mapeia produtos e suas quantidades

    public Venda(String codigo, Cliente cliente) {
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("Código da venda não pode ser nulo ou vazio.");
        }
        this.codigo = codigo;

        this.dateTimeAbertura = LocalDateTime.now();
        this.status = new EnumStatusVenda(EnumStatusVenda.ABERTA);
        
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        this.cliente = cliente;
        this.produtos = new java.util.HashMap<>();
    }

    public Venda(String codigo2, LocalDateTime dateTimeAbertura, LocalDateTime dateTimeConclusao,
            EnumStatusVenda status, Cliente cliente) {
        this.codigo = codigo2;
        this.dateTimeAbertura = dateTimeAbertura;
        this.dateTimeConclusao = dateTimeConclusao;
        this.status = status;
        this.cliente = cliente;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getDateTimeAbertura() {
        return dateTimeAbertura;
    }

    public void setDateTimeAbertura(LocalDateTime dateTimeAbertura) {
        this.dateTimeAbertura = dateTimeAbertura;
    }

    public LocalDateTime getDateTimeConclusao() {
        return dateTimeConclusao;
    }

    public void setDateTimeConclusao(LocalDateTime dateTimeConclusao) {
        this.dateTimeConclusao = dateTimeConclusao;
    }

    public EnumStatusVenda getStatus() {
        return status;
    }

    public void setStatus(EnumStatusVenda status) {
        if (status == null || !EnumStatusVenda.isValid(status.toString())) {
            throw new IllegalArgumentException("Status inválido.");
        }
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Map<Produto, Integer> getProdutos() {
        return produtos;
    }

    public void setProdutos(Map<Produto, Integer> produtos) {
        this.produtos = produtos;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        if (produto == null || quantidade <= 0) {
            throw new IllegalArgumentException("Produto não pode ser nulo e quantidade deve ser maior que zero.");
        }
        if (produtos.containsKey(produto)) {
            produtos.put(produto, produtos.get(produto) + quantidade);
        } else {
            produtos.put(produto, quantidade);
        }
    }

    public double calcularTotal() {
        double total = 0.0;
        for (Map.Entry<Produto, Integer> entry : produtos.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            total += produto.getPreco() * quantidade;
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Venda{")
                .append("codigo='").append(codigo).append('\'')
                .append(", data=").append(dateTimeAbertura)
                .append(", dataConclusao=").append(dateTimeConclusao)
                .append(", status=").append(status)
                .append(", cliente=").append(cliente.getCpf())
                .append('}');
        return sb.toString();
    }

}
