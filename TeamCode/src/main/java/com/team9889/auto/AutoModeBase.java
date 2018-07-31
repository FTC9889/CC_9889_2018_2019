package com.team9889.auto;

import com.team9889.Team9889Linear;

/**
 * Created by joshua9889 on 8/5/2017.
 */

public abstract class AutoModeBase extends Team9889Linear {
    @Override
    public void runOpMode(){
        waitForStart(true);
        run();
        finalAction();
    }

    public abstract void run();
}