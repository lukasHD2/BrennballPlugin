package de.lukashd2.brennball.utils;

import java.util.concurrent.TimeUnit;

public class Timer {

    public static void sleep(int delay, TimeUnit timeUnit){
        try {
            timeUnit.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
