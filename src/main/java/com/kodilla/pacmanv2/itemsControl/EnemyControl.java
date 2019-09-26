package com.kodilla.pacmanv2.itemsControl;

import com.kodilla.pacmanv2.Constant;
import com.kodilla.pacmanv2.GameInit;
import com.kodilla.pacmanv2.items.Enemy;
import com.kodilla.pacmanv2.items.Player;
import com.kodilla.pacmanv2.pacmanBoard.Music;
import com.kodilla.pacmanv2.pacmanBoard.statistic.PlayerLives;

import java.awt.*;

public class EnemyControl {
    private final Enemy enemy;
    private final Player player;
    private final WallCollision wallCollision;
    private GameInit gameInit;
    private Music music = new Music();

    public EnemyControl(Enemy enemy, Player player, WallCollision wallCollision, GameInit gameInit) {
        this.enemy = enemy;
        this.player = player;
        this.gameInit = gameInit;
        this.wallCollision = wallCollision;
    }

    public void checkIfNeedToUseATeleport() {
        if (enemy.x == 1480 && enemy.y == 400) {
            enemy.x = 0;
            goRight();
        }
        if (enemy.x == 0 && enemy.y == 400) {
            enemy.x = 1480;
            goLeft();
        }
    }

    public void checkIfIntersectWithPlayer(int xx, int yy) {
        Rectangle rectangle = new Rectangle(xx, yy, Constant.TILE_SIZE, Constant.TILE_SIZE);
        if (rectangle.intersects(player.getX(), player.getY(), 40, 40)) {

            // if is bonus send enemy to home
            if (Constant.BONUS) {
                enemy.setItsEye(true);
                music.playEatGhostMusic();
            } else {
                if (PlayerLives.getLives() >= 1) {
                    Player.setIsAlive(false);
                    player.stop();
                    music.playDeathMusic();
                }
                gameInit.getPlayerLives().removeLive();
                player.setHitByEnemy(true);
            }
        }
    }

    public void changeDirection() {

        switch (enemy.getDirection()) {
            case UP:
            case DOWN:
                if (checkIfLeftAndRightAreClean()) {
                    chooseLeftOrRight();
                } else {
                    if (wallCollision.thereIsNoCollisionOnRight(enemy.x, enemy.y)) {
                        checkAndMOveRight();
                    } else {
                        checkAndMoveLeft();
                    }
                }
                break;

            case RIGHT:
            case LEFT:
                if (checkIfUpAndDownAreClean()) {
                    CheckAndMoveDownOrUp();
                } else {
                    if (wallCollision.thereIsNoCollisionOnDown(enemy.x, enemy.y)) {
                        checkAndMoveDown();
                    } else {
                        checkAndMoveUp();
                    }
                }
                break;
        }
    }

    private Enemy.Directions escapeOnDistanceWhenLeftOrRight(int safeDistance) {

        // if enemy is in a center of tile  and can go up and  player is above enemy
        if (isInMiddleOfTileX() && wallCollision.thereIsNoCollisionOnUp(enemy.x, enemy.y) && isEnemyAbovePlayer()) {
            if ((player.getY() - enemy.y) < safeDistance) {
                return Enemy.Directions.UP;
            } else {
                if (wallCollision.thereIsNoCollisionOnDown(enemy.x, enemy.y)) {
                    return Enemy.Directions.DOWN;
                }
            }
            if (isInMiddleOfTileX() && wallCollision.thereIsNoCollisionOnDown(enemy.x, enemy.y) && isEnemyUnderPlayer()) {
                if ((enemy.y - player.getY()) < safeDistance) {
                    return Enemy.Directions.DOWN;

                } else {
                    if (wallCollision.thereIsNoCollisionOnUp(enemy.x, enemy.y)) {
                        return Enemy.Directions.UP;
                    }
                }

            }

        }
        return enemy.getDirection();
    }

    private Enemy.Directions escapeOnDistanceWhenUpOrDown(int safeDistance) {
        //If enemy is escaping down/up and have option to turn on right
        // check  if player is on left  side then escape on right
        if (isInTheMiddleOfTileY() && wallCollision.thereIsNoCollisionOnRight(enemy.x, enemy.y) && isEnemyOnTheRight()) {
            // if distance between player and enemy is not safe then still go right
            if ((enemy.x - player.getX()) < safeDistance) {
                return Enemy.Directions.RIGHT;
            }
        }
        //If enemy is escaping down/up and have option to turn on left
        // check  if player is on right side then escape on left
        if (isInTheMiddleOfTileY() && wallCollision.thereIsNoCollisionOnLeft(enemy.x, enemy.y) && isEnemyOnTheLeft()) {
            // if distance between player and enemy is not safe then still go right
            if ((enemy.x - player.getX()) > safeDistance) {
                return Enemy.Directions.LEFT;
            }
        }
        return enemy.getDirection();
    }

    public Enemy.Directions searchBetterPath() {
        switch (enemy.getDirection()) {
            case UP:
            case DOWN:

                if (!Constant.BONUS) {
                    if (enemy.isBrave()) {
                        if (isEnemyOnTheRight() && isInMiddleOfTileX() && wallCollision.thereIsNoCollisionOnLeft(enemy.x, enemy.y)) {
                            goLeft();
                            return Enemy.Directions.LEFT;
                        }
                        if (isEnemyOnTheLeft() && isInMiddleOfTileX() && wallCollision.thereIsNoCollisionOnRight(enemy.x, enemy.y)) {
                            goRight();
                            return Enemy.Directions.RIGHT;
                        }
                        return enemy.getDirection();
                    } else {
                        escapeOnDistanceWhenUpOrDown(160);
                        break;
                    }
                } else {
                    escapeOnDistanceWhenUpOrDown(200);
                    break;
                }

            case RIGHT:
            case LEFT:
                // if brave then get closer to player
                if (!Constant.BONUS) {
                    if (enemy.isBrave()) {
                        if (isEnemyUnderPlayer() && isInTheMiddleOfTileY() && wallCollision.thereIsNoCollisionOnUp(enemy.x, enemy.y)) {
                            goUp();
                            return Enemy.Directions.UP;
                        }
                        if (isEnemyAbovePlayer() && isInTheMiddleOfTileY() && wallCollision.thereIsNoCollisionOnDown(enemy.x, enemy.y)) {
                            goDown();
                            return Enemy.Directions.DOWN;
                        }
                        break;
                        // if is not brave  then when is close to player run away
                    } else {
                        // is bonus if OFF but enemy is not brave
                        return escapeOnDistanceWhenLeftOrRight(160);
                    }
                } else {
                    return escapeOnDistanceWhenUpOrDown(200);
                }
            default:
                return enemy.getDirection();
        }
        return enemy.getDirection();
    }


    private boolean checkIfUpAndDownAreClean() {
        return wallCollision.thereIsNoCollisionOnDown(enemy.x, enemy.y) && wallCollision.thereIsNoCollisionOnUp(enemy.x, enemy.y);

    }

    public boolean checkIfIsAtHome() {
        return enemy.y <= Constant.TILE_SIZE * 11 && enemy.y > Constant.TILE_SIZE * 8 && enemy.x > Constant.TILE_SIZE * 17 && enemy.x < Constant.TILE_SIZE * 21;
    }

    private void CheckAndMoveDownOrUp() {
        if (isEnemyAbovePlayer()) {
            enemy.setDirections(Enemy.Directions.DOWN);
            goDown();
        } else {
            enemy.setDirections(Enemy.Directions.UP);
            goUp();
        }
    }

    private void checkAndMoveUp() {
        if (wallCollision.thereIsNoCollisionOnUp(enemy.x, enemy.y)) {
            enemy.setDirections(Enemy.Directions.UP);
            goUp();
        }
    }

    private void checkAndMoveDown() {
        if (wallCollision.thereIsNoCollisionOnDown(enemy.x, enemy.y)) {
            enemy.setDirections(Enemy.Directions.DOWN);
            goDown();
        }
    }

    private void checkAndMOveRight() {
        if (wallCollision.thereIsNoCollisionOnRight(enemy.x, enemy.y)) {
            enemy.setDirections(Enemy.Directions.RIGHT);
            goRight();
        }
    }

    private void checkAndMoveLeft() {
        if (wallCollision.thereIsNoCollisionOnLeft(enemy.x, enemy.y)) {
            enemy.setDirections(Enemy.Directions.LEFT);
            goLeft();
        }
    }

    private boolean checkIfLeftAndRightAreClean() {
        return wallCollision.thereIsNoCollisionOnLeft(enemy.x, enemy.y) && wallCollision.thereIsNoCollisionOnRight(enemy.x, enemy.y);
    }


    private void chooseLeftOrRight() {
        if (enemy.x > player.getX()) {
            enemy.setDirections(Enemy.Directions.LEFT);
            goLeft();
        } else {
            enemy.setDirections(Enemy.Directions.RIGHT);
            goRight();
        }
    }

    private boolean isEnemyAbovePlayer() {
        return enemy.y < player.getY();
    }

    private boolean isEnemyUnderPlayer() {
        return enemy.y > player.getY();
    }

    private boolean isInTheMiddleOfTileY() {
        return enemy.y % Constant.TILE_SIZE == 0;
    }

    private boolean isEnemyOnTheLeft() {
        return enemy.x <= player.getX();
    }

    private boolean isEnemyOnTheRight() {
        return enemy.x >= player.getX();
    }

    private boolean isInMiddleOfTileX() {
        return enemy.x % Constant.TILE_SIZE == 0;
    }

    public void goDown() {
        enemy.y += Constant.SPEED;
    }

    public void goUp() {
        enemy.y -= Constant.SPEED;
    }

    public void goRight() {
        enemy.x += Constant.SPEED;
    }

    public void goLeft() {
        enemy.x -= Constant.SPEED;
    }
}