package com.team9889.ftc2019.test.subsystems;

/**
 * Created by MannoMation on 12/19/2018.
 */


public class ScoreingLogic {
    
    public double setArmsPositions;
    public double setDistanceFromLander;
    //    public double setInputDistance;
    public requirements Mineral = requirements.GOLDGOLD;
    public boolean setDistance = false;
    public boolean Claws = true;

    public double setInputDistance = 20;

    public enum requirements{
        STORED, GRAB, PARK, GOLDGOLD, GOLDSILVER, SILVERSILVER, SILVERGOLD
    }

    public void setArmsStates(requirements state) {
        switch (state) {
            case SILVERSILVER:
                setArmsPositions = 10;
                setDistanceFromLander = 10;
                break;

            case SILVERGOLD:
                setArmsPositions = 20;
                setDistanceFromLander = 2;
                break;

            case GOLDSILVER:
                setArmsPositions = 15;
                setDistanceFromLander = 1;
                break;

            case GOLDGOLD:
                setArmsPositions = 17.5;
                setDistanceFromLander = 1;
                break;

            case PARK:
                setArmsPositions = 25;
                break;

            case STORED:
                setArmsPositions = 0;
                break;

            case GRAB:
                setArmsPositions = 1;
                break;
        }
    }

    public void setStateInputs(){
        setArmsStates(Mineral);
    }

    public void canWeScore(){
        if (setDistanceFromLander == setInputDistance){
            setDistance = true;
        }
    }

    public void Score(){
        if (setDistance == true){
            System.out.println(setInputDistance);
            System.out.println(Claws = false);
            System.out.println(Mineral);
            System.out.println(setArmsPositions);
        }
    }


}
