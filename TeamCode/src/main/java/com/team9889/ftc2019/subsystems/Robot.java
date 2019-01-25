package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.team9889.ftc2019.states.LiftStates;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.List;

/**
 * Created by joshua9889 on 3/28/2018.
 */

public class Robot extends Subsystem {

    private static Robot mInstance = null;

    private ElapsedTime clawTimer = new ElapsedTime();
    private ElapsedTime liftArmsTimer = new ElapsedTime();
    private ElapsedTime dropTimer = new ElapsedTime();
    private Drive mDrive = new Drive();
    private Lift mLift = new Lift();
    private Intake mIntake = new Intake();
    private Camera mCamera = new Camera();
    private Arms mArms = new Arms();
    private ElapsedTime timer = new ElapsedTime();
    private List<Subsystem> subsystems = Arrays.asList(
            mDrive, mLift, mIntake, mCamera, mArms // Add more subsystems here as needed
    );

    private MineralPositions whichMineral = Robot.MineralPositions.SILVERGOLD;
    private int tracker = 8;


    public static Robot getInstance() {
        if (mInstance == null)
            mInstance = new Robot();

        return mInstance;
    }

    /**
     * @param hardwareMap Hardware Map of the OpMode
     * @param autonomous  Are we running autonomous? (Used for gyros and the like)
     */
    @Override
    public void init(HardwareMap hardwareMap, boolean autonomous) {
        for (Subsystem subsystem : subsystems) {
            RobotLog.a("=========== Initialing " + subsystem.toString() + " ===========");
            subsystem.init(hardwareMap, autonomous);
            RobotLog.a("=========== Finished Initialing " + subsystem.toString() + " ===========");
        }

        // Scoring State Machine Init
        whichMineral = Robot.MineralPositions.SILVERGOLD;
        tracker = 8;
        timer.reset();
    }

    @Override
    public void zeroSensors() {
        for (Subsystem subsystem : subsystems) {
            RobotLog.a("=========== Zeroing " + subsystem.toString() + " ===========");
            subsystem.zeroSensors();
            RobotLog.a("=========== Finished Zeroing " + subsystem.toString() + " ===========");
        }
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        for (Subsystem subsystem : subsystems) {
            telemetry.addData(subsystem.toString(), "");
            subsystem.outputToTelemetry(telemetry);
            telemetry.addLine();
        }
    }

    @Override
    public void update(ElapsedTime time) {
        for (Subsystem subsystem : subsystems) {
            subsystem.update(timer);
        }

        scoringStateMachine();
    }

    @Override
    public void test(Telemetry telemetry) {
        for (Subsystem subsystem : subsystems) {
            RobotLog.a("=========== Testing " + subsystem.toString() + " ===========");
            subsystem.test(telemetry);
            RobotLog.a("=========== Finished Testing " + subsystem.toString() + " ===========");
        }
    }

    @Override
    public void stop() {
        for (Subsystem subsystem : subsystems) {

            RobotLog.a("=========== Stopping " + subsystem.toString() + " ===========");
            subsystem.stop();
            RobotLog.a("=========== Finished Stopping " + subsystem.toString() + " ===========");
        }
    }

    public Drive getDrive() {
        return mDrive;
    }

    public Intake getIntake() {
        return mIntake;
    }

    public Lift getLift() {
        return mLift;
    }

    public Camera getCamera() {
        return mCamera;
    }

    public Arms getArms() {
        return mArms;
    }

    public void resetTracker() {
        tracker = 0;
    }

    public void setWantedSuperStructure(MineralPositions mineralPositions) {
        whichMineral = mineralPositions;
    }

    private void scoringStateMachine() {
        MineralPositions state = whichMineral;
        getLift().liftOperatorControl = false;

        if (tracker <= 1) {
            getIntake().updateMineralVote();
            RobotLog.a("Running updateMineralVote");
        }

//        Using the color sensors in the hopper, the code decides which set of logic to run
        switch (state) {
            case GOLDGOLD:
//                This is the logic if both of the minerals in the hopper are Gold. We use the variable "tracker" to decide which case to run
                switch (tracker) {
//                    To start we set some variables, open the claws, and position the arms to grab the minerals. Once that's done, the code adds one to tracker so that the next
//                    time it loops through it will run case 1
                    case 0:
                        getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
                        getArms().setLeftClawOpen(true);
                        getArms().setRightClawOpen(true);
                        if (getArms().isCurrentStateWantedState())
                            tracker++;
                        break;

//                        Next we have the lift to go all the way down. Once the lift gets to the correct position it adds one to tracker and resets the clawTimer
                    case 1:
                        getLift().setLiftState(LiftStates.DOWN);
                        if (getLift().isCurrentWantedState()) {
                            tracker++;
                            clawTimer.reset();
                        }
                        break;

//                        Now the claws close to grab the minerals. Once the clawTimer is > 1000 milliseconds it will add one to tracker
                    case 2:
                        getArms().setLeftClawOpen(false);
                        getArms().setRightClawOpen(false);
                        if (clawTimer.milliseconds() > 1000)
                            tracker++;
                        break;

//                        Next the lift goes to full height. Once the lift gets there it adds one to tracker
                    case 3:
                        getLift().setLiftState(LiftStates.SCOREINGHEIGHT);
                        if (getLift().isCurrentWantedState())
                            tracker++;
                        break;

//                        Now the arms go to a certain position according to which minerals are in the hopper, in this case GOLDGOLD. Once the arms are in position
//                        it adds 1 to tracker
                    case 4:
                        getArms().setArmsStates(Arms.ArmStates.GOLDGOLD);
                        if (getArms().isCurrentStateWantedState()) {
                            tracker++;
                        }
                        break;

//                        Once both claws are opened by the driver, dropTimer resets and it adds one to tracker
                    case 5:
                        if (getArms().bothOpen()) {
                            dropTimer.reset();
                            tracker++;
                        }
                        break;

//                        Once the dropTimer is > 500 milliseconds, so the minerals fully drop from the claws, then the arms go back to the GRABGOLDGOLD position. Once
//                        the arms are in position the liftArmsTimer resets and it adds one to tracker
                    case 6:
                        if (dropTimer.milliseconds() > 500 && dropTimer.milliseconds() < 1000)
                            getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
                        else if (dropTimer.milliseconds() > 1000) {
                            if (getArms().isCurrentStateWantedState()) {
                                liftArmsTimer.reset();
                                tracker++;
                            }
                        }
                        break;

//                        Once liftArmsTimer is > 2000, to give the arms a chance to get into position, the lift goes to the READY position. Once the lift gets to the
//                        position it adds one to tracker
                    case 7:
                        if (liftArmsTimer.milliseconds() > 2000) {
                            getLift().setLiftState(LiftStates.READY);
                            if (getLift().isCurrentWantedState())
                                tracker++;
                        }
                        break;

                    case 8:
                        break;
                }
                break;

            case SILVERSILVER:
                switch (tracker) {
                    case 0:
                        getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
                        getArms().setLeftClawOpen(true);
                        getArms().setRightClawOpen(true);
                        if (getArms().isCurrentStateWantedState())
                            tracker++;
                        break;
                    case 1:
                        getLift().setLiftState(LiftStates.DOWN);
                        if (getLift().isCurrentWantedState()) {
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
                        getLift().setLiftState(LiftStates.SCOREINGHEIGHT);
                        if (getLift().isCurrentWantedState())
                            tracker++;
                        break;
                    case 4:
                        getArms().setArmsStates(Arms.ArmStates.SILVERSILVER);
                        if (getArms().isCurrentStateWantedState()) {
                            tracker++;
                        }
                        break;
                    case 5:
                        if (getArms().bothOpen()) {
                            dropTimer.reset();
                            tracker++;
                        }
                        break;
                    case 6:
                        if (dropTimer.milliseconds() > 500 && dropTimer.milliseconds() < 1000)
                            getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
                        else if (dropTimer.milliseconds() > 1000) {
                            if (getArms().isCurrentStateWantedState()) {
                                liftArmsTimer.reset();
                                tracker++;
                            }
                        }
                        break;
                    case 7:
                        if (liftArmsTimer.milliseconds() > 2000) {
                            getLift().setLiftState(LiftStates.READY);
                            if (getLift().isCurrentWantedState())
                                tracker++;
                        }
                        break;
                    case 8:
                        break;
                }
                break;

            case SILVERGOLD:
                switch (tracker) {
                    case 0:
                        getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
                        getArms().setRightClawOpen(true);
                        getArms().setLeftClawOpen(true);
                        if (getArms().isCurrentStateWantedState())
                            tracker++;
                        break;
                    case 1:
                        getLift().setLiftState(LiftStates.DOWN);
                        if (getLift().isCurrentWantedState()) {
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
                        getLift().setLiftState(LiftStates.SCOREINGHEIGHT);
                        if (getLift().isCurrentWantedState())
                            tracker++;
                        break;
                    case 4:
                        getArms().setArmsStates(Arms.ArmStates.SILVERGOLD);
                        if (getArms().isCurrentStateWantedState()) {
                            tracker++;
                        }
                        break;
                    case 5:
                        if (getArms().bothOpen()) {
                            dropTimer.reset();
                            tracker++;
                        }
                        break;
                    case 6:
                        if (dropTimer.milliseconds() > 500 && dropTimer.milliseconds() < 1000)
                            getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
                        else if (dropTimer.milliseconds() > 1000) {
                            if (getArms().isCurrentStateWantedState()) {
                                liftArmsTimer.reset();
                                tracker++;
                            }
                        }
                        break;
                    case 7:
                        if (liftArmsTimer.milliseconds() > 2000) {
                            getLift().setLiftState(LiftStates.READY);
                            if (getLift().isCurrentWantedState())
                                tracker++;
                        }
                        break;
                    case 8:
                        break;

                }

            case GOLDSILVER:
                switch (tracker) {
                    case 0:
                        getArms().setArmsStates(Arms.ArmStates.GRABGOLDSILVER);
                        getArms().setRightClawOpen(true);
                        getArms().setLeftClawOpen(true);
                        if (getArms().isCurrentStateWantedState())
                            tracker++;
                        break;
                    case 1:
                        getLift().setLiftState(LiftStates.DOWN);
                        if (getLift().isCurrentWantedState()) {
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
                        getLift().setLiftState(LiftStates.SCOREINGHEIGHT);
                        if (getLift().isCurrentWantedState())
                            tracker++;
                        break;
                    case 4:
                        getArms().setArmsStates(Arms.ArmStates.SILVERGOLD);
                        if (getArms().isCurrentStateWantedState()) {
                            tracker++;
                        }
                        break;
                    case 5:
                        if (getArms().bothOpen()) {
                            dropTimer.reset();
                            tracker++;
                        }
                        break;
                    case 6:
                        if (dropTimer.milliseconds() > 500 && dropTimer.milliseconds() < 1000)
                            getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
                        else if (dropTimer.milliseconds() > 1000) {
                            if (getArms().isCurrentStateWantedState()) {
                                liftArmsTimer.reset();
                                tracker++;
                            }
                        }
                        break;
                    case 7:
                        if (liftArmsTimer.milliseconds() > 2000) {
                            getLift().setLiftState(LiftStates.READY);
                            if (getLift().isCurrentWantedState())
                                tracker++;
                        }
                        break;
                    case 8:
                        break;

                }
                break;
        }
    }

    public enum MineralPositions {
        GOLDGOLD, SILVERSILVER, SILVERGOLD, GOLDSILVER
    }
}
