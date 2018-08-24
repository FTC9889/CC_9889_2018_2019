package com.team9889.ftc2019;

/**
 * Class to store constants
 * Created by joshua9889 on 4/10/2017.
 */

public class Constants {
    //VuMark Licence Key
    public final static String kVuforiaLicenceKey = "AUEjQhP/////AAAAGV8qq1GGcE03gQHKWYPRZBNIZYZq8Do" +
            "Un9lOpeWPY8PZKG1B5UtrsqkqPqh8Vcuv+HuUuobUNZE35fhYNPqxRIWqtoKbUmkZZCspmu0Aw685D5dxk87dT3" +
            "8/oYxzdKFs3EZaD8hvprmWj2Oww4+GtxS+fiImc23ZlkU20esE1MhwvX0xJ8tjoPS9pdTVSt1QtoYp3WxSxtZlK" +
            "d0B0UMCxzj0KxN4JZRlTmF2W3dLU2G9SJ3hQO8jcC+Nuvbfk809C6LSWiijZ9L7IZNcQQiFKDa5yJP+ayX+Y1cp" +
            "wcV19yqlPQTH7CQqcvnZDfwQZXmCpDhcQpW9h+bCXaerRH/uWNZMskyO0AXeFa1oCgB3EGPB";

    /*---------------------
    |                     |
    |     Drivetrain!     |
    |                     |
    ---------------------*/

    //Settings for Drive class
    public final static String kLeftDriveMasterId = "left";
    public final static String kRightDriveMasterId = "right";

    // Robot Specs
    private final static double FinalGearReduction = (19.2 / 1.);
    private final static double WheelDiameter = (4.0);

    // NeveRest 40 Encoders
    private final static double PPR40 = (28.0);
    private final static double EncoderCounts40 = (PPR40 * FinalGearReduction);

    // NeveRest Orbitals 20 Encoders
    private final static double PPR20 = (134.4);
    private final static double EncoderCounts20 = (PPR20 * FinalGearReduction);

    // Same output always
    public final static double CountsPerInch = (EncoderCounts20 / (WheelDiameter*Math.PI));

    public static double ticks2Inches(int ticks){
        return ticks/Constants.CountsPerInch;
    }

    public static int inches2Ticks(double in){
        return (int)(in * CountsPerInch);
    }
}
