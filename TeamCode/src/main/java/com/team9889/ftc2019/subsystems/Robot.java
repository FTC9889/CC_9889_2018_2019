package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.team9889.ftc2019.statemachines.ArmsStateMachine;
import com.team9889.ftc2019.statemachines.ExtenderStateMachine;
import com.team9889.ftc2019.statemachines.HopperCoverStateMachine;
import com.team9889.ftc2019.statemachines.HopperGateStateMachine;
import com.team9889.ftc2019.statemachines.IntakeStateMachine;
import com.team9889.ftc2019.statemachines.LiftStateMachine;
import com.team9889.ftc2019.statemachines.RotatorStateMachine;
import com.team9889.ftc2019.statemachines.StateMachine;
import com.team9889.ftc2019.statemachines.SuperStructureStateMachine;
import com.team9889.ftc2019.states.LiftStates;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.List;

import static com.team9889.ftc2019.subsystems.Robot.MineralPositions.NULL;

/**
 * Created by joshua9889 on 3/28/2018.
 */

public class Robot extends Subsystem {

    private Drive mDrive = new Drive();
    private Lift mLift = new Lift();
    private Intake mIntake = new Intake();
    private Camera mCamera = new Camera();
    private Arms mArms = new Arms();
    private List<Subsystem> subsystems = Arrays.asList(
            mDrive, mLift, mIntake, mCamera, mArms // Add more subsystems here as needed
    );

    private SuperStructureStateMachine structureStateMachine = new SuperStructureStateMachine();

    private static Robot mInstance = null;

    public static Robot getInstance() {
        if (mInstance == null)
            mInstance = new Robot();

        return mInstance;
    }

    private ElapsedTime timer = new ElapsedTime();

    /**
     * @param hardwareMap Hardware Map of the OpMode
     * @param autonomous  Are we running autonomous? (Used for gyros and the like)
     */
    @Override
    public void init(HardwareMap hardwareMap, boolean autonomous) {
        for (Subsystem subsystem : subsystems) {
            RobotLog.a("=========== Initialing " + subsystem.toString() + " ===========");
            subsystem.init(hardwareMap, autonomous);
            RobotLog.a("=========== Finished Initialing " + subsystem.toString() + " ===========");
        }

        getSuperStructure().init(autonomous);

        timer.reset();
    }

    @Override
    public void zeroSensors() {
        for (Subsystem subsystem : subsystems) {
            RobotLog.a("=========== Zeroing " + subsystem.toString() + " ===========");
            subsystem.zeroSensors();
            RobotLog.a("=========== Finished Zeroing " + subsystem.toString() + " ===========");
        }
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        for (Subsystem subsystem : subsystems) {
            telemetry.addData(subsystem.toString(), "");
            subsystem.outputToTelemetry(telemetry);
            telemetry.addLine();
        }
    }

    @Override
    public void update(ElapsedTime time) {
        for (Subsystem subsystem : subsystems) {
            subsystem.update(timer);
        }

        getSuperStructure().update(time);
    }

    @Override
    public void test(Telemetry telemetry) {
        for (Subsystem subsystem : subsystems) {
            RobotLog.a("=========== Testing " + subsystem.toString() + " ===========");
            subsystem.test(telemetry);
            RobotLog.a("=========== Finished Testing " + subsystem.toString() + " ===========");
        }
    }

    @Override
    public void stop() {
        for (Subsystem subsystem : subsystems) {

            RobotLog.a("=========== Stopping " + subsystem.toString() + " ===========");
            subsystem.stop();
            RobotLog.a("=========== Finished Stopping " + subsystem.toString() + " ===========");
        }
    }

    public Drive getDrive() {
        return mDrive;
    }

    public Intake getIntake() {
        return mIntake;
    }

    public Lift getLift() {
        return mLift;
    }

    public Camera getCamera() {
        return mCamera;
    }

    public Arms getArms() {
        return mArms;
    }

    public SuperStructureStateMachine getSuperStructure() {

    }
}
