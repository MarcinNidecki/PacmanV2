package com.kodilla.pacmanv2.pacmanBoard.levelFactory;

import com.kodilla.pacmanv2.items.Items;

import java.util.HashMap;

public class LineOfMaze {

    private HashMap<Integer, Items> lineOfItems = new HashMap<>();


    void addElement(Integer lineNr, Items items) {

        lineOfItems.put(lineNr, items);
    }

    public HashMap<Integer, Items> getLineOfItems() {
        return lineOfItems;
    }


}
