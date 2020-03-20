package com.team9889.lib.control.kinematics;

import java.util.Arrays;

public class TankDriveKinematicModel {
    //input values
    public static double w = 16;

    //output values
    public static double globalX = 0, globalY = 0, globalTheda = 0;

    //function to calculate the arc of the drivetrain
    public double[] calculateDelta(double deltaLeftPos,double deltaRightPos, double pastAbsTheda){
        //calculate average distance of left and right motors
        double a = (deltaRightPos + deltaLeftPos) / 2;

        //calculate the change in angle
        double deltaTheda = (deltaLeftPos - deltaRightPos) / w;

        double deltaX, deltaY;

        //checks if we're going somewhat straight
        if (Math.abs(deltaTheda) > 0.001) {
            //finding the x intercept
            double l = a / deltaTheda;

            //calculating position based on the intercept
            deltaY = l * Math.sin(deltaTheda);
            deltaX = l * (1 - Math.cos(deltaTheda));
        }else{
            //calculating position in a straight line
            deltaX = a * Math.sin(pastAbsTheda);
            deltaY = a * Math.cos(pastAbsTheda);
        }

        //returns change in position and change in angle in both radians and degrees
        double[] ans = {deltaX, deltaY, -deltaTheda, -Math.toDegrees(deltaTheda)};
        return ans;
    }

    //calculate global position and angle
    public double[] calculateAbs(double deltaLeftPos, double deltaRightPos) {
        //import data from calculateDelta into local variable
        double[] calculatedDelta = calculateDelta(deltaLeftPos, deltaRightPos, globalTheda);

        //adding all delta positions and angle to global positions and angle (pose)
        globalX += calculatedDelta[0];
        globalY += calculatedDelta[1];
        globalTheda += calculatedDelta[2];

        //return global pose
        double[] ans = {globalX, globalY, globalTheda, Math.toDegrees(globalTheda)};
        return ans;
    }

    //test
    public static void main(String... args ){
        TankDriveKinematicModel kinematicModel = new TankDriveKinematicModel();
//        System.out.println(Arrays.toString(kinematicModel.calculateDelta(20, 30)));
//        System.out.println(Arrays.toString(kinematicModel.calculateDelta(20, 20)));
//        System.out.println(Arrays.toString(kinematicModel.calculateDelta(30, 20)));

        System.out.println(Arrays.toString(kinematicModel.calculateAbs(20, 20)));
        System.out.println(Arrays.toString(kinematicModel.calculateAbs(30, 20)));
        System.out.println(Arrays.toString(kinematicModel.calculateAbs(0, 30)));

    }

}
