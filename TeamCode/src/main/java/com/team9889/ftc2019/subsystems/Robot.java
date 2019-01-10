package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
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
    private Camera mCamera = new Camera();
    private Arms mArms = new Arms();

    private ElapsedTime timer = new ElapsedTime();

    private List<Subsystem> subsystems = Arrays.asList(
            mDrive, mLift, mIntake, mCamera, mArms // Add more subsystems here as needed
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

        timer.reset();
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
    public void update(ElapsedTime time) {
        for (Subsystem subsystem: subsystems) {
            subsystem.update(timer);
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

    public Lift getLift() {return mLift;}

    public Camera getCamera() {return mCamera;}

    public Arms getArms() {return mArms;}

//    public void setMineralPositions(Arms.MineralPositions state){
//        switch (state){
//            case GOLDGOLD:
//                armTimer.reset();
//                getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
//                if (getArms().getLeftElbowPosition() <= .115 && getLeftShoulderPosition() <= .065 && getRightElbowPosition() >= .88 && getRightShoulderPosition() >= .95) {
//                    setRightClawOpen(false);
//                    setLeftClawOpen(false);
//                    if (getLeftClawPosition() <= .6 && getRightClawPosition() <= .6) {
//                        Robot.getInstance().getLift().setLiftState(Lift.LiftStates.SCOREINGHEIGHT);
//                        if (Robot.getInstance().getLift().getHeight() >= 13) {
//                            setArmsStates(Arms.ArmStates.GOLDGOLD);
//                        }
//                    }
//                }
//                break;
//
//            case SILVERSILVER:
//                getLift().setLiftState();
//                getArms().setArmsStates(Arms.ArmStates.GRABSILVERSILVER);
//
//                getArms().setRightClawOpen(false);
//                getArms().setLeftClawOpen(false);
//
//                Robot.getInstance().getLift().setLiftState(Lift.LiftStates.SCOREINGHEIGHT);
//
//                getArms().setArmsStates(Arms.ArmStates.SILVERSILVER);
//                break;
//
//            case SILVERGOLD:
//                getArms().setArmsStates(Arms.ArmStates.GRABSILVERSILVER);
//                getArms().setRightClawOpen(false);
//                getArms().setLeftClawOpen(false);
//                Robot.getInstance().getLift().setLiftState(Lift.LiftStates.SCOREINGHEIGHT);
//                getArms().setArmsStates(Arms.ArmStates.SILVERGOLD);
//                break;
//
//        }
//    }
}
