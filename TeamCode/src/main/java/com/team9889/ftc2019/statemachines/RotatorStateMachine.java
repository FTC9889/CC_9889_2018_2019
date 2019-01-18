package com.team9889.ftc2019.statemachines;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.states.RotatorStates;
import com.team9889.ftc2019.subsystems.Intake;

/**
 * Created by joshua9889 on 1/18/2019.
 */
public class RotatorStateMachine extends StateMachine {

    private Intake mIntake;
    private RotatorStates currentState = RotatorStates.NULL;
    private RotatorStates wantedState = RotatorStates.UP;

    public RotatorStateMachine(Intake intake) {
        mIntake = intake;
    }

    @Override
    public void init(boolean auto) {

    }

    @Override
    public void update(ElapsedTime time) {
        if (!isCurrentEqualWanted())
            switch (wantedState) {
                case NULL:
                    currentState = RotatorStates.NULL;
                    break;
                case AUTO:
                    mIntake.setIntakeRotatorPosition(0.5);
                    currentState = RotatorStates.AUTO;
                    break;
                case UP:
                    mIntake.setIntakeRotatorPosition(0.55);
                    currentState = RotatorStates.UP;
                    break;
                case DOWN:
                    mIntake.setIntakeRotatorPosition(1);
                    currentState = RotatorStates.DOWN;
                    break;
            }
    }

    @Override
    public void setWantedState(Enum wantedState) {
        this.wantedState = (RotatorStates) wantedState;
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
