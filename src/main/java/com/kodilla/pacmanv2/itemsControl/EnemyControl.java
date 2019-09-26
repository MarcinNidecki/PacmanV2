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
    private Constant constant;
    private GameInit gameInit;
    private final WallCollision wallCollision;
    private Music music = new Music();

    public EnemyControl(Enemy enemy, Player player, Constant constant, WallCollision wallCollision, GameInit gameInit) {
        this.enemy = enemy;
        this.constant = constant;
        this.player = player;
        this.gameInit = gameInit;
        this.wallCollision = wallCollision;
    }


    public void goDown() {
        enemy.y += constant.getSPEED();
    }

    public void goUp() {
        enemy.y -= constant.getSPEED();
    }

    public void goRight() {
        enemy.x += constant.getSPEED();
    }

    public void goLeft() {
        enemy.x -= constant.getSPEED();
    }


    public boolean checkIfIsInHome() {
        return enemy.y <= constant.getTILE_SIZE() * 11 && enemy.y > constant.getTILE_SIZE() * 8 && enemy.x > constant.getTILE_SIZE() * 17 && enemy.x < constant.getTILE_SIZE() * 21;
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
        Rectangle rectangle = new Rectangle(xx, yy, constant.getTILE_SIZE(), constant.getTILE_SIZE());
        if (rectangle.intersects(player.getX(), player.getY(), 40, 40)) {

            // if is bonus send enemy to home
            if (constant.isBONUS()) {
                enemy.setItsEye(true);
                music.playEatGhostMusic();
            } else {
                if (PlayerLives.getLives()>=1) {
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


        //  when enemy have more then 2 way to choose,  check where is player then go closer
        //  when enemy have only one possibilities then just change direction.
        switch (enemy.getDirection()) {
            case UP:
            case DOWN:
                if (wallCollision.thereIsNoCollisionOnLeft(enemy.x, enemy.y) && wallCollision.thereIsNoCollisionOnRight(enemy.x, enemy.y)) {

                    // if you have 2 open direction  check player position then change direction
                    if (enemy.x > player.getX()) {
                        enemy.setDirections(Enemy.Directions.LEFT);
                        goLeft();
                        break;
                    } else {
                        enemy.setDirections(Enemy.Directions.RIGHT);
                        goRight();
                        break;
                    }
                }

                if (wallCollision.thereIsNoCollisionOnRight(enemy.x, enemy.y)) {
                    enemy.setDirections(Enemy.Directions.RIGHT);
                    goRight();
                    break;
                }
                if (wallCollision.thereIsNoCollisionOnLeft(enemy.x, enemy.y)) {
                    enemy.setDirections(Enemy.Directions.LEFT);
                    goLeft();
                    break;
                }

            case RIGHT:
            case LEFT:
                if (wallCollision.thereIsNoCollisionOnDown(enemy.x, enemy.y) && wallCollision.thereIsNoCollisionOnUp(enemy.x, enemy.y)) {
                    if (enemy.y < player.getY()) {
                        enemy.setDirections(Enemy.Directions.DOWN);
                        goDown();
                        break;
                    } else {
                        enemy.setDirections(Enemy.Directions.UP);
                        goUp();
                        break;
                    }

                }
                if (wallCollision.thereIsNoCollisionOnDown(enemy.x, enemy.y)) {
                    enemy.setDirections(Enemy.Directions.DOWN);
                    goDown();
                    break;

                }
                if (wallCollision.thereIsNoCollisionOnUp(enemy.x, enemy.y)) {
                    enemy.setDirections(Enemy.Directions.UP);
                    goUp();
                    break;
                }
        }
    }

    private void EscapeOnDistanceWhenLeftOrRight(int safeDistance) {

        // if enemy is in a center of tile  and can go up and  player is above enemy
        if (enemy.x % constant.getTILE_SIZE() == 0 && wallCollision.thereIsNoCollisionOnUp(enemy.x, enemy.y) && enemy.y <= player.getY()) {
            if ((player.getY() - enemy.y) < safeDistance) {
                enemy.setDirections(Enemy.Directions.UP);
                goUp();
                enemy.setChangeOfDirection(true);
                return;
            } else {
                if (wallCollision.thereIsNoCollisionOnDown(enemy.x, enemy.y)) {
                    enemy.setDirections(Enemy.Directions.DOWN);
                    goDown();
                    enemy.setChangeOfDirection(true);
                    return;
                }

            }
            if (enemy.x % constant.getTILE_SIZE() == 0 && wallCollision.thereIsNoCollisionOnDown(enemy.x, enemy.y) && enemy.y >= player.getY()) {
                if ((enemy.y - player.getY()) < safeDistance) {
                    enemy.setDirections(Enemy.Directions.DOWN);
                    goDown();
                    enemy.setChangeOfDirection(true);

                } else {
                    if (wallCollision.thereIsNoCollisionOnUp(enemy.x, enemy.y)) {
                        enemy.setDirections(Enemy.Directions.UP);
                        goUp();
                        enemy.setChangeOfDirection(true);

                    }
                }

            }

        }
    }

    private void escapeOnDistanceWhenUpOrDown(int safeDistance) {
        //If enemy is escaping down/up and have option to turn on right
        // check  if player is on left  side then escape on right
        if (enemy.y % constant.getTILE_SIZE() == 0 && wallCollision.thereIsNoCollisionOnRight(enemy.x, enemy.y) && enemy.x >= player.getX()) {
            // if distance between player and enemy is not safe then still go right
            if ((enemy.x - player.getX()) < safeDistance) {
                enemy.setDirections(Enemy.Directions.RIGHT);
                goRight();
                enemy.setChangeOfDirection(true);
                return;
                // if distance is safe  then go closer to player
            }


        }
        //If enemy is escaping down/up and have option to turn on left
        // check  if player is on right side then escape on left
        if (enemy.y % constant.getTILE_SIZE() == 0 && wallCollision.thereIsNoCollisionOnLeft(enemy.x, enemy.y) && enemy.x <= player.getX()) {
            // if distance between player and enemy is not safe then still go right
            if ((enemy.x - player.getX()) > safeDistance) {
                enemy.setDirections(Enemy.Directions.LEFT);
                goLeft();
                enemy.setChangeOfDirection(true);
            }

        }
    }

   public boolean trackPlayerAndCheckIfIsBetterToChangeDirectionThenChangeDirection() {
        enemy.setChangeOfDirection(false);
        switch (enemy.getDirection()) {
            case UP:
            case DOWN:

                // if brave and is no bonus time
                if (enemy.isBrave() && !constant.isBONUS()) {

                    if (enemy.x > player.getX() && enemy.x % constant.getTILE_SIZE() == 0 && wallCollision.thereIsNoCollisionOnLeft(enemy.x, enemy.y)) {
                        enemy.setDirections(Enemy.Directions.LEFT);
                        goLeft();
                        enemy.setChangeOfDirection(true);
                        break;
                    }
                    if (enemy.x < player.getX() && enemy.x % constant.getTILE_SIZE() == 0 && wallCollision.thereIsNoCollisionOnRight(enemy.x, enemy.y)) {
                        enemy.setDirections(Enemy.Directions.RIGHT);
                        goRight();
                        enemy.setChangeOfDirection(true);
                        break;
                    }
                    break;


                } else {
                    // if is not brave and bonus is off then go closer and when is close to player run away
                    if (!constant.isBONUS()) {
                        escapeOnDistanceWhenUpOrDown(160);
                        break;
                        // is is bonus then everyone run away
                    } else {
                        escapeOnDistanceWhenUpOrDown(1600);
                    }
                    break;
                }

            case RIGHT:
            case LEFT:
                // if brave then get closer to player
                if (enemy.isBrave() && !constant.isBONUS()) {
                    if (enemy.y > player.getY() && enemy.y % constant.getTILE_SIZE() == 0 && wallCollision.thereIsNoCollisionOnUp(enemy.x, enemy.y)) {
                        enemy.setDirections(Enemy.Directions.UP);
                        goUp();
                        enemy.setChangeOfDirection(true);
                        break;
                    }
                    if (enemy.y < player.getY() && enemy.y % constant.getTILE_SIZE() == 0 && wallCollision.thereIsNoCollisionOnDown(enemy.x, enemy.y)) {
                        enemy.setDirections(Enemy.Directions.DOWN);
                        goDown();

                        enemy.setChangeOfDirection(true);
                        break;
                    }
                    break;

                    // if is not brave  then when is close to player run away
                } else {
                    if (!constant.isBONUS()) {
                        // is bonus if OFF but enemy is not brave
                        EscapeOnDistanceWhenLeftOrRight(160);
                        break;
                        // when bonus is on
                    } else {
                        EscapeOnDistanceWhenLeftOrRight(1600);
                    }


                    break;
                }
                default:
                    enemy.setChangeOfDirection(false);
        }
        return enemy.isChangeOfDirection();
    }

}