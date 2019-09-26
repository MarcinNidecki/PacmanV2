package com.kodilla.pacmanv2.items;

import java.awt.*;

import static com.kodilla.pacmanv2.Constant.TILE_SIZE;

public class Dot extends Rectangle implements Items {

    private boolean isBigDot;

    public Dot(int x, int y, boolean isBigDot) {
        this.isBigDot = isBigDot;
        setBounds(x, y, TILE_SIZE, TILE_SIZE);
    }

    public boolean isBigDot() {
        return isBigDot;
    }

    public void render(Graphics g) {
        if (!isBigDot) {
            g.drawImage(ItemPictures.dot, x, y, 32, 32, null);
        } else {
            g.drawImage(ItemPictures.bigDot, x, y, 32, 32, null);
        }

    }
}





