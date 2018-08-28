package com.team9889.lib.control.motion;

public class MotionProfileSegment {
    private double position, velocity, acceleration;

    public MotionProfileSegment(double position, double velocity, double acceleration){
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public MotionProfileSegment(MotionProfileSegment toCopy){
        this.position = toCopy.getPosition();
        this.velocity = toCopy.getVelocity();
        this.acceleration = toCopy.getAcceleration();
    }

    public double getPosition() {
        return position;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getAcceleration() {
        return acceleration;
    }

    @Override
    public String toString() {
        return "Position: " + this.getPosition() +
                "; Velocity: " + this.getVelocity() +
                "; Acceleration: " + this.getAcceleration();
    }
}
