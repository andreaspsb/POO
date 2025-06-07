package TG.Modelos;

import java.math.BigDecimal;

public class Conta {
    private String numero;
    private BigDecimal saldo;        

    public Conta(String numero, BigDecimal saldo) {
        if (numero == null || saldo == null) {
            throw new IllegalArgumentException("Número da conta e saldo não podem ser nulos.");
        }
        this.numero = numero;
        this.saldo = saldo;
    }

    public String getNumero() {
        return numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void creditar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do depósito deve ser maior que zero.");
        }
        saldo = saldo.add(valor);
    }

    public void debitar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do saque deve ser maior que zero.");
        }
        if (valor.compareTo(saldo) > 0) {
            throw new IllegalArgumentException("Saldo insuficiente para saque.");
        }
        saldo = saldo.subtract(valor);
    }

    @Override
    public String toString() {
        return "Conta{" +
                "numero='" + numero + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
