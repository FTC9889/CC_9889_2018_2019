package com.team9889.lib.control;

import com.team9889.lib.CruiseLib;

/**
 * Created by joshua9889 on 3/29/2018.
 *
 * Simple Bang-Bang Controller
 */

public class BangBangControl {

    private double wantedValue;
    private double tolerence;
    private double lowerValue, upperValue;

    public BangBangControl(double wantedValue, double lowerValue, double upperValue){
        this.wantedValue = wantedValue;
        this.lowerValue = lowerValue;
        this.upperValue = upperValue;
        this.tolerence=0;
    }

    public BangBangControl(double wantedValue, double tolerence, double lowerValue, double upperValue){
        this.wantedValue = wantedValue;
        this.lowerValue = lowerValue;
        this.upperValue = upperValue;
        this.tolerence=tolerence;
    }

    public double getOutput(double currentValue){
        if(CruiseLib.isBetween(currentValue, wantedValue-tolerence, wantedValue+tolerence))
            return wantedValue;
        else if(currentValue<wantedValue)
            return upperValue;
        else if(currentValue>wantedValue)
            return lowerValue;
        else
            return 0;
    }
}
