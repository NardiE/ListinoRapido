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
    public static int ARTICOLO_DIMENSIONESTRINGA = 155;
    public static int CLIENTE_DIMENSIONESTRINGA = 661;
    public static int DESTINAZIONE_DIMENSIONESTRINGA = 141;
    public static int BARCODE_DIMENSIONESTRINGA = 45;
    public static int LISTINO_DIMENSIONESTRINGA = 198;
    public static int LISTINOCLIENTE_DIMENSIONESTRINGA =171;
    public static int TABELLASCONTO_DIMENSIONESTRINGA =38;
    public static int SCONTOC_DIMENSIONESTRINGA =11;
    public static int SCONTOCM_DIMENSIONESTRINGA =14;
    public static int SCONTOCA_DIMENSIONESTRINGA =26;

    public static Articolo importaArticolo(String line){
        String codice = line.substring(0,15);
        String descrizione = line.substring(15,45);
        String descrizione2 = line.substring(45,75);
        String um = line.substring(75,78);
        String me = line.substring(78,81);     // merceologico
        String in = line.substring(81,84);     //inventario
        String cm = line.substring(84,87);     // categoria merceologica
        String esistenza = line.substring(87,104);
        String pezzi_confezione = line.substring(104,121);
        String lotto_vendita = line.substring(121,138);
        String lotto_riordino = line.substring(138,155);
        Articolo arti = new Articolo(codice, descrizione, descrizione2, um, me,in,cm,esistenza,pezzi_confezione,lotto_vendita,lotto_riordino);
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
        String via = line.substring(119,149);
        String cap = line.substring(149,154);
        String citta = line.substring(154,184);
        String provincia = line.substring(184,186);
        String descrizione_blocco = line.substring(186,216);
        String blocco = line.substring(216,217);
        String fuorifido = line.substring(217,218);
        String email = line.substring(218,268);
        String codicepagamento = line.substring(268,271);
        String descrizionepagamento = line.substring(271,331);
        String banca = line.substring(331,431);
        String porto = line.substring(431,461);
        String vettore1 = line.substring(461,561);
        String vettore2 = line.substring(561,661);
        Cliente clie = new Cliente(codice, descrizione, descrizione2, listino, telefono, fax, telex, via, cap, citta, provincia, descrizione_blocco, blocco, fuorifido, email,codicepagamento,descrizionepagamento,banca,vettore1,vettore2);
        clie.save();
        return clie;
    }

    public static Listino importaListino(String line){
        String codicearticolo = line.substring(0,0 + 15);
        String codicelistino = line.substring(15,18);
        String qt1 = (line.substring(18,35));
        String prezzo1 = (line.substring(35,52));
        String sconto1 = line.substring(52,55);
        String provvigioni1 = line.substring(55,63);

        String qt2 = line.substring(63,80);
        String prezzo2 = line.substring(80,97);
        String sconto2 = line.substring(97,100);
        String provvigioni2 = line.substring(100,108);

        String qt3 = line.substring(108,125);
        String prezzo3 = line.substring(125,142);
        String sconto3 = line.substring(142,145);
        String provvigioni3 = line.substring(145,153);

        String qt4 = line.substring(153,170);
        String prezzo4 = line.substring(170,187);
        String sconto4 = line.substring(187,190);
        String provvigioni4 = line.substring(190,198);
        Listino listi = new Listino(codicearticolo, codicelistino,qt1, prezzo1, sconto1, provvigioni1, qt2, prezzo2, sconto2, provvigioni2, qt3, prezzo3, sconto3, provvigioni3, qt4, prezzo4, sconto4, provvigioni4);
        listi.save();
        return listi;
    }

    public static Barcode importaBarcode(String line){
        String codicearticolo = line.substring(0,0 + 15);
        String codiceabarre = line.substring(15,45);
        Barcode barco = new Barcode(codicearticolo, codiceabarre);
        barco.save();
        return barco;
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
}
