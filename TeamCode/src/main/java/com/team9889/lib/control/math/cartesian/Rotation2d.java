package com.team9889.lib.control.math.cartesian;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

/**
 * Created by joshua9889 on 7/25/2018.
 */

public class Rotation2d {

    private double mTheda;
    private Position mPosition = new Position();
    private AngleUnit mUnit;

    public Rotation2d(){
        setTheda(0, AngleUnit.DEGREES);
    }

    public Rotation2d(double theda, AngleUnit unit){
        setTheda(theda, unit);
    }

    public void setTheda(double theda, AngleUnit unit) {
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
        this.mUnit = AngleUnit.RADIANS;

        this.mPosition.x = Math.cos(mTheda);
        this.mPosition.y = Math.sin(mTheda);
    }

    private void setThedaDegrees(double theda){
        this.mTheda = theda;
        this.mUnit = AngleUnit.DEGREES;

        double radianMeasure = Math.toDegrees(theda);
        this.mPosition.x = Math.cos(radianMeasure);
        this.mPosition.y = Math.sin(radianMeasure);
    }

    public double getX() {
        return mPosition.x;
    }

    public double getY() {
        return mPosition.y;
    }

    public double getTheda(AngleUnit unit){
        if(unit == AngleUnit.RADIANS){
            if (mUnit == AngleUnit.DEGREES)
                return Math.toRadians(mTheda);
            else
                return mTheda;
        } else { // Degrees
            if (mUnit == AngleUnit.RADIANS)
                return Math.toDegrees(mTheda);
            else
                return mTheda;
        }
    }

    public static Rotation2d add(Rotation2d r1, Rotation2d r2){
        return new Rotation2d(r1.getTheda(AngleUnit.DEGREES) + r2.getTheda(AngleUnit.DEGREES), AngleUnit.DEGREES);
    }

    @Override
    public String toString() {
        return String.valueOf(getTheda(AngleUnit.DEGREES)) + " Degrees";
    }
}
