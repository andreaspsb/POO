package TG.Modelos;

public enum EnumStatusVenda {
    ABERTA("ABERTA"),
    CONCLUIDA("CONCLUIDA"),
    CANCELADA("CANCELADA");
    
    private final String status;

    EnumStatusVenda(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }

    public static boolean isValid(String status) {
        return status != null && 
               (status.equals(ABERTA.name()) || 
                status.equals(CONCLUIDA.name()) || 
                status.equals(CANCELADA.name()));
    }

}
