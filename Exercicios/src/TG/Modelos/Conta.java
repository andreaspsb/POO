package TG.Modelos;

import java.math.BigDecimal;
import TG.Excecoes.DadoInvalidoException;

public class Conta {
    private String numero;
    private BigDecimal saldo;        

    public Conta(String numero, BigDecimal saldo) throws DadoInvalidoException {
        if (numero == null || saldo == null) {
            throw new DadoInvalidoException("Número da conta e saldo não podem ser nulos.");
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

    public void creditar(BigDecimal valor) throws DadoInvalidoException {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DadoInvalidoException("Valor do crédito deve ser maior que zero.");
        }
        saldo = saldo.add(valor);
    }

    public void debitar(BigDecimal valor) throws DadoInvalidoException {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DadoInvalidoException("Valor do débito deve ser maior que zero.");
        }
        if (valor.compareTo(saldo) > 0) {
            throw new DadoInvalidoException("Saldo insuficiente para débito.");
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
