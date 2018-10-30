package com.team9889.lib.android;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by joshua9889 on 8/26/2018.
 *
 * Class to read data from a file.
 */

public class FileReader {
    private FileInputStream reader = null;

    public FileReader(String filename){
        this.setup(filename);
    }

    public static void main(String... args){
        FileReader fileReader = new FileReader("log.txt");
        System.out.println(fileReader.lines()[0]);
        fileReader.close();
    }

    private void setup(String filename){
        try {
            try {
                String root = Environment.getExternalStorageDirectory().toString();
                File file = new File(root + "/saved_data/" + filename);

                reader = new FileInputStream(file);
            } catch (RuntimeException e){
                reader = new FileInputStream(filename);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public String read(){
        String text = "";
        int content;
        try {
            while ((content = reader.read()) != -1) {
                // convert to char and display it
                text += (char)content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public String[] lines(){
        String wholeFile = read();
        String[] lines = wholeFile.split("\n");

        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replace("\n", "");
        }

        return lines;
    }

    public void close(){
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
