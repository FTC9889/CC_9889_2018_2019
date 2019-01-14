package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.team9889.ftc2019.Constants;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by MannoMation on 9/14/2018.
 */

public class Arms extends Subsystem{
    private Servo leftShoulder, rightShoulder;
    private Servo leftElbow, rightElbow;
    private Servo leftClaw, rightClaw;

    private ArmStates currentArmsState = ArmStates.NULL;
    private ArmStates wantedArmsState = ArmStates.STORED;

    private double startTime;
    private boolean first = true;

    public enum ArmStates{
        NULL, STORED,
        PARK,
        GRABGOLDSILVER, GRABGOLDGOLD,
        GRABSILVERSILVER, GRABSILVERGOLD,
        GOLDGOLD, SILVERSILVER, SILVERGOLD
    }

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        this.leftShoulder = hardwareMap.get(Servo.class, Constants.ArmConstants.kLeftShoulderID);
        this.rightShoulder = hardwareMap.get(Servo.class, Constants.ArmConstants.kRightShoulderId);
        this.leftElbow = hardwareMap.get(Servo.class, Constants.ArmConstants.kLeftElbowId);
        this.rightElbow = hardwareMap.get(Servo.class, Constants.ArmConstants.kRightElbowId);
        this.leftClaw = hardwareMap.get(Servo.class, Constants.ArmConstants.kLeftClawId);
        this.rightClaw = hardwareMap.get(Servo.class, Constants.ArmConstants.kRightClawId);

        if (auto) {
            currentArmsState = ArmStates.NULL;
            currentArmsState = ArmStates.NULL;

            setArmsStates(ArmStates.STORED);
            update(new ElapsedTime());
            setLeftClawOpen(false);
            setRightClawOpen(false);
        }

        first = true;
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Left Arm Shoulder", leftShoulder.getPosition());
        telemetry.addData("Left Arm Elbow", leftElbow.getPosition());
        telemetry.addData("Left Arm Claw", leftClaw.getPosition());
        telemetry.addLine();
        telemetry.addData("Right Arm Shoulder", rightShoulder.getPosition());
        telemetry.addData("Right Arm Elbow", rightElbow.getPosition());
        telemetry.addData("Right Arm Claw", rightClaw.getPosition());
    }

    @Override
    public void update(ElapsedTime time) {
        if(currentArmsState != wantedArmsState){
            switch (wantedArmsState) {
                    case STORED:
                        setLeftArm(0, .131);
                        setRightArm(.998, .844);
                        first = true;

                        currentArmsState = ArmStates.STORED;
                        break;
                    case PARK:
                        setLeftArm(0, .131);
                        setRightArm(0.269, 0.42);
                        first = true;

                        currentArmsState = ArmStates.PARK;
                        break;
                    case GOLDGOLD:
                        setLeftArm(0.589, 0.813);
                        setRightArm(0.02, 0.541);

                        if (first) {
                            startTime = time.milliseconds();
                            first = false;
                        } else {
                            if (time.milliseconds() - startTime > 100) {
                                currentArmsState = ArmStates.GOLDGOLD;
                                first = true;
                            }
                        }
                        break;
                    case SILVERSILVER:
                        setLeftArm(0.733, 0.63);
                        setRightArm(0.269, 0.42);

                        if (first) {
                            startTime = time.milliseconds();
                            first = false;
                        } else {
                            if (time.milliseconds() - startTime > 100) {
                                currentArmsState = ArmStates.SILVERSILVER;
                                first = true;
                            }
                        }
                        break;
                    case SILVERGOLD:
                        setLeftArm(0.733, 0.542);
                        setRightArm(0.259, 0.183);

                        if (first) {
                            startTime = time.milliseconds();
                            first = false;

                        } else {
                            if (time.milliseconds() - startTime > 100) {
                                currentArmsState = ArmStates.SILVERGOLD;
                                first = true;
                            }
                        }
                        break;
                    case GRABGOLDGOLD:
                        if (currentArmsState == ArmStates.GRABGOLDSILVER) {
                            if (first) {
                                startTime = time.milliseconds();
                                first = false;
                                setLeftArm(0.218, 0.039);
                            } else {
                                if (time.milliseconds() - startTime > 70
                                        && time.milliseconds() - startTime < 100) {
                                    setRightArm(.943, .88);
                                } else if (time.milliseconds() - startTime > 100
                                        && time.milliseconds() - startTime > 150) {
                                    setLeftArm(.097, .125);
                                } else if (time.milliseconds() - startTime > 150) {
                                    currentArmsState = ArmStates.GRABGOLDGOLD;
                                    first = true;
                                }
                            }
                        } else {
                            if (first) {
                                startTime = time.milliseconds();
                                first = false;
                                setLeftArm(.097, .125);

                            } else {
                                if (time.milliseconds() - startTime > 200 &&
                                        time.milliseconds() - startTime < 400) {
                                    setRightArm(.943, .88);
                                } else if (time.milliseconds() - startTime > 400) {
                                    currentArmsState = wantedArmsState;
                                    first = true;
                                }
                            }
                        }
                        break;

                    case GRABGOLDSILVER:
                        if (currentArmsState == ArmStates.GRABGOLDGOLD) {
                            if (first) {
                                startTime = time.milliseconds();
                                first = false;
                                setRightArm(0.847, 0.962);
                            } else {
                                if (time.milliseconds() - startTime > 70
                                        && time.milliseconds() - startTime < 100) {
                                    setLeftArm(0.096, 0.185);
                                } else if (time.milliseconds() - startTime > 100
                                        && time.milliseconds() - startTime > 150) {
                                    setRightArm(0.949, 0.943);
                                } else if (time.milliseconds() - startTime > 150) {
                                    currentArmsState = ArmStates.GRABGOLDGOLD;
                                    first = true;
                                }
                            }
                        } else {
                            if (first) {
                                startTime = time.milliseconds();
                                first = false;
                                setLeftArm(0.096, 0.185);

                            } else {
                                if (time.milliseconds() - startTime > 70) {
                                    setRightArm(0.949, 0.943);
                                } else if (time.milliseconds() - startTime > 170) {
                                    currentArmsState = ArmStates.GRABGOLDGOLD;
                                    first = true;
                                }

                            }
                        }
                        break;
                } // End of Switch
        } else { // Current == Wanted
            RobotLog.a("Arms all Ready in Position");
        }
    }

    @Override
    public void test(Telemetry telemetry) {

    }

    @Override
    public void stop() {

    }

    public double getLeftClawPosition(){
        return leftClaw.getPosition();
    }

    public double getRightClawPosition(){
        return rightClaw.getPosition();
    }

    public void setLeftArm(double Shoulder , double Elbow){
        leftShoulder.setPosition(Shoulder);
        leftElbow.setPosition(Elbow);
    }

    public void setRightArm(double Shoulder , double Elbow){
        rightShoulder.setPosition(Shoulder);
        rightElbow.setPosition(Elbow);
    }

    public  void setLeftClawOpen(boolean open){
        if(open)
            leftClaw.setPosition(.215);
        else
            leftClaw.setPosition(.958);
    }

    public void setRightClawOpen(boolean open){
        if (open)
            rightClaw.setPosition(.116);
        else
            rightClaw.setPosition(.833);
    }

    public boolean bothOpen() {
        return getLeftClawPosition() == .215 && getRightClawPosition() == .116;
    }

    public boolean isCurrentStateWantedState(){
        return(currentArmsState == wantedArmsState);
    }

    public void setArmsStates(ArmStates state){
        wantedArmsState = state;

        if(wantedArmsState == ArmStates.GRABSILVERSILVER || wantedArmsState == ArmStates.GRABSILVERGOLD)
            wantedArmsState = ArmStates.GOLDGOLD;
    }

    @Override
    public String toString() {
        return "Arms";
    }
}
