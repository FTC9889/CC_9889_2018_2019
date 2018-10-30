package com.team9889.ftc2019;

/**
 * Class to store constants
 * Created by joshua9889 on 4/10/2017.
 */

public class Constants {

    public static final boolean isSimulation = true;

    //VuMark Licence Key
    public final static String kVuforiaLicenceKey = "AUUHzRL/////AAABmaGWp2jobkGOsFjHcltHEso2M1NH" +
            "Ko/nBDlUfwXKZBwui9l88f5YAT31+8u5yoy3IEJ1uez7rZrIyLdyR4cCMC+a6I7X/EzkR1F094ZegAsXdg9n" +
            "ht01zju+nxxi1+rabsSepS+TZfAa14/0rRidvX6jqjlpncDf8uGiP75f38cW6W4uFRPrLdufA8jMqODGux9d" +
            "w7VkFMqB+DQuNk8n1pvJP87FFo99kr653cjMwO4TYbztNmUYaQUXjHHNhOFxHufN42r7YcCErvX90n/gIvs4" +
            "wrvffXGyU/xkmSaTJzrgiy8R+ZJx2T0JcEJ0m1UUEoF2BmW4ONAVv/TkG9ESGf6iAmx66vrVm3tk6+YY+1q1";

    /*---------------------
    |                     |
    |     Drivetrain!     |
    |                     |
    ---------------------*/

    //Settings for Drive class
    public final static String kLeftDriveMasterId = "lf";
    public final static String kRightDriveMasterId = "rf";
    public final static String kLeftDriveSlaveId = "lb";
    public final static String getkRightDriveSlaveId = "rb";


    public final static double WheelbaseWidth = 16;
    public final static double WheelDiameter = 4;

    /**
     * ticks to inch
     * (Wheel Diameter * PI) / Counts Per Rotation
     */

    public final static double ENCODER_TO_DISTANCE_RATIO = (WheelDiameter * Math.PI) / 537.6;;
    public final static double AngleToInchRatio = (Math.PI/180.) * (WheelbaseWidth/2.);

    public static void main(String... args){
        System.out.println(AngleToInchRatio);
    }
   /*---------------------
    |                     |
    |       Intake        |
    |                     |
    ---------------------*/

    //Settings for Intake
    public final static String kIntakeMotorID = "li";
    public final static String kIntakeExtender = "intakeextender";
    public final static String kIntakeRotator = "intakeRotator";
    public final static String kIntakeSwitch = "intakeSwitch";



   /*---------------------
    |                     |
    |        Arms!        |
    |                     |
    ---------------------*/

    //Settings for Arms
    public final static String kLeftShoulderID = "leftShoulder";
    public final static String kRightShoulderId = "rightShoulder";
    public final static String kLeftElbowId = "leftElbow";
    public final static String kRightElbowId = "rightElbow";
    public final static String kLeftClawId = "leftClaw";
    public final static String kRightClawId = "rightClaw";



    /*---------------------
    |                     |
    |        Lift!        |
    |                     |
    ---------------------*/

    //Settings for Lift
    public final static String kLeftLift = "leftLift";
    public final static String kRightLift = "rightLift";
    public final static String kHookServo = "hookServo";
    public final static String kLiftTouchSensor = "liftTouchSensor";
    public final static double kMaxLiftHeight = 100; // tbd
    public final static double kLiftTicksToHeightRatio = 100; // tbd



    /*---------------------
    |                     |
    |        Camera!        |
    |                     |
    ---------------------*/

    //Settings for Camera
    public final static String kCameraXAxis = "CameraXAxis";
    public final static String kCameraYAxis = "CameraYAxis";
}
