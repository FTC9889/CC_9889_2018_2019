package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.team9889.ftc2019.Constants;
import com.team9889.lib.hardware.RevColorDistance;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by licorice17 on 9/14/2018.
 */

public class Intake extends Subsystem{
    private DcMotor intakemotor;
    private RevColorDistance front, back;

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        this.intakemotor = hardwareMap.get(DcMotor.class, Constants.kIntakeMotorID);
        this.front = new RevColorDistance(Constants.kfrontcolorsensor,hardwareMap);
        this.front = new RevColorDistance(Constants.kbackcolorsensor,hardwareMap);
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("IntakePower",intakemotor.getPower());
        telemetry.addData("front color sensor",front.getCm());
        telemetry.addData("back color sensor",back.getCm());
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


}

