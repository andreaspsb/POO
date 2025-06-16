package exercicios_genericos;

import java.util.ArrayList;
import java.util.List;

public class Conta<T extends Number> {
    private String titular;
    private T saldo;

    public Conta(String titular, T saldoInicial) {
        if (titular == null || titular.isEmpty()) {
            throw new IllegalArgumentException("Titular não pode ser nulo ou vazio.");
        }

        // Verifica o tipo de saldo inicial
        if (saldoInicial instanceof Integer || saldoInicial instanceof Double) {
            // Aceita apenas tipos numéricos válidos
        } else {
            throw new IllegalArgumentException("Tipo de saldo inicial não suportado. Use Integer ou Double.");
        }

        if (saldoInicial == null || saldoInicial.doubleValue() < 0) {
            throw new IllegalArgumentException("Saldo inicial deve ser um número positivo.");
        }
        if (saldoInicial.doubleValue() <= 0) {
            throw new IllegalArgumentException("Saldo inicial deve ser maior que zero.");
        }

        this.titular = titular;
        this.saldo = saldoInicial;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public T getSaldo() {
        return saldo;
    }

    public void setSaldo(T saldo) {
        this.saldo = saldo;
    }

    public void depositar(T valor) {
        if (valor == null || valor.doubleValue() <= 0) {
            throw new IllegalArgumentException("Valor do depósito deve ser positivo.");
        }
        this.saldo = (T) Double.valueOf(this.saldo.doubleValue() + valor.doubleValue());
    }

    public void sacar(T valor) throws SaldoInsuficienteException {
        if (valor == null || valor.doubleValue() <= 0) {
            throw new IllegalArgumentException("Valor do saque deve ser positivo.");
        }
        if (valor.doubleValue() > saldo.doubleValue()) {
            throw new SaldoInsuficienteException("Saldo insuficiente para saque.");
        }
        this.saldo = (T) Double.valueOf(this.saldo.doubleValue() - valor.doubleValue());
    }

    public static void main(String[] args) {
        List<Conta<?>> contas = new ArrayList<>();

        Conta<Integer> conta1 = new Conta<>("João", 1000);
        contas.add(conta1);
        System.out.println("Titular: " + conta1.getTitular());
        System.out.println("Saldo inicial: " + conta1.getSaldo());

        conta1.depositar(500);
        System.out.println("Saldo após depósito: " + conta1.getSaldo());

        try {
            conta1.sacar(300);
        } catch (SaldoInsuficienteException e) {
            System.err.println("Erro ao sacar: " + e.getMessage());
        }
        System.out.println("Saldo após saque: " + conta1.getSaldo());

        Conta<Double> conta2 = new Conta<>("Maria", 1500.75);
        contas.add(conta2);
        System.out.println("Titular: " + conta2.getTitular());
        System.out.println("Saldo inicial: " + conta2.getSaldo());

        conta2.depositar(200.25);
        System.out.println("Saldo após depósito: " + conta2.getSaldo());

        try {
            conta2.sacar(100.50);
        } catch (SaldoInsuficienteException e) {
            System.err.println("Erro ao sacar: " + e.getMessage());
        }

        System.out.println("Saldo após saque: " + conta2.getSaldo());
    }

}
