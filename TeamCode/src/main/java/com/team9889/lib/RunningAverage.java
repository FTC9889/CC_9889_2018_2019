package com.team9889.lib;

/**
 * Created by joshua9889 on 4/12/2019.
 */
public class RunningAverage {
    private double[] inputs;
    private int index;
    double sum = 0;

    public RunningAverage(int size) {
        inputs = new double[size];
        index = 0;
    }

    public double calculate(double input) {
        index++;
        if(index == inputs.length) {
            index = 0;
        }
        inputs[index] = input;

        sum = 0;
        for(int i = 0; i < inputs.length; i++) {
            sum += inputs[i];
        }

        return sum / (inputs.length * 1.0);
    }

    public double get() {
        return sum / (inputs.length * 1.0);
    }
}
