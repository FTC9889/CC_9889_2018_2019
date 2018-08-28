package com.team9889.ftc2019.auto.actions;

/**
 * Created by joshua9889 on 8/26/2018.
 */

public abstract class Action {

    public abstract void setup(String args);

    public double[] getNumbersFromString(String args){
        String[] numbers = args.split(",");
        double[] output = new double[numbers.length];
        for (int i = 0; i < numbers.length; i++)
            output[i] = Double.parseDouble(numbers[i]);

        return output;
    }

    /**
     * Run code once when the action is started, for set up
     */
    public abstract void start();

    /**
     * Called by runAction in AutoModeBase iteratively until isFinished returns true. Iterative logic lives in this
     * method
     */
    public abstract void update();

    /**
     * Returns whether or not the code has finished execution. When implementing this interface, this method is used by
     * the runAction method every cycle to know when to stop running the action
     *
     * @return boolean
     */
    public abstract boolean isFinished();

    /**
     * Run code once when the action finishes, usually for clean up
     */
    public abstract void done();
}
