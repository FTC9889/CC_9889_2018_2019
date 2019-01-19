package com.team9889.ftc2019.auto.actions.Lift;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 1/19/2019.
 */
public class Land2 extends Action {
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getLift().setLiftState(LiftStates.NULL);
    }

    @Override
    public void update() {
        double distance = Robot.getInstance().getLift().getDistanceSensorRange();
        if (distance < 1.5){
            if (Robot.getInstance().getDrive().getRightDistance() > 1 || Robot.getInstance().getDrive().getLeftDistance() > 1){
                Robot.getInstance().getDrive().setThrottleSteerPower(0,0);
                Robot.getInstance().getLift().setLiftPower(.7);

                RobotLog.a("Lowering Robot And Driving Forward");
            } else {
                Robot.getInstance().getLift().setLiftPower(0);
                Robot.getInstance().getDrive().setThrottleSteerPower(.2, 0);
                RobotLog.a("Driving Forward");

            }
        } else {
            Robot.getInstance().getLift().setLiftPower(1);
            RobotLog.a("Lowering Robot");
        }

        RobotLog.a("Value of Laser Sensor: " + String.valueOf(distance));
    }

    @Override
    public boolean isFinished() {
        double height = Robot.getInstance().getLift().getHeight();
        RobotLog.a("Height of Lift: " + String.valueOf(height));

        return height >= 15.5 && Robot.getInstance().getLift().getDistanceSensorRange() < 1.5;
    }

    @Override
    public void done() {
        Robot.getInstance().getLift().setLiftPower(0);
        Robot.getInstance().getDrive().setThrottleSteerPower(0, 0);
        RobotLog.a("Finished Lowering");
    }
}
