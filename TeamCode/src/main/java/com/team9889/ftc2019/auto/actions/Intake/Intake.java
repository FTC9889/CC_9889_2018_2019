package com.team9889.ftc2019.auto.actions.Intake;

import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Robot;

public class Intake extends Action {
    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getIntake().intake();
        Robot.getInstance().getIntake().setHopperDumperState(com.team9889.ftc2019.subsystems.Intake.HopperDumperStates.OPEN);
    }

    @Override
    public void update() {
    }

    @Override
    public boolean isFinished() {
        return Robot.getInstance().getIntake().revLeftHopper.getIN() < 5;
    }

    @Override
    public void done() {

    }
}
