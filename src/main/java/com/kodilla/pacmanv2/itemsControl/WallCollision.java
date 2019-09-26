package com.kodilla.pacmanv2.itemsControl;

import com.kodilla.pacmanv2.items.Wall;
import com.kodilla.pacmanv2.pacmanBoard.levelFactory.LevelFactory;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.kodilla.pacmanv2.Constant.SPEED;
import static com.kodilla.pacmanv2.Constant.TILE_SIZE;

public class WallCollision {


    public boolean isNoCollision(int x, int y) {
        Rectangle rectangle = new Rectangle(x, y, TILE_SIZE, TILE_SIZE);

        int yLine = y / TILE_SIZE;
        int xLine = x / TILE_SIZE;

        List<Map.Entry> listOfCollidingTile = LevelFactory.maze.getMaze().entrySet().parallelStream()
                .filter(lineOfMaze -> lineOfMaze.getKey() <= yLine + 1 && lineOfMaze.getKey() >= yLine - 1)
                .flatMap(line -> line.getValue().getLineOfItems().entrySet().stream())
                .filter(lineOfMaze -> lineOfMaze.getKey() <= xLine + 1 && lineOfMaze.getKey() >= xLine - 1)
                .filter(item -> (item.getValue() instanceof Wall && (rectangle.intersects((Wall) item.getValue()))))
                .collect(Collectors.toList());

        int numberOfCollision = listOfCollidingTile.size();
        return numberOfCollision <= 0;
    }

    public boolean thereIsNoCollisionOnLeft(int x, int y) {
        return isNoCollision(x - SPEED, y);
    }

    public boolean thereIsNoCollisionOnRight(int x, int y) {
        return isNoCollision(x + SPEED, y);
    }

    public boolean thereIsNoCollisionOnDown(int x, int y) {
        return isNoCollision(x, y + SPEED);
    }

    public boolean thereIsNoCollisionOnUp(int x, int y) {
        return isNoCollision(x, y - SPEED);
    }

}

