package com.team9889.lib.android;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by joshua9889 on 4/5/2018.
 *
 * Class to write data to a file.
 */

public class FileWriter {
    private FileOutputStream writer = null;
    private Charset charset;

    public static int NORMAL = 0;
    public static int ERROR = 1;

    public FileWriter(String filename){
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

                writer = new FileOutputStream(file);
            } catch (RuntimeException e){
                writer = new FileOutputStream(filename);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void write(Object object){
        charset = Charset.forName("US-ASCII");
        String printString = String.valueOf(object) + "\n";
        try {
            writer.write(printString.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(int priority, Object object){
        String printString = priority + " " + String.valueOf(object);
        write(printString);
    }

    public void close(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
