package com.kodilla.pacmanv2.pacmanBoard.levelFactory;

import java.util.HashMap;

public class Maze {


    private HashMap<Integer, LineOfMaze> maze = new HashMap<>();

    void addLine(Integer lineNr, LineOfMaze line) {
        maze.put(lineNr, line);
    }

    public HashMap<Integer, LineOfMaze> getMaze() {
        return maze;
    }

}
