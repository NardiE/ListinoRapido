package com.example.edoardo.luxelodge.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.edoardo.luxelodge.R;
import com.example.edoardo.luxelodge.Utility.Utility;
import com.example.edoardo.luxelodge.activity.Scanning;
import com.example.edoardo.luxelodge.classivarie.TipiConfigurazione;
import com.example.edoardo.luxelodge.classivarie.TipoExtra;
import com.example.edoardo.luxelodge.classivarie.TipoOp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import static com.example.edoardo.luxelodge.activity.Sincronizza.cleanUp;
import static com.example.edoardo.luxelodge.activity.Sincronizza.insertSample;


public class Main extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //chiedo i permessi
        getPermission();
        backupDb();
//        insertSample();
//        cleanUp();

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // setto il titolo
        getSupportActionBar().setTitle("Listino Rapido");
        getSupportActionBar().setIcon(R.drawable.logomin);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logomin);

        Typeface font = Typeface.createFromAsset(getAssets(), "font/Bauhaus.ttf");
        TextView qt = (TextView) findViewById(R.id.textView1);
        TextView bd = (TextView) findViewById(R.id.textView);

        bd.setTypeface(font);
        qt.setTypeface(font);


        // controllo che le impostazioni siano inserite
        SharedPreferences sharedpreferences = getSharedPreferences(Impostazioni.preferences, Context.MODE_PRIVATE);
        String serverftp = sharedpreferences.getString(TipiConfigurazione.serverftp,"errore");
        if(serverftp.equals("errore")){
            Intent i = new Intent(this,Impostazioni.class);
            startActivity(i);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.impostazioni) {
            Intent i = new Intent(this, Impostazioni.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.info){
            AlertDialog.Builder myb = Utility.creaDialogoVeloce(this, "Versione 1.0 \n\n Sviluppato da Signorini & C. SRL \n\n Per informazioni contattare: edoardo@signorini.it", "Informazioni");
            myb.create().show();
        }

        if (id == R.id.clear_database){

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Avviso");
            builder.setMessage("La procedura elimina qualsiasi dato dal database e va usata solo in caso di malfunzionamenti. Al termine del ripristino sarà necessario lanciare l'allineamento clienti. Procedere?");
            builder.setCancelable(false);
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    cleanUp();
                    //TODO togliere una volta finito
                    insertSample();
                    dialog.dismiss();
                    return;
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    return;
                }
            });

            builder.create().show();


        }


        return super.onOptionsItemSelected(item);
    }

    public void startQrRec(View v) {
        Intent i = new Intent(this,Scanning.class);
        i.putExtra(TipoExtra.tipoop,TipoOp.OP_NOTHING);
        startActivity(i);
    }

    public void sincronizza(View v) {
        Intent i = new Intent(this,Sincronizza.class);
        startActivity(i);
    }
    public void getPermission(){
        //chiedo permesso scrivere su sd
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }
    }

    public void backupDb(){
        try {
            String state = Environment.getExternalStorageState();
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                Log.d("Test", "sdcard mounted and writable");
            }
            else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                Log.d("Test", "sdcard mounted readonly");
            }
            else {
                Log.d("Test", "sdcard state: " + state);
            }
            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + getPackageName() + "/databases/listinoveloce.db";
                String backupDBPath = "listinoveloce.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
    }

}
