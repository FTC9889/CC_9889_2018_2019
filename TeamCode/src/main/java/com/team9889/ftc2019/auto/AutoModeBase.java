package com.team9889.ftc2019.auto;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.team9889.ftc2019.Team9889Linear;
import com.team9889.ftc2019.auto.actions.Action;

/**
 * Created by joshua9889 on 8/5/2017.
 */

public abstract class AutoModeBase extends Team9889Linear {

    private AllianceColor currentAutoRunning = AllianceColor.RED;

    protected enum AllianceColor{
        RED, BLUE, UNKNOWN;
        private static int RED_Num = 1;
        private static int BLUE_Num = -1;

        /**
         * @param allianceColor
         * @return +1 if Red
         *         -1 if Blue
         */
        public static int getNum(AllianceColor allianceColor){
            if(allianceColor != BLUE)
                return RED_Num;
            else
                return BLUE_Num;
        }
    }

    private String red      = "Red";
    private String blue     = "Blue";
    private String crater   = "Crater";
    private String depot    = "Depot";

    private void setCurrentAutoRunning(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(hardwareMap.appContext);
        String alliance = preferences.getString("AllianceColor", "error");
        String side = preferences.getString("Side", "error");
        boolean doubleSample = preferences.getBoolean("DoubleSample", false);

        if (alliance.equals(red))
            this.currentAutoRunning = AllianceColor.RED;
        else if(alliance.equals(blue))
            this.currentAutoRunning = AllianceColor.BLUE;
    }

    public void runAction(Action action){
        if(opModeIsActive())
            action.start();

        while (!action.isFinished() && opModeIsActive())
            action.update();

        if(opModeIsActive())
            action.done();
    }

    public void ThreadAction(final Action action){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                runAction(action);
            }
        };

        if(opModeIsActive() && !isStopRequested())
            new Thread(runnable).start();
    }

    @Override
    public void runOpMode(){
        setCurrentAutoRunning();

        waitForStart(true);

        run(currentAutoRunning);

        finalAction();
    }

    public abstract void run(AllianceColor allianceColor);
}