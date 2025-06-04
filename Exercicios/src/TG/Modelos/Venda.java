package TG.Modelos;

import java.time.LocalDate;
import java.util.Map;

public class Venda {
    private String codigo;
    private LocalDate data;
    private Cliente cliente;
    private Map<Produto, Integer> produtos; // Mapeia produtos e suas quantidades    

    public Venda(String codigo, LocalDate data, Cliente cliente) {
        this.codigo = codigo;
        this.data = data;
        this.cliente = cliente;
    }

    public String getCodigo() {
        return codigo;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "codigo='" + codigo + '\'' +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                '}';
    }
}
