package com.team9889.ftc2019.statemachines;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.states.HopperGateStates;
import com.team9889.ftc2019.subsystems.Intake;

/**
 * Created by joshua9889 on 1/18/2019.
 */
public class HopperGateStateMachine extends StateMachine {

    private Intake mIntake;
    private HopperGateStates currentState = HopperGateStates.NULL;
    private HopperGateStates wantedState = HopperGateStates.UP;
    public HopperGateStateMachine(Intake intake) {
        mIntake = intake;
    }

    @Override
    public void init(boolean auto) {

    }

    @Override
    public void update(ElapsedTime time) {
        if(!isCurrentEqualWanted()) {
            switch (wantedState) {
                case NULL:
                    currentState = HopperGateStates.NULL;
                    break;
                case UP:
                    mIntake.setHopperGateUp();
                    currentState = HopperGateStates.UP;
                    break;
                case DOWN:
                    mIntake.setHopperGateDown();
                    currentState = HopperGateStates.DOWN;
                    break;
            }
        }
    }

    @Override
    public void setWantedState(Enum wantedState) {
        this.wantedState = (HopperGateStates) wantedState;
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
