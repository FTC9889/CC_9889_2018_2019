package com.team9889.lib;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by joshua9889 on 4/5/2018.
 *
 * Class to log data quickly
 */

public class Logger {
    private FileOutputStream logger = null;
    private Charset charset;

    public Logger(String filename){
        this.setup(filename);
    }

    private void setup(String filename){
        try {
            try {
                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/saved_data");
                myDir.mkdirs();
                File file = new File(myDir, filename);
                if (file.exists())
                    file.delete();

                logger = new FileOutputStream(file);
            } catch (RuntimeException e){
                logger = new FileOutputStream(filename);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void write(Object object){
        charset = Charset.forName("US-ASCII");
        String printString = String.valueOf(object) + "\n";
        try {
            logger.write(printString.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            logger.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
