package com.team9889.ftc2019.auto.actions.Drive;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Constants;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.android.FileWriter;
import com.team9889.lib.control.controllers.MotionProfileFollower;
import com.team9889.lib.control.math.cartesian.Rotation2d;
import com.team9889.lib.control.motion.MotionProfileSegment;
import com.team9889.lib.control.motion.ProfileParameters;
import com.team9889.lib.control.motion.TrapezoidalMotionProfile;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by joshua9889 on 12/28/2018.
 *
 * Drive in an arc. Given a total distance and final angle.
 *
 * Somewhat of a copy 1678/c2018/autonomous/autonomous_base.cpp/AutonomousBase::StartDriveRelative,
 * but converted into code that we have.
 *
 * TODO: Add gyro correction to the code
 */
public class DriveRelative extends Action {

    public static void main(String[] args){
        DriveRelative driveRelative = new DriveRelative(10, new Rotation2d(90, AngleUnit.DEGREES));
        driveRelative.start();
    }

    // Drive object
    private Drive mDrive = Robot.getInstance().getDrive();
    private boolean simulation = true;

    // Distance to travel and final angle
    private double forward;
    private Rotation2d theda;

    // Profile parameters
    private ProfileParameters encoderParameters =
            new ProfileParameters(3 * 12, 12*12); // In Inches TODO: Tune this Value

    // Offsets
    private double leftOffset = 0;
    private double rightOffset = 0;

    // Profile followers and Timer
    private ElapsedTime timer = new ElapsedTime();
    private MotionProfileFollower leftFollower =
            new MotionProfileFollower(0, 0, 0.01, 0.01); // TODO: Tune these values
    private MotionProfileFollower rightFollower = leftFollower;

    /**
     * @param forward Distance to travel
     * @param theda Angle to end at
     */
    public DriveRelative(double forward, Rotation2d theda) {
        this.forward = forward;
        this.theda = theda;
    }

    @Override
    public void setup(String args) {}

    @Override
    public void start() {
        TrapezoidalMotionProfile leftProfile = new TrapezoidalMotionProfile(forward, encoderParameters);
        TrapezoidalMotionProfile rightProfile = new TrapezoidalMotionProfile(forward, encoderParameters);

        if(forward == 0) {
            leftProfile = new TrapezoidalMotionProfile(-Constants.DriveConstants.WheelbaseWidth * theda.getTheda(AngleUnit.RADIANS), encoderParameters);
            rightProfile = new TrapezoidalMotionProfile(Constants.DriveConstants.WheelbaseWidth * theda.getTheda(AngleUnit.RADIANS), encoderParameters);
        } else if(theda.getTheda(AngleUnit.DEGREES) != 0) {
            double radius = forward / theda.getTheda(AngleUnit.RADIANS);

            // This is a ratio between the center radius and outer and inner radii
            // It is equal to the ratio between the center arc length and outer and inner arc lengths
            double slower = (radius - (Constants.DriveConstants.WheelbaseWidth/2.0)) / radius;
            double faster = (radius + (Constants.DriveConstants.WheelbaseWidth/2.0)) / radius;

            // Multiple the "Gains"
            if(theda.getTheda(AngleUnit.DEGREES) > 0) {
                leftProfile.scale(slower);
                rightProfile.scale(faster);
            } else if(theda.getTheda(AngleUnit.DEGREES) < 0){
                leftProfile.scale(faster);
                rightProfile.scale(slower);
            }
        }

        // Set followers
        leftFollower.setProfile(leftProfile);
        rightFollower.setProfile(rightProfile);

        // Check if we are running on a computer
        if(!simulation) {
            leftOffset = mDrive.getLeftDistance();
            rightOffset = mDrive.getRightDistance();
        } else {
            FileWriter log = new FileWriter(this.getClass().getSimpleName() + ".csv");

            int step = 1000;
            for (int i = 0; i < step; i++) {
                double currentTime = i/(step/ leftProfile.getTotalTime());
                MotionProfileSegment leftSegment = leftProfile.getOutput(currentTime);
                MotionProfileSegment rightSegment = rightProfile.getOutput(currentTime);

                String output = (leftSegment.getPosition()) + "," + (rightSegment.getPosition()) + ","
                        + (leftSegment.getVelocity()) + "," + (rightSegment.getVelocity()) + ","
                        + (leftSegment.getAcceleration()) + "," + (rightSegment.getAcceleration());

                log.write(output);
            }

            log.close();
        }

        timer.reset();
    }

    @Override
    public void update() {
        if(!simulation) {
            double leftCurrent = mDrive.getLeftDistance() - leftOffset;
            double rightCurrent = mDrive.getRightDistance() - rightOffset;

            double left = leftFollower.update(leftCurrent, timer.milliseconds());
            double right = rightFollower.update(rightCurrent, timer.milliseconds());

            mDrive.setLeftRightPower(left, right);
        }
    }

    @Override
    public boolean isFinished() {
        return !simulation && (leftFollower.isFinished() && rightFollower.isFinished());
    }

    @Override
    public void done() {
        // Check if we are running this code in a simulation
        if (simulation)
            mDrive.setLeftRightPower(0,0);
    }
}
