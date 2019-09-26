package com.kodilla.pacmanv2.pacmanBoard;

import com.kodilla.pacmanv2.Constant;

public class BonusMode {

    private final int bonusTime = 10000;
    private TimerTaskPaccman timer = new TimerTaskPaccman(bonusTime);


    public void startBonus() {

        Constant.setBONUS(true);
        timer.StartTimer();
    }

    public void checkIfBonusIsOn() {

        timer.checkIfTimerIsEnd();
        if (timer.isTimerOFF()) {
            Constant.setBONUS(false);
        }
    }
}
