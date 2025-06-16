package exercicios_genericos;

import java.util.ArrayList;
import java.util.List;


public class Tarefa {
    private String descricao;
    private Status status;

    public Tarefa(String descricao, Status status) {
        this.descricao = descricao;
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }    

    public Tarefa(String descricao) {
        this.descricao = descricao;
        this.status = Status.PENDENTE; // Padrão para novas tarefas
    }

    public boolean isConcluida() {
        return status == Status.CONCLUIDA;
    }

    public void concluir() {
        this.status = Status.CONCLUIDA;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "descricao='" + descricao + '\'' +
                ", status=" + status +
                '}';
    }    

    public enum Status {
        PENDENTE,
        CONCLUIDA
    }

    public static void main(String[] args) {
        List<Tarefa> tarefas = new ArrayList<>();

        Tarefa tarefa1 = new Tarefa("Estudar Generics em Java");
        tarefas.add(tarefa1);

        Tarefa tarefa2 = new Tarefa("Revisar conceitos de Generics");
        tarefas.add(tarefa2);

        try {
            removerTarefaPorIndice(1, tarefas);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Erro ao remover tarefa: " + e.getMessage());
        }

        //testando a remoção de uma tarefa com índice inválido
        try {
            removerTarefaPorIndice(5, tarefas);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Erro ao remover tarefa: " + e.getMessage());
        }

    }

    static void removerTarefaPorIndice(int indice, List<Tarefa> tarefas) throws IndexOutOfBoundsException {
        if (indice >= 0 && indice < tarefas.size()) {
            Tarefa tarefaRemovida = tarefas.remove(indice);
            System.out.println("Tarefa removida: " + tarefaRemovida);
        } else {
            // Lançar exceção se o índice for inválido
            throw new IndexOutOfBoundsException("Índice inválido: " + indice);
        }
    }

}
