package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.team9889.ftc2019.Constants;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by licorice17 on 9/14/2018.
 */

public class Intake extends Subsystem{
    private DcMotor intakemotor;
    private DcMotorEx extender;
    private Servo intakeRotator;
    private DigitalChannel limitSwitch;
    private boolean intakeTouchSensor = false;

    enum States {
        INTAKING, EXTENDING, ZEROING
    }

    private States currentState = States.ZEROING;

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        this.intakemotor = hardwareMap.get(DcMotor.class, Constants.kIntakeMotorID);
        this.extender = hardwareMap.get(DcMotorEx.class, Constants.kIntakeExtender);

        extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.intakeRotator = hardwareMap.get(Servo.class, Constants.kIntakeRotator);
        this.limitSwitch = hardwareMap.get(DigitalChannel.class, Constants.kIntakeSwitch);

        setIntakeRotatorPosition(.75);
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
        setIntakePower(-1);
    }

    public void outtake (){
        setIntakePower(1);
    }

    public void setIntakeExtenderPower(double power){
//        if (power < 0 && intakeSwitchValue() == true){
//            setIntakeExtenderPower(0);
//        }
//        else
        extender.setPower(power);
    }

    public boolean intakeSwitchValue (){
        if (limitSwitch.getState())
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
    public double getIntakeRotatorPosition(){
        return(intakeRotator.getPosition());
    }

}

