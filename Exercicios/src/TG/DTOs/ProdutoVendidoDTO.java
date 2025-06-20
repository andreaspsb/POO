package TG.DTOs;

public class ProdutoVendidoDTO {
    private String codigo;
    private String nome;
    private int quantidadeVendida;
    private double valorTotal;

    public ProdutoVendidoDTO(String codigo, String nome, int quantidadeVendida, double valorTotal) {
        this.codigo = codigo;
        this.nome = nome;
        this.quantidadeVendida = quantidadeVendida;
        this.valorTotal = valorTotal;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(int quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
