package com.team9889.lib.loops;

/**
 * Created by joshua9889 on 4/5/2018.
 */

public interface Loop {

    void start(double timestamp);

    void loop(double timestamp);

    void stop(double timestamp);
}
