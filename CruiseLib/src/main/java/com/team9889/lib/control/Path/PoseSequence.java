package com.team9889.lib.control.Path;

import com.team9889.lib.control.Math.Pose;
import com.team9889.lib.control.Math.Rotation2d;

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
        return poses.get(index).getTheda();
    }
}
