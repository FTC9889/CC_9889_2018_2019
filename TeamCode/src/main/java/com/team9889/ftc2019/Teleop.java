package com.team9889.ftc2019;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.subsystems.Arms;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Lift;

/**
 * Created by joshua9889 on 3/28/2018.
 */

@TeleOp
public class Teleop extends Team9889Linear{

    DriverStation driverStation;

    private double leftClaw = 0;
    private double rightClaw = 0;
    private double leftArm = 1;
    private double rightArm = 1;
    private double leftBumperState = 0;
    private double rightBumperState = 0;
    private double dpadState = 0;
    private double ClawsClosed = 0;

    private boolean moveMineralsFromHopperToClaws = false;
    private ElapsedTime armTimer = new ElapsedTime();

    private boolean directLiftControl = false;


    private Lift.LiftStates wantedLiftState = Lift.LiftStates.READY;
    private Intake.RotatorStates wantedRotatorState = Intake.RotatorStates.UP;

    @Override
    public void runOpMode() {
        driverStation = new DriverStation(gamepad1, gamepad2);
        waitForStart(false);

        // Init States
        Robot.getIntake().setIntakeRotatorState(Intake.RotatorStates.UP);
        Robot.getLift().setLiftState(Lift.LiftStates.READY);
        Robot.getArms().setArmsStates(Arms.ArmStates.GRAB);

        Robot.getArms().setArmsStates(Arms.ArmStates.STORED);

        // Loop
        while (opModeIsActive()){
            // Drivetrain
            Robot.getDrive().setThrottleSteerPower(driverStation.getThrottle(),
                    driverStation.getSteer());

            // Logic for bumper
            if (driverStation.getReleaseLeftClaw() && leftClaw == 1 || leftBumperState == 1 && leftClaw == 1) {
                leftBumperState = 1;
                if (!driverStation.getReleaseLeftClaw()) {
                    Robot.getArms().setLeftClawOpen(false);
                    leftClaw = 0;
                    leftBumperState = 0;
                }
            } else if (driverStation.getReleaseLeftClaw() && leftClaw == 0 || leftBumperState == 1 && leftClaw == 0) {
                leftBumperState = 1;
                if (!driverStation.getReleaseLeftClaw()) {
                    Robot.getArms().setLeftClawOpen(true);
                    leftClaw = 1;
                    leftArm = 0;
                    leftBumperState = 0;
                }
            }

            if (driverStation.getReleaseRightClaw() && rightClaw == 1 || rightBumperState == 1 && rightClaw == 1) {
                rightBumperState = 1;
                if (!driverStation.getReleaseRightClaw()) {
                    Robot.getArms().setRightClawOpen(false);
                    rightClaw = 0;
                    rightBumperState = 0;
                }
            } else if (driverStation.getReleaseRightClaw() && rightClaw == 0 || rightBumperState == 1 && rightClaw == 0) {
                rightBumperState = 1;
                if (!driverStation.getReleaseRightClaw()) {
                    Robot.getArms().setRightClawOpen(true);
                    rightClaw = 1;
                    rightArm = 0;
                    rightBumperState = 0;
                }
            }

            // Power For Intake
            if (driverStation.getIntaking())
                Robot.getIntake().intake();
            else if (driverStation.getOuttake())
                Robot.getIntake().outtake();
            else if (driverStation.getStopIntaking())
                Robot.getIntake().stop();

            // Set intake extender power
            Robot.getIntake().setIntakeExtenderPower(driverStation.getIntakeExtenderPower());

            // Rotator State Controller
            Robot.getIntake().setIntakeRotatorState(driverStation.getIntakeRotatorState());

            // Logic for grabbing minerals from Hopper,
            // then moving the lift up to scoring position, but not the arms
            if (gamepad2.dpad_up){
                moveMineralsFromHopperToClaws = true;
                directLiftControl = false;
            }

//            if (moveMineralsFromHopperToClaws){
//
//            }
//
//            // Move lift to direct operator control
//            if(Math.abs(gamepad2.right_stick_y) > 0.01){
                Robot.getLift().setLiftPower(-gamepad2.right_stick_y);
//                directLiftControl = true;
//            } else if(!directLiftControl){
//                Robot.getLift().setLiftState(wantedLiftState);
//                directLiftControl = false;
//            }

            if (gamepad2.dpad_up || dpadState == 1){
                dpadState = 1;
                Robot.getLift().setLiftState(Lift.LiftStates.DOWN);
                if (Robot.getLift().getHeight() < 0.2){
                    Robot.getArms().setLeftClawOpen(false);
                    Robot.getArms().setRightClawOpen(false);
                    Robot.getLift().setLiftState(Lift.LiftStates.SCOREINGHEIGHT);
                }


            }

            // Return to grabbing position
            if (rightArm == 0 && leftArm == 0){
                Robot.getArms().setRightArm(1, 1);
                Robot.getArms().setLeftArm(0, 0.25);
                leftArm = 1;
                rightArm = 1;
            }

            if (gamepad1.b){ // Silver Silver
                Robot.getArms().setLeftArm(.35, .25);
                Robot.getArms().setLeftArm(.7, 0.25);
                Robot.getArms().setLeftArm(.7, 0.75);
                Robot.getArms().setRightArm(.25, 1);
                Robot.getArms().setRightArm(.25, 0.4);
            }
//            else if (gamepad1.b){ // Silver Gold
//                Robot.getArms().setRightArm(.25, .1);
//                Robot.getArms().setRightArm(.25, .2);
//                Robot.getArms().setLeftArm(.35, .25);
//                Robot.getArms().setLeftArm(.7, 0.75);
//            }

            Robot.getCamera().setCameraPosition(Camera.CameraPositions.UPRIGHT);

            updateTelemetry();
        }
    }
}
