package com.example.edoardo.luxelodge.database;

import com.orm.SugarRecord;

/**
 * Created by edoardo on 20/12/16.
 */
public class Barcode extends SugarRecord<Barcode> {
    public static int barcode_lenght = 30;
    int id;
    String codicearticolo;
    String codiceabarre;

    public Barcode(){

    }

    public Barcode(String codicearticolo, String codiceabarre) {
        this.codicearticolo = codicearticolo;
        this.codiceabarre = codiceabarre;
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

    public String getCodiceabarre() {
        return codiceabarre;
    }

    public void setCodiceabarre(String codiceabarre) {
        this.codiceabarre = codiceabarre;
    }
}
