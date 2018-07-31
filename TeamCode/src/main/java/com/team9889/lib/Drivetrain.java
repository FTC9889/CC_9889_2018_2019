package com.team9889.lib;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.team9889.lib.control.Math.Pose;
import com.team9889.lib.control.Math.Rotation2d;

/**
 * @author Joshua
 */

public abstract class HolonomicDrivetrain {
    // Current Position on the Cartesian Place
    public Pose currentPose = new Pose(0,0,new Rotation2d(0, Rotation2d.Unit.DEGREES));

    protected DcMotorEx[] motors;

    public void Setup()
}
