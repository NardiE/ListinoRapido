package com.example.edoardo.luxelodge.importazione;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.edoardo.luxelodge.activity.Sincronizza;
import com.example.edoardo.luxelodge.gestioneerrori.LOGClass;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.edoardo.luxelodge.activity.Sincronizza.*;


/**
 * Created by edoardo on 21/12/16.
 */
public class AsyncImporter extends AsyncTask<String, Void, Void> {
// downloadAndSaveFile(String server, int portNumber,String user, String password, String filename, File localFile)
    private Sincronizza sincronizza;



    public AsyncImporter(Sincronizza sincronizza) {
        this.sincronizza = sincronizza;
    }

    @Override
    protected Void doInBackground(String... params) {
        cleanUp();
        sincronizza.importaArticolo();
        sincronizza.updateBarHandler.post(new Runnable() {

            public void run() {

                sincronizza.barProgressDialog.incrementProgressBy(33);

            }

        });
        sincronizza.importaListini();
        sincronizza.updateBarHandler.post(new Runnable() {

            public void run() {

                sincronizza.barProgressDialog.incrementProgressBy(33);

            }

        });
        sincronizza.importaBarcode();
        sincronizza.updateBarHandler.post(new Runnable() {

            public void run() {

                sincronizza.barProgressDialog.incrementProgressBy(33);

            }

        });

        sincronizza.barProgressDialog.dismiss();
        return null;
    }

    protected void onPostExecute(Void unused) {

        sincronizza.onTaskComplete();
    }
}
