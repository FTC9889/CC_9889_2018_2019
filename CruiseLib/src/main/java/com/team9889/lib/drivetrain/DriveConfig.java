package com.team9889.lib.drivetrain;

public class DriveConfig {
    enum  GyroType {
        MR_Gyro_X,
        MR_Gyro_Y,
        MR_Gyro_Z,
        REV_IMU_X,
        REV_IMU_Y,
        REV_IMU_Z,
    }

    public GyroType gyro_type;

    public double dt;
    public double wheelbase_diameter;
    public double wheel_diameter;
    public double gear_reduction;
    public double max_vel;
}
