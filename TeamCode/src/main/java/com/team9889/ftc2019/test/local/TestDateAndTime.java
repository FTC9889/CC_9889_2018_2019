package com.team9889.ftc2019.test.local;

import com.team9889.ftc2019.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.team9889.lib.android.FileWriter.NORMAL;

public class TestDateAndTime {
    public static void main(String... args){
        Date currentData = new Date();
        SimpleDateFormat format = new SimpleDateFormat("M.dd.yyyy hh:mm:ss");

        Constants.LOG.write(NORMAL, "Robot Init Started at " + format.format(currentData));
        Constants.LOG.close();
    }
}
