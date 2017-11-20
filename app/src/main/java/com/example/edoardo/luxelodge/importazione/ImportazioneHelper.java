package com.example.edoardo.luxelodge.importazione;

import com.example.edoardo.luxelodge.database.Articolo;
import com.example.edoardo.luxelodge.database.Barcode;
import com.example.edoardo.luxelodge.database.Cliente;
import com.example.edoardo.luxelodge.database.Destinazione;
import com.example.edoardo.luxelodge.database.Listino;

/**
 * Created by edoardo on 20/12/16.
 */
public class ImportazioneHelper {
    public static int ARTICOLO_DIMENSIONESTRINGA = 78;
    public static int CLIENTE_DIMENSIONESTRINGA = 236;
    public static int DESTINAZIONE_DIMENSIONESTRINGA = 141;
    public static int BARCODE_DIMENSIONESTRINGA = 45;
    public static int LISTINO_DIMENSIONESTRINGA = 70;

    public static Articolo importaArticolo(String line){
        String codice = line.substring(0,15);
        String descrizione = line.substring(15,45);
        String descrizione2 = line.substring(45,75);
        String UM = line.substring(75,78);
        //TODO aggiungere esistenza e numeropezzi
        int esistenza = 0;
        int numeropezzi = 0;
        Articolo arti = new Articolo(codice, descrizione, descrizione2, UM, esistenza, numeropezzi);
        arti.save();
        return arti;
    }

    public static Cliente importaCliente(String line){
        String codice = line.substring(0,8);
        String descrizione = line.substring(8,38);
        String descrizione2 = line.substring(38,68);
        String listino = line.substring(68,71);
        String telefono = line.substring(74,89);
        String fax = line.substring(89,104);
        String telex = line.substring(104,119);
        String via = line.substring(169,199);
        String cap = line.substring(199,204);
        String citta = line.substring(204,234);
        String provincia = line.substring(234,236);
        //TODO Implementare campi mancanti
        String codice_blocco = "blocco";
        String descrizione_blocco = "descrizione blocco";
        int blocco = 0;
        int fuorifido = 0;
        Cliente clie = new Cliente(codice, descrizione, descrizione2, listino, codice_blocco, telefono, fax, telex, via, cap, citta, provincia, descrizione_blocco, blocco, fuorifido);
        clie.save();
        return clie;
    }

    public static Destinazione importaDestinazione(String line){
        String codicecliente = line.substring(0,0 + 8);
        String codice = line.substring(8,14);
        String descrizione = line.substring(14,44);
        String descrizione2 = line.substring(44,74);
        String via = line.substring(74,104);
        String cap = line.substring(104, 109);
        String citta = line.substring(109, 139);
        String provincia = line.substring(139, 141);
        Destinazione desti = new Destinazione(codicecliente, codice, descrizione, descrizione2, via, cap, citta, provincia);
        desti.save();
        return desti;
    }

    public static Barcode importaBarcode(String line){
        String codicearticolo = line.substring(0,0 + 15);
        String codiceabarre = line.substring(15,45);
        Barcode barco = new Barcode(codicearticolo, codiceabarre);
        barco.save();
        return barco;
    }

    public static Listino importaListino(String line){
        String codicearticolo = line.substring(0,0 + 15);
        String codicelistino = line.substring(15,18);
        Float prezzo = Float.parseFloat(line.substring(52,70));
        Listino listi = new Listino(codicearticolo, codicelistino, prezzo);
        listi.save();
        return listi;
    }

}
