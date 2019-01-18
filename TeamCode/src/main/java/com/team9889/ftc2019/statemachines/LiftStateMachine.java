package com.team9889.ftc2019.statemachines;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Lift;

/**
 * Created by joshua9889 on 1/18/2019.
 */
public class LiftStateMachine extends StateMachine {

    private LiftStates currentState = LiftStates.NULL;
    private LiftStates wantedState = LiftStates.NULL;
    private Lift mLift;

    public LiftStateMachine(Lift lift) {
        mLift = lift;
    }

    @Override
    public void init(boolean auto) {

    }

    @Override
    public void update(ElapsedTime time) {
        if (currentState != wantedState) {
            switch (wantedState) {
                case DOWN:
                    if (mLift.getLowerLimitPressed()) {
                        mLift.setLiftPower(0);
                        mLift.zeroSensors();
                        currentState = LiftStates.DOWN;
                    } else {
                        mLift.setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        mLift.setLiftPower(-.7);
                    }
                    break;

                case HOOKHEIGHT:
                    mLift.setLiftPosition(12);

                    if (mLift.inPosition())
                        currentState = wantedState;
                    break;

                case SCOREINGHEIGHT:
                    if (mLift.getUpperLimitPressed()) {
                        mLift.setLiftPower(0);
                        currentState = LiftStates.SCOREINGHEIGHT;
                    } else {
                        mLift.setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        mLift.setLiftPower(.7);
                    }
                    break;

                case READY:
                    mLift.setLiftPosition(10);

                    if (mLift.inPosition())
                        currentState = wantedState;
                    break;
            }
        }
    }

    @Override
    public void setWantedState(Enum wantedState) {
        this.wantedState = (LiftStates) wantedState;
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
