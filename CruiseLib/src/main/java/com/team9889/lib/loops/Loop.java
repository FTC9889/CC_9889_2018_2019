package com.team9889.lib.loops;

/**
 * Created by joshua9889 on 4/5/2018.
 */

public abstract class Loop {

    public abstract void start(double timestamp);

    public abstract void loop(double timestamp);

    public abstract void stop(double timestamp);
}
