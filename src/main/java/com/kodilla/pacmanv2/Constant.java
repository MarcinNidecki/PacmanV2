package com.kodilla.pacmanv2;

public class Constant {

    private final int WIDTH = 1520;
    private final int HEIGHT = 960;
    private final int TILE_SIZE = 40;
    private final int SPEED = 4;
    private final String TITLE = "PacMan v.1.0.0";
    private final double TARGET_TICK = 45;
    private boolean BONUS, IS_NEW_ROUND = false;
    private final String STOP = "STOP";

    public String getSTOP() {
        return STOP;
    }



    public double getTARGET_TICK() {
        return TARGET_TICK;
    }

    public boolean isBONUS() {
        return BONUS;
    }

    public boolean isIS_NEW_ROUND() {
        return IS_NEW_ROUND;
    }

    public void setBONUS(boolean BONUS) {
        this.BONUS = BONUS;
    }

    public void setIS_NEW_ROUND(boolean IS_NEW_ROUND) {
        this.IS_NEW_ROUND = IS_NEW_ROUND;
    }

    int getWIDTH() {
        return WIDTH;
    }

    int getHEIGHT() {
        return HEIGHT;
    }

    public int getTILE_SIZE() {
        return TILE_SIZE;
    }

    public  int getSPEED() {
        return SPEED;
    }

    String getTITLE() {
        return TITLE;
    }


}
