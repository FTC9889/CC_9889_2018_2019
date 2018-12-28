package com.team9889.ftc2019.auto.actions.Drive;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
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

    private double distance;
    private ProfileParameters parameters = new ProfileParameters(3 * 12.0, 5);
    private MotionProfile profile = new TrapezoidalMotionProfile();
    private MotionProfileFollower motionProfileFollower =
            new MotionProfileFollower(0, 0, 0.00002, 0.0000001);

    public DriveMotionProfile(double distance) {
        this.distance = distance;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        leftOffset = mDrive.getLeftDistance();
        rightOffset = mDrive.getRightDistance();

        profile.calculate(distance, parameters);
        motionProfileFollower.setProfile(profile);

        timer.reset();
    }

    @Override
    public void update() {
        double leftPosition = mDrive.getLeftDistance() - leftOffset;
        double rightPosition = mDrive.getRightDistance() - rightOffset;
        double currentDistance = (leftPosition + rightPosition)/2.0;

        motionProfileFollower.update(currentDistance, timer.milliseconds());
    }

    @Override
    public boolean isFinished() {
        return motionProfileFollower.isFinished();
    }

    @Override
    public void done() {
        mDrive.setLeftRightPower(0,0);
    }
}
