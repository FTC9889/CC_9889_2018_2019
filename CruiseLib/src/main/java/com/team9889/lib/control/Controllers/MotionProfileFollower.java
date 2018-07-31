package com.team9889.lib.control.Controllers;

import com.team9889.lib.control.MotionProfiling.MotionProfile;

/**
 * Created by joshua9889 on 7/20/2018.
 */

public class MotionProfileFollower extends Controller{

    private double p, d, v, a;
    private MotionProfile profileToFollow;
    private double startTime, lastTime;
    private double lastError;
    private boolean started = false;

    public MotionProfileFollower(double p, double d, double v, double a, MotionProfile profile){
        this.p = p;
        this.d = d;
        this.v = v;
        this.a = a;
        setProfile(profile);
    }

    public MotionProfileFollower(double p, double d, double v, double a){
        this.p = p;
        this.d = d;
        this.v = v;
        this.a = a;
    }

    @Override
    public double update(double current) {
        double output = 0;

        if (!started){
            startTime = System.currentTimeMillis();
            lastTime = startTime;
            started = true;
        } else {
            double[] profile = profileToFollow.getOutput(System.currentTimeMillis() - startTime);

            double dt = System.currentTimeMillis() - lastTime;

            double error = current - profile[0];

            output = p * error + d * ((error - lastError)
                    / dt - profile[1]) + (v * profile[1]
                    + a * profile[2]);
        }

        lastError = current;
        lastTime = System.currentTimeMillis();
        return output;
    }

    public void setProfile(MotionProfile profile){
        profileToFollow = profile;
        started = false;
    }
}
