package com.team9889.lib.control.Math;

/**
 * A Pose is the representation of the above view of the robot,
 * relative to it's starting position, (0,0,0).
 *
 * Created by joshua9889 on 7/24/2018.
 */


public class Pose {
    private double mX, mY;
    private Rotation2d mTheda;

    public Pose(double x, double y, Rotation2d theda){
        mX = x;
        mY = y;
        mTheda = theda;
    }

    public Pose(){
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
