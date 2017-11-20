package com.example.edoardo.luxelodge.database;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by edoardo on 20/11/2017.
 */

public class Query {
    public static List<Barcode> getBarcode(String barcode){
        String SQL1 = "SELECT * FROM BARCODE WHERE CODICEABARRE LIKE '%" + barcode + "%'";
        List<Barcode> barcodefind;
        barcodefind = Barcode.findWithQuery(Barcode.class,SQL1);
        return barcodefind;
    }
    public static List<Listino> getListino(String codicearticolo, String codicelistino){
        String SQL1 = "SELECT * FROM LISTINO WHERE CODICEARTICOLO LIKE '%" + codicearticolo + "%' AND CODICELISTINO LIKE '%" + codicelistino + "%'";
        List<Listino> listinofind;
        listinofind = Listino.findWithQuery(Listino.class,SQL1);
        return listinofind;
    }
    public static List<Articolo> getArticolo(String codicearticolo){
        String SQL1 = "SELECT * FROM ARTICOLO WHERE CODICEARTICOLO LIKE '%" + codicearticolo + "%'";
        List<Articolo> articolofind;
        articolofind = Articolo.findWithQuery(Articolo.class,SQL1);
        return articolofind;
    }

}
