package com.example.edoardo.luxelodge.database;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class Articolo extends SugarRecord<Articolo> {
    // codice, descrizione, descrizione2, UM, esistenza, numeropezzi
    private String codice;
    private String descrizione;
    private String descrizione2;
    private String UM;
    private float esistenza;
    private float numeropezzi;

    public Articolo(){
    }


    public Articolo(String codice, String descrizione, String descrizione2, String UM, float esistenza, float numeropezzi) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.descrizione2 = descrizione2;
        this.UM = UM;
        this.esistenza = esistenza;
        this.numeropezzi = numeropezzi;
    }


    public Articolo(String codice, String descrizione, String descrizione2, String UM, int esistenza, int numeropezzi){
        this.setCodice(codice);
        this.setDescrizione(descrizione);
        this.setDescrizione2(descrizione2);
        this.setUM(UM);
        this.setEsistenza(esistenza);
        this.setNumeropezzi(numeropezzi);
    }


    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione2() {
        return descrizione2;
    }

    public void setDescrizione2(String descrizione2) {
        this.descrizione2 = descrizione2;
    }

    public String getUM() {
        return UM;
    }

    public void setUM(String UM) {
        this.UM = UM;
    }

    public float getEsistenza() {
        return esistenza;
    }

    public void setEsistenza(float esistenza) {
        this.esistenza = esistenza;
    }

    public float getNumeropezzi() {
        return numeropezzi;
    }

    public void setNumeropezzi(float numeropezzi) {
        this.numeropezzi = numeropezzi;
    }

    public static List<Articolo> getItemsSearching(String param){
        String SQL1 = "SELECT * FROM Articolo WHERE codice LIKE '%" + param + "%' OR descrizione LIKE '%" + param + "%'";
        List<Articolo> articolofind;
        articolofind = Articolo.findWithQuery(Articolo.class,SQL1);
        return articolofind;
    }

    public static List<Articolo> getAll(){
        return Select.from(Articolo.class).list();
    }

    public static List<Articolo> getTop100Items(){
        return Select.from(Articolo.class).limit("100").list();
    }

    public static List<Articolo> getTop100ItemsbyParam(String param){
        return Select.from(Articolo.class)
                .where(Condition.prop("codice").like(param),
                        Condition.prop("descrizione").like(param))
                .limit("100").list();
    }
}