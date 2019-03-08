package com.team9889.lib.control.motion;

import com.team9889.lib.android.FileWriter;

/**
 * Created by joshua9889 on 7/16/2018.
 *
 * See https://www.desmos.com/calculator/y5dfaz9vlr for a graph of the equations being implemented.
 */

public class TrapezoidalMotionProfile implements MotionProfile {

    private double totalTime, timeToAcc, timeToCruise;
    private double setpoint, max_v, max_a;
    private double direction;
    public double scale = 1;

    public TrapezoidalMotionProfile(){}

    public TrapezoidalMotionProfile(double setPoint, ProfileParameters profileParameters){
        calculate(setPoint, profileParameters);
    }

    // Demo
    public static void main(String... args){
        TrapezoidalMotionProfile profile =
                new TrapezoidalMotionProfile(-48, new ProfileParameters(22.937, 15.646));
        FileWriter log = new FileWriter(profile.getClass().getSimpleName() + ".csv");

        int step = 1000;
        for (int i = 0; i < step; i++) {
            double currentTime = i/(step/profile.totalTime);
            MotionProfileSegment segment = profile.getOutput(currentTime);
            System.out.println("Time: " + currentTime +
                    " | Position: " + segment.getPosition() +
                    " | Velocity: " + segment.getVelocity() +
                    " | Acceleration: " + segment.getAcceleration()
            );
            log.write(segment.getPosition() + "," + segment.getVelocity() + "," + segment.getAcceleration());
        }

        log.close();
    }


    @Override
    public void calculate(double setpoint, ProfileParameters profileParameters) {
        if(setpoint<0)
            direction = -1.0;
        else
            direction = 1.0;

        this.setpoint = Math.abs(setpoint);
        this.max_v = Math.abs(profileParameters.getMaxV());
        this.max_a = Math.abs(profileParameters.getMaxA());

        // This is used to prevent not continuous profiles
        boolean invalidTimes = true;
        while(invalidTimes) {
            // This is in absolute time
            timeToAcc = (max_v / max_a);
            totalTime = (max_v / max_a) + (this.setpoint / max_v);
            timeToCruise = totalTime - timeToAcc;

            if (totalTime - 2 * timeToAcc < totalTime/10) {
                max_v -= 0.01;
            } else {
                invalidTimes = false;
            }

            if(false) {
                System.out.println("Acceleration Time: " + timeToAcc);
                System.out.println("Cruise Time: " + timeToCruise);
                System.out.println("Total Time: " + totalTime);
            }
        }
    }

    @Override
    public MotionProfileSegment getOutput(double t) {
        double Position, Velocity, Acceleration;

        if (t<=timeToAcc){
            Position = (max_a/2.0) * t * t;
            Velocity = max_a * t;
            Acceleration =  max_a;

        } else if(t<=timeToCruise){
            Position = (max_v * t) - ((max_v * max_v)/(2.0 * max_a));
            Velocity = max_v;
            Acceleration = 0;

        } else if(t<=totalTime){
            Position = (totalTime * max_v)
                        + (totalTime * max_a * t)
                        - (Math.pow(max_v, 2) / max_a)
                        - (0.5 * ((max_a * t*t)
                        + (max_a * totalTime * totalTime)));

            Velocity = (max_a * totalTime) - (max_a * t);
            Acceleration = -max_a;

        } else {
            Position = setpoint;
            Velocity = 0;
            Acceleration = 0;
        }

        Position *= direction * scale;
        Velocity *= direction * scale;
        Acceleration *= direction * scale;

        return new MotionProfileSegment(Position, Velocity, Acceleration);
    }

    @Override
    public double getTotalTime(){
        return totalTime;
    }

    @Override
    public void scale(double scale) {
        this.scale = scale;
    }
}
