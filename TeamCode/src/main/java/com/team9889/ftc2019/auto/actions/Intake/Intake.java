package com.team9889.ftc2019.auto.actions.Intake;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Robot;

public class Intake extends Action {

    private ElapsedTime timer = new ElapsedTime();
    private double timeOut;

    public Intake(double timeOut){
        this.timeOut = timeOut;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getIntake().intake();
        Robot.getInstance().getIntake().setIntakeGateState(com.team9889.ftc2019.subsystems.Intake.IntakeGateStates.DOWN);
        Robot.getInstance().getIntake().setIntakeRotatorState(com.team9889.ftc2019.subsystems.Intake.RotatorStates.DOWN);
        timer.reset();
    }

    @Override
    public void update() {
        Robot.getInstance().getIntake().update(timer);
        if (Robot.getInstance().getIntake().getIntakeExtenderPosition() < 20){
            Robot.getInstance().getIntake().setIntakeExtenderPower(.3);
        }else
            Robot.getInstance().getIntake().setIntakeExtenderPower(0);
    }

    @Override
    public boolean isFinished() {
        return timer.milliseconds() > timeOut;
    }

    @Override
    public void done() {
//        Robot.getInstance().getIntake().update(timer);
        Robot.getInstance().getIntake().setIntakeExtenderPower(0);
//        Robot.getInstance().getIntake().setIntakePower(0);
    }
}
