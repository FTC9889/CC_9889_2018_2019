package com.team9889.ftc2019.statemachines;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.states.IntakeStates;
import com.team9889.ftc2019.subsystems.Intake;

/**
 * Created by joshua9889 on 1/18/2019.
 */
public class IntakeStateMachine extends StateMachine {
    private Intake mIntake;
    private IntakeStates currentState = IntakeStates.WAITING;
    private IntakeStates wantedState = IntakeStates.WAITING;

    public IntakeStateMachine(Intake intake) {
        mIntake = intake;
    }

    @Override
    public void init(boolean auto) {

    }

    @Override
    public void update(ElapsedTime time) {
        if(!isCurrentEqualWanted())
            switch (wantedState){
                case WAITING:
                    mIntake.setIntakePower(0);
                    break;
                case INTAKING:
                    mIntake.intake();
                    break;
                case OUTTAKING:
                    mIntake.outtake();
                    break;
                case DEPOSIT_MARKER:
                    mIntake.setIntakePower(-0.8);
                    break;
            }
    }

    @Override
    public void setWantedState(Enum wantedState) {
        this.wantedState = (IntakeStates) wantedState;
    }

    @Override
    public Enum getCurrentState() {
        return this.currentState;
    }

    @Override
    public boolean isCurrentEqualWanted() {
        return wantedState == currentState;
    }
}
