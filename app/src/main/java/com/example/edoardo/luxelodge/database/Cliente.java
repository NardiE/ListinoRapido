package com.example.edoardo.luxelodge.database;

import com.orm.SugarRecord;
import com.orm.query.Select;

import java.util.List;

public class Cliente extends SugarRecord<Cliente> {
    // codice, descrizione, descrizione2, listino, codice_blocco, telefono, fax, telex, via, cap, citta, provincia, descrizione_blocco, blocco, fuorifido
    int id;
    private String codice;
    private String descrizione;
    private String descrizione2;
    private String listino;
    private String codiceblocco;
    private String telefono;
    private String fax;
    private String telex;
    private String via;
    private String cap;
    private String citta;
    private String provincia;
    private String descrizioneblocco;
    private int blocco;
    private int fuorifido;

    public Cliente(){
    }

    public Cliente(int id, String codice, String descrizione, String descrizione2, String listino, String codiceblocco, String telefono, String fax, String telex, String via, String cap, String citta, String provincia, String descrizioneblocco, int blocco, int fuorifido) {
        this.id = id;
        this.codice = codice;
        this.descrizione = descrizione;
        this.descrizione2 = descrizione2;
        this.listino = listino;
        this.codiceblocco = codiceblocco;
        this.telefono = telefono;
        this.fax = fax;
        this.telex = telex;
        this.via = via;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
        this.descrizioneblocco = descrizioneblocco;
        this.blocco = blocco;
        this.fuorifido = fuorifido;
    }

    public Cliente( String codice, String descrizione, String descrizione2, String listino, String codiceblocco, String telefono, String fax, String telex, String via, String cap, String citta, String provincia, String descrizioneblocco, int blocco, int fuorifido) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.descrizione2 = descrizione2;
        this.listino = listino;
        this.codiceblocco = codiceblocco;
        this.telefono = telefono;
        this.fax = fax;
        this.telex = telex;
        this.via = via;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
        this.descrizioneblocco = descrizioneblocco;
        this.blocco = blocco;
        this.fuorifido = fuorifido;
    }

    public Cliente(String codice, String descrizione, String descrizione2, String listino, String codiceblocco, String telefono, String fax, String telex, String via, String cap, String citta, String provincia, String descrizioneblocco, int blocco) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.descrizione2 = descrizione2;
        this.listino = listino;
        this.codiceblocco = codiceblocco;
        this.telefono = telefono;
        this.fax = fax;
        this.telex = telex;
        this.via = via;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
        this.descrizioneblocco = descrizioneblocco;
        this.blocco = blocco;
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

    public String getCodiceblocco() {
        return codiceblocco;
    }

    public void setCodiceblocco(String codiceblocco) {
        this.codiceblocco = codiceblocco;
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

    public String getDescrizioneblocco() {
        return descrizioneblocco;
    }

    public void setDescrizioneblocco(String descrizioneblocco) {
        this.descrizioneblocco = descrizioneblocco;
    }

    public int getBlocco() {
        return blocco;
    }

    public void setBlocco(int blocco) {
        this.blocco = blocco;
    }

    public int getFuorifido() {
        return fuorifido;
    }

    public void setFuorifido(int fuorifido) {
        this.fuorifido = fuorifido;
    }

    public static List<Cliente> getClientsSearching(String param){
        String SQL1 = "SELECT * FROM Cliente WHERE codice LIKE '%" + param + "%' OR descrizione LIKE '%" + param + "%'";
        List<Cliente> clientsfind;
        clientsfind = Cliente.findWithQuery(Cliente.class,SQL1);
        return clientsfind;
    }

    public static List<Cliente> getTop100Items(){
        return Select.from(Cliente.class).limit("100").list();
    }
}
