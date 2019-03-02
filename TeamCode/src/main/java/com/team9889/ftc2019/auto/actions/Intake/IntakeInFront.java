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

    public IntakeInFront(double intakeOutDistance){
        Robot.getInstance().getIntake().autoIntakeOut = intakeOutDistance;
    }
    public IntakeInFront(){
        Robot.getInstance().getIntake().autoIntakeOut = 20;
    }

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
        Robot.getInstance().getIntake().update(timer);
    }

    @Override
    public boolean isFinished() {
        return Robot.getInstance().getIntake().isCurrentStateWantedState() && Robot.getInstance().getIntake().inPosition();
    }

    @Override
    public void done() {

    }
}
