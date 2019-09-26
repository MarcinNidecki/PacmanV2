package com.kodilla.pacmanv2.items;

import java.awt.*;

import static com.kodilla.pacmanv2.Constant.TILE_SIZE;

public class Empty extends Rectangle implements Items {

    public Empty(int x, int y) {
        setBounds(x, y, TILE_SIZE, TILE_SIZE);
    }
}
