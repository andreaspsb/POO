package TG.Modelos;

import java.time.LocalDateTime;
import java.util.Map;

public class Venda {
    private String codigo;
    private LocalDateTime data;
    private Cliente cliente;
    private Map<Produto, Integer> produtos; // Mapeia produtos e suas quantidades

    public Venda(String codigo, Cliente cliente) {
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("Código da venda não pode ser nulo ou vazio.");
        }
        this.codigo = codigo;
        
        this.data = LocalDateTime.now();
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        this.cliente = cliente;
        this.produtos = new java.util.HashMap<>();
    }

    public Venda(String codigo2, LocalDateTime dataHora, Cliente cliente) {
        this.codigo = codigo2;
        this.data = dataHora;
        this.cliente = cliente;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getData() {
        return data;
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
          .append(", data=").append(data)
          .append(", cliente=").append(cliente)
          .append('}');
        return sb.toString();
    }
    

}
