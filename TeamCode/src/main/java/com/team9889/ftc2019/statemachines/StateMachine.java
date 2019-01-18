package com.team9889.ftc2019.statemachines;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by joshua9889 on 1/18/2019.
 */
public abstract class StateMachine {
    public abstract void init(boolean auto);

    public abstract void update(ElapsedTime time);

    public abstract void setWantedState(Enum wantedState);

    public abstract Enum getCurrentState();

    public abstract boolean isCurrentEqualWanted();

}
