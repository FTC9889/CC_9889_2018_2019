package com.team9889.lib.control.Path;

import com.team9889.lib.control.Math.Pose;
import com.team9889.lib.control.Math.Position2d;
import com.team9889.lib.control.Math.Rotation2d;

import java.util.ArrayList;

/**
 * Created by joshua9889 on 7/18/2018.
 **/

public class PoseSequence {
    public ArrayList<Position2d> poses = new ArrayList<>();

    public void addPose(Position2d newPose){
        poses.add(newPose);
    }

    public Position2d getPose(int index){
        return poses.get(index);
    }

    public int numberOfPoses(){
        return poses.size();
    }

    public Rotation2d getAngle(int index){
        return poses.get(index).getTheda();
    }
}
