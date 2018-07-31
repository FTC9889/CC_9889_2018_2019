package com.team9889.lib.control.MotionProfiling;

/**
 * Created by joshua9889 on 6/28/2018.
 */

public interface MotionProfile {
    public double updateSetpoint(double curSetpoint, double curSource, double curTime);
    public double setGoal(double goal, double curSource, double t);}
