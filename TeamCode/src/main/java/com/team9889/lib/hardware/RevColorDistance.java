package com.team9889.lib.hardware;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.team9889.ftc2019.Constants;

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
            if(sensorColor==null)
                sensorColor = hardwareMap.get(ColorSensor.class, id);

            if(sensorDistance==null)
                sensorDistance = hardwareMap.get(DistanceSensor.class, id);

            Constants.LOG.write("RevColorDistance " + id +" - Initialed");
        }
    }

    public double getCm(){
        return sensorDistance.getDistance(DistanceUnit.CM);
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
}
