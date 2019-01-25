package com.team9889.ftc2019.auto;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Team9889Linear;
import com.team9889.ftc2019.auto.actions.Action;

/**
 * Created by joshua9889 on 8/5/2017.
 */

public abstract class AutoModeBase extends Team9889Linear {

    private AllianceColor currentAutoRunning = AllianceColor.RED;
    protected ElapsedTime autoTimer = new ElapsedTime();
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

    private void setCurrentAutoRunning(){
        //TODO: Add code for GUI
        this.currentAutoRunning = AllianceColor.RED;
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
    public void runOpMode() throws InterruptedException{
        setCurrentAutoRunning();

        waitForStart(true);
        autoTimer.reset();

        if (opModeIsActive() && !isStopRequested()) {
            run(currentAutoRunning);
        }

        finalAction();
    }

    public abstract void run(AllianceColor allianceColor);
}