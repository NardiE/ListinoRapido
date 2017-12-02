package com.example.edoardo.luxelodge.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edoardo.luxelodge.R;
import com.example.edoardo.luxelodge.classivarie.TipiConfigurazione;
import com.example.edoardo.luxelodge.classivarie.TipoExtra;
import com.example.edoardo.luxelodge.classivarie.TipoOp;
import com.example.edoardo.luxelodge.database.Articolo;
import com.example.edoardo.luxelodge.database.Barcode;
import com.example.edoardo.luxelodge.database.Listino;
import com.example.edoardo.luxelodge.database.Query;

import java.util.List;


public class Scanning extends AppCompatActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    Scanning sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // setto il titolo
        getSupportActionBar().setTitle("Listino Rapido");
        getSupportActionBar().setIcon(R.drawable.logomin);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logomin);

        sc = this;

        ImageView scannerBtn = (ImageView) findViewById(R.id.getcode);

        Animation shake = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.shake);
        (findViewById(R.id.layoutsc)).startAnimation(shake);

        Typeface font = Typeface.createFromAsset(getAssets(), "font/Bauhaus.ttf");
        TextView qt = (TextView) findViewById(R.id.prezzotw);
        TextView qt1 = (TextView) findViewById(R.id.lottotw);
        TextView qt2 = (TextView) findViewById(R.id.articolotw);
        TextView t1 = (TextView) findViewById(R.id.testo);
        TextView t2 = (TextView) findViewById(R.id.testo2);
        TextView t3 = (TextView) findViewById(R.id.testo3);
        EditText bde = (EditText) findViewById(R.id.barcoden);
        bde.setTypeface(font);
        t1.setTypeface(font);
        t2.setTypeface(font);
        t3.setTypeface(font);
        qt.setTypeface(font);
        qt1.setTypeface(font);
        qt2.setTypeface(font);

        scannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ZxingScannerActivity.class);
                startActivity(intent);
            }
        });
        Bundle extras = getIntent().getExtras();
        int tipoop = extras.getInt(TipoExtra.tipoop);
        if(tipoop == TipoOp.OP_SCANNERESULT){
            //mi arrivano barcode e quantita e li inserisco
            ((EditText)findViewById(R.id.barcoden)).setText(extras.getString(TipoExtra.barcode));
            popolaCampi();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scanning, menu);
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

    public void scanBarcode(View view) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
        }
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    public void cercaPrezzo(View view) {
        popolaCampi();
    }

    public void popolaCampi(){
        EditText barcoden = (EditText) findViewById(R.id.barcoden);
        List<Barcode> mylistbar;
        List<Articolo> myartlist;
        List<Listino> mylislist;
        TextView valoreprezzo = (TextView) findViewById(R.id.valoreprezzo);
        TextView valorelotto = (TextView) findViewById(R.id.valorelotto);
        TextView valorenome = (TextView) findViewById(R.id.valorenome);
        String codicearticolo;

        String codiceabarre = barcoden.getText().toString();

        //formatto il barcode
        codiceabarre = formattastringa(codiceabarre, Barcode.barcode_lenght);

        //provo a cercare barcode
        List <Barcode> listabarcode = Query.getBarcode(codiceabarre);

        if(listabarcode.size()==0){
            codicearticolo = barcoden.getText().toString();
            codicearticolo = formattastringa(codicearticolo, Articolo.lenght_codice);
        }
        else{
            codicearticolo = listabarcode.get(0).getCodicearticolo();
        }


        myartlist = Query.getArticolo(codicearticolo);
        if(myartlist.size() == 1){
            Articolo myart = myartlist.get(0);
            SharedPreferences sharedpreferences = getSharedPreferences(Impostazioni.preferences, Context.MODE_PRIVATE);
            String listino = sharedpreferences.getString(TipiConfigurazione.listinodefault,"001");
            valorenome.setText(myart.getDescrizione().toString());
            valorelotto.setText(myart.getLotto_vendita());
            mylislist = Query.getListino(codicearticolo,listino);
            if(mylislist.size() == 1){
                Listino mylis = mylislist.get(0);
                valoreprezzo.setText(mylis.getPrezzo1().toString() + " €");
                //TODO implementare valore lotto
            }
            else {
                Toast.makeText(this,"Impossibile Trovare Prezzo Articolo \n assicurari di avere un listino aggiornato \n oppure eseguire la sincronizzazione",Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this,"Impossibile Trovare Barcode \n assicurari di aver digitato correttamente \n oppure eseguire la sincronizzazione",Toast.LENGTH_LONG).show();
        }

    }

    public String formattastringa(String s, int lenght){
        while (s.length() != lenght){
            s = s + " ";
        }
        return s;
    }

    @Override
    public void onBackPressed() {
    }

    public void returntoMain(View view) {
        Intent i = new Intent(this,Main.class);
        startActivity(i);
    }
}
