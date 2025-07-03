package it.uniroma2.progettoispw.model.domain;

public class PrincipioAttivo {
    private String codiceAtc;
    private String nome;

    public PrincipioAttivo(String codiceAtc, String nome) {
        this.codiceAtc = codiceAtc;
        this.nome = nome;
    }

    public PrincipioAttivo(String codiceAtc) {
        this.codiceAtc = codiceAtc;
    }
    public String getCodiceAtc() {
        return codiceAtc;
    }
    public void setCodiceAtc(String codiceAtc) {
        this.codiceAtc = codiceAtc;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

}
