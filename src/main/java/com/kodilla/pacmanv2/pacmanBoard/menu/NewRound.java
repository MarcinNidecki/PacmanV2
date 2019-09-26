package com.kodilla.pacmanv2.pacmanBoard.menu;

import com.kodilla.pacmanv2.GameInit;
import com.kodilla.pacmanv2.items.Player;
import com.kodilla.pacmanv2.pacmanBoard.statistic.PlayerLives;

import java.awt.*;

public class NewRound {

    private GameInit gameInit;

    public NewRound (GameInit gameInit) {
        this.gameInit = gameInit;

    }

    private int tick = 0;
    private String countDown;


    public void paint(Graphics g) {
        if (PlayerLives.getLives()>0){
            gameInit.setPause(true);
            if (tick ==0) {
                countDown = "";
            }
            if (tick ==120) {
                countDown = "3";
            } else if (tick ==180) {
                countDown = "3 2";
            } else if (tick==240) {
                countDown = "3 2 1";
            } else if (tick == 300) {
                countDown = "3 2 1 GO";
            }
            tick ++;
            if (tick > 360) {
                countDown = "";
                gameInit.setPause(false);
                gameInit.getConstant().setIS_NEW_ROUND(false);
                Player.setIsAlive(true);
                Player.setTimeAnimation(0);
                tick=0;
            }
            Font stringFont = new Font("Arial", Font.BOLD, 32);
            g.setFont(stringFont);
            g.setColor(Color.RED);
            g.drawString(countDown, 700, 400);
        }

    }
}
