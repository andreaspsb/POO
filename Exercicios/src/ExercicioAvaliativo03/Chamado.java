public class Chamado {

    private String cliente;
    private String descricao;
    private Urgencia urgencia;
    private Status status;

    public Chamado(String cliente, String descricao, Urgencia urgencia, Status status) {
        this.cliente = cliente;
        this.descricao = descricao;
        this.urgencia = urgencia;
        this.status = status;
    }

    public String toString() {
        return "Chamado{" +
                "cliente='" + cliente + '\'' +
                ", descricao='" + descricao + '\'' +
                ", urgencia=" + urgencia +
                ", status=" + status +
                '}';
    }

    public Urgencia getUrgencia() {
        return urgencia;
    }
}
