package com.team9889.lib.control.controllers;

import com.team9889.lib.CruiseLib;

/**
 * Created by joshua9889 on 3/29/2018.
 *
 * Simple Bang-Bang Controller
 */

public class BangBang extends FeedBackController{

    public BangBang(double wantedValue, double lowerValue, double upperValue){
        this.wantedValue = wantedValue;
        this.lowerValue = lowerValue;
        this.upperValue = upperValue;
        this.tolerance =0;
    }

    public BangBang(double wantedValue, double tolerance, double lowerValue, double upperValue){
        this.wantedValue = wantedValue;
        this.lowerValue = lowerValue;
        this.upperValue = upperValue;
        this.tolerance = tolerance;
    }

    private double wantedValue;
    private double tolerance;
    private double lowerValue, upperValue;

    @Override
    public double update(double current, double wanted) {
        if(CruiseLib.isBetween(current, wanted- tolerance, wanted+ tolerance))
            return wantedValue;
        else if(current<wantedValue)
            return upperValue;
        else if(current>wantedValue)
            return lowerValue;
        else
            return 0;
    }
}
