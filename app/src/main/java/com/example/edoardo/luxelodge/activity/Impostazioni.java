package com.example.edoardo.luxelodge.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


import com.example.edoardo.luxelodge.R;
import com.example.edoardo.luxelodge.classivarie.TipiConfigurazione;


public class Impostazioni extends AppCompatActivity {
    public static String preferences = "preferenze";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedpreferences = getSharedPreferences(preferences, Context.MODE_PRIVATE);

        EditText serverftp = (EditText) findViewById(R.id.serverftp);
        EditText portaftp = (EditText) findViewById(R.id.portaftp);
        EditText nomeutente = (EditText) findViewById(R.id.nomeutente);
        EditText password = (EditText) findViewById(R.id.password);
        EditText listino = (EditText) findViewById(R.id.listino);
        serverftp.setText(sharedpreferences.getString(TipiConfigurazione.serverftp, ""));
        portaftp.setText(sharedpreferences.getString(TipiConfigurazione.portaftp, ""));
        nomeutente.setText(sharedpreferences.getString(TipiConfigurazione.nomeutente, ""));
        password.setText(sharedpreferences.getString(TipiConfigurazione.password,""));
        listino.setText(sharedpreferences.getString(TipiConfigurazione.listinodefault,""));
    }

    public void salvaConfigurazioni(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences(preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String serverftp = ((EditText) findViewById(R.id.serverftp)).getText().toString();
        String portaftp = ((EditText) findViewById(R.id.portaftp)).getText().toString();
        String nomeutente = ((EditText) findViewById(R.id.nomeutente)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String listino = ((EditText) findViewById(R.id.listino)).getText().toString();

        editor.putString(TipiConfigurazione.serverftp, serverftp);
        editor.putString(TipiConfigurazione.portaftp, portaftp);
        editor.putString(TipiConfigurazione.nomeutente, nomeutente);
        editor.putString(TipiConfigurazione.password, password);
        editor.putString(TipiConfigurazione.listinodefault, listino);
        editor.apply();
        editor.commit();

        // configurazione incompleta
        if(serverftp.equals("") || portaftp.equals("") || nomeutente.equals("") || password.equals("") || listino.equals("")){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Avviso");
            builder.setMessage("Configurazione Incompleta");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    return;
                }
            });

            builder.create().show();
            return;
        }
        else{
            Intent i = new Intent(this, Main.class);
            startActivity(i);
            return;
        }
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }
}
