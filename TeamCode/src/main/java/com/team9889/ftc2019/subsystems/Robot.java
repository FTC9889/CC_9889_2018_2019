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
    private Drive mDrive = new Drive();
    private ScoringLift mScoringLift = new ScoringLift();
    private HangingLift mHangingLift = new HangingLift();
    private Intake mIntake = new Intake();
    private Camera mCamera = new Camera();
    private Dumper mDumper = new Dumper();
    private ElapsedTime timer = new ElapsedTime();
    private ElapsedTime scoringTimer = new ElapsedTime();
    private boolean timerReset = true;
    private boolean firstIntaking = true;
    private boolean upperLimitWasPressed = false;

    private ElapsedTime liftUpTimer = new ElapsedTime();
    private boolean liftUpFirst = true;

    private boolean auto;

    public boolean transitionDone = false;

    private int scoringCounter = 1;
    private boolean scoringCounterFirst = true;

    public boolean stopIntake = false;

    public boolean autoSampled = true;

    private List<Subsystem> subsystems = Arrays.asList(
            mDrive, mScoringLift, mHangingLift, mIntake, mCamera, mDumper// Add more subsystems here as needed
    );

    public static Robot getInstance() {
        if (mInstance == null)
            mInstance = new Robot();

        return mInstance;
    }

    public enum scorerStates{
        COLLECTING, STORED, SCORING, DUMP, NULL, AUTONOMOUS
    }

    public enum intakeStates{
        COLLECTING, HARDSTOP, TRANSITION, NULL
    }

    public scorerStates wantedScorerState = scorerStates.NULL;
    public intakeStates wantedIntakeState = intakeStates.NULL;


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

        this.auto = autonomous;
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

        telemetry.addData("Wanted Robot State", wantedScorerState);
    }

    @Override
    public void update(ElapsedTime time) {
        switch (wantedScorerState) {
            case SCORING:
                getLift().setLiftState(LiftStates.UP);

                if (getLift().getHeight() < -600) {
                    getDumper().wantedDumperState = Dumper.dumperStates.SCORING;
                }
                timerReset = true;
                liftUpTimer.reset();
                break;

            case COLLECTING:
                    getDumper().wantedDumperState = Dumper.dumperStates.COLLECTING;
                    if (getDumper().dumperTimer.milliseconds() > 300)
                        getLift().setLiftState(LiftStates.DOWN);
                break;

            case DUMP:
                getDumper().wantedDumperState = Dumper.dumperStates.DUMP;
                getLift().setLiftState(LiftStates.UP);
                liftUpTimer.reset();
                timerReset = true;
                break;

            case AUTONOMOUS:
                getLift().setLiftState(LiftStates.READY);
                if (getLift().isCurrentWantedState())
                    getDumper().setDumperStates(Dumper.dumperStates.SCORING);
                liftUpTimer.reset();
                timerReset = true;
                break;

            case NULL:
                getDumper().wantedDumperState = Dumper.dumperStates.NULL;
                liftUpTimer.reset();
                timerReset = true;
                break;
        }

        for (Subsystem subsystem : subsystems) {
            subsystem.update(timer);
        }
    }

    @Override
    public void test(Telemetry telemetry) {
        for (Subsystem subsystem : subsystems) {
            RobotLog.a("=========== Testing " + subsystem.toString() + " ===========");
            subsystem.test(telemetry);
            RobotLog.a("=========== Finished Testing " + subsystem.toString() + " ===========");
        }
    }

    public void setScorerStates(scorerStates states){
        this.wantedScorerState = states;
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

    public ScoringLift getLift() {
        return mScoringLift;
    }

    public HangingLift getHangingLift() {
        return mHangingLift;
    }

    public Camera getCamera() {
        return mCamera;
    }

    public Dumper getDumper(){
        return mDumper;
    }
}
