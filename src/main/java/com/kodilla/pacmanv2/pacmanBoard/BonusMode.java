package com.kodilla.pacmanv2.pacmanBoard;

import com.kodilla.pacmanv2.Constant;

public class BonusMode {

    private final int bonusTime = 10000;
    private final Constant constant;
    private TimerTaskPaccman timer = new TimerTaskPaccman(bonusTime);

    public  BonusMode (Constant constant) {
        this.constant = constant;
    }

    public void startBonus() {

        constant.setBONUS(true);
        timer.StartTimer();
    }


    public void checkIfBonusIsOn() {

        timer.checkIfTimerIsEnd();
        if (timer.isTimerOFF()) {
            constant.setBONUS(false);
        }
    }
}
