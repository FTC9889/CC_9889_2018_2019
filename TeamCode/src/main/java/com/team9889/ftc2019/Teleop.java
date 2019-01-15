package com.team9889.ftc2019;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
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
    private boolean leftClawState = false;
    private boolean rightClawState = false;

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

        Robot.getArms().setLeftClawOpen(true);
        Robot.getArms().setRightClawOpen(true);

        // Loop
        while (opModeIsActive()){
            // Drivetrain
            Robot.getDrive().setThrottleSteerPower(driverStation.getThrottle(),
                    driverStation.getSteer());

            // Logic for bumper
            if (driverStation.getReleaseLeftClaw() && leftBumperState == 1) {
                    Robot.getArms().setLeftClawOpen(leftClawState);
                    leftBumperState = 0;
                    leftClawState = !leftClawState;
            } else if (!driverStation.getReleaseLeftClaw()){
                leftBumperState = 1;
            }

            if (driverStation.getReleaseRightClaw() && rightBumperState == 1) {
                Robot.getArms().setRightClawOpen(rightClawState);
                rightBumperState = 0;
                rightClawState = !rightClawState;
            } else if (!driverStation.getReleaseRightClaw()){
                rightBumperState = 1;
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

            Robot.getLift().setLiftPower(-gamepad2.right_stick_y);
/*
            if (gamepad1.b){ // Silver Silver
                Robot.getArms().setArmsStates(Arms.ArmStates.SILVERSILVER);
            }

            else if (gamepad2.dpad_down) {
                Robot.getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
            }
*/
            if(leftClawState && rightClawState)
                Robot.getCamera().setCameraPosition(Camera.CameraPositions.UPRIGHT);
            else
                Robot.getCamera().setCameraPosition(Camera.CameraPositions.TWO_GOLD);

            if (gamepad2.left_stick_button)
                Robot.getIntake().setHopperGateDown();

            else if (gamepad2.right_stick_button)
                Robot.getIntake().setHopperGateUp();

            updateTelemetry();
        }
    }
}
