package com.example.edoardo.luxelodge.database;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by edoardo on 20/11/2017.
 */

public class Query {

    public static List<Listino> getListino(String codicearticolo, String codicelistino){
        String SQL1 = "SELECT * FROM LISTINO WHERE CODICEARTICOLO LIKE '%" + codicearticolo + "%' AND CODICELISTINO LIKE '%" + codicelistino + "%'";
        List<Listino> listinofind;
        listinofind = Listino.findWithQuery(Listino.class,SQL1);
        return listinofind;
    }
    public static List<Articolo> getArticolo(String codicearticolo){
        String SQL1 = "SELECT * FROM ARTICOLO WHERE CODICE LIKE '%" + codicearticolo + "%'";
        List<Articolo> articolofind;
        articolofind = Select.from(Articolo.class)
                .where(Condition.prop("CODICE").eq(codicearticolo))
                .list();
        return articolofind;
    }
    public static List<Barcode> getBarcode(String barcode){
        String SQL1 = "SELECT * FROM BARCODE WHERE CODICEABARRE = '%" + barcode + "%'";
        List<Barcode> barcodefind;
        barcodefind = Select.from(Barcode.class)
                .where(Condition.prop("CODICEABARRE").eq(barcode))
                .list();
        return barcodefind;
    }

}
