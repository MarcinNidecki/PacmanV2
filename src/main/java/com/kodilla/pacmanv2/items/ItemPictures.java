package com.kodilla.pacmanv2.items;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ItemPictures {

    public static BufferedImage[][] player;
    static BufferedImage[] enemyBlue, enemyRed, enemyGreen, enemyPurple;
    static BufferedImage dot, bigDot;
    private BufferedImage TextureGrid;

    public ItemPictures() {
        try {

            ClassLoader classLoader = getClass().getClassLoader();
            InputStream is = classLoader.getResourceAsStream("assets/textures/grid.png");
            // File filePlayer = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/textures/grid.png")).getFile());
            TextureGrid = ImageIO.read(Objects.requireNonNull(is));
        } catch (IOException e) {
            System.out.print("ERORO");
            e.printStackTrace();
        }

        player = new BufferedImage[6][4];

        //right position animation
        player[0][0] = getPartOfImage(0, 0);
        player[0][1] = getPartOfImage(32, 0);
        player[0][2] = getPartOfImage(64, 0);
        player[0][3] = getPartOfImage(96, 0);
        // left position animation
        player[1][0] = getPartOfImage(0, 32);
        player[1][1] = getPartOfImage(32, 32);
        player[1][2] = getPartOfImage(64, 32);
        player[1][3] = getPartOfImage(96, 32);
        // up position animation
        player[2][0] = getPartOfImage(0, 64);
        player[2][1] = getPartOfImage(32, 64);
        player[2][2] = getPartOfImage(64, 64);
        player[2][3] = getPartOfImage(96, 64);
        // down position animation
        player[3][0] = getPartOfImage(0, 96);
        player[3][1] = getPartOfImage(32, 96);
        player[3][2] = getPartOfImage(64, 96);
        player[3][3] = getPartOfImage(96, 96);

        // dead player
        player[4][0] = getPartOfImage(0, 288);
        player[4][1] = getPartOfImage(32, 288);
        player[4][2] = getPartOfImage(64, 288);
        player[4][3] = getPartOfImage(96, 288);
        player[5][0] = getPartOfImage(0, 320);
        player[5][1] = getPartOfImage(32, 320);
        player[5][2] = getPartOfImage(64, 320);


        enemyBlue = new BufferedImage[3];

        // enemy picture
        enemyBlue[0] = getPartOfImage(0, 128);
        // enemy picture when bonus is on
        enemyBlue[1] = getPartOfImage(64, 128);
        // blue eye
        enemyBlue[2] = getPartOfImage(94, 128);


        enemyRed = new BufferedImage[3];
        // enemy picture
        enemyRed[0] = getPartOfImage(0, 160);
        // enemy picture when bonus is on
        enemyRed[1] = getPartOfImage(64, 160);
        // red eye
        enemyRed[2] = getPartOfImage(94, 160);


        enemyPurple = new BufferedImage[3];

        enemyPurple[0] = getPartOfImage(0, 192);
        // up position animation
        enemyPurple[1] = getPartOfImage(64, 192);
        // purple eye
        enemyPurple[2] = getPartOfImage(94, 192);

        enemyGreen = new BufferedImage[3];
        // enemy picture
        enemyGreen[0] = getPartOfImage(0, 224);
        // enemy picture when bonus is on
        enemyGreen[1] = getPartOfImage(64, 224);
        // green eye
        enemyGreen[2] = getPartOfImage(94, 224);

        dot = getPartOfImage(0, 256);
        bigDot = getPartOfImage(32, 256);

    }

    private BufferedImage getPartOfImage(int xx, int yy) {
        return TextureGrid.getSubimage(xx, yy, 32, 32);
    }
}
