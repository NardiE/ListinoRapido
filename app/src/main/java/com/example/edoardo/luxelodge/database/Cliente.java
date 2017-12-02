package com.example.edoardo.luxelodge.database;

import com.orm.SugarRecord;

public class Cliente extends SugarRecord<Cliente> {
    private String codice;
    private String descrizione;
    private String descrizione2;
    private String listino;
    private String telefono;
    private String fax;
    private String telex;
    private String via;
    private String cap;
    private String citta;
    private String provincia;
    private String descrizione_blocco;
    private String blocco;
    private String fuorifido;
    private String email;
    private String codicepagamento;
    private String descrizionepagamento;
    private String banca;
    private String vettore1;
    private String vettore2;

    public Cliente(){
    }

    public Cliente(String codice, String descrizione, String descrizione2, String listino, String telefono, String fax, String telex, String via, String cap, String citta, String provincia, String descrizione_blocco, String blocco, String fuorifido, String email, String codicepagamento, String descrizionepagamento, String banca, String vettore1, String vettore2) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.descrizione2 = descrizione2;
        this.listino = listino;
        this.telefono = telefono;
        this.fax = fax;
        this.telex = telex;
        this.via = via;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
        this.descrizione_blocco = descrizione_blocco;
        this.blocco = blocco;
        this.fuorifido = fuorifido;
        this.email = email;
        this.codicepagamento = codicepagamento;
        this.descrizionepagamento = descrizionepagamento;
        this.banca = banca;
        this.vettore1 = vettore1;
        this.vettore2 = vettore2;
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

    public String getListino() {
        return listino;
    }

    public void setListino(String listino) {
        this.listino = listino;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTelex() {
        return telex;
    }

    public void setTelex(String telex) {
        this.telex = telex;
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

    public String getDescrizione_blocco() {
        return descrizione_blocco;
    }

    public void setDescrizione_blocco(String descrizione_blocco) {
        this.descrizione_blocco = descrizione_blocco;
    }

    public String getBlocco() {
        return blocco;
    }

    public void setBlocco(String blocco) {
        this.blocco = blocco;
    }

    public String getFuorifido() {
        return fuorifido;
    }

    public void setFuorifido(String fuorifido) {
        this.fuorifido = fuorifido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodicepagamento() {
        return codicepagamento;
    }

    public void setCodicepagamento(String codicepagamento) {
        this.codicepagamento = codicepagamento;
    }

    public String getDescrizionepagamento() {
        return descrizionepagamento;
    }

    public void setDescrizionepagamento(String descrizionepagamento) {
        this.descrizionepagamento = descrizionepagamento;
    }

    public String getBanca() {
        return banca;
    }

    public void setBanca(String banca) {
        this.banca = banca;
    }

    public String getVettore1() {
        return vettore1;
    }

    public void setVettore1(String vettore1) {
        this.vettore1 = vettore1;
    }

    public String getVettore2() {
        return vettore2;
    }

    public void setVettore2(String vettore2) {
        this.vettore2 = vettore2;
    }
}
