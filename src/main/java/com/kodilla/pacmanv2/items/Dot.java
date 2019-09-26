package com.kodilla.pacmanv2.items;

import com.kodilla.pacmanv2.Constant;

import java.awt.*;

public class Dot extends Rectangle implements Items {

    private Constant constant;

    public boolean isBigDot() {
        return isBigDot;
    }

    private boolean isBigDot;

    public Dot(int x, int y, boolean isBigDot, Constant constant) {
        this.constant = constant;
        this.isBigDot = isBigDot;
        setBounds(x, y, constant.getTILE_SIZE(),constant.getTILE_SIZE());
    }

    public void render(Graphics g) {
        if (!isBigDot) {
            g.drawImage(ItemPictures.dot, x, y, 32, 32, null);
        } else {
            g.drawImage(ItemPictures.bigDot, x, y, 32, 32, null);
        }

    }

    @Override
    public String toString() {
        return "" + x + y;
    }
}





