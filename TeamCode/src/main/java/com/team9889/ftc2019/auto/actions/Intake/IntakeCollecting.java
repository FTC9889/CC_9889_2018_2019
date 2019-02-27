package com.team9889.ftc2019.auto.actions.Intake;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Robot;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 2/27/2019.
 */
public class IntakeCollecting extends Action {

    private ElapsedTime timer = new ElapsedTime();
    private Boolean left = false;
    private Boolean right = true;

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getIntake().setWantedIntakeState(Intake.IntakeStates.INTAKING);
    }

    @Override
    public void update() {
        Robot.getInstance().update(timer);
        double angle = Robot.getInstance().getDrive().getAngle().getTheda(AngleUnit.DEGREES);

        if (angle < 35 && !(angle > 55) || right) {
            Robot.getInstance().getDrive().setLeftRightPower(.4, -.4);
            left = false;
            right = true;
        }

        if (angle > 55 || left){
            Robot.getInstance().getDrive().setLeftRightPower(-.4, .4);
            right = false;
            left = true;
        }
    }

    @Override
    public boolean isFinished() {
        return Robot.getInstance().getIntake().getCurrentIntakeState() == Intake.IntakeStates.INTAKING;
    }

    @Override
    public void done() {
        Robot.getInstance().getDrive().setLeftRightPower(0,0);
        Robot.getInstance().getIntake().outtake();
        Robot.getInstance().update(timer);
    }
}
