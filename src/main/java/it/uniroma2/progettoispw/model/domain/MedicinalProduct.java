package it.uniroma2.progettoispw.model.domain;

public class MedicinalProduct extends Medication{
    private int codiceAic;
    private int codFarmaco;
    private int codConfezione;
    private String denominazione;
    private String descrizione;
    private int codiceDitta;
    private String ragioneSociale;
    private String statoAmministrativo;
    private String tipoProcedura;
    private String forma;
    private String codiceAtc;
    private String paAssociati;
    private long quantita;
    private String unitaMisura;
    private String link;

    public MedicinalProduct(int codiceAic) {
        this.codiceAic = codiceAic;
    }

    @Override
    public String getId() {
        return String.valueOf(codiceAic);
    }

    @Override
    public MedicationType getType() {
        return MedicationType.CONFEZIONE;
    }

    public void setCodiceAic(int codiceAic) {
        this.codiceAic = codiceAic;
    }
    public int getCodFarmaco() {
        return codFarmaco;
    }
    public void setCodFarmaco(int codFarmaco) {
        this.codFarmaco = codFarmaco;
    }
    public int getCodConfezione() {
        return codConfezione;
    }
    public void setCodConfezione(int codConfezione) {
        this.codConfezione = codConfezione;
    }
    @Override
    public String getName() {
        return denominazione;
    }
    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public int getCodiceDitta() {
        return codiceDitta;
    }
    public void setCodiceDitta(int codiceDitta) {
        this.codiceDitta = codiceDitta;
    }
    public String getRagioneSociale() {
        return ragioneSociale;
    }
    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }
    public String getStatoAmministrativo() {
        return statoAmministrativo;
    }
    public void setStatoAmministrativo(String statoAmministrativo) {
        this.statoAmministrativo = statoAmministrativo;
    }
    public String getTipoProcedura() {
        return tipoProcedura;
    }
    public void setTipoProcedura(String tipoProcedura) {
        this.tipoProcedura = tipoProcedura;
    }
    public String getForma() {
        return forma;
    }
    public void setForma(String forma) {
        this.forma = forma;
    }
    public String getCodiceAtc() {
        return codiceAtc;
    }
    public void setCodiceAtc(String codiceAtc) {
        this.codiceAtc = codiceAtc;
    }
    public String getPaAssociati() {
        return paAssociati;
    }
    public void setPaAssociati(String paAssociati) {
        this.paAssociati = paAssociati;
    }
    public long getQuantita() {
        return quantita;
    }
    public void setQuantita(long quantita) {
        this.quantita = quantita;
    }
    public String getUnitaMisura() {
        return unitaMisura;
    }
    public void setUnitaMisura(String unitaMisura) {
        this.unitaMisura = unitaMisura;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("confezione : " + "Nome: ").append(denominazione).append(", Descrizione: ").append(descrizione).append(", Forma:").append(forma).append(",pa associati: ").append(paAssociati).append(",codice aic: ").append(codiceAic).append(", codice Atc: ").append(codiceAtc);
        return builder.toString();
    }
}
