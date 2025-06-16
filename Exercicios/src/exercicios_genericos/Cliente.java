package exercicios_genericos;

import java.util.Map;

public class Cliente {
    private String cpf;
    private String nome;

    public Cliente(String cpf, String nome) {
        // Validação simples do CPF (deve ter 11 dígitos)
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d+")) {
            throw new CpfInvalidoException("CPF deve conter 11 dígitos numéricos.");
        }
        this.cpf = cpf;
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Map<String, Cliente> clientes = new java.util.HashMap<>();

        try {

            Cliente cliente1 = new Cliente("12345678901", "João Silva");
            Cliente cliente2 = new Cliente("12345678901", "João Silva");
            Cliente cliente3 = new Cliente("98765432100", "Maria Oliveira");
            
            // Adicionando clientes ao mapa
            clientes.put(cliente1.getCpf(), cliente1);
            clientes.put(cliente2.getCpf(), cliente2); // Este não deve ser adicionado, pois o CPF já existe
            clientes.put(cliente3.getCpf(), cliente3);

        } catch (CpfInvalidoException e) {
            System.err.println("Erro ao criar cliente: " + e.getMessage());
        }

        // Exibindo os clientes cadastrados
        for (Map.Entry<String, Cliente> entry : clientes.entrySet()) {
            System.out.println("CPF: " + entry.getKey() + ", Cliente: " + entry.getValue());
        }

        try {
            // Tentativa de adicionar cliente com CPF inválido
            Cliente clienteInvalido = new Cliente("12345", "Carlos Pereira");
            clientes.put(clienteInvalido.getCpf(), clienteInvalido);

        } catch (CpfInvalidoException e) {
            System.err.println("Erro ao criar cliente: " + e.getMessage());
        }
    }

}
