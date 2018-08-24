package com.team9889.lib.loops;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by joshua9889 on 4/5/2018.
 *
 * LoopManager is used to control update/controller loops
 */

public class LoopManager {

    private List<Loop> loops;
    private TimerTask updater;

    private Thread updaterThread;

    public LoopManager(int maxLoops){
        loops = new ArrayList<>(maxLoops);
    }

    public void addLoop(Loop loop){
        loops.add(loop);
    }

    public void start(){
        setup();

        updaterThread.start();
    }

    public void stop(){
        if(!updaterThread.isInterrupted())
            updaterThread.interrupt();
    }

    private void setup(){
        updater = new TimerTask() {
            @Override
            public void run() {
                for (Loop loop: loops) {
                    loop.start(System.nanoTime());
                }

                while (!Thread.interrupted()){
                    for (Loop loop : loops) {
                        loop.loop(System.nanoTime());
                    }
                }

                for (Loop loop: loops) {
                    loop.stop(System.nanoTime());
                }
            }
        };

        updaterThread = new Thread(updater);
    }

}
