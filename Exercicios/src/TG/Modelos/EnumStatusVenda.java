package TG.Modelos;

public class EnumStatusVenda {
    public static final String ABERTA = "ABERTA";
    public static final String CONCLUIDA = "CONCLUIDA";
    public static final String CANCELADA = "CANCELADA";

    private EnumStatusVenda() {
        // Construtor privado para evitar instância
    }

    public EnumStatusVenda(String aberta2) {
        if (!isValid(aberta2)) {
            throw new IllegalArgumentException("Status inválido: " + aberta2);
        }
        // Aqui você poderia inicializar o status, se necessário
        
    }

    public static boolean isValid(String status) {
        return ABERTA.equals(status) || CONCLUIDA.equals(status) || CANCELADA.equals(status);
    }

}
