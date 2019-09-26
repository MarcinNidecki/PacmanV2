package com.kodilla.pacmanv2.pacmanBoard;

public class TimerTaskPaccman {

    private double timer;
    private boolean timerON = false;
    private boolean timerStarted = false;
    private int time;

    public TimerTaskPaccman(int time) {
        this.time = time;
    }

    public boolean isTimerOFF() {
        return !timerON;
    }

    public void StartTimer() {
        // POWER ON
        if (!timerON) {
            timerON = true;
        }

        // set current time to timer  and mark timerStarted TRUE
        if (!timerStarted) {
            timer = System.currentTimeMillis();
            timerStarted = true;
        }
    }

    public boolean checkIfTimerIsEnd() {
        if (System.currentTimeMillis() - timer >= time) {
            timerON = false;
            timerStarted = false;

return true;
        }  return false;
    }
}
