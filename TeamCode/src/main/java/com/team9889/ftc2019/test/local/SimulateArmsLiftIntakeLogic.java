package com.team9889.ftc2019.test.local;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.lib.android.FileWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.team9889.ftc2019.states.LiftStates.DOWN;
import static com.team9889.ftc2019.states.LiftStates.NULL;
import static com.team9889.ftc2019.states.LiftStates.SCOREINGHEIGHT;
import static com.team9889.ftc2019.subsystems.Arms.ArmStates;
import static com.team9889.ftc2019.subsystems.Robot.MineralPositions;

/**
 * Created by joshua9889 on 1/17/2019.
 */
public class SimulateArmsLiftIntakeLogic {

    private static ElapsedTime timer = new ElapsedTime();
    private static com.team9889.lib.android.FileWriter fileWriter;

    public static void main(String[] args) {
        load();

        Robot Robot = new Robot();
        Robot.init(true);

        timer.reset();

        while (timer.seconds() < 7) {
            Robot.update(timer);

            if(timer.seconds()> 1 && timer.seconds() < 2) {
                Robot.setWantedSuperStructure(MineralPositions.GOLDGOLD);
            } else if(timer.seconds() > 4)
                Robot.setWantedSuperStructure(MineralPositions.SILVERGOLD);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println();
        }

        fileWriter.close();
    }

    private static void load() {
        fileWriter = new FileWriter("Simulate.csv");
    }

    private interface Subsystem {
        void init(boolean auto);
        void update(ElapsedTime time);
    }
    
    public static class Robot implements Subsystem {
        private SimulateArmsLiftIntakeLogic.Arms mArms = new SimulateArmsLiftIntakeLogic.Arms();
        private Lift mLift = new Lift();
        private Intake mIntake = new Intake();
        
        private List<Subsystem> subsystems = Arrays.asList(mArms, mLift, mIntake);
        private MineralPositions whichMineral;
        private ElapsedTime clawTimer = new ElapsedTime();

        @Override
        public void init(boolean auto) {
            for (Subsystem subsystem: subsystems) {
                subsystem.init(auto);
            }

            clawTimer.reset();
        }

        @Override
        public void update(ElapsedTime time) {
            for (Subsystem subsystem: subsystems) {
                subsystem.update(time);
            }

            if(whichMineral != null)
                scoringStateMachine(whichMineral);
        }

        public SimulateArmsLiftIntakeLogic.Arms getArms() {
            return mArms;
        }

        public Lift getLift() {
            return mLift;
        }

        public Intake getIntake() {
            return mIntake;
        }

        private int tracker = 0;
        public boolean intakeCruiseControl = true;
        public boolean liftCruiseControl = true;

        public void resetTracker() {
            tracker = 0;
        }

        public void setWantedSuperStructure(MineralPositions mineralPositions) {
            whichMineral = mineralPositions;
        }

        private void scoringStateMachine(MineralPositions state) {
            liftCruiseControl = false;

            String Log = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
            switch (tracker) {
                case 0:
                    Log += "Move Arms Back";
                    break;
                case 1:
                    Log += ("Lift Moving Down");
                    break;
                case 2:
                    Log += ("Claws Grabbing From Hopper");
                    break;
                case 3:
                    Log += ("Moving Lift To Scoring Height");
                    break;
                case 4:
                    Log += ("Move Arms to appropriate Scoring Location");
                    break;
                case 5:
                    Log += ("Wait for both hands to open");
                    break;
                case 6:
                    Log += ("Move Arms to GRABGOLDGOLD");
                    break;
                case 7:
                    Log += ("Move Lift to Ready Position");
                    break;
            }

            Log(Log);

            switch (state) {
                case GOLDGOLD:
                    switch (tracker) {
                        case 0:
                            getArms().setWantedArmState(ArmStates.GRABGOLDGOLD);
                            getArms().setRightClawOpen(true);
                            getArms().setLeftClawOpen(true);
                            if (getArms().isCurrentStateWantedState())
                                tracker++;
                            break;
                        case 1:
                            getLift().setLiftState(LiftStates.DOWN);
                            if (getLift().isCurrentWantedState()) {
                                tracker++;
                                clawTimer.reset();
                            }
                            break;
                        case 2:
                            getArms().setRightClawOpen(false);
                            getArms().setLeftClawOpen(false);
                            if (clawTimer.milliseconds() > 1000)
                                tracker++;
                            break;
                        case 3:
                            getLift().setLiftState(LiftStates.SCOREINGHEIGHT);
                            if (getLift().isCurrentWantedState())
                                tracker++;
                            break;
                        case 4:
                            getArms().setArmsStates(com.team9889.ftc2019.subsystems.Arms.ArmStates.GOLDGOLD);
                            if (getArms().isCurrentStateWantedState()) {
                                tracker++;
                            }
                            break;
                        case 5:
                            if (getArms().bothOpen()) {
                                tracker++;
                            }
                            break;
                        case 6:
                            getArms().setArmsStates(com.team9889.ftc2019.subsystems.Arms.ArmStates.GRABGOLDGOLD);
                            if (getArms().isCurrentStateWantedState())
                                tracker++;
                            break;
                        case 7:
                        getLift().setLiftState(LiftStates.READY);
                        if(getLift().isCurrentWantedState())
                            tracker++;
                            break;
                    }
                    break;

                case SILVERSILVER:
                    switch (tracker) {
                        case 0:
                            getArms().setArmsStates(com.team9889.ftc2019.subsystems.Arms.ArmStates.GRABGOLDGOLD);
                            getArms().setRightClawOpen(true);
                            getArms().setLeftClawOpen(true);
                            if (getArms().isCurrentStateWantedState())
                                tracker++;
                            break;
                        case 1:
                            getLift().setLiftState(LiftStates.DOWN);
                            if (getLift().isCurrentWantedState()) {
                                tracker++;
                                clawTimer.reset();
                            }
                            break;
                        case 2:
                            getArms().setRightClawOpen(false);
                            getArms().setLeftClawOpen(false);
                            if (clawTimer.milliseconds() > 1000)
                                tracker++;
                            break;
                        case 3:
                            getLift().setLiftState(LiftStates.SCOREINGHEIGHT);
                            if (getLift().isCurrentWantedState())
                                tracker++;
                            break;
                        case 4:
                            getArms().setArmsStates(com.team9889.ftc2019.subsystems.Arms.ArmStates.SILVERSILVER);
                            if (getArms().isCurrentStateWantedState()) {
                                tracker++;
                            }
                            break;
                        case 5:
                            if (getArms().bothOpen()) {
                                tracker++;
                            }
                            break;
                        case 6:
                            getArms().setArmsStates(com.team9889.ftc2019.subsystems.Arms.ArmStates.GRABGOLDGOLD);
                            if (getArms().isCurrentStateWantedState())
                                tracker++;
                            break;
                        case 7:
                        getLift().setLiftState(LiftStates.READY);
                        if(getLift().isCurrentWantedState())
                            tracker++;
                            break;
                    }
                    break;

                case SILVERGOLD:
                    switch (tracker) {
                        case 0:
                            getArms().setArmsStates(com.team9889.ftc2019.subsystems.Arms.ArmStates.GRABGOLDGOLD);
                            getArms().setRightClawOpen(true);
                            getArms().setLeftClawOpen(true);
                            if (getArms().isCurrentStateWantedState())
                                tracker++;
                            break;
                        case 1:
                            getLift().setLiftState(LiftStates.DOWN);
                            if (getLift().isCurrentWantedState()) {
                                tracker++;
                                clawTimer.reset();
                            }
                            break;
                        case 2:
                            getArms().setRightClawOpen(false);
                            getArms().setLeftClawOpen(false);
                            if (clawTimer.milliseconds() > 1000)
                                tracker++;
                            break;
                        case 3:
                            getLift().setLiftState(LiftStates.SCOREINGHEIGHT);
                            if (getLift().isCurrentWantedState())
                                tracker++;
                            break;
                        case 4:
                            getArms().setArmsStates(com.team9889.ftc2019.subsystems.Arms.ArmStates.SILVERGOLD);
                            if (getArms().isCurrentStateWantedState()) {
                                tracker++;
                            }
                            break;
                        case 5:
                            if (getArms().bothOpen()) {
                                tracker++;
                            }
                            break;
                        case 6:
                            getArms().setArmsStates(com.team9889.ftc2019.subsystems.Arms.ArmStates.GRABGOLDGOLD);
                            if (getArms().isCurrentStateWantedState())
                                tracker++;
                            break;
                        case 7:
                        getLift().setLiftState(LiftStates.READY);
                        if(getLift().isCurrentWantedState())
                            tracker++;
                            break;
                        case 8:
                            break;

                    }

                case GOLDSILVER:
                    switch (tracker) {
                        case 0:
                            getArms().setWantedArmState(ArmStates.GRABGOLDSILVER);
                            getArms().setRightClawOpen(true);
                            getArms().setLeftClawOpen(true);
                            if (getArms().isCurrentStateWantedState())
                                tracker++;
                            break;
                        case 1:
                            getLift().setLiftState(LiftStates.DOWN);
                            if (getLift().isCurrentWantedState()) {
                                tracker++;
                                clawTimer.reset();
                            }
                            break;
                        case 2:
                            getArms().setRightClawOpen(false);
                            getArms().setLeftClawOpen(false);
                            if (clawTimer.milliseconds() > 1000)
                                tracker++;
                            break;
                        case 3:
                            getLift().setLiftState(LiftStates.SCOREINGHEIGHT);
                            if (getLift().isCurrentWantedState())
                                tracker++;
                            break;
                        case 4:
                            getArms().setArmsStates(com.team9889.ftc2019.subsystems.Arms.ArmStates.SILVERGOLD);
                            if (getArms().isCurrentStateWantedState()) {
                                tracker++;
                            }
                            break;
                        case 5:
                            if (getArms().bothOpen()) {
                                tracker++;
                            }
                            break;
                        case 6:
                            getArms().setArmsStates(com.team9889.ftc2019.subsystems.Arms.ArmStates.GRABGOLDGOLD);
                            if (getArms().isCurrentStateWantedState())
                                tracker++;
                            break;
                        case 7:
                        getLift().setLiftState(LiftStates.READY);
                        if(getLift().isCurrentWantedState())
                            tracker++;
                            break;
                        case 8:
                            break;

                    }
                    break;

            }
            liftCruiseControl = true;
        }
    }

    private static class Arms implements Subsystem {

        private ArmStates currentArmsState = ArmStates.NULL;
        private ArmStates wantedArmsState = ArmStates.NULL;
        private boolean first = true;
        private double startTime = 0;

        @Override
        public void init(boolean auto) {
            first = true;
            currentArmsState = ArmStates.NULL;

            if(auto)
                setWantedArmState(ArmStates.STORED);
            else
                setWantedArmState(ArmStates.GRABGOLDGOLD);
        }

        @Override
        public void update(ElapsedTime time) {
            String log = this.getClass().getSimpleName() + "/update| " +
                    " | Current Arms State: "  + currentArmsState.toString() +
                    " | Wanted Arms State: " + wantedArmsState.toString();
            if(currentArmsState != wantedArmsState){
                switch (wantedArmsState) {
                    case STORED:
                        setLeftArm(0, .131);
                        setRightArm(.998, .844);
                        first = true;

                        currentArmsState = ArmStates.STORED;
                        break;
                    case PARK:
                        setLeftArm(0, .131);
                        setRightArm(0.269, 0.42);
                        first = true;

                        currentArmsState = ArmStates.PARK;
                        break;
                    case GOLDGOLD:

                        if (first) {
                            startTime = time.milliseconds();
                            first = false;
                            setRightArm(0.02, 0.541);

                        } else {
                            if (time.milliseconds() - startTime > 100) {
                                setLeftArm(0.589, 0.813);
                                currentArmsState = ArmStates.GOLDGOLD;
                                first = true;

                                log += " | Left Arm Moving To Final Position";
                            } else {
                                log += " | Right Arm Moving To Position";
                            }
                        }
                        break;
                    case SILVERSILVER:
                        setLeftArm(0.733, 0.63);
                        setRightArm(0.269, 0.42);

                        if (first) {
                            startTime = time.milliseconds();
                            first = false;
                            log += " | Arms Moving To Final Position";
                        } else {
                            if (time.milliseconds() - startTime > 100) {
                                currentArmsState = ArmStates.SILVERSILVER;
                                first = true;
                            }
                        }
                        break;
                    case SILVERGOLD:
                        setLeftArm(0.733, 0.542);
                        setRightArm(0.259, 0.183);

                        if (first) {
                            startTime = time.milliseconds();
                            first = false;
                            log += " | Arms State Moving To Final Position";
                        } else {
                            if (time.milliseconds() - startTime > 100) {
                                currentArmsState = ArmStates.SILVERGOLD;
                                first = true;
                            }
                        }
                        break;
                    case GRABGOLDGOLD:
                        if (currentArmsState == ArmStates.GRABGOLDSILVER) {
                            if (first) {
                                startTime = time.milliseconds();
                                first = false;
                                setLeftArm(0.218, 0.039);
                                log += " | Left Arm Moving To Mid Position";

                            } else {
                                if (time.milliseconds() - startTime > 70
                                        && time.milliseconds() - startTime < 100) {
                                    setRightArm(.943, .88);
                                    log += " | Right Arm Moving To Final Position";
                                } else if (time.milliseconds() - startTime > 100
                                        && time.milliseconds() - startTime > 150) {
                                    setLeftArm(.097, .125);
                                    log += " | Left Arm Moving To Final Position";
                                } else if (time.milliseconds() - startTime > 150) {
                                    currentArmsState = ArmStates.GRABGOLDGOLD;
                                    first = true;
                                }
                            }
                        } else {
                            if (first) {
                                startTime = time.milliseconds();
                                first = false;
                                setLeftArm(.097, .125);
                                log = log + " | Left Arm Moving To Position";
                            } else {
                                if (time.milliseconds() - startTime > 200) {
                                    setRightArm(.943, .88);
                                    currentArmsState = ArmStates.GRABGOLDGOLD;
                                    first = true;
                                    log = log + " | Right Arm Moving To Position";
                                } else {
                                    log = log + " | Left Arm Moving To Position";
                                }
                            }
                        }
                        break;

                    case GRABGOLDSILVER:
                        if (currentArmsState == ArmStates.GRABGOLDGOLD) {
                            if (first) {
                                startTime = time.milliseconds();
                                first = false;
                                setRightArm(0.847, 0.962);

                                log += " | Right Arm Moving To Mid Position";
                            } else {
                                if (time.milliseconds() - startTime > 300
                                        && time.milliseconds() - startTime < 500) {
                                    setLeftArm(0.096, 0.185);
                                    log += " | Left Arm Moving To Final Position";
                                } else if (time.milliseconds() - startTime > 500
                                        && time.milliseconds() - startTime < 850) {
                                    setRightArm(0.949, 0.943);
                                    log += " | Right Arm Moving To Final Position";
                                } else if (time.milliseconds() - startTime > 900) {
                                    currentArmsState = ArmStates.GRABGOLDSILVER;
                                    first = true;
                                }
                            }
                        } else {
                            if (first) {
                                startTime = time.milliseconds();
                                first = false;
                                setLeftArm(0.096, 0.185);
                                log += " | Left Arm Moving To Final Position";
                            } else {
                                if (time.milliseconds() - startTime > 200 && time.milliseconds() - startTime < 400) {
                                    setRightArm(0.949, 0.943);
                                    log += " | Right Arm Moving To Final Position";
                                } else if (time.milliseconds() - startTime > 400) {
                                    currentArmsState = ArmStates.GRABGOLDSILVER;
                                    first = true;
                                }

                            }
                        }
                        break;
                } // End of Switch

            } else { // Current == Wanted

            }

            Log(log);
        }

        private void setRightArm(double v, double v1) {
        }

        private void setLeftArm(double i, double v) {

        }

        public void setWantedArmState(ArmStates state) {
            wantedArmsState = state;

            if(wantedArmsState == ArmStates.GRABSILVERSILVER || wantedArmsState == ArmStates.GRABSILVERGOLD)
                wantedArmsState = ArmStates.GOLDGOLD;
        }

        public void setRightClawOpen(boolean b) {

        }

        public void setLeftClawOpen(boolean b) {
        }

        public boolean isCurrentStateWantedState() {
            return currentArmsState == wantedArmsState;
        }

        public void setArmsStates(ArmStates armState) {
            wantedArmsState = armState;
        }

        public boolean bothOpen() {
            Random rand = new Random(20);
            return rand.nextBoolean();
        }
    }

    private static class Lift implements Subsystem {

        private LiftStates currentState = NULL;
        private LiftStates wantedState = NULL;

        @Override
        public void init(boolean auto) {
            if(auto) {
                currentState = NULL;
                wantedState = DOWN;
            }
        }

        @Override
        public void update(ElapsedTime time) {
            String log = this.getClass().getSimpleName() + "/update| " +
                    " | Current Lift State: " + currentState.toString() +
                    " | Wanted Lift State: " + wantedState.toString();

            if (currentState != wantedState){
                switch (wantedState) {
                    case DOWN:
                        if (getLowerLimitPressed()){
                            setLiftPower(0);
                            currentState = LiftStates.DOWN;
                        } else {
                            setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                            setLiftPower(-.7);

                        }
                        break;

                    case HOOKHEIGHT:
                        setLiftPosition(12);

                        if(inPosition())
                            currentState = wantedState;
                        break;

                    case SCOREINGHEIGHT:
                        if (getUpperLimitPressed()){
                            setLiftPower(0);
                            currentState = SCOREINGHEIGHT;
                        } else {
                            setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                            setLiftPower(.7);
                        }
                        break;

                    case READY:
                        setLiftPosition(10);

                        if(inPosition())
                            currentState = wantedState;
                        break;
                }
            }

            log += " | inPosition: " + inPosition();

            Log(log);
        }

        private void setRunMode(DcMotor.RunMode runMode) {
            Log("Lift Change Runmode to " + runMode.toString());
        }

        private boolean inPosition() {
            Random rand = new Random(2000);
            return rand.nextBoolean();
        }

        private void setLiftPower(double v) {
            Log("Lift Power Set: " + v);
        }

        private void setLiftPosition(double d) {
            Log("Lift Position Set: " + d + "in");
        }

        private boolean getLowerLimitPressed() {
            Random rand = new Random(200);
            return rand.nextBoolean();
        }

        private boolean getUpperLimitPressed() {
            Random rand = new Random(20);
            return rand.nextBoolean();
        }

        public void setLiftState(LiftStates wanted) {
            wantedState = wanted;
        }

        public boolean isCurrentWantedState() {
            return currentState == wantedState;
        }
    }

    private static class Intake implements Subsystem {

        @Override
        public void init(boolean auto) {

        }

        @Override
        public void update(ElapsedTime time) {

        }

        public MineralPositions getMineralPositions() {
            return MineralPositions.GOLDGOLD;
        }
    }

    protected static void Log(Object object) {
        double time = ((int)(timer.seconds() * 100000));
        time = time/100000;
        Object output = time + "| " + object;

        System.out.println(output);
        fileWriter.write(output);
    }

    public static int getIntFromIn() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String value = null;

        try {
            value = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Integer.parseInt(value);
    }

    public static boolean getBooleanFromIn() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String value = null;

        try {
            value = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Boolean.parseBoolean(value);
    }
}
