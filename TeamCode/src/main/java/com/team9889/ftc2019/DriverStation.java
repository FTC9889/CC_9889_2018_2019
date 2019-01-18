package com.team9889.ftc2019;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.team9889.ftc2019.states.RotatorStates;

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
        return gamepad1.right_bumper;
    }

    public boolean getReleaseRightClaw(){
        return gamepad1.left_bumper;
    }

    public boolean getIntaking(){
        return gamepad2.a;
    }

    public boolean getOuttake(){
       return gamepad2.y;
    }

    public boolean getStopIntaking(){
        return gamepad2.b;
    }

    public double getIntakeExtenderPower(){
        return -gamepad2.left_stick_y;
    }

    public boolean getIntakeExtendToCrater() {
        return gamepad2.x;
    }

    private RotatorStates wantedState = RotatorStates.UP;
    public RotatorStates getIntakeRotatorState(){
        if(gamepad2.right_bumper) {
            wantedState = RotatorStates.UP;
        } else if(gamepad2.left_bumper) {
            wantedState = RotatorStates.DOWN;
        }
        return wantedState;
    }


}
