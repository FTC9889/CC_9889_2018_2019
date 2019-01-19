package com.team9889.ftc2019.states;

/**
 * Created by joshua9889 on 1/18/2019.
 */
public enum SuperStructureStates {
    NULL, // When we don't know where anything is

    PARK, // Arms Extended, Intake Grabbing, Lift ScoringHeight
    HANGING,
    GO_TO_HANG_HEIGHT,
    LAND,

    WAIT_FOR_MINERALS, // Arms Ready, Intake Grabbing, Lift Ready
    MANUEL_INTAKING, // Arms Ready, Intake Extended, Lift Ready

    GRAB_MINERALS_AND_SCORE,
    SCORE_GOLDGOLD, SCORE_SILVERSILVER, SCORE_SILVERGOLD, SCORE_GOLDSILVER,

}
