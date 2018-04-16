package com.team9889.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by joshua9889 on 3/28/2018.
 */

public abstract class Subsystem {
    public abstract void init(HardwareMap hardwareMap, boolean auton);

    public abstract void zeroSensors();

    public abstract void outputToTelemetry(Telemetry telemetry);

    public abstract void test(Telemetry telemetry);

    public abstract void stop();
}