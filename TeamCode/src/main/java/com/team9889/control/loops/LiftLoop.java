package com.team9889.control.loops;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by joshua9889 on 4/5/2018.
 */

public class LiftLoop extends Loop {
    ElapsedTime t = new ElapsedTime();

    @Override
    public void start() {
        t.reset();
    }

    @Override
    public void loop() {
        System.out.println("LiftLoop> "+t.nanoseconds() + " | System Time: " + System.currentTimeMillis());
        t.reset();
    }

    @Override
    public void stop() {

    }
}
