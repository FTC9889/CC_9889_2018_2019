package com.team9889.ftc2019.auto;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Team9889Linear;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.lib.android.FileReader;

/**
 * Created by joshua9889 on 8/5/2017.
 */

public abstract class AutoModeBase extends Team9889Linear {

    private Side currentAutoRunning = AutoModeBase.Side.CRATER;
    private boolean doubleSample = false;
    private boolean scoreSample = true;

    protected ElapsedTime autoTimer = new ElapsedTime();

    protected enum Side {
        CRATER, DEPOT;

        private static String depotString = "Depot";
        private static String craterString = "Crater";

        private static int CRATER_Num = 1;
        private static int DEPOT_Num = -1;

        public static int getNum(com.team9889.ftc2019.auto.AutoModeBase.Side side){
            return side == CRATER ? CRATER_Num : DEPOT_Num;
        }

        public static Side fromText(String side) {
            return side.equals(craterString) ? CRATER : DEPOT;
        }
    }

    public static void main(String... args) {
        com.team9889.ftc2019.auto.AutoModeBase.Side side = AutoModeBase.Side.DEPOT;

        System.out.println(AutoModeBase.Side.getNum(side));
    }

    private void setCurrentAutoRunning(){
        String filename = "autonomousSettings.txt";
        FileReader settingsFile = new FileReader(filename);

        String[] settings = settingsFile.lines();
        settingsFile.close();

        this.currentAutoRunning = Side.fromText(settings[0]);
        this.doubleSample = String.valueOf(settings[1]).equals("true");
        this.scoreSample = String.valueOf(settings[2]).equals("true");
    }

    public void runAction(Action action){
        if(opModeIsActive())
            action.start();

        while (!action.isFinished() && opModeIsActive()) {
            action.update();
        }

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
            run(currentAutoRunning, doubleSample, scoreSample);
        }

        finalAction();
    }

    public abstract void run(Side side, boolean doubleSample, boolean scoreSample);
}