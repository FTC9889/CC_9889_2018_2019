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
     * @return  Position, Velocity, Acceleration
     */
    MotionProfileSegment getOutput(double t);


    /**
     * @return Time required to finish the profile
     */
    double getTotalTime();

    void scale(double scale);
}
