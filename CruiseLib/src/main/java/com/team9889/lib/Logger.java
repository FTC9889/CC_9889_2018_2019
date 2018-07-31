package com.team9889.lib;

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

    private static Logger localIntance = new Logger("localLogs/" +
            String.valueOf(System.currentTimeMillis()) + ".txt");
    public static Logger getInstance(boolean local){
        return localIntance;
    }

    public Logger(String fileName){
        charset = Charset.forName("US-ASCII");
        try {
            logger = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void write(Object object){
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
