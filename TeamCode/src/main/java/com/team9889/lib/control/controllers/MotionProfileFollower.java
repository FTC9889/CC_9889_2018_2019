package com.team9889.lib.control.controllers;

import com.team9889.lib.Logger;
import com.team9889.lib.control.motion.MotionProfile;
import com.team9889.lib.control.motion.ProfileParameters;
import com.team9889.lib.control.motion.TrapezoidalMotionProfile;

import java.util.ArrayList;

/**
 * Created by joshua9889 on 7/20/2018.
 */

public class MotionProfileFollower extends Controller{

    private double p, d, v, a;
    private MotionProfile profileToFollow;
    private double startTime, lastTime;
    private double lastError;
    private boolean started = false;

    public MotionProfileFollower(double p, double d, double v, double a){
        this.p = p;
        this.d = d;
        this.v = v;
        this.a = a;
    }

    public static void main(String... args){
        Logger log = new Logger("motionProfile.csv");

        TrapezoidalMotionProfile profile = new TrapezoidalMotionProfile();
        profile.calculate(10, new ProfileParameters(20, 50));

        MotionProfileFollower follower = new MotionProfileFollower(0.0, 0.0, 1, 0);
        follower.setProfile(profile);

        ArrayList outputs = new ArrayList(12000000);

        double startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis()-startTime)/1000<profile.getTotalTime()){
            outputs.add(follower.update(0));
        }

        for (int i = 0; i < outputs.size()-1; i++) {
           // log.write(outputs.get(1));
        }

        log.close();

    }

    @Override
    public double update(double currentPosition) {
        double output = 0;

        if (started){
            startTime = System.currentTimeMillis();
            lastTime = startTime;
            started = false;
        } else {

            double[] profile = profileToFollow.getOutput((System.currentTimeMillis() - startTime)/1000);

            double dt = System.currentTimeMillis() - lastTime;

            double error = currentPosition - profile[0];

            //p * error //+ d * ((error - lastError)/ dt - profile[1])
            output =  (p * error) + // P
                    //(d * ((error - lastError) / dt - profile[1])) + // D
                    (v * profile[1]) ;//+ // V
                    //(a * profile[2]); // A
            //output = profile[1];
        }

        lastError = currentPosition;
        lastTime = System.currentTimeMillis();
        return output;
    }

    public void setProfile(MotionProfile profile){
        profileToFollow = profile;
        started = true;
    }
}
