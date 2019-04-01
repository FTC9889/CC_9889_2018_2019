package com.team9889.ftc2019.auto.actions.Lift;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Dumper;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 1/19/2019.
 */
public class Land2 extends Action {

    private ElapsedTime timer = new ElapsedTime();
    private double timeOut;

    public Land2(double timeOut){
        this.timeOut = timeOut;
    }
    public Land2(){
        this.timeOut = 5000;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getLift().setLiftState(LiftStates.NULL);
        Robot.getInstance().getDumper().setDumperStates(Dumper.dumperStates.COLLECTING);
        timer.reset();
    }

    @Override
    public void update() {
        Robot.getInstance().getLift().setLiftPower(1);
        Robot.getInstance().getDumper().update(timer);
    }

    @Override
    public boolean isFinished() {
        double height = Robot.getInstance().getLift().getHeight();
        RobotLog.a("Height of ScoringLift: " + String.valueOf(height));

        return height > 15.75 || this.timer.milliseconds() > timeOut;
    }

    @Override
    public void done() {
        Robot.getInstance().getLift().setLiftPower(0);
        Robot.getInstance().getDrive().setThrottleSteerPower(0, 0);
        Robot.getInstance().setScorerStates(Robot.scorerStates.NULL);
        RobotLog.a("Finished Lowering");
    }
}
