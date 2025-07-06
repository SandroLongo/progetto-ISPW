package it.uniroma2.progettoispw.model.domain;

public class ActiveIngridient extends Medication {
    private String codiceAtc;
    private String nome;

    public ActiveIngridient(String codiceAtc, String nome) {
        this.codiceAtc = codiceAtc;
        this.nome = nome;
    }

    public ActiveIngridient(String codiceAtc) {
        this.codiceAtc = codiceAtc;
    }

    @Override
    public String getId() {
        return codiceAtc;
    }

    @Override
    public MedicationType getType() {
        return MedicationType.PRINCIPIOATTIVO;
    }

    @Override
    public String getName() {
        return nome;
    }

    public void setName(String name) {
        this.nome = name;
    }

}
