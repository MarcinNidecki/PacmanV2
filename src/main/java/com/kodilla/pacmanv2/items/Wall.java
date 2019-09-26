package com.kodilla.pacmanv2.items;

import java.awt.*;

import static com.kodilla.pacmanv2.Constant.TILE_SIZE;

public class Wall extends Rectangle implements Items {

    public Wall(int x, int y) {
        setBounds(x, y, TILE_SIZE, TILE_SIZE);
    }

}
