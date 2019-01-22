package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.subsystems.Intake;

/**
 * Created by MannoMation on 1/11/2019.
 */

@Autonomous
@Disabled
public class IntakeIn extends AutoModeBase {
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void run(AllianceColor allianceColor) {
        Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.ZEROING);
        while (opModeIsActive() && Robot.getIntake().isCurrentStateWantedState()){
            Robot.getIntake().update(timer);
        }
    }
}
