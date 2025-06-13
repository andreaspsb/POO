package ExercicioAvaliativo01;
import java.util.ArrayList;

public class Cliente {

    private ArrayList<Reserva> reservas = new ArrayList<Reserva>();

    private String nome, telefone, endereco, cpf;

    public Cliente(String nome, String telefone, String endereco, String cpf) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cpf = cpf;
    }

    public void criarReserva(ArrayList<Voo> voos) {
        Reserva reserva = new Reserva(voos);
        this.reservas.add(reserva);
    }
    
    public String getCpf() {
        return this.cpf;
    }

    public ArrayList<Reserva> getReservas() {
        return this.reservas;
    }

    public String getNome() {
        return this.nome;
    }
    public String getTelefone() {
        return this.telefone;
    }
    public String getEndereco() {
        return this.endereco;
    }

}
