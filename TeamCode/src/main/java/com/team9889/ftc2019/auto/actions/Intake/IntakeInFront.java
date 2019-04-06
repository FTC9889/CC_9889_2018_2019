package com.team9889.ftc2019.auto.actions.Intake;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 1/11/2019.
 */
public class IntakeInFront extends Action {
    private ElapsedTime timer = new ElapsedTime();
    private double timeOut;
    private boolean intakeRotator;

    public IntakeInFront(double intakeOutDistance, double timeOut, boolean intakeRotatorUp){
        Robot.getInstance().getIntake().autoIntakeOut = intakeOutDistance;
        this.timeOut = timeOut;
        this.intakeRotator = intakeRotatorUp;
    }
//    public IntakeInFront(){
//        Robot.getInstance().getIntake().autoIntakeOut = 20;
//    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getIntake().setWantedIntakeState(Intake.IntakeStates.AUTONOMOUS);

        timer.reset();
    }

    @Override
    public void update() {
        if (intakeRotator){
            Robot.getInstance().getIntake().setIntakeRotatorState(Intake.RotatorStates.UP);
        }else
            Robot.getInstance().getIntake().setIntakeRotatorState(Intake.RotatorStates.DOWN);
        Robot.getInstance().getIntake().update(timer);
    }

    @Override
    public boolean isFinished() {
        return Robot.getInstance().getIntake().currentIntakeState == Intake.IntakeStates.AUTONOMOUS || timer.milliseconds() > timeOut;
    }

    @Override
    public void done() {

    }
}
