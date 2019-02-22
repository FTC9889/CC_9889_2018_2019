package com.team9889.ftc2019;

/**
 * Class to store constants
 * Created by joshua9889 on 4/10/2017.
 */

public class Constants {

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
    public static class DriveConstants {
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
        public final static double AngleToInchRatio = (Math.PI / 180.) * (WheelbaseWidth / 2.);
    }

   /*---------------------
    |                     |
    |       Intake        |
    |                     |
    ---------------------*/

    //Settings for Intake
    public static class IntakeConstants {
        public final static String kIntakeMotorId = "li";
        public final static String kIntakeExtenderId = "intakeextender";
        public final static String kIntakeRotatorId = "intakerotator";
        public final static String kHopperDumperId = "hopperdumper";
        public final static String kHopperHardStopID = "hopperhardstop";
        public final static String kIntakeSwitchId = "intakescoringswitch";
        public final static String kIntakeInSwitchId = "intakeinswitch";

        public final static String kLeftIntakeDetectorId = "leftrev";
        public final static String kRightIntakeDetectorId = "rightrev";

        final static double kIntakeTicksPerRev = 537.6;  //NeveRest Orbital 20
        final static double kIntakeSpoolDiameter = 1.5;
        public final static double kIntakeTicksToInchRatio = kIntakeSpoolDiameter * Math.PI / kIntakeTicksPerRev;
    }



   /*---------------------
    |                     |
    |        Arms!        |
    |                     |
    ---------------------*/

    //Settings for Arms
    public static class ArmConstants {
        public final static String kLeftShoulderId = "leftshoulder";
        public final static String kRightShoulderId = "rightshoulder";
        public final static String kLeftElbowId = "leftelbow";
        public final static String kRightElbowId = "rightelbow";
        public final static String kLeftClawId = "leftclaw";
        public final static String kRightClawId = "rightclaw";
    }



    /*---------------------
    |                     |
    |       Dumper!       |
    |                     |
    ---------------------*/

    //Settings for Dumper
    public static class DumperConstants {
        public final static String kRightDumperShoulderId = "rightdumpershoulder";
        public final static String kLeftDumperShoulderId = "leftdumpershoulder";
        public final static String kDumperRotatorId = "dumperrotator";
    }


    /*---------------------
    |                     |
    |        Lift!        |
    |                     |
    ---------------------*/

    //Settings for Lift
    public static class LiftConstants {
        public final static String kLeftLiftId = "leftlift";
        public final static String kRightLiftId = "rightlift";
        public final static String kLiftUpperLimitSensorId = "liftupperlimitsensor";
        public final static String kLiftLowerLimitSensorId = "liftlowerlimitsensor";
        public final static String kRobotToGround = "liftdistancesensor";
        final static double kLiftTicksPerRev = 1120; // ticks
        final static double kLiftSpoolDiameter = 1.25; // in
        public final static double kLiftTicksToHeightRatio = kLiftSpoolDiameter * Math.PI / kLiftTicksPerRev; // in
    }


    /*---------------------
    |                     |
    |        Camera!      |
    |                     |
    ---------------------*/

    //Settings for Camera
    public static class CameraConstants {
        public final static String kCameraXAxisId = "cameraxaxis";
        public final static String kCameraYAxisId = "camerayaxis";
    }
}
