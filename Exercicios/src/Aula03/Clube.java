public class Clube {

    private SocioAtleta[] sociosAtleta;
    private int qtdSocios;

    public Clube (int tamanho) {
        this.sociosAtleta = new SocioAtleta[tamanho];
        this.qtdSocios = 0;
    }

    public void inscreveSocio(SocioAtleta socio) {
        if (qtdSocios == this.sociosAtleta.length) {
            
        }

        this.sociosAtleta[this.qtdSocios] = socio;
        this.qtdSocios++;
    }

}
