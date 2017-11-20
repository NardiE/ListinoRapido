package com.example.edoardo.luxelodge.database;

import com.orm.SugarRecord;

/**
 * Created by edoardo on 20/12/16.
 */
public class Destinazione extends SugarRecord<Destinazione> {
    int id;
    String codicecliente;
    String codice;
    String descrizione;
    String descrizione2;
    String via;
    String cap;
    String citta;
    String provincia;

    public Destinazione(){

    }

    public Destinazione(String codicecliente, String codice, String descrizione, String descrizione2, String via, String cap, String citta, String provincia) {
        this.codicecliente = codicecliente;
        this.codice = codice;
        this.descrizione = descrizione;
        this.descrizione2 = descrizione2;
        this.via = via;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getCodicecliente() {
        return codicecliente;
    }

    public void setCodicecliente(String codicecliente) {
        this.codicecliente = codicecliente;
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

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
