package com.kodilla.pacmanv2.items;

import com.kodilla.pacmanv2.Constant;
import com.kodilla.pacmanv2.GameInit;
import com.kodilla.pacmanv2.itemsControl.EnemyControl;
import com.kodilla.pacmanv2.itemsControl.WallCollision;
import com.kodilla.pacmanv2.pacmanBoard.levelFactory.LevelFactory;
import com.kodilla.pacmanv2.pacmanBoard.statistic.PlayerLives;

import java.awt.*;

public class Enemy extends Rectangle {

    private final EnemyControl enemyControl;
    private Constant constant;
    private GameInit gameInit;
    private final WallCollision wallCollision;
    private int startingLocationX, startingLocationY, tick = 0, animationPicture;
    private boolean brave;
    private boolean changeOfDirection;
    private boolean itsEye = false;
    private String colour;
    private Directions direction = Directions.DOWN;
    private LevelFactory levelFactory;

    public Enemy(int x, int y, boolean brave, String colour, Player player, Constant constant, WallCollision wallCollisio, LevelFactory levelFactory, GameInit gameInit) {
        this.enemyControl = new EnemyControl(this, player, constant,wallCollisio,gameInit);
        this.wallCollision = new WallCollision(constant);
        this.brave = brave;
        this.colour = colour;
        this.constant = constant;
        this.levelFactory = levelFactory;
        startingLocationY = y;
        startingLocationX = x;
        setBounds(x, y, constant.getTILE_SIZE(), constant.getTILE_SIZE());
    }

    public void enemyTick() {

        // TURN ON animation enemy when bonus ON
        if (constant.isBONUS()) {
            animationPicture = 1;
        } else {
            animationPicture = 0;
        }

        enemyControl.checkIfIntersectWithPlayer(x, y);
        enemyControl.checkIfNeedToUseATeleport();

        if (enemyControl.checkIfIsInHome()) {
            levelFactory.openDoor();
            if (colour.equals("RED") || colour.equals("BLUE")) {
                itsEye = false;
                waitThenGoOutside(3);
            } else {
                waitThenGoOutside(8);
                itsEye = false;
            }

        } else {
            levelFactory.closeDoor();
        }

        goToHomeWhenIsEye();
        if(PlayerLives.getLives()==0) {
            direction= Directions.STOP;
        }

        switch (direction) {
            case DOWN:

                if (wallCollision.thereIsNoCollisionOnDown(x, y)) {
                    if (enemyControl.trackPlayerAndCheckIfIsBetterToChangeDirectionThenChangeDirection()) {
                        enemyControl.trackPlayerAndCheckIfIsBetterToChangeDirectionThenChangeDirection();
                        break;
                    }
                    enemyControl.goDown();
                    tick++;

                    break;
                } else {
                    enemyControl.changeDirection();
                    tick = 0;
                    break;
                }
            case UP:

                if (wallCollision.thereIsNoCollisionOnUp(x, y)) {
                    if (enemyControl.trackPlayerAndCheckIfIsBetterToChangeDirectionThenChangeDirection()) {
                        enemyControl.trackPlayerAndCheckIfIsBetterToChangeDirectionThenChangeDirection();
                        break;
                    }
                    enemyControl.goUp();
                    tick++;
                    break;
                } else {
                    enemyControl.changeDirection();
                    tick = 0;
                    break;
                }
            case LEFT:

                if (wallCollision.thereIsNoCollisionOnLeft(x, y)) {
                    if (enemyControl.trackPlayerAndCheckIfIsBetterToChangeDirectionThenChangeDirection()) {
                        enemyControl.trackPlayerAndCheckIfIsBetterToChangeDirectionThenChangeDirection();
                        break;
                    }
                    enemyControl.goLeft();
                    tick++;

                    break;
                } else {
                    enemyControl.changeDirection();
                    tick = 0;
                    break;
                }
            case RIGHT:
                if (wallCollision.thereIsNoCollisionOnRight(x, y)) {
                    if (enemyControl.trackPlayerAndCheckIfIsBetterToChangeDirectionThenChangeDirection()) {
                        enemyControl.trackPlayerAndCheckIfIsBetterToChangeDirectionThenChangeDirection();
                        break;
                    }
                    enemyControl.goRight();
                    tick++;

                    break;
                } else {
                    enemyControl.changeDirection();
                    tick = 0;
                    break;
                }
            default:
                direction = Directions.DOWN;
                break;
        }

    }

    private void goToHomeWhenIsEye() {

        if (itsEye) {
            if (x > 600 && x < 880 && y > 310 && y < 520) {
                x = startingLocationX;
                y = startingLocationY;
            }
            direction = Directions.STOP;
            animationPicture = 2;
            if (y > startingLocationY) {
                enemyControl.goUp();
            } else if (y < startingLocationY) {
                enemyControl.goDown();
            }
            if (x > startingLocationX) {
                enemyControl.goLeft();
            } else if (x < startingLocationX) {
                enemyControl.goRight();
            }
        }
    }

    public void waitThenGoOutside(int timeInSeconds) {
        double time = timeInSeconds * constant.getTARGET_TICK();
        if (wallCollision.thereIsNoCollisionOnUp(x, y)) {
            // when fps is set to 60  then 1 second is 60 ticks
            setDirections(Enemy.Directions.STOP);
            if (tick > time) {
                setDirections(Directions.UP);
                enemyControl.goUp();

            }
            tick++;
        }
    }

    public boolean isBrave() {
        return brave;
    }

    public void setItsEye(boolean itsEye) {
        this.itsEye = itsEye;
    }

    public boolean isChangeOfDirection() {
        return changeOfDirection;
    }

    public void setChangeOfDirection(boolean changeOfDirection) {
        this.changeOfDirection = changeOfDirection;
    }

    public Directions getDirection() {
        return direction;
    }

    public void setDirections(Directions direction) {
        this.direction = direction;
    }

    public int getStartingLocationX() {
        return startingLocationX;
    }

    public int getStartingLocationY() {
        return startingLocationY;
    }

    public void paintEnemyBlue(Graphics g) {
        g.drawImage(ItemPictures.enemyBlue[animationPicture], x, y, 32, 32, null);
    }

    public void paintEnemyRed(Graphics g) {
        g.drawImage(ItemPictures.enemyRed[animationPicture], x, y, 32, 32, null);
    }

    public void paintEnemyPurple(Graphics g) {
        g.drawImage(ItemPictures.enemyPurple[animationPicture], x, y, 32, 32, null);
    }

    public void paintEnemyGreen(Graphics g) {
        g.drawImage(ItemPictures.enemyGreen[animationPicture], x, y, 32, 32, null);
    }


    public void setTick(int tick) {
        this.tick = tick;
    }

    public enum Directions {
        DOWN,
        UP,
        LEFT,
        RIGHT,
        STOP
    }
}
