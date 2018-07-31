package com.team9889.lib.testCode;

import java.util.Random;

public class TestCode {
    public static void main(String... args){
        double currentHeading = randomHeading();

    }

    public static double randomHeading(){
        Random random = new Random();
        double heading = 10000;

        while (heading >180 || heading < -180) {
            heading = random.nextDouble();
            heading = Math.toDegrees(heading);
        }

        return heading;
    }
}
