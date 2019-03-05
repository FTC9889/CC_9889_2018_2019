package com.team9889.ftc2019.auto.actions.Intake;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Robot;

public class Intake extends Action {

    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getIntake().intake();
        Robot.getInstance().getIntake().setHopperDumperState(com.team9889.ftc2019.subsystems.Intake.HopperDumperStates.OPEN);
        timer.reset();
    }

    @Override
    public void update() {
        Robot.getInstance().update(timer);
        Robot.getInstance().getIntake().setIntakeExtenderPower(.3);
    }

    @Override
    public boolean isFinished() {
        return Robot.getInstance().getIntake().revLeftHopper.getIN() < 2.5 || Robot.getInstance().getIntake().revRightHopper.getIN() < 2.5;
    }

    @Override
    public void done() {
        Robot.getInstance().getIntake().setIntakeExtenderPower(0);
        Robot.getInstance().getIntake().setIntakePower(0);
    }
}
