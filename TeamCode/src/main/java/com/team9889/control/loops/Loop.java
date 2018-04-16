package com.team9889.control.loops;

/**
 * Created by joshua9889 on 4/5/2018.
 */

public abstract class Loop {
    private Thread looperThread = null;

    public void run(){
        looperThread = new Thread(new Runnable() {
            @Override
            public void run() {
                start();
                while (!Thread.interrupted() && looperThread.isAlive()){
                    Thread.yield();
                    loop();
                }
                stop();
            }
        });

        looperThread.start();
    }

    public void interrupt(){
        if(looperThread!=null && looperThread.isAlive())
            while(looperThread.isAlive())
                looperThread.interrupt();
    }

    public abstract void start();

    public abstract void loop();

    public abstract void stop();
}
