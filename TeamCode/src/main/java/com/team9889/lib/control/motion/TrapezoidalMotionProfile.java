package com.team9889.lib.control.motion;

import com.team9889.lib.Logger;

/**
 * Created by joshua9889 on 7/16/2018.
 *
 * See https://www.desmos.com/calculator/y5dfaz9vlr for a graph of the equations being implemented.
 */

public class TrapezoidalMotionProfile implements MotionProfile {

    private double totalTime, timeToAcc, timeToCruise;
    private double setpoint, max_v, max_a;
    private double direction;


    public TrapezoidalMotionProfile(){}

    public TrapezoidalMotionProfile(double setPoint, ProfileParameters profileParameters){
        calculate(setPoint, profileParameters);
    }

    // Demo
    public static void main(String... args){
        Logger log = new Logger("trapezoidalProfile.csv");
        TrapezoidalMotionProfile profile = new TrapezoidalMotionProfile();
        ProfileParameters parameters = new ProfileParameters(1.5, 2);
        profile.calculate(2.5, parameters);

        int step = 1000;
        for (int i = 0; i < step; i++) {
            double currentTime = i/(step/profile.totalTime);
            double[] segment = profile.getOutput(currentTime);
            System.out.println("Time: " + currentTime +
                    " | Position: " + segment[0] +
                    " | Velocity: " + segment[1] +
                    " | Acceleration: " + segment[2]
            );
            log.write(segment[0] + "," + segment[1] + "," + segment[2]);
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
        this.max_v = profileParameters.getMaxV();
        this.max_a = profileParameters.getMaxV();

        // This is in absolute time
        timeToAcc = (max_v/max_a);
        totalTime = (max_v/max_a)+(this.setpoint/max_v);
        timeToCruise = totalTime-timeToAcc;
    }

    @Override
    public double[] getOutput(double t) {
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
            Position = (totalTime * max_v) +
                    (totalTime * max_a * t) -
                    (Math.pow(max_v, 2) / max_a) -
                    (0.5 * ((max_a * t*t) + (max_a * totalTime * totalTime)));

            Velocity = (max_a * totalTime) - (max_a * t);
            Acceleration = -max_a;

        } else {
            Position = setpoint;
            Velocity = 0;
            Acceleration = 0;
        }

        Position *= direction;
        Velocity *= direction;
        Acceleration *= direction;

        return new double[]{Position, Velocity, Acceleration};
    }

    public double getTotalTime(){
        return totalTime;
    }
}
