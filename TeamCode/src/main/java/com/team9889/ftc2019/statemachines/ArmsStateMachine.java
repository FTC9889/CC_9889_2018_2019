package com.team9889.ftc2019.statemachines;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.states.ArmStates;
import com.team9889.ftc2019.subsystems.Arms;

import static com.team9889.ftc2019.states.ArmStates.GOLDGOLD;
import static com.team9889.ftc2019.states.ArmStates.GRABGOLDGOLD;
import static com.team9889.ftc2019.states.ArmStates.GRABGOLDSILVER;
import static com.team9889.ftc2019.states.ArmStates.PARK;
import static com.team9889.ftc2019.states.ArmStates.SILVERGOLD;
import static com.team9889.ftc2019.states.ArmStates.SILVERSILVER;
import static com.team9889.ftc2019.states.ArmStates.STORED;

/**
 * Created by joshua9889 on 1/18/2019.
 */
public class ArmsStateMachine extends StateMachine {

    private Arms mArms;
    private ArmStates currentState = ArmStates.NULL;
    private ArmStates wantedState = STORED;
    private double startTime;
    private boolean first = true;
    public ArmsStateMachine(Arms arms) {
        this.mArms = arms;
    }

    @Override
    public void init(boolean auto) {

    }

    @Override
    public void update(ElapsedTime time) {
        if (currentState != wantedState) {
            switch (wantedState) {
                case STORED:
                    mArms.setLeftArm(0, .131);
                    mArms.setRightArm(.998, .844);
                    first = true;

                    currentState = STORED;
                    break;
                case PARK:
                    mArms.setLeftArm(0, .131);
                    mArms.setRightArm(0.269, 0.42);
                    first = true;

                    currentState = PARK;
                    break;
                case GOLDGOLD:
                    mArms.setRightArm(0.02, 0.541);

                    if (first) {
                        startTime = time.milliseconds();
                        first = false;
                    } else {
                        if (time.milliseconds() - startTime > 100) {
                            mArms.setLeftArm(0.589, 0.813);
                            currentState = GOLDGOLD;
                            first = true;
                        }
                    }
                    break;
                case SILVERSILVER:
                    mArms.setLeftArm(0.733, 0.63);
                    mArms.setRightArm(0.269, 0.42);

                    if (first) {
                        startTime = time.milliseconds();
                        first = false;
                    } else {
                        if (time.milliseconds() - startTime > 100) {
                            currentState = SILVERSILVER;
                            first = true;
                        }
                    }
                    break;
                case SILVERGOLD:
                    mArms.setLeftArm(0.733, 0.542);
                    mArms.setRightArm(0.259, 0.183);

                    if (first) {
                        startTime = time.milliseconds();
                        first = false;

                    } else {
                        if (time.milliseconds() - startTime > 100) {
                            currentState = SILVERGOLD;
                            first = true;
                        }
                    }
                    break;
                case GRABGOLDGOLD:
                    if (currentState == GRABGOLDSILVER) {
                        if (first) {
                            mArms.setRightClawOpen(true);
                            mArms.setLeftClawOpen(true);

                            startTime = time.milliseconds();
                            first = false;
                            mArms.setLeftArm(0.218, 0.039);
                        } else {
                            if (time.milliseconds() - startTime > 70
                                    && time.milliseconds() - startTime < 100) {
                                mArms.setRightArm(.943, .88);
                            } else if (time.milliseconds() - startTime > 100
                                    && time.milliseconds() - startTime > 150) {
                                mArms.setLeftArm(.097, .125);
                            } else if (time.milliseconds() - startTime > 150) {
                                currentState = GRABGOLDGOLD;
                                first = true;
                            }
                        }
                    } else {
                        if (first) {
                            mArms.setRightClawOpen(true);
                            mArms.setLeftClawOpen(true);

                            startTime = time.milliseconds();
                            first = false;
                            mArms.setLeftArm(.097, .125);

                        } else {
                            if (time.milliseconds() - startTime > 200 &&
                                    time.milliseconds() - startTime < 400) {
                                mArms.setRightArm(.943, .88);
                            } else if (time.milliseconds() - startTime > 400) {
                                mArms.setRightArm(.943, .88);
                                currentState = GRABGOLDGOLD;
                                first = true;
                            }
                        }
                    }
                    break;

                case GRABGOLDSILVER:
                    if (currentState == GRABGOLDGOLD) {
                        if (first) {
                            mArms.setRightClawOpen(true);
                            mArms.setLeftClawOpen(true);

                            startTime = time.milliseconds();
                            first = false;
                            mArms.setRightArm(0.847, 0.962);
                        } else {
                            if (time.milliseconds() - startTime > 300
                                    && time.milliseconds() - startTime < 500) {
                                mArms.setLeftArm(0.096, 0.185);
                            } else if (time.milliseconds() - startTime > 500
                                    && time.milliseconds() - startTime < 850) {
                                mArms.setRightArm(0.949, 0.943);
                            } else if (time.milliseconds() - startTime > 900) {
                                currentState = GRABGOLDSILVER;
                                first = true;
                            }
                        }
                    } else {
                        if (first) {
                            mArms.setRightClawOpen(true);
                            mArms.setLeftClawOpen(true);

                            startTime = time.milliseconds();
                            first = false;
                            mArms.setLeftArm(0.096, 0.185);

                        } else {
                            if (time.milliseconds() - startTime > 200 && time.milliseconds() - startTime < 400) {
                                mArms.setRightArm(0.949, 0.943);
                            } else if (time.milliseconds() - startTime > 400) {
                                currentState = GRABGOLDSILVER;
                                first = true;
                            }

                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void setWantedState(Enum wantedState) {
        this.wantedState = (ArmStates) wantedState;

        if(wantedState == ArmStates.GRABSILVERSILVER || wantedState == ArmStates.GRABSILVERGOLD)
            this.wantedState = ArmStates.GRABGOLDGOLD;
    }

    @Override
    public Enum getCurrentState() {
        return currentState;
    }

    @Override
    public boolean isCurrentEqualWanted() {
        return wantedState == currentState;
    }
}
