package com.team9889.lib.testCode;

import com.team9889.lib.control.MotionProfiling.TrapezoidalMotionProfile;

public class TestTrapezoidProfile {
    public static void main(String... args){
//        TriangleMotionProfile profile = new TriangleMotionProfile();
        TrapezoidalMotionProfile profile = new TrapezoidalMotionProfile();
        profile.calculate(5, 3, 2);

        for (int i = 0; i < 2000; i++) {
            double step = 1000.0;

//            System.out.println(profile.getOutput(i/step)[0] + "\t" +
//                    profile.getOutput(i/step)[1] + "\t" +
//                    profile.getOutput(i/step)[1]);
        }
    }
}
