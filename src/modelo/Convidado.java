package modelo;

public class Convidado extends Participante {
    
    private String instituicao;
    
    public Convidado(String nome, String email, String instituicao) {
        super(nome, email);
        this.instituicao = instituicao;
    }
    
    public String getInstituicao() {
        return instituicao;
    }
    
    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }
    
    @Override
    public String toString() {
        return super.toString() + " - Instituição: " + instituicao;
    }
}