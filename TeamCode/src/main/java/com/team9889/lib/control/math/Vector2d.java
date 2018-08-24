package com.team9889.lib.control.math;

/**
 * Created by joshua9889 on 8/9/2018.
 **/

public class Vector2d {

    // X and Y components
    private double mX, mY;

    public Vector2d(){
        setX(0);
        setY(0);
    }

    public Vector2d(double x, double y){
        setX(x);
        setY(y);
    }

    public void set(Vector2d vector2d){
        setX(vector2d.mX);
        setY(vector2d.mY);
    }

    public double getX(){
        return mX;
    }

    public double getY(){
        return mY;
    }

    public void setX(double x) {
        this.mX = x;
    }

    public void setY(double y){
        this.mY = y;
    }

    public Vector2d add(Vector2d pos){
        return new Vector2d(this.mX+pos.getX(), this.mY+pos.getY());
    }

    public static Vector2d add(Vector2d v1, Vector2d v2){
        return new Vector2d(v1.getX()+v2.getY(), v1.getY()+v2.getY());
    }

    public Vector2d subtract(Vector2d pos){
        return subtract(this, pos);
    }

    public static Vector2d subtract(Vector2d v1, Vector2d v2){
        return new Vector2d(v1.getX()-v2.getX(), v1.getY()-v2.getY());
    }

    public Vector2d multiple(double scaler){
        return new Vector2d(this.getX() * scaler, this.getY() * scaler);
    }

    public Vector2d multiple(Vector2d pos){
        return new Vector2d(mX*pos.getX(), mY*pos.getY());
    }

    public Vector2d divide(double scaler){
        return multiple(1.0/scaler);
    }

    public Vector2d divide(Vector2d pos){
        return new Vector2d(mX / pos.getX(), mY / pos.getY());
    }

    public void limit(double max) {
        if (mag() > max) {
            normalize();
            this.set(multiple(max));
        }
    }

    /**
     * Normalize the vector to length 1 (make it a unit vector)
     */
    public void normalize() {
        double m = mag();
        if (m != 0 && m != 1) {
            setX(divide(m).mX);
            setY(divide(m).mY);
        }
    }

    public static double distance(Vector2d pos1, Vector2d pos2){
        double dx = pos1.getX() - pos2.getX();
        double dy = pos1.getY() - pos2.getY();
        return (float) Math.sqrt(dx*dx + dy*dy);
    }

    public double distance(Vector2d pos){
        return distance(this, pos);
    }

    public double dot(Vector2d pos1, Vector2d pos2){
        return (pos1.getX() * pos2.getX()) + (pos1.getY() * pos2.getY());
    }

    public double dot(Vector2d v){
        return dot(this, v);
    }

    public Vector2d copy(){
        return new Vector2d(getX(), getY());
    }

    public double mag(){
        double x2 = getX()*getX();
        double y2 = getY()*getY();
        return Math.sqrt(x2+y2);
    }
}
