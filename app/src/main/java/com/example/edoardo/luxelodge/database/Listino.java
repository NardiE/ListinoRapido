package com.example.edoardo.luxelodge.database;

import com.orm.SugarRecord;

/**
 * Created by edoardo on 20/12/16.
 */
public class Listino extends SugarRecord<Listino> {
    int id;
    String codicearticolo;
    String codicelistino;
    Float prezzo;

    public Listino(){

    }

    public Listino(String codicearticolo, String codicelistino, Float prezzo) {
        this.codicearticolo = codicearticolo;
        this.codicelistino = codicelistino;
        this.prezzo = prezzo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodicearticolo() {
        return codicearticolo;
    }

    public void setCodicearticolo(String codicearticolo) {
        this.codicearticolo = codicearticolo;
    }

    public String getCodicelistino() {
        return codicelistino;
    }

    public void setCodicelistino(String codicelistino) {
        this.codicelistino = codicelistino;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }
}

