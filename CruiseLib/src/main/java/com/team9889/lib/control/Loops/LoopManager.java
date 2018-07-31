package com.team9889.control.loops;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshua9889 on 4/5/2018.
 *
 * LoopManager is used to control update/controller loops
 */

public class LoopManager {

    private List<Loop> loops;

    public LoopManager(int maxLoops){
        loops = new ArrayList<Loop>();
    }

    public void addLoop(Loop loop){
        loops.add(loop);
    }

    public void startLoops(){
        System.out.println("======= Starting Control Loops =======");
        for (Loop loop:loops) {
            loop.run();
        }
    }

    public void stopLoops(){
        System.out.println("======= Stopping Control Loops =======");
        for (Loop loop:loops) {
            loop.interrupt();
        }
    }

}
