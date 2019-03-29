package com.team9889.lib.control.math.cartesian;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Constants;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.android.FileWriter;
import com.team9889.lib.control.controllers.MotionProfileFollower;
import com.team9889.lib.control.motion.MotionProfileSegment;
import com.team9889.lib.control.motion.ProfileParameters;
import com.team9889.lib.control.motion.TrapezoidalMotionProfile;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by joshua9889 on 3/25/2019.
 */
public class Arc extends Action{
    public static void main(String... args){

        double distance = 20;
        Rotation2d goal_heading = new Rotation2d(90, AngleUnit.DEGREES);

        double wheelBase = Constants.DriveConstants.WheelbaseWidth;

        double radius = distance/goal_heading.getTheda(AngleUnit.RADIANS);

        System.out.println(radius);

        System.out.println(radius * (goal_heading.getTheda(AngleUnit.RADIANS)/2));

        double leftRadius = radius + wheelBase/2;
        double rightRadius  = radius - wheelBase/2;

        System.out.println();
        System.out.println(leftRadius);
        System.out.println(rightRadius);


        double leftArclength = leftRadius * goal_heading.getTheda(AngleUnit.RADIANS);
        double rightArclength = rightRadius * goal_heading.getTheda(AngleUnit.RADIANS);
        System.out.println();
        System.out.println(leftArclength);
        System.out.println(rightArclength);
        System.out.println();

        TrapezoidalMotionProfile left = new TrapezoidalMotionProfile();
        TrapezoidalMotionProfile right = new TrapezoidalMotionProfile();
        TrapezoidalMotionProfile theda = new TrapezoidalMotionProfile();

        ProfileParameters parameters = new ProfileParameters(
                ((2 * Math.PI * ((5475.764) / 20)) / 60.0) * 0.8,
                50);

        right.calculate(distance, parameters);
        left.calculate(distance, parameters);

        double faster = (radius + (wheelBase/2)) / radius;
        double slower = (radius - (wheelBase/2)) / radius;

        if(goal_heading.getTheda(AngleUnit.DEGREES) > 0) {
            left.scale = faster;
            right.scale = slower;
        } else {
            left.scale = slower;
            right.scale = faster;
        }

        System.out.println(faster + ", " + slower);


        FileWriter log = new FileWriter(left.getClass().getSimpleName() + ".csv");

        double max_velocity = 0;
        int step = 100;
        for (int i = 0; i < step; i++) {
            double currentTime = i/(step/left.getTotalTime());
            MotionProfileSegment leftOutput = left.getOutput(currentTime);
            MotionProfileSegment rightOutput = right.getOutput(currentTime);

            if(max_velocity<leftOutput.getVelocity()) {
                max_velocity = leftOutput.getVelocity();
            }

            if(max_velocity<rightOutput.getVelocity()) {
                max_velocity = rightOutput.getVelocity();
            }

            System.out.println("Time: " + currentTime + " | Left: " + leftOutput.getPosition() + " | Right: " + rightOutput.getPosition());
            log.write(leftOutput.getPosition() + "," + rightOutput.getPosition() + "," + leftOutput.getVelocity() + "," + rightOutput.getVelocity());
        }

        log.close();

        System.out.println(max_velocity);
        System.out.println();
        System.out.println(parameters.getMaxV());
        System.out.println();
    }

    private Drive mDrive = Robot.getInstance().getDrive();

    private double distance;
    private Rotation2d angle;

    private double leftOffset, rightOffset;

    private TrapezoidalMotionProfile left, right;

    private MotionProfileFollower leftMotionProfileFollower =
            new MotionProfileFollower(0.000003, 0, 0.0225, 0.0005);
    private MotionProfileFollower rightMotionProfileFollower =
            new MotionProfileFollower(0.000003, 0, 0.0225, 0.0005);

    private ElapsedTime timer = new ElapsedTime();

    ProfileParameters parameters = new ProfileParameters(
            ((2 * Math.PI * ((5475.764) / 20)) / 60.0) * 0.6,
            50);

    public Arc(double distance, Rotation2d angle) {
        this.distance = distance;
        this.angle = angle;
    }

    @Override
    public void setup(String args) {}

    @Override
    public void start() {
        // Check if we are running in a simulation
        mDrive.DriveControlState(Drive.DriveControlStates.MOTION_PROFILE);
        leftOffset = mDrive.getLeftDistance();
        rightOffset = mDrive.getRightDistance();

        double radius = distance/angle.getTheda(AngleUnit.RADIANS);

        double faster = (radius + Constants.DriveConstants.WheelbaseWidth/2) / radius;
        double slower = (radius - Constants.DriveConstants.WheelbaseWidth/2) / radius;

        right.calculate(distance, parameters);
        left.calculate(distance, parameters);

        if(angle.getTheda(AngleUnit.DEGREES) > 0) {
            left.scale = faster;
            right.scale = slower;
        } else {
            left.scale = slower;
            right.scale = faster;
        }
    }

    @Override
    public void update() {
        double leftPosition, rightPosition;
        leftPosition = mDrive.getLeftDistance() - leftOffset;
        rightPosition = mDrive.getRightDistance() - rightOffset;

        double currentAngle = mDrive.getAngle().getTheda(AngleUnit.DEGREES);
        double steer = 0;

        double leftSpeed = leftMotionProfileFollower.update(leftPosition, timer.seconds());
        double rightSpeed = rightMotionProfileFollower.update(rightPosition, timer.seconds());

        mDrive.setLeftRightPower(leftSpeed + steer, rightSpeed - steer);
    }

    @Override
    public boolean isFinished() {
        return (leftMotionProfileFollower.isFinished() && rightMotionProfileFollower.isFinished());
    }

    @Override
    public void done() {
        mDrive.setLeftRightPower(0,0);
    }
}
