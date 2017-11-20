package com.example.edoardo.luxelodge.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.edoardo.luxelodge.R;
import com.example.edoardo.luxelodge.database.Articolo;
import com.example.edoardo.luxelodge.database.Barcode;
import com.example.edoardo.luxelodge.database.Cliente;
import com.example.edoardo.luxelodge.database.Destinazione;
import com.example.edoardo.luxelodge.database.Listino;
import com.example.edoardo.luxelodge.gestioneerrori.LOGClass;
import com.example.edoardo.luxelodge.importazione.AsyncFTPDownloader;
import com.example.edoardo.luxelodge.importazione.AsyncImporter;
import com.example.edoardo.luxelodge.importazione.ImportazioneHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Sincronizza extends AppCompatActivity {
    public static Context context;
    public ProgressDialog barProgressDialog = null;
    public final Handler updateBarHandler = new Handler();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincronizza);
        context = this;
    }

    public void downloadFile(View v){
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // riempio le arraylist dei file per android
        ArrayList<File> files = new ArrayList<>(5);
        files.add(new File(path, "ART.txt"));
        files.add(new File(path, "LIS.txt"));
        files.add(new File(path, "DES.txt"));
        files.add(new File(path, "CLI.txt"));
        files.add(new File(path, "BAR.txt"));

        ArrayList<String> filesname = new ArrayList<String>(5);
        filesname.add("ART.txt");
        filesname.add("LIS.txt");
        filesname.add("DES.txt");
        filesname.add("CLI.txt");
        filesname.add("BAR.txt");

        Boolean result = false;
        //TODO impostare parametri in impostazione
        AsyncFTPDownloader downloader = new AsyncFTPDownloader("ftp.signorini.it", 21, "signoriniftp", "signorini", filesname, files, this);
        downloader.execute();



        barProgressDialog = new ProgressDialog(Sincronizza.this);

        barProgressDialog.setTitle("Download");
        barProgressDialog.setMessage("Sto Scaricando...");
        barProgressDialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
        barProgressDialog.setProgress(0);
        barProgressDialog.setMax(100);
        barProgressDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sincronizza, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onDownloadComplete(){
        //richiamato una volta effettuato download archivi, chiama Task per importazione nel database di tutte le tabelle necessarie

        barProgressDialog.setProgress(0);
        barProgressDialog.setTitle("Importazione");
        barProgressDialog.setMessage("Sto Importando...");

        AsyncImporter importer = new AsyncImporter(this);
        importer.execute();
    }

    public void onTaskComplete(){
        //richiamato alla fine di download ed importazione

    }

    public static void importaArticolo(){
        FileInputStream is;
        BufferedReader reader;
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        final File file = new File(path,"ART.txt");
        int nonimportati = 0;
        if (file.exists()) {
            try {
                is = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(is));
                String line = reader.readLine();
                while (line != null) {
                    if(line.length() >= ImportazioneHelper.ARTICOLO_DIMENSIONESTRINGA) {
                        ImportazioneHelper.importaArticolo(line);
                        line = reader.readLine();
                    }else{
                        nonimportati++;
                        line = reader.readLine();
                    }
                }
                if (nonimportati>0){
//                    Toast.makeText(Sincronizza.context, "Articoli non importati: " + String.valueOf(nonimportati),Toast.LENGTH_LONG).show();
                }
            } catch(Exception ioe){
                Log.e(LOGClass.IMPORTAZIONE, "Errore nell' importazione degli Articoli " + ioe.toString());
                ioe.printStackTrace();
            }
        }
    }

    public static void importaCliente(){
        FileInputStream is;
        BufferedReader reader;
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        final File file = new File(path,"CLI.txt");
        int nonimportati = 0;

        if (file.exists()) {
            try {
                is = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(is));
                String line = reader.readLine();
                while (line != null) {
                    if(line.length() >= ImportazioneHelper.CLIENTE_DIMENSIONESTRINGA) {
                        ImportazioneHelper.importaCliente(line);
                        line = reader.readLine();
                    }else{
                    nonimportati++;
                    line = reader.readLine();
                    }
                }
                if (nonimportati>0){
//                    Toast.makeText(Sincronizza.context, "Clienti non importati: " + String.valueOf(nonimportati),Toast.LENGTH_LONG).show();
                }
            } catch(Exception ioe){
                Log.e(LOGClass.IMPORTAZIONE, "Errore nell' importazione dei Clienti " + ioe.toString());
                ioe.printStackTrace();
            }
        }
    }

    public static void importaDestinazione(){
        FileInputStream is;
        BufferedReader reader;
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        final File file = new File(path,"DES.txt");
        int nonimportati = 0;

        if (file.exists()) {
            try {
                is = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(is));
                String line = reader.readLine();
                while (line != null) {
                    if(line.length() >= ImportazioneHelper.DESTINAZIONE_DIMENSIONESTRINGA) {
                        ImportazioneHelper.importaDestinazione(line);
                        line = reader.readLine();
                    }else{
                        nonimportati++;
                        line = reader.readLine();
                    }
                }
                if (nonimportati>0){
//                    Toast.makeText(Sincronizza.context, "Clienti non importati: " + String.valueOf(nonimportati),Toast.LENGTH_LONG).show();
                }
            } catch(Exception ioe){
                Log.e(LOGClass.IMPORTAZIONE, "Errore nell' importazione delle Destinazioni " + ioe.toString());
                ioe.printStackTrace();
            }
        }
    }

    public static void importaListini(){
        FileInputStream is;
        BufferedReader reader;
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        final File file = new File(path,"LIS.txt");
        int nonimportati = 0;

        if (file.exists()) {
            try {
                is = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(is));
                String line = reader.readLine();
                while (line != null) {
                    if(line.length() >= ImportazioneHelper.LISTINO_DIMENSIONESTRINGA) {
                        ImportazioneHelper.importaListino(line);
                        line = reader.readLine();
                    }else{
                        nonimportati++;
                        line = reader.readLine();
                    }
                }
                if (nonimportati>0){
//                    Toast.makeText(Sincronizza.context, "Clienti non importati: " + String.valueOf(nonimportati),Toast.LENGTH_LONG).show();
                }
            } catch(Exception ioe){
                Log.e(LOGClass.IMPORTAZIONE, "Errore nell' importazione del Listino " + ioe.toString());
                ioe.printStackTrace();
            }
        }
    }

    public static void importaBarcode(){
        FileInputStream is;
        BufferedReader reader;
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        final File file = new File(path,"BAR.txt");
        int nonimportati = 0;
        if (file.exists()) {
            try {
                is = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(is));
                String line = reader.readLine();
                while (line != null) {
                    if(line.length() >= ImportazioneHelper.BARCODE_DIMENSIONESTRINGA) {
                        ImportazioneHelper.importaBarcode(line);
                        line = reader.readLine();
                    }else{
                        nonimportati++;
                        line = reader.readLine();
                    }
                }
                if (nonimportati>0){
//                    Toast.makeText(Sincronizza.context, "Clienti non importati: " + String.valueOf(nonimportati),Toast.LENGTH_LONG).show();
                }
            } catch(Exception ioe){
                Log.e(LOGClass.IMPORTAZIONE, "Errore nell' importazione del Barcode " + ioe.toString());
                ioe.printStackTrace();
            }
        }
    }

    public static void cleanUp(){
        Cliente.deleteAll(Cliente.class);
        Barcode.deleteAll(Barcode.class);
        Articolo.deleteAll(Articolo.class);
        Destinazione.deleteAll(Destinazione.class);
        Articolo.deleteAll(Listino.class);
        //TODO Gestione eccezione
        Cliente.executeQuery("delete from sqlite_sequence where name='CLIENTE'");
        Barcode.executeQuery("delete from sqlite_sequence where name='BARCODE'");
        Articolo.executeQuery("delete from sqlite_sequence where name='ARTICOLO'");
        Destinazione.executeQuery("delete from sqlite_sequence where name='DESTINAZIONE'");
        Listino.executeQuery("delete from sqlite_sequence where name='LISTINO'");
    }

    public static void insertSample(){
        ArrayList <Cliente> clienti = new ArrayList<>();
        ArrayList <Articolo> articoli = new ArrayList<>();
        ArrayList <Barcode> barcode = new ArrayList<>();
        //ArrayList <Destinazione> destinazioni = new ArrayList<>();
        ArrayList <Listino> listini = new ArrayList<>();
        clienti.add(new Cliente("codice", "descrizione", "descrizione2", "listino", "codiceblocco", "telefono", "fax", "telex", "via", "cap", "citta", "provincia", "descrizioneblocco", 0, 0));
        articoli.add(new Articolo("codice", "descrizione", "descrizione2", "UM", 0, 0));
        barcode.add(new Barcode("codicearticolo", "codiceabarre"));
        listini.add(new Listino("codicearticolo", "codicelistino", new Float(1.0)));
        for (Cliente object: clienti) {
            object.save();
        }
        for (Articolo object: articoli) {
            object.save();
        }
        for (Barcode object: barcode) {
            object.save();
        }
        /*for (Destinazione object: destinazioni) {
            object.save();
        }*/
        for (Listino object: listini) {
            object.save();
        }
    }
}
