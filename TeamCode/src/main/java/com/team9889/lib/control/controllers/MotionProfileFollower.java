package com.team9889.lib.control.controllers;

import com.team9889.lib.control.motion.MotionProfile;
import com.team9889.lib.control.motion.MotionProfileSegment;

/**
 * Created by joshua9889 on 7/20/2018.
 */

public class MotionProfileFollower {

    private double p, d, v, a;
    private MotionProfile profileToFollow;
    private double lastTime, lastError;

    private double currentPosition = 0;

    public MotionProfileFollower(double p, double d, double v, double a){
        this.p = p;
        this.d = d;
        this.v = v;
        this.a = a;
    }

    public void setProfile(MotionProfile profile){
        profileToFollow = profile;
    }

    public double update(double currentPosition, double currentTime) {
        this.currentPosition = currentPosition;

        MotionProfileSegment profile = profileToFollow.getOutput(currentTime);

        double dt = currentTime - lastTime;

        double error = currentPosition - profile.getPosition();

        double output = (p * error) +                                               // P
                        (d * ((error - lastError) / dt - profile.getVelocity())) + // D
                        (v * profile.getVelocity()) +                             // V
                        (a * profile.getAcceleration());                         // A

        lastError = currentPosition;
        lastTime = currentTime;
        return output;
    }

    public boolean isFinished(){
        return currentPosition >= profileToFollow.getOutput(profileToFollow.getTotalTime()).getPosition();
    }
}
