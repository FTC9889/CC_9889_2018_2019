package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.openftc.revextensions2.ExpansionHubEx;
import org.openftc.revextensions2.ExpansionHubMotor;
import org.openftc.revextensions2.RevBulkData;

/**
 * Created by joshua9889 on 4/7/2019.
 */
public class RevHub {
    private RevBulkData hub2Data, hub3Data;
    private ExpansionHubEx hub2, hub3;

    private static RevHub instance = null;

    private RevHub(){}
    public static RevHub getInstance() {
        if(instance == null)
            instance = new RevHub();

        return instance;
    }

    public RevBulkData getHub2Data() {
        return hub2Data;
    }

    public RevBulkData getHub3Data() {
        return hub3Data;
    }

    public int getMotorPosition(ExpansionHubMotor motor) {
        try {
            return getHub2Data().getMotorCurrentPosition(motor);
        } catch (Exception ignored){
            RobotLog.e("Could not find " + motor.toString() + " in Hub 2. Trying Hub 3.");
        }

        try {
            return getHub3Data().getMotorCurrentPosition(motor);
        } catch (Exception ignored){
            RobotLog.e("Could not find " + motor.toString() + " in Hub 3.");
        }

        return 0;
    }

    public double getMotorVelocity(ExpansionHubMotor motor) {
        try {
            return getHub2Data().getMotorVelocity(motor);
        } catch (Exception ignored){
            RobotLog.e("Could not find " + motor.toString() + " in Hub 2. Trying Hub 3.");
        }

        try {
            return getHub3Data().getMotorVelocity(motor);
        } catch (Exception ignored){
            RobotLog.e("Could not find " + motor.toString() + " in Hub 3.");
        }

        return 0;
    }

    public boolean getDigitalState(DigitalChannel channel) {
        try {
            return getHub2Data().getDigitalInputState(channel);
        } catch (Exception ignored){
            RobotLog.e("Could not find " + channel.toString() + " in Hub 2. Trying Hub 3.");
        }

        try {
            return getHub3Data().getDigitalInputState(channel);
        } catch (Exception ignored){
            RobotLog.e("Could not find " + channel.toString() + " in Hub 3.");
        }

        return false;
    }

    public int getAnalogInput(AnalogInput input) {
        try {
            return getHub2Data().getAnalogInputValue(input);
        } catch (Exception ignored){
            RobotLog.e("Could not find " + input.toString() + " in Hub 2. Trying Hub 3.");
        }

        try {
            return getHub3Data().getAnalogInputValue(input);
        } catch (Exception ignored){
            RobotLog.e("Could not find " + input.toString() + " in Hub 3.");
        }

        return 0;
    }

    public void init(HardwareMap hardwareMap) {
        hub2 = hardwareMap.get(ExpansionHubEx.class, "2");
        hub3 = hardwareMap.get(ExpansionHubEx.class, "3");
    }

    public void update() {
        hub2Data = hub2.getBulkInputData();
        hub3Data = hub3.getBulkInputData();
    }

    public void stop() {
        hub2.close();
        hub3.close();
    }
}
