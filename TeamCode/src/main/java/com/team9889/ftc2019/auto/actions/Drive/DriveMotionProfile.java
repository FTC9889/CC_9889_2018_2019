package com.team9889.ftc2019.auto.actions.Drive;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Constants;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.android.FileWriter;
import com.team9889.lib.control.controllers.MotionProfileFollower;
import com.team9889.lib.control.motion.MotionProfile;
import com.team9889.lib.control.motion.ProfileParameters;
import com.team9889.lib.control.motion.TrapezoidalMotionProfile;

/**
 * Created by joshua9889 on 12/27/2018.
 */
public class DriveMotionProfile extends Action {

    private Drive mDrive = Robot.getInstance().getDrive();

    private ElapsedTime timer = new ElapsedTime();
    private double leftOffset, rightOffset;
    FileWriter fileWriter;

    private double distance;
    private ProfileParameters parameters = new ProfileParameters(1.745, 0.002522365);
    private MotionProfile profile = new TrapezoidalMotionProfile();
    private MotionProfileFollower motionProfileFollower =
            new MotionProfileFollower(0, 0, 0.7306, 0.2);

    public DriveMotionProfile(double distance) {
        this.distance = distance / Constants.ENCODER_TO_DISTANCE_RATIO;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        fileWriter = new FileWriter("output.csv");

        leftOffset = mDrive.getLeftTicks();
        rightOffset = mDrive.getRightTicks();

        profile.calculate(distance, parameters);
        motionProfileFollower.setProfile(profile);

        timer.reset();
    }

    @Override
    public void update() {
        double leftPosition = mDrive.getLeftTicks() - leftOffset;
        double rightPosition = mDrive.getRightTicks() - rightOffset;
        double currentDistance = (leftPosition + rightPosition)/2.0;

        double speed = motionProfileFollower.update(currentDistance, timer.milliseconds());
        fileWriter.write(speed);
        mDrive.setThrottleSteerPower(speed, 0);
    }

    @Override
    public boolean isFinished() {
        return motionProfileFollower.isFinished();
    }

    @Override
    public void done() {
        mDrive.setLeftRightPower(0,0);
        fileWriter.close();
    }
}
