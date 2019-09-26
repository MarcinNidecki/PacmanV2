package com.kodilla.pacmanv2.pacmanBoard;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Music {
    private TimerTaskPaccman eatGhostMusic = new TimerTaskPaccman(573);
    private TimerTaskPaccman eatBallMusic = new TimerTaskPaccman(440);
    private TimerTaskPaccman winnerMusic = new TimerTaskPaccman(25030);

    public TimerTaskPaccman getWelcomeMusicTimer() {
        return welcomeMusic;
    }

    private TimerTaskPaccman welcomeMusic = new TimerTaskPaccman(5030);
    private TimerTaskPaccman background = new TimerTaskPaccman(435);
    private TimerTaskPaccman death = new TimerTaskPaccman(5435);

    public void playWelocmeSound() {
        welcomeMusic.checkIfTimerIsEnd();
        try {
        //read audio data from whatever source (file/classloader/etc.)
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream audioSrc = classLoader.getResourceAsStream("assets/sounds/pacman_beginning.wav");
            //add buffer for mark/reset support
            playAudioFileWithTimer(audioSrc,welcomeMusic);
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }

    }

    public void playBackgroundSound() {
        background.checkIfTimerIsEnd();
        try {
            //read audio data from whatever source (file/classloader/etc.)
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream audioSrc = classLoader.getResourceAsStream("assets/sounds/siren.wav");
            //add buffer for mark/reset support
            playAudioFileWithTimer(audioSrc,background);
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public void playDeathMusic() {
        death.checkIfTimerIsEnd();
        try {
            //read audio data from whatever source (file/classloader/etc.)
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream audioSrc = classLoader.getResourceAsStream("assets/sounds/death2.wav");
            //add buffer for mark/reset support
            playAudioFileWithTimer(audioSrc,death);
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public void playEatBallMusic() {
        eatBallMusic.checkIfTimerIsEnd();
        try {
            //read audio data from whatever source (file/classloader/etc.)
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream audioSrc = classLoader.getResourceAsStream("assets/sounds/eatball.wav");
            //add buffer for mark/reset support
            playAudioFileWithTimer(audioSrc,eatBallMusic);
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }

    }

    public void playEatGhostMusic() {

        eatGhostMusic.checkIfTimerIsEnd();

        try {
            //read audio data from whatever source (file/classloader/etc.)
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream audioSrc = classLoader.getResourceAsStream("assets/sounds/pacman_eatghost.wav");
            //add buffer for mark/reset support
            playAudioFileWithTimer(audioSrc,eatBallMusic);
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
    public void playWinnerMusic() {
        winnerMusic.checkIfTimerIsEnd();
        try {
            //read audio data from whatever source (file/classloader/etc.)
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream audioSrc = classLoader.getResourceAsStream("assets/sounds/pacman_intermission.wav");
            //add buffer for mark/reset support
            playAudioFileWithTimer(audioSrc, winnerMusic);
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }

    }

    private void playAudioFileWithTimer(InputStream audioSrc, TimerTaskPaccman timer) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        InputStream bufferedIn = new BufferedInputStream(audioSrc);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        if (timer.isTimerOFF()) {
            clip.start();
            timer.StartTimer();
        }

    }


}

