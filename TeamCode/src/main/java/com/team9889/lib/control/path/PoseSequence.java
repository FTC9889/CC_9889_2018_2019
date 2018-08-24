package com.team9889.lib.control.path;

import com.team9889.lib.control.math.Pose;
import com.team9889.lib.control.math.Rotation2d;

import java.util.ArrayList;

/**
 * Created by joshua9889 on 7/18/2018.
 **/

public class PoseSequence {
    public ArrayList<Pose> poses = new ArrayList<>();

    public void addPose(Pose newPose){
        poses.add(newPose);
    }

    public Pose getPose(int index){
        return poses.get(index);
    }

    public int numberOfPoses(){
        return poses.size();
    }

    public Rotation2d getAngle(int index){
        return poses.get(index).getRotation2d();
    }
}
