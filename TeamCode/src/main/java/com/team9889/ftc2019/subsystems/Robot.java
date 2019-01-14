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

    public enum MineralPositions{
        GOLDGOLD, SILVERSILVER, SILVERGOLD
    }

    private static Robot mInstance = null;
    private ElapsedTime clawTimer = new ElapsedTime();

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
            telemetry.addData(subsystem.toString(), "");
            subsystem.outputToTelemetry(telemetry);
            telemetry.addLine();
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

    private int tracker = 0;

    public void resetTracker(){
        tracker = 0;
    }

    public void setMineralPositions(MineralPositions state){
        switch (state){
            case GOLDGOLD:
                switch (tracker) {
                    case 0:
                        getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
                        getArms().setRightClawOpen(true);
                        getArms().setLeftClawOpen(true);
                        if(getArms().isCurrentStateWantedState())
                            tracker++;
                        break;
                    case 1:
                        getLift().setLiftState(Lift.LiftStates.DOWN);
                        if(getLift().isCurrentWantedState()) {
                            tracker++;
                            clawTimer.reset();
                        }
                        break;
                    case 2:
                        getArms().setRightClawOpen(false);
                        getArms().setLeftClawOpen(false);
                        if (clawTimer.milliseconds() > 1000)
                            tracker++;
                        break;
                    case 3:
                        getLift().setLiftState(Lift.LiftStates.SCOREINGHEIGHT);
                        if (getLift().isCurrentWantedState())
                            tracker++;
                        break;
                    case 4:
                        getArms().setArmsStates(Arms.ArmStates.GOLDGOLD);
                        if(getArms().isCurrentStateWantedState())
                            tracker++;
                        break;
                    case 5:
                        if(getArms().bothOpen())
                            tracker++;
                        break;
                    case 6:
                        getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
                        if(getArms().isCurrentStateWantedState())
                            tracker++;
                        break;
                    case 7:
                        getLift().setLiftState(Lift.LiftStates.READY);
                        if(getLift().isCurrentWantedState())
                            tracker++;
                        break;
                }
                break;

            case SILVERSILVER:
                getLift().setLiftState(Lift.LiftStates.READY);
                getArms().setArmsStates(Arms.ArmStates.GRABSILVERSILVER);

                getArms().setRightClawOpen(false);
                getArms().setLeftClawOpen(false);

                Robot.getInstance().getLift().setLiftState(Lift.LiftStates.SCOREINGHEIGHT);

                getArms().setArmsStates(Arms.ArmStates.SILVERSILVER);
                break;

            case SILVERGOLD:
                getArms().setArmsStates(Arms.ArmStates.GRABSILVERSILVER);
                getArms().setRightClawOpen(false);
                getArms().setLeftClawOpen(false);
                Robot.getInstance().getLift().setLiftState(Lift.LiftStates.SCOREINGHEIGHT);
                getArms().setArmsStates(Arms.ArmStates.SILVERGOLD);
                break;

        }

        RobotLog.d("setMineralPositions has been updated");
    }
}
