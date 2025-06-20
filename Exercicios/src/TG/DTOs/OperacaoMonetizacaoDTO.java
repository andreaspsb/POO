package TG.DTOs;

import java.time.LocalDateTime;

public class OperacaoMonetizacaoDTO {
    public enum TipoOperacao { DEPOSITO, TRANSFERENCIA }

    private TipoOperacao tipo;
    private double valor;
    private String contaOrigem;
    private String contaDestino;
    private LocalDateTime dataHora;

    public OperacaoMonetizacaoDTO(TipoOperacao tipo, double valor, String contaOrigem, String contaDestino, LocalDateTime dataHora) {
        this.tipo = tipo;
        this.valor = valor;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.dataHora = dataHora;
    }

    public TipoOperacao getTipo() { return tipo; }
    public double getValor() { return valor; }
    public String getContaOrigem() { return contaOrigem; }
    public String getContaDestino() { return contaDestino; }
    public LocalDateTime getDataHora() { return dataHora; }
}
