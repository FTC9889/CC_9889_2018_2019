package com.team9889.ftc2019.auto.actions;

import com.qualcomm.ftccommon.SoundPlayer;

public class ThatsOneSmallStepPart1 extends Action{

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
            SoundPlayer.getInstance().startPlaying();
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void done() {

    }
}