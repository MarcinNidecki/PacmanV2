package com.kodilla.pacmanv2.pacmanBoard.statistic;

import java.awt.*;

public class ScoreCounter {

    private int score = 0;

    public void resetScore() {

        this.score = 0;
    }

    public void addPointForSmallDot() {
        int pointForSmallDot = 10;
        this.score = this.score + pointForSmallDot;
    }

    public void addPointForBigDot() {
        int pointForBigDot = 100;
        this.score = this.score + pointForBigDot;
    }


    public void paint(Graphics g) {

        Font stringFont = new Font("Courier", Font.BOLD, 18);
        String scoreString = String.valueOf(score);
        g.setFont(stringFont);
        g.setColor(Color.YELLOW);
        g.drawString("Score: " + scoreString, 1400, 920);
    }
}
