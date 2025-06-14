package TG.Modelos;

import TG.Excecoes.DadoInvalidoException;

public class Cliente {
    private String cpf;
    private String nome;
    private String email;
    private Conta conta;

    public Cliente(String cpf, String nome, String email, Conta conta) throws DadoInvalidoException {
        if (cpf == null || nome == null || email == null) {
            throw new DadoInvalidoException("CPF, nome e email não podem ser nulos.");
        }
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.conta = conta;
    }

    public Cliente(String cpf) throws DadoInvalidoException {
        if (cpf == null) {
            throw new DadoInvalidoException("CPF não pode ser nulo.");
        }
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Conta getConta() {
        return conta;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", conta=" + conta.getNumero() +
                '}';
    }

}
