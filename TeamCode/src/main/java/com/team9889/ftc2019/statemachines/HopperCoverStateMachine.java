package com.team9889.ftc2019.statemachines;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.states.HopperCoverStates;
import com.team9889.ftc2019.subsystems.Intake;

/**
 * Created by joshua9889 on 1/18/2019.
 */
public class HopperCoverStateMachine extends StateMachine {
    private Intake mIntake;
    private HopperCoverStates currentState = HopperCoverStates.NULL;
    private HopperCoverStates wantedState = HopperCoverStates.NULL;
    public HopperCoverStateMachine(Intake intake) {
        mIntake = intake;
    }

    @Override
    public void init(boolean auto) {

    }

    @Override
    public void update(ElapsedTime time) {
        if (!isCurrentEqualWanted()) {
            switch (wantedState) {
                case OPEN:
                    mIntake.HopperCoverOpen();
                    currentState = HopperCoverStates.OPEN;
                    break;
                case CLOSED:
                    mIntake.HopperCoverClosed();
                    currentState = HopperCoverStates.CLOSED;
                    break;
            }
        }
    }

    @Override
    public void setWantedState(Enum wantedState) {
        this.wantedState = (HopperCoverStates) wantedState;
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
