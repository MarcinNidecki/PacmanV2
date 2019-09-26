package com.kodilla.pacmanv2;

import com.kodilla.pacmanv2.items.Enemy;
import com.kodilla.pacmanv2.items.ItemPictures;
import com.kodilla.pacmanv2.items.Player;
import com.kodilla.pacmanv2.itemsControl.WallCollision;
import com.kodilla.pacmanv2.pacmanBoard.BonusMode;
import com.kodilla.pacmanv2.pacmanBoard.Music;
import com.kodilla.pacmanv2.pacmanBoard.TimerTaskPaccman;
import com.kodilla.pacmanv2.items.BackgroundImageLevel1;
import com.kodilla.pacmanv2.pacmanBoard.levelFactory.LevelFactory;
import com.kodilla.pacmanv2.pacmanBoard.levelFactory.Maze;
import com.kodilla.pacmanv2.pacmanBoard.menu.GameMenu;
import com.kodilla.pacmanv2.pacmanBoard.menu.NewRound;
import com.kodilla.pacmanv2.pacmanBoard.menu.RankingMenu;
import com.kodilla.pacmanv2.pacmanBoard.menu.Winner;
import com.kodilla.pacmanv2.pacmanBoard.statistic.PlayerLives;
import com.kodilla.pacmanv2.pacmanBoard.statistic.Ranking;
import com.kodilla.pacmanv2.pacmanBoard.statistic.ScoreCounter;

public class GameInit {



    private boolean isPause;



    private boolean isRunning;
    private Player player;
    private Winner winner;
    private PlayerLives playerLives;
    private ScoreCounter score;
    private NewRound newRound;
    private LevelFactory level;
    private Enemy enemyBlue, enemyRed, enemyGreen, enemyPurple;
    private BackgroundImageLevel1 background = new BackgroundImageLevel1();
    private Music music;
    private TimerTaskPaccman welcomeTimerMusic;
    private GameMenu gameMenu;
    private Constant constant = new Constant();
    private Ranking ranking;
    private RankingMenu rankingMenu;


    GameInit() {


        ItemPictures itemPicture = new ItemPictures();
        level = new LevelFactory(constant);
        WallCollision wallCollision = new WallCollision(constant);
        BonusMode bonusMode = new BonusMode(constant);
        player = new Player(constant.getTILE_SIZE(), constant.getTILE_SIZE() * 10,this,constant, wallCollision, bonusMode);
        enemyBlue = new Enemy(constant.getTILE_SIZE() * 19, constant.getTILE_SIZE() * 10, false, "BLUE",player,constant, wallCollision,level,this);
        enemyRed = new Enemy(constant.getTILE_SIZE() * 18, constant.getTILE_SIZE() * 10, true, "RED",player,constant, wallCollision,level,this);
        enemyPurple = new Enemy(constant.getTILE_SIZE() * 18, constant.getTILE_SIZE() * 11, true, "PURPLE",player,constant, wallCollision,level,this);
        enemyGreen = new Enemy(constant.getTILE_SIZE() * 19, constant.getTILE_SIZE() * 11, false, "GREEN",player,constant, wallCollision,level,this);
        score = new ScoreCounter();
        TimerTaskPaccman timerMusic = new TimerTaskPaccman(413);
        welcomeTimerMusic = new TimerTaskPaccman(4216);
        playerLives = new PlayerLives();
        music = new Music();
        Maze maze = new Maze();
        newRound = new NewRound(this);
        winner = new Winner();
        ranking = new Ranking();
        rankingMenu = new RankingMenu(this);
        gameMenu = new GameMenu(this,rankingMenu);
        isRunning = false;


    }

    void sendEnemyToHome() {

        sendEnemyToStartingLocation();
        enemyBlue.setTick(0);
        enemyGreen.setTick(0);
        enemyPurple.setTick(0);
        enemyRed.setTick(0);
        enemyRed.waitThenGoOutside(3);
        enemyBlue.waitThenGoOutside(3);
        enemyGreen.waitThenGoOutside(3);
        enemyPurple.waitThenGoOutside(3);
        player.setHitByEnemy(false);


    }

    private void sendEnemyToStartingLocation() {
        enemyRed.setLocation(enemyRed.getStartingLocationX(), enemyRed.getStartingLocationY());
        enemyBlue.setLocation(enemyBlue.getStartingLocationX(), enemyBlue.getStartingLocationY());
        enemyPurple.setLocation(enemyPurple.getStartingLocationX(), enemyPurple.getStartingLocationY());
        enemyGreen.setLocation(enemyGreen.getStartingLocationX(), enemyGreen.getStartingLocationY());
    }

    public void resetGame() {
        playerLives.setLives(3);
        player.sendPlayerToStart();
        music.playWelocmeSound();
        Player.setAddedToRanking(false);
        welcomeTimerMusic.StartTimer();
        gameMenu.getPanel().setVisible(false);
        rankingMenu.getPanel().setVisible(false);
        isPause = false;
        player.setMainDirection("STOP");
        player.setNextDirection("STOP");
        constant.setBONUS(false);
        sendEnemyToStartingLocation();
        level.closeDoor();
        level = new LevelFactory(constant);
        score.resetScore();

    }
    Ranking getRanking() {
        return ranking;
    }

    boolean isNotPause() {
        return !isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    LevelFactory getLevel() {
        return level;
    }

    Enemy getEnemyBlue() {
        return enemyBlue;
    }

    Enemy getEnemyRed() {
        return enemyRed;
    }

    Enemy getEnemyGreen() {
        return enemyGreen;
    }

    Enemy getEnemyPurple() {
        return enemyPurple;
    }

    BackgroundImageLevel1 getBackground() {
        return background;
    }

    Music getMusic() {
        return music;
    }


    Player getPlayer() {
        return player;
    }

    public ScoreCounter getScore() {
        return score;
    }


    public PlayerLives getPlayerLives() {
        return playerLives;
    }

    NewRound getNewRound() {
        return newRound;
    }

    Winner getWinner() {
        return winner;
    }

    public Constant getConstant() {
        return constant;
    }

    GameMenu getGameMenu() {
        return gameMenu;
    }
    boolean isRunning() {
        return isRunning;
    }
    void setRunning(boolean running) {
        isRunning = running;
    }
    RankingMenu getRankingMenu() {
        return rankingMenu;
    }
}
