package com.kodilla.pacmanv2;

import com.kodilla.pacmanv2.items.Player;
import com.kodilla.pacmanv2.pacmanBoard.TimerTaskPaccman;
import com.kodilla.pacmanv2.pacmanBoard.statistic.PlayerLives;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class PacmanAppRunner extends Canvas implements Runnable, KeyListener {

    private static GameInit gameInit;
    private Thread thread;
    private TimerTaskPaccman deadAnimation = new TimerTaskPaccman(3000);

    private PacmanAppRunner() {
        Dimension dimension = new Dimension(gameInit.getConstant().getWIDTH(), gameInit.getConstant().getHEIGHT());
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        addKeyListener(this);
    }

    public static void main(String[] arg) {

        gameInit = new GameInit();
        PacmanAppRunner game = new PacmanAppRunner();
        JFrame frame = new JFrame();
        frame.setBackground(Color.BLACK);
        frame.setBounds(0, 0, 1600, 960);
        frame.add(gameInit.getGameMenu().getPanel()).setLocation(630, 300);
        frame.add(gameInit.getRankingMenu().getPanel()).setLocation(600, 250);
        frame.setTitle(gameInit.getConstant().getTITLE());
        frame.setUndecorated(true);
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();
    }

    private synchronized void start() {

        if (gameInit.isRunning()) return;
        gameInit.setRunning(true);
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {

        if (!gameInit.isRunning()) return;
        gameInit.setRunning(false);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void thick() {

        gameInit.getPlayer().tick();

        if (gameInit.getPlayer().isHitByEnemy()) {
            gameInit.sendEnemyToHome();
            gameInit.getConstant().setIS_NEW_ROUND(true);
        }

        if (gameInit.isNotPause()) {
            gameInit.getEnemyBlue().enemyTick();
            gameInit.getEnemyPurple().enemyTick();
            gameInit.getEnemyGreen().enemyTick();
            gameInit.getEnemyRed().enemyTick();
        }
    }

    private void render() {

        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.fillRect(0, 0, PacmanAppRunner.WIDTH, PacmanAppRunner.HEIGHT);
        gameInit.getBackground().render(g);
        gameInit.getLevel().render(g);
        gameInit.getPlayer().repaint(g);
        gameInit.getEnemyBlue().paintEnemyBlue(g);
        gameInit.getEnemyRed().paintEnemyRed(g);
        gameInit.getEnemyGreen().paintEnemyGreen(g);
        gameInit.getEnemyPurple().paintEnemyPurple(g);
        gameInit.getScore().paint(g);
        gameInit.getPlayerLives().paintComponent(g);

        if (gameInit.getLevel().listOfDots().size() < 1) {
            gameInit.getWinner().paint(g);
            gameInit.setPause(true);
            gameInit.getGameMenu().showMenu();
        }
        if (gameInit.getConstant().isIS_NEW_ROUND()) {
            gameInit.getNewRound().paint(g);

        }
        g.dispose();
        bs.show();
    }

    @Override
    public void run() {

        requestFocus();
        int fps = 0;
        double timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        double delta = 0;
        double ns = 1000000000 / gameInit.getConstant().getTARGET_TICK();

        gameInit.getMusic().playWelocmeSound();
        gameInit.getRanking().readRanking();

        while (gameInit.isRunning()) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {

                playBackgroundMusic();
                ifPlayerIsDeadShowEndMenu();
                ifThereAreNoMoreDotsThenPlayMusicWinner();
                render();
                thick();

                fps++;
                delta--;
            }
            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println(fps);
                fps = 0;
                timer += 1000;
            }
        }
        stop();
    }

    private void ifPlayerIsDeadShowEndMenu() {
        if (PlayerLives.getLives() == 0) {

            deadAnimation.StartTimer();
            deadAnimation.checkIfTimerIsEnd();
            if (deadAnimation.isTimerOFF()) {

                gameInit.getRanking().RankingTop10ToString();
                gameInit.getGameMenu().showMenu();
                gameInit.setPause(true);
            }
        }
    }


    private void ifThereAreNoMoreDotsThenPlayMusicWinner() {

        if (gameInit.getLevel().listOfDots().size() == 0) {
            gameInit.getMusic().playWinnerMusic();

        }
    }


    private void playBackgroundMusic() {

        gameInit.getMusic().getWelcomeMusicTimer().checkIfTimerIsEnd();
        if (gameInit.getMusic().getWelcomeMusicTimer().isTimerOFF() && gameInit.isNotPause() && PlayerLives.getLives() > 0) {
            gameInit.getMusic().playBackgroundSound();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (Player.isAlive() && gameInit.isNotPause()) {
            String right = "RIGHT";
            String left = "LEFT";
            String up = "UP";
            String down = "DOWN";
            pressKey(e, KeyEvent.VK_RIGHT, left, right, up, down);
            pressKey(e, KeyEvent.VK_LEFT, right, left, up, down);
            pressKey(e, KeyEvent.VK_DOWN, up, down, left, right);
            pressKey(e, KeyEvent.VK_UP, down, up, left, right);
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameInit.getGameMenu().getPanel().setVisible(!gameInit.getGameMenu().getPanel().isVisible());
            gameInit.setPause(gameInit.isNotPause());
            gameInit.getPlayer().setMainDirection(gameInit.getConstant().getSTOP());
            gameInit.getPlayer().setNextDirection(gameInit.getConstant().getSTOP());
        }

    }

    private void pressKey(KeyEvent e, int vkCode, String oppositeToVKCodeDirection, String vkDirection, String RotationDirectionBy90Degree, String oppositeRotatedDirection) {
        if (e.getKeyCode() == vkCode) {

            if (gameInit.getPlayer().getMainDirection().equals(oppositeToVKCodeDirection) || gameInit.getPlayer().getMainDirection().equals(gameInit.getConstant().getSTOP())) {
                gameInit.getPlayer().setMainDirection(vkDirection);
                gameInit.getPlayer().setNextDirection(gameInit.getConstant().getSTOP());

            } else if (gameInit.getPlayer().getMainDirection().equals(RotationDirectionBy90Degree) || gameInit.getPlayer().getMainDirection().equals(oppositeRotatedDirection)) {
                gameInit.getPlayer().setNextDirection(vkDirection);

            }

        }
    }


}