package com.example.edoardo.luxelodge.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edoardo.luxelodge.R;
import com.example.edoardo.luxelodge.Utility.Utility;
import com.example.edoardo.luxelodge.classivarie.TipiConfigurazione;
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
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Sincronizza extends AppCompatActivity {
    public static Context context;
    public ProgressDialog barProgressDialog = null;
    public final Handler updateBarHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincronizza);
        context = this;

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // setto il titolo
        getSupportActionBar().setTitle("Listino Rapido");
        getSupportActionBar().setIcon(R.drawable.logomin);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logomin);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // setto il titolo
        getSupportActionBar().setTitle("Listino Rapido");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "font/Bauhaus.ttf");
        TextView bd = (TextView) findViewById(R.id.downloadend);

        bd.setTypeface(font);
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


    public void downloadFile(View v){
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // riempio le arraylist dei file per android
        ArrayList<File> files = new ArrayList<>(2);
        files.add(new File(path, "ART.txt"));
        files.add(new File(path, "LIS.txt"));
        /*files.add(new File(path, "DES.txt"));
        files.add(new File(path, "CLI.txt"));*/
        files.add(new File(path, "BAR.txt"));

        ArrayList<String> filesname = new ArrayList<String>(2);
        filesname.add("ART.txt");
        filesname.add("LIS.txt");
        /*filesname.add("DES.txt");
        filesname.add("CLI.txt");*/
        filesname.add("BAR.txt");

        Boolean result = false;
        //TODO impostare parametri in impostazione
        SharedPreferences sharedpreferences = getSharedPreferences(Impostazioni.preferences, Context.MODE_PRIVATE);
        String serverftp = sharedpreferences.getString(TipiConfigurazione.serverftp,"ftp.signorini.it");
        String portaftp = sharedpreferences.getString(TipiConfigurazione.portaftp,"21");
        String nomeutente = sharedpreferences.getString(TipiConfigurazione.nomeutente,"signoriniftp");
        String password = sharedpreferences.getString(TipiConfigurazione.password,"signorini");
        try{
            Integer porta = Integer.parseInt(portaftp);
            AsyncFTPDownloader downloader = new AsyncFTPDownloader(serverftp, porta, nomeutente, password, filesname, files, this);
            downloader.execute();
        }catch (Exception e){
            Toast.makeText(this,"Errore Porta Controlllare Impostazioni",Toast.LENGTH_LONG).show();
        }




        barProgressDialog = new ProgressDialog(Sincronizza.this);

        barProgressDialog.setTitle("Download");
        barProgressDialog.setMessage("Sto Scaricando...");
        barProgressDialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
        barProgressDialog.setProgress(0);
        barProgressDialog.setMax(100);
        barProgressDialog.show();

    }

    public void onDownloadComplete(boolean result){
        //richiamato una volta effettuato download archivi, chiama Task per importazione nel database di tutte le tabelle necessarie
        if(result) {
            barProgressDialog.setProgress(0);
            barProgressDialog.setTitle("Importazione");
            barProgressDialog.setMessage("Sto Importando...");

            AsyncImporter importer = new AsyncImporter(this);
            importer.execute();
        }
        else{
            barProgressDialog.dismiss();
            TextView finito = (TextView)findViewById(R.id.downloadend);
            finito.setText("Errore, controllare la connessione");
            finito.setTextColor(Color.RED);
            finito.setVisibility(View.VISIBLE);
            findViewById(R.id.finito).setBackgroundResource(R.drawable.no);
        }

    }

    public void onTaskComplete(){
        //richiamato alla fine di download ed importazione
        findViewById(R.id.downloadend).setVisibility(View.VISIBLE);
        findViewById(R.id.finito).setBackgroundResource(R.drawable.ok);
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

    public void showmessage(final String message, final String title){
        runOnUiThread(new Runnable(){

            @Override
            public void run(){
                Utility.creaDialogoVeloce(context, message, title);
            }
        });
    }

    public static void cleanUp(){
        Cliente.deleteAll(Cliente.class);
        Barcode.deleteAll(Barcode.class);
        Articolo.deleteAll(Articolo.class);
        Destinazione.deleteAll(Destinazione.class);
        Articolo.deleteAll(Listino.class);
        //TODO Gestione eccezione
        Articolo.executeQuery("delete from sqlite_sequence where name='ARTICOLO'");
        Listino.executeQuery("delete from sqlite_sequence where name='LISTINO'");
    }

    public static void insertSample(){
    }

    public void returntoMain(View view) {
        Intent i = new Intent(this,Main.class);
        startActivity(i);
    }


    @Override
    public void onBackPressed() {//ritorno alla schermata precedente
    }

}
