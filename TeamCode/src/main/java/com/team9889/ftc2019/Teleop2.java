package com.team9889.ftc2019;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.subsystems.Arms;
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
    public void runOpMode() throws InterruptedException {

        waitForStart(false);

        Robot.resetTracker();

        while (opModeIsActive()){

            boolean changeMode = gamepad2.left_stick_button && gamepad2.right_stick_button;
            if (changeMode && changeMode != lastChangeMode){
                autoMode = !autoMode;
            }

            lastChangeMode = changeMode;

            if (autoMode){

                if (gamepad2.y) {
                    Robot.setMineralPositions(com.team9889.ftc2019.subsystems.Robot.MineralPositions.GOLDGOLD);
                } else if(gamepad2.a) {
                    Robot.resetTracker();
                }

                if (gamepad2.left_bumper){
                    Robot.getArms().setRightClawOpen(true);
                }

                if (gamepad2.right_bumper){
                    Robot.getArms().setLeftClawOpen(true);
                }

                telemetry.addData("Are Arms In Position", Robot.getArms().isCurrentStateWantedState());

            } else {
                if (gamepad2.a){
                    wanted = Arms.ArmStates.SILVERSILVER;
                }
                else if (gamepad2.y){
                    wanted = Arms.ArmStates.GOLDGOLD;
                }
                else if (gamepad2.b){
                    wanted = Arms.ArmStates.SILVERGOLD;
                } else if (gamepad2.x){
                    wanted = Arms.ArmStates.GRABGOLDGOLD;
                }

                Robot.getArms().setArmsStates(wanted);
                telemetry.addData("Wanted", wanted.toString());
            }

            Robot.getArms().update(matchTime);
            Robot.getLift().update(matchTime);

            telemetry.addData("Mode", autoMode);
            Robot.outputToTelemetry(telemetry);
            telemetry.update();

        }

        finalAction();
    }
}
