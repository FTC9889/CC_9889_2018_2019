package com.team9889.lib.hardware;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by joshua9889 on 10/5/2018.
 */

public class ModernRoboticsUltrasonic {
    private ModernRoboticsI2cRangeSensor ping;

    public ModernRoboticsUltrasonic(String id, HardwareMap hardwareMap){
        ping = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, id);
    }

    public double getDistance(DistanceUnit unit) {
        return ping.getDistance(unit);
    }
}
