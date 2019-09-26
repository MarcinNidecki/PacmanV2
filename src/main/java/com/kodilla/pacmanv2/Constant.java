package com.kodilla.pacmanv2;

public class Constant {

    public static final int WIDTH = 1520;
    public static final int HEIGHT = 960;
    public static final int TILE_SIZE = 40;
    public static final int SPEED = 4;
    public static final String TITLE = "PacMan v.1.0.0";
    public static final double TARGET_TICK = 45;
    public static boolean BONUS, IS_NEW_ROUND = false;

    public static void setIsNewRound(boolean isNewRound) {
        IS_NEW_ROUND = isNewRound;
    }

    public static void setBONUS(boolean BONUS) {
        Constant.BONUS = BONUS;
    }

    public static final String STOP = "STOP";

}
