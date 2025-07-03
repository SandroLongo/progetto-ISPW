package it.uniroma2.progettoispw.model.domain;

public class Confezione {
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

    public Confezione(int codiceAic) {
        this.codiceAic = codiceAic;
    }

    public int getCodiceAic() {
        return codiceAic;
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
    public String getDenominazione() {
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
    public String createLink(){
        return null;
    }

}
