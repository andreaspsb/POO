package ExercicioAvaliativo01;

import java.util.Arrays;
import java.util.ArrayList;


public class App {

    private static ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    private static ArrayList<Voo> voos = new ArrayList<Voo>();

    public static void main(String[] args) {
        Aeroporto aeroporto1 = new Aeroporto("Salgado Filho", "SBPA","Porto Alegre");
        Aeroporto aeroporto2 = new Aeroporto("João Simões Lopes Neto", "PET","Pelotas");
        Aeroporto aeroporto3 = new Aeroporto("Aeroporto Internacional de São Paulo/Guarulhos – Governador André Franco Montoro", "GRU","Guarulhos");
        
        Cliente cliente1 = new Cliente("Joao da Silva", "51912345678", "Rua tal, 123", "12345678901");
        Cliente cliente2 = new Cliente("Bezerra da Silva", "51912345679", "Rua tal, 124", "12345678902");
        Cliente cliente3 = new Cliente("Benedita da Silva", "51912345670", "Rua tal, 125", "12345678903");
        clientes.add(cliente1);
        clientes.add(cliente2);
        clientes.add(cliente3);

        Voo voo1 = new Voo(1, 100, "12:00", "14:00");
        Voo voo2 = new Voo(2, 100, "14:00", "16:00");
        Voo voo3 = new Voo(3, 100, "16:00", "18:00");
        voos.add(voo1);
        voos.add(voo2);
        voos.add(voo3);

        ArrayList<Voo> listaVoos = new ArrayList<Voo>();
        voos.add(voo2);
        voos.add(voo3);
        cliente1.criarReserva(listaVoos);

        listaVoos = new ArrayList<Voo>();
        voos.add(voo1);
        voos.add(voo3);
        cliente1.criarReserva(listaVoos);

        System.out.println(obterCodigosVoos("12345678901"));

        ArrayList<Reserva> reservas = cliente1.getReservas();
        Reserva reserva = reservas.get(0);
        reserva.adicionarVoo(voo1);

        System.out.println(obterCodigosVoos("12345678901"));

        reserva.removerVoo(voo3);

        for (Cliente cliente : clientes) {
            System.out.println(obterCodigosVoos(cliente.getCpf()));
        }
    }

    public static ArrayList<Integer> obterCodigosVoos(String cpf) {
        ArrayList<Integer> codigos = new ArrayList<Integer>();
        for (Cliente cliente : clientes) {
            if (cpf.equals(cliente.getCpf())) {
                for (Reserva reserva : cliente.getReservas()) {
                    for (Voo voo : reserva.getVoos()) {
                        codigos.add(voo.getNumero());
                    }
                }
                break;
            }
        }
        return codigos;
    }

}
