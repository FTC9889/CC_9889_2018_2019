package com.team9889.ftc2019.test.subsystems;

import android.app.backup.RestoreObserver;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.Team9889Linear;
import com.team9889.ftc2019.subsystems.Arms;

/**
 * Created by MannoMation on 12/29/2018.
 */

@Disabled
@TeleOp
public class TestScoreing extends Team9889Linear {
    @Override
    public void runOpMode() throws InterruptedException {

    }
/*
    public int Minerals = 1;
    public boolean Enum;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart(false);

        while (opModeIsActive()){
            if (gamepad1.b)
                Minerals = 1;
            else if (gamepad1.y)
                Minerals = 2;
            else if (gamepad1.x)
                Minerals = 3;


            if (gamepad1.a) {
                //Camera Scans
                switch (Minerals) {
//                  GOLDGOLD
                    case 1:
                        Robot.getArms().setMineralPositions(Arms.MineralPositions.GOLDGOLD);
                        Enum = true;
                        break;

//                  SILVERSILVER
                    case 2:
                        Robot.getArms().setMineralPositions(Arms.MineralPositions.SILVERSILVER);
                        Enum = true;
                        break;

//                  SILVERGOLD
                    case 3:
                        Robot.getArms().setMineralPositions(Arms.MineralPositions.SILVERGOLD);
                        Enum = true;
                        break;
                }
            }

            if (Enum = true){
                switch (Minerals) {
//                  GOLDGOLD
                    case 1:
                        Robot.getArms().setMineralPositions(Arms.MineralPositions.GOLDGOLD);
                        Enum = true;
                        break;

//                  SILVERSILVER
                    case 2:
                        Robot.getArms().setMineralPositions(Arms.MineralPositions.SILVERSILVER);
                        Enum = true;
                        break;

//                  SILVERGOLD
                    case 3:
                        Robot.getArms().setMineralPositions(Arms.MineralPositions.SILVERGOLD);
                        Enum = true;
                        break;
                }
            }
        }
    }
    */
}