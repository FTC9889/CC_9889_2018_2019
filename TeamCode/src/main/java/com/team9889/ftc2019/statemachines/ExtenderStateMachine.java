package com.team9889.ftc2019.statemachines;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.states.ExtenderStates;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Robot;

import static com.team9889.ftc2019.states.ExtenderStates.EXTENDING;
import static com.team9889.ftc2019.states.ExtenderStates.GRABBING;
import static com.team9889.ftc2019.states.ExtenderStates.INTAKING;
import static com.team9889.ftc2019.states.ExtenderStates.NULL;
import static com.team9889.ftc2019.states.ExtenderStates.ZEROING;

/**
 * Created by joshua9889 on 1/18/2019.
 */
public class ExtenderStateMachine extends StateMachine {

    public boolean operaterControl = false;
    private Intake mIntake;
    private ExtenderStates currentState = NULL;
    private ExtenderStates wantedState = NULL;

    public ExtenderStateMachine(Intake intake) {
        mIntake = intake;
    }

    @Override
    public void init(boolean auto) {

    }

    @Override
    public void update(ElapsedTime time) {
        if (currentState != wantedState)
            switch (wantedState) {
                case ZEROING:
                    if (mIntake.zeroingLimitSwitch()) {
                        mIntake.setIntakeExtenderPower(0);
                        mIntake.zeroSensors();

                        operaterControl = true;
                        currentState = ZEROING;
                    } else {
                        operaterControl = false;
                        mIntake.setIntakeExtenderPower(-.5);
                    }
                    break;
                case GRABBING:
                    if (currentState == ZEROING) {
                        if (mIntake.grabbingLimitSwitch()) {
                            mIntake.setIntakeExtenderPower(0);
                            currentState = GRABBING;
                            operaterControl = true;
                        } else {
                            Robot.getInstance().intakeCruiseControl = false;
                            mIntake.setIntakeExtenderPower(.3);
                        }
                    } else {
                        if (mIntake.grabbingLimitSwitch()) {
                            mIntake.setIntakeExtenderPower(0);
                            currentState = GRABBING;
                            operaterControl = true;
                        } else {
                            operaterControl = false;
                            mIntake.setIntakeExtenderPower(-0.5);
                        }
                    }
                    break;
                case INTAKING:
                    currentState = INTAKING;
                    break;
                case EXTENDING:
                    currentState = EXTENDING;
                    break;
                case NULL:
                    currentState = NULL;
                    break;
            }
    }

    @Override
    public void setWantedState(Enum wantedState) {
        this.wantedState = (ExtenderStates) wantedState;
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
