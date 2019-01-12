package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Constants;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by licorice17 on 9/14/2018.
 */

public class Intake extends Subsystem{
    private DcMotor intakemotor;
    private DcMotorEx extender;
    private Servo intakeRotator;
    private Servo hoppergate;
    private DigitalChannel scoreingSwitch;
    private DigitalChannel inSwitch;
//    private ModernRoboticsUltrasonic craterdetector;
    private boolean intakeTouchSensor = false;

    public enum States {
        INTAKING, EXTENDING, GRABBING, ZEROING
    }

    public enum RotatorStates {
        UP, DOWN
    }

    private States currentExtenderState = States.ZEROING;
    private States wantedExtenderState = States.ZEROING;
    private RotatorStates currentRotatorState = RotatorStates.UP;
    private RotatorStates wantedRotatorState = RotatorStates.UP;

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        this.intakemotor = hardwareMap.get(DcMotor.class, Constants.IntakeConstants.kIntakeMotorID);
        this.extender = hardwareMap.get(DcMotorEx.class, Constants.IntakeConstants.kIntakeExtender);

        extender.setDirection(DcMotorSimple.Direction.REVERSE);
        extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.intakeRotator = hardwareMap.get(Servo.class, Constants.IntakeConstants.kIntakeRotator);
        this.scoreingSwitch = hardwareMap.get(DigitalChannel.class, Constants.IntakeConstants.kIntakeSwitch);
        this.inSwitch = hardwareMap.get(DigitalChannel.class, Constants.IntakeConstants.kIntakeInSwitch);
//        this.craterdetector = new ModernRoboticsUltrasonic(Constants.kCraterDetector , hardwareMap);

        this.hoppergate = hardwareMap.get(Servo.class, Constants.IntakeConstants.kHopperGate);

        if (auto) {
            extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            setIntakeRotatorState(RotatorStates.UP);
            setWantedIntakeState(States.ZEROING);
            currentExtenderState = States.ZEROING;
            currentRotatorState = RotatorStates.UP;
            setHoppergateup();
        } else {
            extender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    @Override
    public void zeroSensors() {
        extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Thread.yield();
        extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("IntakePower",intakemotor.getPower());
        telemetry.addData("Intake Extender", extender.getCurrentPosition());
        telemetry.addData("Angle of Intake", intakeRotator.getPosition());
        telemetry.addData("Fully In Intake Switch", intakeInSwitchValue());
        telemetry.addData("Grabbing Intake Switch", intakeGrabbingSwitchValue());
    }

    @Override
    public void update(ElapsedTime time) {
        if (currentExtenderState != wantedExtenderState){
            switch (wantedExtenderState) {
                case ZEROING:
                    if (intakeInSwitchValue()) {
                        setIntakeExtenderPower(0);
                        zeroSensors();
                        currentExtenderState = States.ZEROING;
                    } else {
                        extender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        setIntakeExtenderPower(-1);
                        setIntakeRotatorState(wantedRotatorState);
                        currentRotatorState = wantedRotatorState;
                    }
                    break;
                case GRABBING:
                    if (currentExtenderState == States.ZEROING) {
                        if (intakeGrabbingSwitchValue()){
                            setIntakeExtenderPower(0);
                            currentExtenderState = States.GRABBING;

                            setIntakeRotatorPosition(0.8);
                            currentRotatorState = RotatorStates.DOWN;
                        }
                        else {
                            extender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                            setIntakeExtenderPower(.5);
                        }
                    }
                    else {
                        if (intakeGrabbingSwitchValue()){
                            setIntakeExtenderPower(0);
                            currentExtenderState = States.GRABBING;
                        }
                        else {
                            extender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                            setIntakeExtenderPower(-0.3);

                            setIntakeRotatorState(RotatorStates.UP);
                            currentRotatorState = RotatorStates.UP;
                        }
                    }
                    break;
                case INTAKING:
                    break;
                case EXTENDING:
                    break;
            }
        }
    }

    @Override
    public void test(Telemetry telemetry) {

    }

    @Override
    public void stop() {
        intakemotor.setPower(0);
    }

    public void setIntakePower(double power){
        intakemotor.setPower(power);
    }

    public void intake (){
        setIntakePower(1);
    }

    public void outtake (){
        setIntakePower(-1);
    }

    public void setIntakeExtenderPower(double power){
//        if (power < 0 && intakeInSwitchValue() == true){
//            setIntakeExtenderPower(0);
//        }
//        else
        extender.setPower(power);
    }

    public boolean intakeInSwitchValue(){
        return !inSwitch.getState();
    }

    public boolean intakeGrabbingSwitchValue(){
        return !scoreingSwitch.getState();
    }

    //TODO fix this method
    public void setIntakeExtenderPosition(double position){
        setIntakeExtenderPosition(position * Constants.IntakeConstants.kIntakeTicksToInchRatio);
    }

    public void setIntakeRotatorPosition(double position){
        intakeRotator.setPosition(position);
    }

    public void setIntakeRotatorState(RotatorStates state){
        switch (state){
            case UP:
                setIntakeRotatorPosition(0.4);
                break;

            case DOWN:
                setIntakeRotatorPosition(1);
                break;
        }
    }

    public void setWantedIntakeState(States wantedState, RotatorStates wantedRoatatorState){
        this.wantedExtenderState = wantedState;

        if (wantedExtenderState != States.INTAKING && wantedExtenderState != States.GRABBING){
            this.wantedRotatorState = RotatorStates.UP;
        } else{
            this.wantedRotatorState = wantedRoatatorState;
        }
    }

    public void setWantedIntakeState(States wantedState){
        this.wantedExtenderState = wantedState;

        if (wantedExtenderState != States.INTAKING && wantedExtenderState != States.GRABBING){
            this.wantedRotatorState = RotatorStates.UP;
        } else{
            this.wantedRotatorState = RotatorStates.DOWN;
        }
    }

    public boolean isCurrentStateWantedState(){
        return(currentExtenderState == wantedExtenderState);
    }

    public void setHoppergatedown(){
        hoppergate.setPosition(1);
    }

    public void setHoppergateup(){
        hoppergate.setPosition(0.5);
    }

//    public double getCraterDistance(){
//        return craterdetector.getDistance(DistanceUnit.INCH);
//    }

    public void setIntakeOut(){
//        setIntakeExtenderPosition(getCraterDistance() + 3);
    }
}

