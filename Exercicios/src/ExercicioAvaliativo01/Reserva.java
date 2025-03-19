package ExercicioAvaliativo01;
import java.util.ArrayList;

public class Reserva {

    private ArrayList<Voo> voos;

    public Reserva(ArrayList<Voo> voos) {
        this.voos = voos;
    }

    public ArrayList<Voo> getVoos() {
        return this.voos;
    }

    public void adicionarVoo(Voo voo) {
        this.voos.add(voo);
    }

    public void removerVoo(Voo voo) {
        this.voos.remove(voo);
    }
}
