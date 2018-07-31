package com.team9889.lib.control.Controllers;

public abstract class FeedBackController extends Controller {
    @Override
    public double update(double current) {
        return 0;
    }

    public abstract double update(double current, double wanted);
}
