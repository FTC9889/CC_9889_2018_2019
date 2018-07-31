package com.team9889.lib.control.MotionProfiling;

public class TrapezoidalMotionProfile implements MotionProfile {

    private double totalTime, accTime, cruiseTime;
    private double direction;

    public TrapezoidalMotionProfile(){}

    public TrapezoidalMotionProfile(double setpoint, double max_v, double max_a){
        calculate(setpoint, max_v, max_a);
    }

    @Override
    public void calculate(double setpoint, double max_v, double max_a) {
        if(setpoint<0)
            direction = -1.0;
        else
            direction = 1.0;

        setpoint = Math.abs(setpoint);

        double timeToAcc = (max_v/max_a);
        double timeToCruise = (setpoint/max_v) - (max_v/max_a);
        double totalTime = (2.0*timeToAcc) + timeToCruise;

        System.out.println(timeToAcc);
        System.out.println(timeToCruise);
        System.out.println(totalTime);
    }

    @Override
    public double[] getOutput(double t) {
        double Position, Speed, Acceleration;

        Position = 0.0;
        Speed = 0.0;
        Acceleration = 0.0;

        return new double[]{Position, Speed, Acceleration};
    }
}
