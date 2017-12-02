package com.example.edoardo.luxelodge.database;

import com.orm.SugarRecord;

public class Articolo extends SugarRecord<Articolo> {
    // codice, descrizione, descrizione2, UM, esistenza, numeropezzi
    public static int lenght_codice = 15;
    private String codice;
    private String descrizione;
    private String descrizione2;
    private String UM;
    private String me;
    private String inventario;
    private String cm;
    private String esistenza;
    private String pezzi_confezione;
    private String lotto_vendita;
    private String lotto_riordino;

    public Articolo(){
    }

    public Articolo(String codice, String descrizione, String descrizione2, String UM, String me, String inventario, String cm, String esistenza, String pezzi_confezione, String lotto_vendita, String lotto_riordino) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.descrizione2 = descrizione2;
        this.UM = UM;
        this.me = me;
        this.inventario = inventario;
        this.cm = cm;
        this.esistenza = esistenza;
        this.pezzi_confezione = pezzi_confezione;
        this.lotto_vendita = lotto_vendita;
        this.lotto_riordino = lotto_riordino;
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

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public String getInventario() {
        return inventario;
    }

    public void setInventario(String inventario) {
        this.inventario = inventario;
    }

    public String getCm() {
        return cm;
    }

    public void setCm(String cm) {
        this.cm = cm;
    }

    public String getEsistenza() {
        return esistenza;
    }

    public void setEsistenza(String esistenza) {
        this.esistenza = esistenza;
    }

    public String getPezzi_confezione() {
        return pezzi_confezione;
    }

    public void setPezzi_confezione(String pezzi_confezione) {
        this.pezzi_confezione = pezzi_confezione;
    }

    public String getLotto_vendita() {
        return lotto_vendita;
    }

    public void setLotto_vendita(String lotto_vendita) {
        this.lotto_vendita = lotto_vendita;
    }

    public String getLotto_riordino() {
        return lotto_riordino;
    }

    public void setLotto_riordino(String lotto_riordino) {
        this.lotto_riordino = lotto_riordino;
    }
}