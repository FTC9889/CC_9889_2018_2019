package com.team9889.lib.control;

import java.util.ArrayList;

/**
 * Created by joshua9889 on 4/3/2018.
 */

public abstract class Profile {
    public double max_v, max_a, distance, t, timeStep;
    public ArrayList<Double> output;

    public abstract void calculate();
}
