package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Constants;
import com.team9889.lib.hardware.ModernRoboticsUltrasonic;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

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

    enum States {
        INTAKING, EXTENDING, ZEROING
    }

    public enum RotatorStates {
        UP, DOWN
    }

    private States currentState = States.ZEROING;

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        this.intakemotor = hardwareMap.get(DcMotor.class, Constants.kIntakeMotorID);
        this.extender = hardwareMap.get(DcMotorEx.class, Constants.kIntakeExtender);

        extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.intakeRotator = hardwareMap.get(Servo.class, Constants.kIntakeRotator);
        this.scoreingSwitch = hardwareMap.get(DigitalChannel.class, Constants.kIntakeSwitch);
        this.inSwitch = hardwareMap.get(DigitalChannel.class, Constants.kIntakeInSwitch);
//        this.craterdetector = new ModernRoboticsUltrasonic(Constants.kCraterDetector , hardwareMap);

        this.hoppergate = hardwareMap.get(Servo.class, Constants.kHopperGate);

        setIntakeRotatorState(RotatorStates.UP);
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("IntakePower",intakemotor.getPower());
        telemetry.addData("Intake Extender", extender.getCurrentPosition());
        telemetry.addData("Angle of Intake", intakeRotator.getPosition());
        telemetry.addData("Intake Switch", intakeSwitchValue());
    }

    @Override
    public void update(ElapsedTime time) {

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
//        if (power < 0 && intakeSwitchValue() == true){
//            setIntakeExtenderPower(0);
//        }
//        else
        extender.setPower(power);
    }

    public boolean intakeSwitchValue (){
        if (scoreingSwitch.getState())
            return intakeTouchSensor = false;

        else
            return intakeTouchSensor = true;
    }

    public void setIntakeExtenderPosition(double position){
        setIntakeExtenderPosition(position * Constants.kIntakeTicksToInchRatio);
    }

    public void setIntakeRotatorPosition(double position){
        intakeRotator.setPosition(position);
    }

    public void setWantedState(States wantedState){
        if(wantedState != currentState){
            switch (wantedState){
                case ZEROING:

            }
        }
    }

    public void update(){

    }

    public double getIntakeRotatorPosition(){
        return(intakeRotator.getPosition());
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

