package com.team9889.ftc2019.test.local;

public class ScoringSimulator {

    //Game Related
    // Timing
    static final int Autonomous = 30;
    static final int Teleop = 90;
    static final int Endgame = 30;

    // Robot Scoring
    static final int Landing = 30;
    static final int Claiming = 15;
    static final int Parking = 10;
    static final int Sampling = 25;
    static final int Latching = 50;
    static final int RobotInCrater = 15;
    static final int RobotCompletelyInCrater = 25;

    // Mineral Scoring
    static final int MineralInDepot = 2;
    static final int GoldInGoldCargoHold = 5;
    static final int SilverinSilverCargoHold = 5;
    static final int NumberOfGold = 60;
    static final int NumberOfSilver = 90;

    // Robot Related
    // Robot Auton Timing
    static final double timeToLand = 2;
    static final double timeToClaim = 10;
    static final double timeToSample = 2;
    static final double timeToPark = 3;

    static final double ips = 3 * 12;
    static final double distance = Math.sqrt(25*25*2);

    static final double timeTraveling = 2 * distance / (ips * 0.8);
    static final double lineUpTime = 2;
    static final double intakeTime = 2;

    // Robot Teleop Timing
    static double timePerCycle = timeTraveling + lineUpTime + intakeTime;

    // Robot Teleop Timing
    static double hangTime = 1.6;

    // How much a robot is slowed down each cycle
    static double defenceFactor = 0;

    public static void main(String... args){
        double autonomousTimeLeftWithoutMinerals = (Autonomous - timeToLand - timeToSample - timeToClaim - timeToPark);

        int autoPointsWithMinerals = (
                (int)mineralPoints(autonomousTimeLeftWithoutMinerals, timePerCycle, 0, 2 * SilverinSilverCargoHold)
                        + Landing
                        + Claiming
                        + Parking
                        + Sampling);

        int teleopPoints = (int)mineralPoints(Teleop, timePerCycle, defenceFactor, 2 * SilverinSilverCargoHold);

        int endgamePoints = (int)(mineralPoints((Endgame - hangTime), timePerCycle, 1, 2 * SilverinSilverCargoHold) + Latching);

        print("--Auton--");
        print("Time left autonomous w/o minerals: " + autonomousTimeLeftWithoutMinerals);
        print("Number of Cycles by robot in auto: " + autonomousTimeLeftWithoutMinerals/timePerCycle);
        print("Mineral Points: " + (int)mineralPoints(autonomousTimeLeftWithoutMinerals, timePerCycle, 0, 2 * SilverinSilverCargoHold));
        print("Score with minerals: " + autoPointsWithMinerals);

        print("\n--Teleop--");
        print("Teleop Points: " + teleopPoints);
        print("Points per second: " + (double)teleopPoints/(double)Teleop);
        print("Seconds per Cycle: " + timePerCycle + "\n");

        print(((3*NumberOfGold/2) * 5) + ((15 + (NumberOfSilver/2)) * 5));

        print("\n--Endgame--");
        print("Endgame Points: " + endgamePoints + "\n");

        int finalScore = (autoPointsWithMinerals + teleopPoints + endgamePoints);
        print("Final Score (Single Robot): " + finalScore);
        print("Final Score (Double Robot): " + (2*finalScore));
    }

    public static double mineralPoints(double time, double timePerCycle, double defenceFactor, double pointsPerCycle){
        double cycles = Math.floor(time / (timePerCycle + defenceFactor));
        double points = cycles * pointsPerCycle;

        return points;
    }

    public static void print(Object object){
        System.out.println(object.toString());
    }
}
