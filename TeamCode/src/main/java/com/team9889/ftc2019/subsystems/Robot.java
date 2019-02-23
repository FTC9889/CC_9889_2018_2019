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
    private Lift mLift = new Lift();
    private Intake mIntake = new Intake();
    private Camera mCamera = new Camera();
    private Dumper mDumper = new Dumper();
    private ElapsedTime timer = new ElapsedTime();
    private ElapsedTime scoringTimer = new ElapsedTime();
    private boolean timerReset = true;
    private boolean firstIntaking = true;
    private boolean upperLimitWasPressed = false;
    private List<Subsystem> subsystems = Arrays.asList(
            mDrive, mLift, mIntake, mCamera, mDumper// Add more subsystems here as needed
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
        for (Subsystem subsystem : subsystems) {
            subsystem.update(timer);
        }

        switch (wantedScorerState) {
            case STORED:
                getDumper().wantedDumperState = Dumper.dumperStates.STORED;
                getLift().setLiftState(LiftStates.DOWN);
                timerReset = true;
            break;

            case SCORING:
                getLift().setLiftState(LiftStates.SCOREINGHEIGHT);

                if (getLift().getHeight() > 10) {
                    getDumper().wantedDumperState = Dumper.dumperStates.SCORING;
                }
                timerReset = true;
                break;

            case COLLECTING:
                getDumper().wantedDumperState = Dumper.dumperStates.COLLECTING;
                if (getDumper().dumperTimer.milliseconds() > 1000)
                    getLift().setLiftState(LiftStates.READY);
                break;

            case DUMP:
                getDumper().wantedDumperState = Dumper.dumperStates.DUMP;
                getLift().setLiftState(LiftStates.SCOREINGHEIGHT);
                timerReset = true;
                break;

            case AUTONOMOUS:

                break;

            case NULL:
                getDumper().wantedDumperState = Dumper.dumperStates.NULL;
                timerReset = true;
                break;
        }

        switch (wantedIntakeState){
            case COLLECTING:
                getIntake().setWantedIntakeState(Intake.IntakeStates.INTAKING);
                break;

            case HARDSTOP:
                break;

            case TRANSITION:
                break;

            case NULL:
                break;
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
    public void setIntakeStates(intakeStates states){
        this.wantedIntakeState = states;
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

    public Dumper getDumper(){
        return mDumper;
    }
}
