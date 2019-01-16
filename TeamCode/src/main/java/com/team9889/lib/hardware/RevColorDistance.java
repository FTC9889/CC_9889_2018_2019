package com.team9889.lib.hardware;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by joshua9889 on 2/16/2018.
 *
 * Class to wrap the things into one class
 */

public class RevColorDistance{
    private ColorSensor sensorColor = null;
    private DistanceSensor sensorDistance = null;
    private HardwareMap hardwareMap = null;
    private String id = null;

    public RevColorDistance(String id, HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        this.id = id;

        if (hardwareMap != null) {
            if(sensorColor==null) {
                sensorColor = hardwareMap.get(ColorSensor.class, id);
                sensorColor.enableLed(false);
            }

            if(sensorDistance==null)
                sensorDistance = hardwareMap.get(DistanceSensor.class, id);

            RobotLog.d("===== RevColorDistance " + id + " - Initialed =====");
        }
    }

    public double getIN(){
        if(sensorDistance != null)
            return sensorDistance.getDistance(DistanceUnit.INCH);
        else
            return 0;
    }

    public double red(){
        return sensorColor.red();
    }

    public double green(){
        return sensorColor.green();
    }

    public double blue(){
        return sensorColor.blue();
    }

    public float[] hsv() {
        float[] hsvOutput = new float[3];
        Color.RGBToHSV((int)red()*100, (int)green()*100, (int)blue()*100, hsvOutput);
        return hsvOutput;
    }
}
