package com.example.edoardo.luxelodge.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edoardo.luxelodge.R;
import com.example.edoardo.luxelodge.*;
import com.example.edoardo.luxelodge.classivarie.TipoExtra;
import com.example.edoardo.luxelodge.database.Articolo;
import com.example.edoardo.luxelodge.database.Barcode;
import com.example.edoardo.luxelodge.database.Listino;
import com.example.edoardo.luxelodge.database.Query;
import com.example.edoardo.luxelodge.utility.Utility;

import java.util.List;


public class Scanning extends AppCompatActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);


        ImageView scannerBtn = (ImageView) findViewById(R.id.getcode);

        Typeface font = Typeface.createFromAsset(getAssets(), "font/Bauhaus.ttf");
        TextView qt = (TextView) findViewById(R.id.prezzotw);
        TextView qt1 = (TextView) findViewById(R.id.lottotw);
        TextView bd = (TextView) findViewById(R.id.valoreprezzo);
        TextView bd1 = (TextView) findViewById(R.id.valorelotto);
        TextView t1 = (TextView) findViewById(R.id.testo);
        TextView t2 = (TextView) findViewById(R.id.testo2);
        TextView t3 = (TextView) findViewById(R.id.testo3);
        EditText bde = (EditText) findViewById(R.id.barcoden);
        bd.setTypeface(font);
        bd1.setTypeface(font);
        bde.setTypeface(font);
        t1.setTypeface(font);
        t2.setTypeface(font);
        t3.setTypeface(font);
        qt.setTypeface(font);
        qt1.setTypeface(font);

        scannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), ZxingScannerActivity.class);
                    startActivity(intent);
            }
        });
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
        EditText barcoden = (EditText) findViewById(R.id.barcoden);
        String codiceabarre = barcoden.getText().toString();
        List<Barcode> mylistbar = Query.getBarcode(codiceabarre);
        Barcode mybar = mylistbar.get(0);
        String codicearticolo = mybar.getCodicearticolo();
        Articolo articolo = Query.getArticolo(codicearticolo).get(0);
        //TODO PARAMETRIZZARE
        Listino mylis = Query.getListino(codicearticolo,"001").get(0);
    }
}
