package com.example.edoardo.luxelodge.importazione;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.edoardo.luxelodge.Utility.Utility;
import com.example.edoardo.luxelodge.activity.Sincronizza;
import com.example.edoardo.luxelodge.gestioneerrori.LOGClass;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by edoardo on 21/12/16.
 */
public class AsyncFTPDownloader extends AsyncTask<String, Void, Void> {
// downloadAndSaveFile(String server, int portNumber,String user, String password, String filename, File localFile)
    private String server;
    private int portNumber;
    private String user;
    private String password;
    private ArrayList<String> filenames;
    private ArrayList<File> localFiles;
    private ArrayList<Boolean> success = new ArrayList<Boolean>();
    private Sincronizza sincronizza;
    boolean result;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getFilenames() {
        return filenames;
    }

    public void setFilenames(ArrayList<String> filenames) {
        this.filenames = filenames;
    }

    public List<File> getLocalFiles() {
        return localFiles;
    }

    public void setLocalFiles(ArrayList<File> localFiles) {
        this.localFiles = localFiles;
    }

    public AsyncFTPDownloader(String server, int portNumber, String user, String password, ArrayList<String> filenames, ArrayList<File> localFiles, Sincronizza sincronizza) {
        this.server = server;
        this.portNumber = portNumber;
        this.user = user;
        this.password = password;
        this.filenames = filenames;
        this.localFiles = localFiles;
        this.sincronizza = sincronizza;
    }

    @Override
    protected Void doInBackground(String... params) {
        result = true;
        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            Log.v(LOGClass.IMPORTAZIONE, "Cerco di collegarmi all'FTP");
            ftp.connect(server);

            ftp.login(user, password);
            Log.v(LOGClass.IMPORTAZIONE, "Collegato a FTP: " + server );
            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            OutputStream outputStream = null;
            try {
                if(localFiles.size() == filenames.size()) {
                    int i = 0;
                    for(i=0; i < localFiles.size(); i++) {
                        success.add(false);
                        try {
                            outputStream = new BufferedOutputStream(new FileOutputStream(
                                    localFiles.get(i)));
                            success.set(i, ftp.retrieveFile(filenames.get(i), outputStream));
                            outputStream.flush();

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            Log.e(LOGClass.IMPORTAZIONE, "Eccezione 1 non gestita");
                            result = false;
                        }finally {
                            outputStream.flush();
                            outputStream.close();
                        }
                        if(success.get(i) == true) Log.v(LOGClass.IMPORTAZIONE, "Copiato correttamente il file: " + filenames.get(i));

                        else{
                            Log.e(LOGClass.IMPORTAZIONE, "Copia fallita: " + filenames.get(i));
                            result = false;
                        }
                        sincronizza.updateBarHandler.post(new Runnable() {

                            public void run() {

                                sincronizza.barProgressDialog.incrementProgressBy(33);

                            }

                        });
                    }
                }
                else{
                    // gestisco l'errore
                    Log.e(LOGClass.IMPORTAZIONE, "Gli ArrayList hanno diverse dimensioni");
                    result = false;
                }

            }catch (Exception e) {
                e.printStackTrace();
                Log.e(LOGClass.IMPORTAZIONE, "Eccezione 2 non gestita");
                result = false;
            }finally{
                if (outputStream != null) {
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e(LOGClass.IMPORTAZIONE, "Eccezione 3 non gestita");
            result = false;
        }
        finally {

        }


        return null;
    }

    protected void onPostExecute(Void unused) {
        sincronizza.onDownloadComplete(result);
    }
}
