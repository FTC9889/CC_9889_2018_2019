package com.team9889.ftc2019;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by MannoMation on 12/14/2018.
 */
public class DriverStation {
    private Gamepad gamepad1;
    private Gamepad gamepad2;

    public DriverStation(Gamepad gamepad1, Gamepad gamepad2){
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
    }

    public double getThrottle(){
        return -gamepad1.left_stick_y;
    }

    public double getSteer(){
        return gamepad1.right_stick_x;
    }

    public boolean getReleaseLeftClaw(){
        return gamepad2.right_bumper || gamepad1.right_bumper;
    }

    public boolean getReleaseRightClaw(){
        return gamepad2.left_bumper || gamepad1.left_bumper;
    }

    public boolean getStartIntaking(){
        return gamepad2.a || gamepad1.x;
    }

    public boolean getStartScoringSquence() {
        return gamepad2.y;
    }

    public double getIntakeExtenderPower(){
        return -gamepad2.left_stick_y;
    }

    public boolean getLiftStick(){
        return gamepad2.right_stick_y > 0.05 || gamepad2.right_stick_y < 0.05;
    }

    public boolean getIntakeStick(){
        return gamepad2.left_stick_y > 0.05 || gamepad2.left_stick_y < 0.05;
    }
}
