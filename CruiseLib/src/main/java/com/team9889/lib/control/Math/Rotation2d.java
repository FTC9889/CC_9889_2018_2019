package com.team9889.lib.control.Math;

/**
 * Created by joshua9889 on 7/25/2018.
 */

public class Rotation2d {

    public enum Unit{
        DEGREES, RADIANS
    }

    private double mTheda;
    private double x, y;
    private Unit mUnit;

    public Rotation2d(double theda, Unit unit){
        setTheda(theda, unit);
    }

    public void setTheda(double theda, Unit unit) {
        switch (unit){
            case DEGREES:
                setThedaDegrees(theda);
                break;
            case RADIANS:
                setThedaRadians(theda);
                break;
        }
    }

    private void setThedaRadians(double theda){
        this.mTheda = theda;
        this.mUnit = Unit.RADIANS;

        this.x = Math.cos(mTheda);
        this.y = Math.sin(mTheda);
    }

    private void setThedaDegrees(double theda){
        this.mTheda = theda;
        this.mUnit = Unit.DEGREES;

        double radianMeasue = Math.toDegrees(theda);
        this.x = Math.cos(radianMeasue);
        this.y = Math.sin(radianMeasue);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getTheda(Unit unit){
        if(unit == Unit.RADIANS){
            if (mUnit == Unit.DEGREES)
                return Math.toRadians(mTheda);
            else
                return mTheda;
        } else { // Degrees
            if (mUnit == Unit.RADIANS)
                return Math.toDegrees(mTheda);
            else
                return mTheda;
        }
    }
}
