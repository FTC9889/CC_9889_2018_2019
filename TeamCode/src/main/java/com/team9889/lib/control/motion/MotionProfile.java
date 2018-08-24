package com.team9889.lib.control.motion;

/**
 * Created by joshua9889 on 6/28/2018.
 *
 */

public interface MotionProfile {

    /**
     * @param setpoint in units
     * @param profileParameters Profile Parameters for the motion profile
     */
    void calculate(double setpoint, ProfileParameters profileParameters);


    /**
     * @param t Current Time
     * @return  [position, velocity, acceleration]
     */
    double[] getOutput(double t);
}
