package it.uniroma2.progettoispw.model.domain;

public class PrincipioAttivo {
    private String codice_atc;
    private String nome;

    public PrincipioAttivo(String codice_atc, String nome) {
        this.codice_atc = codice_atc;
        this.nome = nome;
    }
    public String getCodice_atc() {
        return codice_atc;
    }
    public void setCodice_atc(String codice_atc) {
        this.codice_atc = codice_atc;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

}
