package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwareK9bot;
@TeleOp
public class TestDriveTrainMotors extends LinearOpMode {
    DcMotor leftDrive,rightDrive;

    @Override
    public void runOpMode() throws InterruptedException {
        leftDrive = hardwareMap.get(DcMotor.class, "left");
        rightDrive = hardwareMap.get(DcMotor.class, "right");

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();


        while (opModeIsActive() && Math.abs(leftDrive.getCurrentPosition()) < Rotations2Ticks(3)){
            leftDrive.setPower(.15);
            telemetry.addData("Current Position", leftDrive.getCurrentPosition());
            telemetry.update();
        }

        while (opModeIsActive() && Math.abs(leftDrive.getCurrentPosition()) > Rotations2Ticks(0)){
            leftDrive.setPower(-.15);
            telemetry.addData("Current Position", leftDrive.getCurrentPosition());
            telemetry.update();
        }

//        while (opModeIsActive() && rightDrive.getCurrentPosition() < Rotations2Ticks(3)){
//            rightDrive.setPower(.15);
//        }
//
//        while (opModeIsActive() && rightDrive.getCurrentPosition() > Rotations2Ticks(0)){
//            rightDrive.setPower(-.15);
//        }
    }

    private int Rotations2Ticks(double rotaions){
        return (int)(rotaions * 537.6);
    }
}
