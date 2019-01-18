package com.team9889.ftc2019.statemachines;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.states.ArmStates;
import com.team9889.ftc2019.states.ExtenderStates;
import com.team9889.ftc2019.states.HopperCoverStates;
import com.team9889.ftc2019.states.HopperGateStates;
import com.team9889.ftc2019.states.IntakeStates;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.states.RotatorStates;
import com.team9889.ftc2019.states.SuperStructureStates;
import com.team9889.ftc2019.subsystems.Robot;

import java.util.Arrays;
import java.util.List;

import static com.team9889.ftc2019.states.SuperStructureStates.NULL;
import static com.team9889.ftc2019.states.SuperStructureStates.WAIT_FOR_MINERALS;

/**
 * Created by joshua9889 on 1/18/2019.
 */
public class SuperStructureStateMachine extends StateMachine {
    private LiftStateMachine mLiftStateMachine = new LiftStateMachine(Robot.getInstance().getLift());
    private RotatorStateMachine mRotatorStateMachine = new RotatorStateMachine(Robot.getInstance().getIntake());
    private ArmsStateMachine mArmsStateMachine = new ArmsStateMachine(Robot.getInstance().getArms());
    private ExtenderStateMachine mExtenderStateMachine = new ExtenderStateMachine(Robot.getInstance().getIntake());
    private HopperCoverStateMachine mHopperCoverStateMachine = new HopperCoverStateMachine(Robot.getInstance().getIntake());
    private HopperGateStateMachine mHopperGateStateMachine = new HopperGateStateMachine(Robot.getInstance().getIntake());
    private IntakeStateMachine mIntakeStateMachine = new IntakeStateMachine(Robot.getInstance().getIntake());
    private List<StateMachine> stateMachines = Arrays.asList(mLiftStateMachine, mRotatorStateMachine,
            mArmsStateMachine, mExtenderStateMachine, mHopperCoverStateMachine, mHopperGateStateMachine,
            mIntakeStateMachine);

    private SuperStructureStates currentState = NULL;
    private SuperStructureStates wantedState = NULL;

    private int tracker = 0;
    private boolean liftCruiseControl = true;
    private boolean intakeCruiseControl = true;
    private ElapsedTime clawTimer = new ElapsedTime();

    public void resetTracker() {
        tracker = 0;
    }


    @Override
    public void init(boolean auto) {
        for (StateMachine stateMachine : stateMachines) {
            stateMachine.init(auto);
        }
    }

    @Override
    public void update(ElapsedTime time) {
        switch (wantedState) {
            case SCORE_GOLDGOLD:
                liftCruiseControl = false;
                switch (tracker) {
                    case 0:
                        mArmsStateMachine.setWantedState(ArmStates.GRABGOLDGOLD);
                        if (mArmsStateMachine.isCurrentEqualWanted())
                            tracker++;
                        break;
                    case 1:
                        mLiftStateMachine.setWantedState(LiftStates.DOWN);
                        if (mLiftStateMachine.isCurrentEqualWanted()) {
                            tracker++;
                            clawTimer.reset();
                        }
                        break;
                    case 2:
                        Robot.getInstance().getArms().setRightClawOpen(false);
                        Robot.getInstance().getArms().setLeftClawOpen(false);
                        if (clawTimer.milliseconds() > 1000)
                            tracker++;
                        break;
                    case 3:
                        mLiftStateMachine.setWantedState(LiftStates.SCOREINGHEIGHT);
                        if (mLiftStateMachine.isCurrentEqualWanted())
                            tracker++;
                        break;
                    case 4:
                        mArmsStateMachine.setWantedState(ArmStates.GOLDGOLD);
                        if (mArmsStateMachine.isCurrentEqualWanted()) {
                            tracker++;
                        }
                        break;
                    case 5:
                        if (Robot.getInstance().getArms().bothOpen()) {
                            tracker++;
                        }
                        break;
                    case 6:
                        mArmsStateMachine.setWantedState(ArmStates.GRABGOLDGOLD);
                        if (mArmsStateMachine.isCurrentEqualWanted())
                            tracker++;
                        break;
                    case 7:
                        setWantedState(WAIT_FOR_MINERALS);
                        tracker++;
                        break;
                }
                break;

            case SCORE_SILVERSILVER:
                liftCruiseControl = false;
                switch (tracker) {
                    case 0:
                        mArmsStateMachine.setWantedState(ArmStates.GRABGOLDGOLD);
                        if (mArmsStateMachine.isCurrentEqualWanted())
                            tracker++;
                        break;
                    case 1:
                        mLiftStateMachine.setWantedState(LiftStates.DOWN);
                        if (mLiftStateMachine.isCurrentEqualWanted()) {
                            tracker++;
                            clawTimer.reset();
                        }
                        break;
                    case 2:
                        Robot.getInstance().getArms().setRightClawOpen(false);
                        Robot.getInstance().getArms().setLeftClawOpen(false);
                        if (clawTimer.milliseconds() > 1000)
                            tracker++;
                        break;
                    case 3:
                        mLiftStateMachine.setWantedState(LiftStates.SCOREINGHEIGHT);
                        if (mLiftStateMachine.isCurrentEqualWanted())
                            tracker++;
                        break;
                    case 4:
                        mArmsStateMachine.setWantedState(ArmStates.SILVERSILVER);
                        if (mArmsStateMachine.isCurrentEqualWanted()) {
                            tracker++;
                        }
                        break;
                    case 5:
                        if (Robot.getInstance().getArms().bothOpen()) {
                            tracker++;
                        }
                        break;
                    case 6:
                        mArmsStateMachine.setWantedState(ArmStates.GRABGOLDGOLD);
                        if (mArmsStateMachine.isCurrentEqualWanted())
                            tracker++;
                        break;
                    case 7:
                        setWantedState(WAIT_FOR_MINERALS);
                        tracker++;
                        break;
                }
                break;

            case SCORE_SILVERGOLD:
                liftCruiseControl = false;
                switch (tracker) {
                    case 0:
                        mArmsStateMachine.setWantedState(ArmStates.GRABGOLDGOLD);
                        if (mArmsStateMachine.isCurrentEqualWanted())
                            tracker++;
                        break;
                    case 1:
                        mLiftStateMachine.setWantedState(LiftStates.DOWN);
                        if (mLiftStateMachine.isCurrentEqualWanted()) {
                            tracker++;
                            clawTimer.reset();
                        }
                        break;
                    case 2:
                        Robot.getInstance().getArms().setRightClawOpen(false);
                        Robot.getInstance().getArms().setLeftClawOpen(false);
                        if (clawTimer.milliseconds() > 1000)
                            tracker++;
                        break;
                    case 3:
                        mLiftStateMachine.setWantedState(LiftStates.SCOREINGHEIGHT);
                        if (mLiftStateMachine.isCurrentEqualWanted())
                            tracker++;
                        break;
                    case 4:
                        mArmsStateMachine.setWantedState(ArmStates.SILVERGOLD);
                        if (mArmsStateMachine.isCurrentEqualWanted()) {
                            tracker++;
                        }
                        break;
                    case 5:
                        if (Robot.getInstance().getArms().bothOpen()) {
                            tracker++;
                        }
                        break;
                    case 6:
                        mArmsStateMachine.setWantedState(ArmStates.GRABGOLDGOLD);
                        if (mArmsStateMachine.isCurrentEqualWanted())
                            tracker++;
                        break;
                    case 7:
                        setWantedState(WAIT_FOR_MINERALS);
                        tracker++;
                        break;
                    case 8:
                        break;

                }

            case SCORE_GOLDSILVER:
                liftCruiseControl = false;
                switch (tracker) {
                    case 0:
                        mArmsStateMachine.setWantedState(ArmStates.GRABGOLDSILVER);
                        if (mArmsStateMachine.isCurrentEqualWanted())
                            tracker++;
                        break;
                    case 1:
                        mLiftStateMachine.setWantedState(LiftStates.DOWN);
                        if (mLiftStateMachine.isCurrentEqualWanted()) {
                            tracker++;
                            clawTimer.reset();
                        }
                        break;
                    case 2:
                        Robot.getInstance().getArms().setRightClawOpen(false);
                        Robot.getInstance().getArms().setLeftClawOpen(false);
                        if (clawTimer.milliseconds() > 1000)
                            tracker++;
                        break;
                    case 3:
                        mLiftStateMachine.setWantedState(LiftStates.SCOREINGHEIGHT);
                        if (mLiftStateMachine.isCurrentEqualWanted())
                            tracker++;
                        break;
                    case 4:
                        mArmsStateMachine.setWantedState(ArmStates.SILVERGOLD);
                        if (mArmsStateMachine.isCurrentEqualWanted())
                            tracker++;
                        break;
                    case 5:
                        if (Robot.getInstance().getArms().bothOpen())
                            tracker++;
                        break;
                    case 6:
                        mArmsStateMachine.setWantedState(ArmStates.GRABGOLDGOLD);
                        if (mArmsStateMachine.isCurrentEqualWanted())
                            tracker++;
                        break;
                    case 7:
                        setWantedState(WAIT_FOR_MINERALS);
                        tracker++;
                        break;
                    case 8:
                        break;

                }
                break;

            case PARK:
                mLiftStateMachine.setWantedState(LiftStates.SCOREINGHEIGHT);
                mRotatorStateMachine.setWantedState(RotatorStates.UP);
                mHopperCoverStateMachine.setWantedState(HopperCoverStates.CLOSED);
                mHopperGateStateMachine.setWantedState(HopperGateStates.UP);

                if(mLiftStateMachine.isCurrentEqualWanted())
                    mExtenderStateMachine.setWantedState(ExtenderStates.GRABBING);
                break;
            case LAND:
                mLiftStateMachine.setWantedState(LiftStates.HOOKHEIGHT);
                mArmsStateMachine.setWantedState(ArmStates.STORED);

                mExtenderStateMachine.setWantedState(ExtenderStates.ZEROING);
                mRotatorStateMachine.setWantedState(RotatorStates.UP);

                mHopperCoverStateMachine.setWantedState(HopperCoverStates.CLOSED);
                mHopperGateStateMachine.setWantedState(HopperGateStates.UP);

                break;
            case WAIT_FOR_MINERALS:
                if(mLiftStateMachine.getCurrentState() != LiftStates.READY ||
                        mExtenderStateMachine.getCurrentState() != ExtenderStates.GRABBING) {
                    liftCruiseControl = false;
                    intakeCruiseControl = false;
                    mLiftStateMachine.setWantedState(LiftStates.READY);

                    mRotatorStateMachine.setWantedState(RotatorStates.UP);
                    mExtenderStateMachine.setWantedState(ExtenderStates.GRABBING);
                    mHopperCoverStateMachine.setWantedState(HopperCoverStates.OPEN);
                    mHopperGateStateMachine.setWantedState(HopperGateStates.DOWN);

                } else {
                    liftCruiseControl = true;
                    intakeCruiseControl = true;
                }
                break;
            case NULL:
                liftCruiseControl = true;
                intakeCruiseControl = true;
                break;
            case MANUEL_INTAKING:
                intakeCruiseControl = true;
                mHopperGateStateMachine.setWantedState(HopperGateStates.UP);
                mHopperCoverStateMachine.setWantedState(HopperCoverStates.CLOSED);
                break;
        }

        for (StateMachine stateMachine : stateMachines) {
            stateMachine.update(time);
        }
    }

    @Override
    public void setWantedState(Enum wantedState) {
        this.wantedState = (SuperStructureStates) wantedState;
    }

    @Override
    public Enum getCurrentState() {
        return currentState;
    }

    @Override
    public boolean isCurrentEqualWanted() {
        return wantedState == currentState;
    }
}
