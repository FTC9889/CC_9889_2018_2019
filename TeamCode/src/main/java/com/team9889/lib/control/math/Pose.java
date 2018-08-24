package com.team9889.lib.control.math;

/**
 * Created by joshua9889 on 7/24/2018.
 */


public class Pose {
    Rotation2d rotation2d = new Rotation2d();
    Vector2d vector2D = new Vector2d();

    public Pose(){}

    public Pose(Vector2d vector2d, Rotation2d rotation2d){
        this.vector2D = vector2d;
        this.rotation2d = rotation2d;
    }

    public Vector2d getVector2D() {
        return vector2D;
    }

    public Rotation2d getRotation2d() {
        return rotation2d;
    }

    public void setVector2D(Vector2d vector2D) {
        this.vector2D = vector2D;
    }

    public void setRotation2d(Rotation2d rotation2d) {
        this.rotation2d = rotation2d;
    }
}
