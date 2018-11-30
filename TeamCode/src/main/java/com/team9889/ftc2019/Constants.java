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

    public final static double ENCODER_TO_DISTANCE_RATIO = (WheelDiameter * Math.PI) / 537.6;
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
    public final static String kIntakeRotator = "intakerotator";
    public final static String kIntakeSwitch = "intakeswitch";
    public final static double kIntakeTicksPerRev = 100;  //TBD
    public final static double kIntakeSpoolDiameter = 1.5;
    public final static double kIntakeTicksToInchRatio = kIntakeSpoolDiameter * Math.PI / kIntakeTicksPerRev;



   /*---------------------
    |                     |
    |        Arms!        |
    |                     |
    ---------------------*/

    //Settings for Arms
    public final static String kLeftShoulderID = "leftshoulder";
    public final static String kRightShoulderId = "rightshoulder";
    public final static String kLeftElbowId = "leftelbow";
    public final static String kRightElbowId = "rightelbow";
    public final static String kLeftClawId = "leftclaw";
    public final static String kRightClawId = "rightclaw";



    /*---------------------
    |                     |
    |        Lift!        |
    |                     |
    ---------------------*/

    //Settings for Lift
    public final static String kLeftLift = "leftlift";
    public final static String kRightLift = "rightlift";
    public final static String kHookServo = "hookservo";
    public final static String kLiftStopServo = "liftstopservo";
    public final static String kLiftTouchSensor = "lifttouchsensor";
    public final static double kMaxLiftHeight = 100; // tbd
    public final static double kLiftTicksPerRev = 1120;
    public final static double kLiftSpoolDiameter = 1.25;
    public final static double kLiftTicksToHeightRatio = kLiftSpoolDiameter * Math.PI / kLiftTicksPerRev;


    /*---------------------
    |                     |
    |        Camera!      |
    |                     |
    ---------------------*/

    //Settings for Camera
    public final static String kCameraXAxis = "cameraxaxis";
    public final static String kCameraYAxis = "camerayaxis";
}
