package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.team9889.ftc2019.Constants;
import com.team9889.lib.control.motion.MotionProfile;
import com.team9889.lib.control.motion.ProfileParameters;
import com.team9889.lib.control.motion.TrapezoidalMotionProfile;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by MannoMation on 9/14/2018.
 */

public class Arms extends Subsystem{
    private Servo leftShoulder, rightShoulder;
    private Servo leftElbow, rightElbow;
    private Servo leftClaw, rightClaw;

    private ProfileParameters profileParameters = new ProfileParameters(0.01, 0.4);
    private TrapezoidalMotionProfile profile = new TrapezoidalMotionProfile();

    private ArmStates currentLeftArmState = ArmStates.NULL;
    private ArmStates currentRightArmState = ArmStates.NULL;

    private ArmStates wantedLeftArmState = ArmStates.STORED;
    private ArmStates wantedRightArmState = ArmStates.STORED;

    private double startTime;
    private boolean first = false;

    public enum ArmStates{
        NULL, STORED,
        PARK,
        GRABGOLDGOLD, GRABSILVERSILVER, GRABSILVERGOLD, GRABGOLDSILVER,
        GOLDGOLD, SILVERSILVER, SILVERGOLD
    }

    public enum MineralPositions {
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
            setArmsStates(ArmStates.STORED, ArmStates.STORED);
            setLeftClawOpen(false);
            setRightClawOpen(false);
        }
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
        if(currentRightArmState != wantedRightArmState && currentLeftArmState != wantedLeftArmState){

            if(currentLeftArmState == ArmStates.NULL && currentRightArmState == ArmStates.NULL){ // INIT
                setLeftArm(0, 0); // STORED POSITION VALUES
                setRightArm(0, 0); // STORED POSITION VALUES

                currentLeftArmState = wantedLeftArmState;
                currentRightArmState = wantedRightArmState;

            } else {
                if (wantedRightArmState == ArmStates.GOLDGOLD && wantedLeftArmState == ArmStates.GOLDGOLD){
                    setLeftArm(0, 0);
                    setRightArm(0,0);

                    if(first) {
                        startTime = time.milliseconds();
                        first = false;

                    } else {
                        if(time.milliseconds() - startTime > 400) {
                            currentRightArmState = wantedRightArmState;
                            currentLeftArmState = wantedLeftArmState;
                            first = true;

                        }

                    }
                } else if (wantedRightArmState == ArmStates.SILVERSILVER && wantedLeftArmState == ArmStates.SILVERSILVER){
                    setLeftArm(0, 0);
                    setRightArm(0,0);

                    if(first) {
                        startTime = time.milliseconds();
                        first = false;

                    } else {
                        if(time.milliseconds() - startTime > 400) {
                            currentRightArmState = wantedRightArmState;
                            currentLeftArmState = wantedLeftArmState;
                            first = true;

                        }

                    }
                } else if (wantedRightArmState == ArmStates.SILVERGOLD && wantedLeftArmState == ArmStates.SILVERGOLD){
                    setLeftArm(0, 0);
                    setRightArm(0,0);

                    if(first) {
                        startTime = time.milliseconds();
                        first = false;

                    } else {
                        if(time.milliseconds() - startTime > 400) {
                            currentRightArmState = wantedRightArmState;
                            currentLeftArmState = wantedLeftArmState;
                            first = true;

                        }

                    }
                } else if (wantedRightArmState == ArmStates.GRABGOLDGOLD && wantedLeftArmState == ArmStates.GRABGOLDGOLD){

                    if(currentRightArmState == ArmStates.GRABGOLDSILVER && currentLeftArmState == ArmStates.GRABGOLDSILVER) {

                    } else if ((currentRightArmState == ArmStates.GOLDGOLD && currentLeftArmState == ArmStates.GOLDGOLD)
                            || (currentRightArmState == ArmStates.SILVERSILVER && currentLeftArmState == ArmStates.SILVERSILVER)
                            || (currentRightArmState == ArmStates.SILVERGOLD && currentLeftArmState == ArmStates.SILVERGOLD)){
                        if(first) {
                            startTime = time.milliseconds();
                            first = false;
                            setLeftArm(0, 0);

                        } else {
                            if(time.milliseconds() - startTime > 200) {
                                currentLeftArmState = wantedLeftArmState;
                                setRightArm(0,0);

                            } else if(time.milliseconds() - startTime > 600){
                                currentRightArmState = wantedRightArmState;
                                first = true;
                            }

                        }

                    }

                }

            }

        } else {
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
            leftClaw.setPosition(.6);
        else
            leftClaw.setPosition(.9);
    }

    public void setRightClawOpen(boolean open){
        if (open)
            rightClaw.setPosition(.6);
        else
            rightClaw.setPosition(.9);
    }

    public void setArmsStates(ArmStates leftState, ArmStates rightState){
        wantedLeftArmState = leftState;
        wantedRightArmState = rightState;

        if(wantedLeftArmState == ArmStates.GRABSILVERSILVER || wantedLeftArmState == ArmStates.GRABSILVERGOLD)
            wantedLeftArmState = ArmStates.GRABGOLDGOLD;

        if(wantedRightArmState == ArmStates.GRABSILVERSILVER || wantedRightArmState == ArmStates.GRABSILVERGOLD)
            wantedRightArmState = ArmStates.GRABGOLDGOLD;
    }
}
