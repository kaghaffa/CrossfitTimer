package com.example.crossfittimer;

/**
 * Created by Kayvon on 10/3/13.
 */
public class StopWatch {

    private long startTime;
    private long endTime;
    private long pauseTime;
    private boolean isRunning;


    public StopWatch() {
        reset();
    }

    public void start() {
        if (!isRunning) {
            pauseTime = getElapsedTime();
            startTime = System.currentTimeMillis();
            endTime = 0;
            isRunning = true;
        }
    }

    public void stop() {
        if (isRunning) {
            endTime = System.currentTimeMillis();
            isRunning = false;
        }
    }

    public void reset() {
        startTime = 0;
        endTime = 0;
        pauseTime = 0;
        isRunning = false;
    }


    public long getElapsedTime() {
        if (isRunning) {
            return pauseTime + (System.currentTimeMillis() - startTime);
        } else {
            return pauseTime + (endTime - startTime);
        }
    }


    public String getTimeAsString() {
        long time = getElapsedTime();
        long hours = 0;
        long minutes = 0;
        long seconds = 0;
        long tenths = 0;
        long hundredths = 0;

        if (time < 1000) {
            tenths = time / 100;
            hundredths = ( time % 100 ) / 10;
        } else if (time < 60000) {
            seconds = time / 1000;
            time = time % 1000;
            tenths = time / 100;
            hundredths = (time % 100) / 10;
        } else if (time < 3600000) {
            hours = time / 360000;
            time = time % 3600000;
            minutes = time / 60000;
            time = time % 60000;
            seconds = time / 1000;
            time = time % 1000;
            tenths = time / 100;
            hundredths = (time % 100) / 10;
        }

        if ( hours == 0 ) {
            return String.format(formatDigit(minutes) + ":"
                    + formatDigit(seconds) + "."
                    + tenths + hundredths);
        } else {
            return String.format(formatDigit(hours) + ":"
                    + formatDigit(minutes) + ":"
                    + formatDigit(seconds) + "."
                    + tenths + hundredths);
        }
    }

    public String formatDigit(long num) {
        return (num < 10) ? "0" + num : Long.toString(num);
    }


    public long getStartTime() {
        return startTime;
    }


    public long getEndTime() {
        return endTime;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
