package com.team9889.ftc2019;

import android.app.backup.RestoreObserver;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.subsystems.Arms;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 1/14/2019.
 */

@TeleOp
public class Teleop2 extends Team9889Linear{
    public boolean autoMode = false;
    private boolean lastChangeMode;
    Arms.ArmStates wanted = Arms.ArmStates.NULL;

    @Override
    public void runOpMode() {

        waitForStart(false);

        Robot.resetTracker();
        Robot.whichMineral = com.team9889.ftc2019.subsystems.Robot.MineralPositions.NULL;

        while (opModeIsActive()){

            if (Robot.intakeCruiseControl){
                Robot.getIntake().setIntakeExtenderPower(gamepad2.left_stick_y);
            }
            if (gamepad2.x){
                Robot.getIntake().setWantedIntakeState(Intake.States.ZEROING);
            }


// auto/manuel mode changer
            boolean changeMode = gamepad2.left_stick_button && gamepad2.right_stick_button;
            if (changeMode && changeMode != lastChangeMode){
                autoMode = !autoMode;
            }

            lastChangeMode = changeMode;


// auto/manuel lift arm logic
            if (autoMode){
                setBackground(Color.GREEN);

                if (gamepad2.y) {
                    Robot.setWantedSuperStructure(com.team9889.ftc2019.subsystems.Robot.MineralPositions.GOLDGOLD);
                } else if (gamepad2.a) {
                    Robot.setWantedSuperStructure(com.team9889.ftc2019.subsystems.Robot.MineralPositions.SILVERSILVER);
                }else if (gamepad2.b){
                    Robot.setWantedSuperStructure(com.team9889.ftc2019.subsystems.Robot.MineralPositions.SILVERGOLD);
                }else if (gamepad2.x){
                    Robot.setWantedSuperStructure(com.team9889.ftc2019.subsystems.Robot.MineralPositions.GOLDSILVER);
                }else if(gamepad2.dpad_up && Robot.isReady) {
                    Robot.resetTracker();
                }

                if (gamepad2.left_bumper && Robot.armsReadyForClaws){
                    Robot.getArms().setRightClawOpen(true);
                }

                if (gamepad2.right_bumper && Robot.armsReadyForClaws){
                    Robot.getArms().setLeftClawOpen(true);
                }

                Robot.update(matchTime);

                telemetry.addData("Are Arms In Position", Robot.getArms().isCurrentStateWantedState());

            } else {
                setBackground(Color.RED);

                if (gamepad2.a){
                    wanted = Arms.ArmStates.SILVERSILVER;
                }
                else if (gamepad2.y){
                    wanted = Arms.ArmStates.GOLDGOLD;
                }
                else if (gamepad2.b){
                    wanted = Arms.ArmStates.SILVERGOLD;
                }
//                else if (gamepad2.x){
//                    wanted = Arms.ArmStates.GRABGOLDGOLD;
//                }

                Robot.getArms().setArmsStates(wanted);
                telemetry.addData("Wanted", wanted.toString());
                Robot.getArms().update(matchTime);
                Robot.getLift().update(matchTime);
            }



            Robot.getIntake().update(matchTime);

//telemetry
            telemetry.addData("Mode", autoMode);
            Robot.outputToTelemetry(telemetry);
            telemetry.update();

        }

        finalAction();
    }
}
