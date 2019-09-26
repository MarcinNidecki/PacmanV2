package com.kodilla.pacmanv2.items;

import com.kodilla.pacmanv2.Constant;

import java.awt.*;

public class Empty extends Rectangle implements Items {
    Constant constant;

    public Empty(int x, int y, Constant constant) {
        this.constant = constant;
        setBounds(x, y, constant.getTILE_SIZE(), constant.getTILE_SIZE());
    }


    @Override
    public String toString() {
        return "" + x + y;
    }
}
