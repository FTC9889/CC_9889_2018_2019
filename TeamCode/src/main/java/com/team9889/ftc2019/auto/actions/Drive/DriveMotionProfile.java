package com.team9889.ftc2019.auto.actions.Drive;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.CruiseLib;
import com.team9889.lib.android.FileWriter;
import com.team9889.lib.control.controllers.MotionProfileFollower;
import com.team9889.lib.control.controllers.PID;
import com.team9889.lib.control.math.cartesian.Rotation2d;
import com.team9889.lib.control.motion.MotionProfile;
import com.team9889.lib.control.motion.ProfileParameters;
import com.team9889.lib.control.motion.TrapezoidalMotionProfile;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by joshua9889 on 12/27/2018.
 *
 * Follow a straight line using a motion profile, PDVA and PID controllers. PDVA is used on the
 * motors and PID is used for gyro correction.
 */
public class DriveMotionProfile extends Action {

    // Test Locally
    public static void main(String[] args) {
       DriveMotionProfile driveMotionProfile =  new DriveMotionProfile(-5, 0);

       driveMotionProfile.start();

       while (!driveMotionProfile.isFinished())
           driveMotionProfile.update();

       driveMotionProfile.done();
    }

    // Drivetrain Object
    private Drive mDrive = Robot.getInstance().getDrive();

    // File Writer to log all the things/
    private FileWriter log;

    // Wanted Distance (Inches)
    private double distance;

    //(Inches)
    private double leftOffset = 0, rightOffset = 0;

    // Parameters for the motion profile
    // max_v = ((2 * PI * ((MotorFreeSpeed)/20)) / 60) * 0.8 (Inches / Second)
    // timeToMaxSpeed = 3 (Seconds)
    // max_a = max_v / timeToMaxSpeed (Inches / Second^2)
    // max_a = [(((2 * Math.PI * ((5475.764) / 20)) / 60.0) * 0.8) / 3.0]
    // lol just throw out the max_a calculation
    // https://github.com/TeamOverdrive/Relic_Main/blob/master/TeamCode/src/main/java/com/team2753/Constants.java#L44
    private ProfileParameters parameters = new ProfileParameters(
            ((2 * Math.PI * ((5475.764) / 20)) / 60.0) * 0.8,
            50
    );

    // Our profile we are following
    private MotionProfile profile = new TrapezoidalMotionProfile();

    // Our profile following controller
    // See https://github.com/TeamOverdrive/Relic_Main/blob/master/TeamCode/src/main/java/com/team2753/Constants.java#L28
    // Current v and a values based on ratio from above site
    private MotionProfileFollower leftMotionProfileFollower =
            new MotionProfileFollower(0.00003, 0, 0.0225, 0.0005);
    private MotionProfileFollower rightMotionProfileFollower =
            new MotionProfileFollower(0.00003, 0, 0.0225, 0.0005);

    private ElapsedTime timer = new ElapsedTime();

    // Wanted Angle
    private Rotation2d angle;

    // Angle is in radians
    private PID angleCorrection = new PID(0.1/Math.PI, 0, 0.5);

    // Run on computer?
    private boolean simulation = false;

    /**
     * @param distance Distance to travel in Inches
     * @param angle Angle to maintain
     */
    public DriveMotionProfile(double distance, double angle) {
        this.distance = distance;
        this.angle = new Rotation2d(angle, AngleUnit.DEGREES);
    }

    @Override
    public void setup(String args) {}

    @Override
    public void start() {

        // We can graph this data using the command >python plotMP.py driveMotionProfile.
        // Requires py +3.6 i think and matplotlib
        log = new FileWriter("driveMotionProfile.csv");

        // Check if we are running in a simulation
        mDrive.DriveControlState(Drive.DriveControlStates.MOTION_PROFILE);
        leftOffset = mDrive.getLeftDistance();
        rightOffset = mDrive.getRightDistance();

        profile.calculate(distance, parameters);

        leftMotionProfileFollower.setProfile(profile);
        rightMotionProfileFollower.setProfile(profile);

        timer.reset();
    }

    private double[] currentPosition = new double[10000];
    private double[] calculatedpositions = new double[10000];
    private int count = 0;

    @Override
    public void update() {

        double leftPosition, rightPosition;
        leftPosition = mDrive.getLeftDistance() - leftOffset;
        rightPosition = mDrive.getRightDistance() - rightOffset;

        double currentAngle = mDrive.getAngle().getTheda(AngleUnit.DEGREES);
        double steer = angleCorrection.update(currentAngle, angle.getTheda(AngleUnit.DEGREES));
        steer = Math.sin(steer);

        double currentTime = timer.seconds();

        double leftSpeed = leftMotionProfileFollower.update(leftPosition, currentTime);
        double rightSpeed = rightMotionProfileFollower.update(rightPosition, currentTime);

        double currentWantedPosition = profile.getOutput(currentTime).getPosition();

        mDrive.setLeftRightPower(leftSpeed + steer, rightSpeed - steer);

        currentPosition[count] = CruiseLib.Average(leftPosition, rightPosition);
        calculatedpositions[count] = currentWantedPosition;
        count++;
    }

    @Override
    public boolean isFinished() {
        return (leftMotionProfileFollower.isFinished() && rightMotionProfileFollower.isFinished());
    }

    @Override
    public void done() {
        mDrive.setLeftRightPower(0,0);

        for (int i=0;i<count;i++) {
            log.write(currentPosition[i] + "," + calculatedpositions[i]);
        }

        log.close();
    }
}
