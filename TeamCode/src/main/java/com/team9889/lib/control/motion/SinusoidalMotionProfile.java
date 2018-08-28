package com.team9889.lib.control.motion;

import com.team9889.lib.android.FileWriter;

/**
 * Created by joshua9889 on 7/16/2018.
 *
 * See https://www.desmos.com/calculator/ggyz14jvur for a graph of the equation being implementing.
 */

public class SinusoidalMotionProfile implements MotionProfile {

    private double K1, K2, K3, M;
    private double totalTime;
    private double direction;

    public SinusoidalMotionProfile(){}

    public SinusoidalMotionProfile(double setPoint, ProfileParameters profileParameters){
        calculate(setPoint, profileParameters);
    }

    // Demo
    public static void main(String... args){
        FileWriter log = new FileWriter("sinusoidalProfile.csv");
        SinusoidalMotionProfile profile = new SinusoidalMotionProfile(7, new ProfileParameters(3, 1));

        int step = 100;
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

        setpoint = Math.abs(setpoint);

        totalTime = (2.0 * setpoint * Math.PI) / profileParameters.getMaxA();
        totalTime = Math.pow(totalTime, 0.5);

        K1 = K1(totalTime);
        K2 = K2(profileParameters.getMaxA(), totalTime);
        K3 = K3(totalTime);
        M = profileParameters.getMaxA();
    }

    @Override
    public MotionProfileSegment getOutput(double t) {
        double Position, Velocity, Acceleration;

        if(t< getTotalTime()){
            Position = direction * K2 * (t - (K3 * Math.sin(K1 * t)));
            Velocity = direction * K2 * (1 - Math.cos(K1 * t));
            Acceleration = direction * M * Math.sin(K1 * t);
        } else {
            Position = direction * K2 * (totalTime - (K3 * Math.sin(K1 * totalTime)));
            Velocity = 0.0;
            Acceleration = 0.0;
        }

        return new MotionProfileSegment(Position, Velocity, Acceleration);
    }

    public double getTotalTime(){
        return totalTime;
    }

    private double K1(double T){
        return (2.0 * Math.PI) / T;
    }

    private double K2(double M, double T){
        return M / K1(T);
    }

    private double K3(double T){
        return 1.0 / K1(T);
    }
}
