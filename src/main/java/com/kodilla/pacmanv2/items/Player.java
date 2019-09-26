package com.kodilla.pacmanv2.items;

import com.kodilla.pacmanv2.Constant;
import com.kodilla.pacmanv2.GameInit;
import com.kodilla.pacmanv2.itemsControl.WallCollision;
import com.kodilla.pacmanv2.pacmanBoard.BonusMode;
import com.kodilla.pacmanv2.pacmanBoard.Music;
import com.kodilla.pacmanv2.pacmanBoard.statistic.PlayerLives;

import java.awt.*;

import static com.kodilla.pacmanv2.pacmanBoard.levelFactory.LevelFactory.maze;

public class Player extends Rectangle {


    private static boolean isAlive= true;
    private GameInit gameInit;
    private static boolean addedToRanking=false;
    private final WallCollision wallCollision;
    private int animationPicture;
    private Music music = new Music();
    private int playerImagePossition;
    private static int timeAnimation = 0;
    private int startingLocationX, startingLocationY;
    private BonusMode bonusMode;
    private int speed = 4;
    private String mainDirection = "STOP", nextDirection = "STOP";
    private boolean HitByEnemy = false;
    private Constant constant;

    public Player(int x, int y, GameInit gameInit, Constant constant, WallCollision wallCollision, BonusMode bonusMode) {
        this.gameInit = gameInit;
        this.constant = constant;
        this.bonusMode = bonusMode;
        this.wallCollision = wallCollision;
        startingLocationY = y;
        startingLocationX = x;
        setBounds(x, y, constant.getTILE_SIZE(), constant.getTILE_SIZE());
    }


    public void tick() {

        bonusMode.checkIfBonusIsOn();

        switch (mainDirection) {
            case "RIGHT": {
                if (isInTheMiddleOfTile(x) && checkIfIsNextDirectionTenChangeDirection()) {
                    checkIfIsNextDirectionTenChangeDirection();
                    break;
                }

                if (mainDirection.equals("RIGHT") && wallCollision.thereIsNoCollisionOnRight(x, y)) {
                    ifIntersectWithDotThenRemoveDotAndAddPoints(x + speed, y);
                    checkIfPlayerIsInTeleport();
                    goRight();
                    playerImagePossition = 0;
                    break;

                } else {
                    setMainDirection("STOP");
                    playerImagePossition = 0;

                    break;
                }
            }
            case "LEFT": {
                if (isInTheMiddleOfTile(x) && checkIfIsNextDirectionTenChangeDirection()) {
                    checkIfIsNextDirectionTenChangeDirection();
                    break;
                }

                if (mainDirection.equals("LEFT") && wallCollision.thereIsNoCollisionOnLeft(x, y)) {
                    ifIntersectWithDotThenRemoveDotAndAddPoints(x - speed, y);
                    checkIfPlayerIsInTeleport();
                    goLeft();
                    playerImagePossition = 1;
                    break;
                } else {
                    setMainDirection("STOP");
                    playerImagePossition = 1;
                    break;
                }
            }
            case "UP": {
                if (isInTheMiddleOfTile(y) && checkIfIsNextDirectionTenChangeDirection()) {
                    checkIfIsNextDirectionTenChangeDirection();
                    break;
                }
                if (mainDirection.equals("UP") && wallCollision.thereIsNoCollisionOnUp(x, y)) {
                    ifIntersectWithDotThenRemoveDotAndAddPoints(x, y - speed);
                    goUp();
                    playerImagePossition = 2;
                    break;
                } else {
                    setMainDirection("STOP");
                    playerImagePossition = 2;

                    break;
                }
            }
            case "DOWN": {
                if (isInTheMiddleOfTile(y) && checkIfIsNextDirectionTenChangeDirection()) {
                    checkIfIsNextDirectionTenChangeDirection();
                    break;
                }

                if (mainDirection.equals("DOWN") && wallCollision.thereIsNoCollisionOnDown(x, y)) {
                    ifIntersectWithDotThenRemoveDotAndAddPoints(x, y + speed);
                    goDown();
                    playerImagePossition = 3;
                    break;
                } else {
                    setMainDirection("STOP");
                    playerImagePossition = 3;
                    break;
                }
            }

            default:
                break;

        }

        checkIfPlayerisAlive();
        playerAnimation();
    }

    private void checkIfPlayerIsInTeleport() {
        if (x == 1480 && y == 400) {
            x = 0;
        } else if (x == 0 && y == 400) {
            x = 1480;
        }
    }

    private boolean isInTheMiddleOfTile(int y) {
        return y % constant.getTILE_SIZE() == 0;
    }

    private void playerAnimation() {

        if(isAlive) {

            if (timeAnimation == 0) {
                animationPicture = 0;
            } else if (timeAnimation == 10) {
                animationPicture = 1;
            } else if (timeAnimation == 20) {
                animationPicture = 2;
            } else if (timeAnimation == 30) {
                animationPicture = 3;
            }
            if (timeAnimation == 40) {
                timeAnimation = 0;
            }
            timeAnimation++;

            //
        } else  {

            if (timeAnimation == 0) {
                playerImagePossition=4;
                animationPicture = 0;
            } else if (timeAnimation == 20) {
                playerImagePossition=4;
                animationPicture = 1;
            } else if (timeAnimation == 40) {
                playerImagePossition=4;
                animationPicture = 2;
            } else if (timeAnimation == 60) {
                playerImagePossition=4;
                animationPicture = 3;
            } else if (timeAnimation ==80) {
                playerImagePossition =5;
                animationPicture=0;
            } else if (timeAnimation ==100) {
                playerImagePossition =5;
                animationPicture=1;
            } else if (timeAnimation==120) {
                playerImagePossition =5;
                animationPicture=2;

            } else if (timeAnimation== 230) {

                animationPicture = 0;
                playerImagePossition =0;
                sendPlayerToStart();

            }

            timeAnimation++;
        }


    }

    private boolean checkIfIsNextDirectionTenChangeDirection() {
        boolean isNextDirection = false;
        switch (nextDirection) {
            case "UP":
                if (wallCollision.thereIsNoCollisionOnUp(x, y)) {

                    mainDirection = "UP";
                    goUp();
                    nextDirection = "STOP";
                    playerImagePossition = 2;
                    isNextDirection = true;
                    break;
                }
                break;
            case "DOWN":
                if (wallCollision.thereIsNoCollisionOnDown(x, y)) {

                    mainDirection = "DOWN";
                    goDown();
                    nextDirection = "STOP";
                    playerImagePossition = 3;
                    isNextDirection = true;
                    break;

                }
                break;

            case "LEFT":
                if (wallCollision.thereIsNoCollisionOnLeft(x, y)) {
                    mainDirection = "LEFT";
                    goLeft();
                    nextDirection = "STOP";
                    playerImagePossition = 1;
                    isNextDirection = true;
                    break;
                }
                break;

            case "RIGHT":
                if (wallCollision.thereIsNoCollisionOnRight(x, y)) {
                    mainDirection = "RIGHT";
                    goRight();
                    nextDirection = "STOP";
                    isNextDirection = true;
                    playerImagePossition = 0;

                    break;
                }
                break;

            default:
                isNextDirection = false;

        }
        return isNextDirection;

    }


    private void ifIntersectWithDotThenRemoveDotAndAddPoints(int x, int y) {


        Rectangle rectangle = new Rectangle(x, y, constant.getTILE_SIZE(), constant.getTILE_SIZE());
        int lineNumber = y / constant.getTILE_SIZE();

        int minLineNumber, maxLinenumber;
        maxLinenumber = lineNumber + 1;
        if (lineNumber <= 0) {
            minLineNumber = 0;
        } else minLineNumber = lineNumber - 1;


        for (lineNumber = minLineNumber; lineNumber < maxLinenumber; lineNumber++) {
            for (int itemNumber = 0; itemNumber < maze.getMaze().get(lineNumber).getLineOfItems().size(); itemNumber++) {
                Items item = maze.getMaze().get(lineNumber).getLineOfItems().get(itemNumber);
                if (item instanceof Dot) {
                    if (rectangle.intersects((Dot) item)) {

                        maze.getMaze().get(lineNumber).getLineOfItems().replace(itemNumber, new Empty(x, y,constant));
                        music.playEatBallMusic();
                        if (((Dot) item).isBigDot()) {
                            gameInit.getScore().addPointForBigDot();
                            bonusMode.startBonus();
                        } else {
                            gameInit.getScore().addPointForSmallDot();
                        }
                    }
                }
            }
        }
    }

    private void goDown() {
        y += speed;
    }

    private void goUp() {
        y -= speed;
    }

    private void goLeft() {
        x -= speed;
    }

    private void goRight() {
        x += speed;
    }

    public String getMainDirection() {
        return mainDirection;
    }

    public void setMainDirection(String mainDirection) {
        this.mainDirection = mainDirection;
    }

    public void setNextDirection(String nextDirection) {
        this.nextDirection = nextDirection;
    }

    public boolean isHitByEnemy() {
         return HitByEnemy;
    }

    public void setHitByEnemy(boolean hitByEnemy) {
        HitByEnemy = hitByEnemy;
    }
    public void stop() {
        mainDirection = "STOP";
        nextDirection = "STOP";
    }
    public static String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        Player.userName = userName;
    }

    private static String userName = "Ad";

    public static boolean isAlive() {
        return isAlive;
    }

    public static void setIsAlive(boolean isAlive) {
        Player.isAlive = isAlive;
    }

    public static boolean isAddedToRanking() {
        return addedToRanking;
    }

    public static void setAddedToRanking(boolean addedToRanking) {
        Player.addedToRanking = addedToRanking;
    }

    public static void setTimeAnimation(int timeAnimation) {
        Player.timeAnimation = timeAnimation;
    }
    public void repaint(Graphics g) {

            g.drawImage(ItemPictures.player[playerImagePossition][animationPicture], x, y, 32, 32, null);
    }

    public void sendPlayerToStart() {
        x = startingLocationX;
        y = startingLocationY;
    }

    public void checkIfPlayerisAlive() {
        if (PlayerLives.getLives()<0) {
            isAlive = false;
            mainDirection = "STOP";
            nextDirection = "STOP";
        }
    }

}
