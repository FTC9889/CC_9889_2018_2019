package com.team9889.ftc2019.auto.actions.Intake;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Robot;

public class MarkerDumperUp extends Action {

    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getIntake().setMarkerDumperState(Intake.MarkerDumperStates.HOLDING);
        timer.reset();
    }

    @Override
    public void update() {
        Robot.getInstance().getIntake().update(timer);
    }

    @Override
    public boolean isFinished() {
        return timer.milliseconds() > 250;
    }

    @Override
    public void done() {

    }
}
