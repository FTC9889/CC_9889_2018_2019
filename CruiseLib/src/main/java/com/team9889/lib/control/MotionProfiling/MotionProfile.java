package com.team9889.lib.control.MotionProfiling;

/**
 * Created by joshua9889 on 6/28/2018.
 *
 */

public interface MotionProfile {

    /**
     * @param setpoint in units
     * @param max_v in units/sec
     * @param max_a in units/sec^2
     */
    void calculate(double setpoint, double max_v, double max_a);


    /**
     * @param t Current Time
     * @return  [position, velocity, acceleration]
     */
    double[] getOutput(double t);
}
