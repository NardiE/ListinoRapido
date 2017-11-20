package com.example.edoardo.luxelodge.importazione;

import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by edoardo on 20/12/16.
 */
public class FtpDownloader {
    public static Boolean downloadAndSaveFile(String server, int portNumber,String user, String password, String filename, File localFile) throws IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);FTPClient ftp = null;

        try {
            ftp = new FTPClient();
            ftp.connect(server);

            ftp.login(user, password);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            OutputStream outputStream = null;
            boolean success = false;
            try {
                outputStream = new BufferedOutputStream(new FileOutputStream(
                        localFile));
                success = ftp.retrieveFile(filename, outputStream);
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            }


            return success;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        finally {
            if (ftp != null) {
                ftp.logout();
                ftp.disconnect();
            }
        }
    }

}
