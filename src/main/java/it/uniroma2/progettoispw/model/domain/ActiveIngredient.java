package it.uniroma2.progettoispw.model.domain;

public class ActiveIngredient extends Medication {
    private String codiceAtc;
    private String nome;

    public ActiveIngredient(String codiceAtc, String nome) {
        this.codiceAtc = codiceAtc;
        this.nome = nome;
    }

    public ActiveIngredient(String codiceAtc) {
        this.codiceAtc = codiceAtc;
    }

    @Override
    public String getId() {
        return codiceAtc;
    }

    @Override
    public MedicationType getType() {
        return MedicationType.ACRIVEINGREDIENT;
    }

    @Override
    public String getName() {
        return nome;
    }

    public void setName(String name) {
        this.nome = name;
    }

}
