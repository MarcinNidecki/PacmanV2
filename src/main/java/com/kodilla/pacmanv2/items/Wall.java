package com.kodilla.pacmanv2.items;

import com.kodilla.pacmanv2.Constant;

import java.awt.*;

public class Wall extends Rectangle implements Items {
    private Constant constant;

    public Wall(int x, int y, Constant constant) {
        this.constant = constant;
        setBounds(x, y, constant.getTILE_SIZE(), constant.getTILE_SIZE());
    }

    @Override
    public String toString() {
        return "" + x + y;
    }
}
