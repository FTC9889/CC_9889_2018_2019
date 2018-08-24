package com.team9889.lib.simulator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;
import com.vuforia.Vec2F;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Rotation;

public class SimDcMotor implements DcMotor {

    public static void main(String... args) {
        SimDcMotor motor = new SimDcMotor();

    }

    private MotorConfigurationType type = null;
    private ZeroPowerBehavior zeroPowerBehavior = ZeroPowerBehavior.FLOAT;
    private double power = 0.0;
    private int wantedPosition = 0;
    private int currentPosition = 0;
    private RunMode runMode = RunMode.RUN_WITHOUT_ENCODER;
    private Direction direction = Direction.FORWARD;
    private double lastTime = 0;

    @Override
    public MotorConfigurationType getMotorType() {
        MotorConfigurationType orbital = new MotorConfigurationType();

        orbital.setTicksPerRev(537.6);
        orbital.setGearing(19.2);
        orbital.setMaxRPM(340);
        orbital.setOrientation(Rotation.CCW);

        if(type != null)
            return type;
        else
            return orbital;
    }

    @Override
    public void setMotorType(MotorConfigurationType motorType) {
        this.type = motorType;
    }

    @Override
    public DcMotorController getController() {
        return null;
    }

    @Override
    public int getPortNumber() {
        return 0;
    }

    @Override
    public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {
        this.zeroPowerBehavior = zeroPowerBehavior;
    }

    @Override
    public ZeroPowerBehavior getZeroPowerBehavior() {
        return this.zeroPowerBehavior;
    }

    @Override
    @Deprecated
    public void setPowerFloat() {}

    @Override
    @Deprecated
    public boolean getPowerFloat() {return false;}

    @Override
    public void setTargetPosition(int position) {
        this.wantedPosition = position;
    }

    @Override
    public int getTargetPosition() {
        return this.wantedPosition;
    }

    @Override
    @Deprecated
    public boolean isBusy() {
        return false;
    }

    @Override
    public int getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public void setMode(RunMode mode) {
        this.runMode = mode;
    }

    @Override
    public RunMode getMode() {
        return this.runMode;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public void setPower(double power) {
        this.power = power;
    }

    @Override
    public double getPower() {
        return this.power;
    }

    @Override
    public Manufacturer getManufacturer() {
        return null;
    }

    @Override
    public String getDeviceName() {
        return null;
    }

    @Override
    public String getConnectionInfo() {
        return null;
    }

    @Override
    public int getVersion() {
        return 9889;
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {

    }

    @Override
    public void close() {

    }
}
