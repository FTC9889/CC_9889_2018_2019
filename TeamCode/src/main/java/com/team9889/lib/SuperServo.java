package com.team9889.lib;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Class to wrap both servo types into one
 */

public class SuperServo {
    public enum ServoType{
        SERVO, CR_SERVO
    }

    private CRServo mCr;
    private Servo mNormal;

    private ServoType mServoType;

    public SuperServo(HardwareMap hardwareMap, String id, ServoType servoType){
        mServoType = servoType;
        init(hardwareMap, id);
    }

    private void init(HardwareMap hardwareMap, String id){
        switch (mServoType){
            case SERVO:
                mNormal = hardwareMap.get(Servo.class, id);
                break;
            case CR_SERVO:
                mCr = hardwareMap.get(CRServo.class, id);
        }
    }

    public void setDirection(Servo.Direction direction){
        switch (mServoType){
            case SERVO:
                mNormal.setDirection(direction);
                break;
            case CR_SERVO:
                if (direction == Servo.Direction.FORWARD)
                    mCr.setDirection(DcMotorSimple.Direction.FORWARD);
                else if(direction == Servo.Direction.REVERSE)
                    mCr.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
        }
    }

    public void setPosition(double position){
        if(mServoType == ServoType.SERVO)
            mNormal.setPosition(Range.clip(position, 0.0, 1.0));
    }

    public void setSpeed(double speed){
        if (mServoType == ServoType.CR_SERVO)
            mCr.setPower(Range.clip(speed, -1.0, 1.0));
    }

    public void stop(){
        mCr.setPower(0.0);
    }
}
