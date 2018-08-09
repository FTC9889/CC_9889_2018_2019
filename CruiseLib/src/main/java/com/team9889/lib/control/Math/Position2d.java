package com.team9889.lib.control.Math;

/**
 * Created by joshua9889 on 8/9/2018.
 **/

public class Position2d {
    private double mX, mY;
    private Rotation2d mTheda;

    public Position2d(double x, double y, Rotation2d theda){
        mX = x;
        mY = y;
        mTheda = theda;
    }

    public Position2d(){
        mX = 0.0;
        mY = 0.0;
        mTheda = new Rotation2d(0, Rotation2d.Unit.DEGREES);
    }

    public double getX(){
        return mX;
    }

    public double getY(){
        return mY;
    }

    public double getTheda(Rotation2d.Unit unit){
        return getTheda().getTheda(unit);
    }

    public Rotation2d getTheda() {
        return mTheda;
    }

    public void setX(double x) {
        this.mX = x;
    }

    public void setY(double y){
        this.mY = y;
    }

    public void setTheda(Rotation2d theda){
        mTheda = theda;
    }

    public void setTheda(double theda, Rotation2d.Unit unit){
        mTheda.setTheda(theda, unit);
    }
}
