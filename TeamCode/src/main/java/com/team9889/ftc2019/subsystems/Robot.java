package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.List;

/**
 * Created by joshua9889 on 3/28/2018.
 */

public class Robot extends Subsystem{

    private static Robot mInstance = null;

    public static Robot getInstance() {
        if(mInstance == null)
            mInstance = new Robot();

        return mInstance;
    }

    private Drive mDrive = new Drive();
    private Lift mLift = new Lift();
    private Intake mIntake = new Intake();

    private List<Subsystem> subsystems = Arrays.asList(
            mDrive, mLift, mIntake // Add more subsystems here as needed
    );

    /**
     * @param hardwareMap Hardware Map of the OpMode
     * @param autonomous Are we running autonomous? (Used for gyros and the like)
     */
    @Override
    public void init(HardwareMap hardwareMap, boolean autonomous){
        for (Subsystem subsystem: subsystems){
            RobotLog.a("=========== Initialing "+subsystem.toString()+" ===========");
            subsystem.init(hardwareMap, autonomous);
            RobotLog.a("=========== Finished Initialing "+subsystem.toString()+" ===========");
        }
    }

    @Override
    public void zeroSensors() {
        for (Subsystem subsystem: subsystems){
            RobotLog.a("=========== Zeroing "+subsystem.toString()+" ===========");
            subsystem.zeroSensors();
            RobotLog.a("=========== Finished Zeroing "+subsystem.toString()+" ===========");
        }
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        for (Subsystem subsystem: subsystems){
            subsystem.outputToTelemetry(telemetry);
        }
    }

    @Override
    public void test(Telemetry telemetry) {
        for (Subsystem subsystem: subsystems){
            RobotLog.a("=========== Testing "+subsystem.toString()+" ===========");
            subsystem.test(telemetry);
            RobotLog.a("=========== Finished Testing "+subsystem.toString()+" ===========");
        }
    }

    @Override
    public void stop() {
        for (Subsystem subsystem: subsystems){

            RobotLog.a("=========== Stopping "+subsystem.toString()+" ===========");
            subsystem.stop();
            RobotLog.a("=========== Finished Stopping "+subsystem.toString()+" ===========");
        }
    }

    public Drive getDrive(){
        return mDrive;
    }

    public Intake getIntake() {
        return mIntake;
    }
}
