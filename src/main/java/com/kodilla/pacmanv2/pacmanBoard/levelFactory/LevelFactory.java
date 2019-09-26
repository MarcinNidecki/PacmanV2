package com.kodilla.pacmanv2.pacmanBoard.levelFactory;

import com.kodilla.pacmanv2.Constant;
import com.kodilla.pacmanv2.items.Dot;
import com.kodilla.pacmanv2.items.Empty;
import com.kodilla.pacmanv2.items.Wall;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class LevelFactory {

    public static Maze maze = new Maze();
    private Constant constant;

    public LevelFactory(Constant constant) {
        this.constant = constant;

        // try read the file
        Scanner scanner = null;

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream("assets/text/pacman_level.txt");

        // File fileTxTLevel = new File(Objects.requireNonNull(classLoader.getResource("assets/text/pacman_level.txt")).getFile());
        scanner = new Scanner(is);


        ArrayList<String> levelData = new ArrayList<>();
        assert scanner != null;
        while (scanner.hasNextLine()) {
            levelData.add(scanner.nextLine());
        }
        scanner.close();


        // load elements from file
        int w = levelData.get(0).length();
        Dot dot;
        for (int xx = 0; xx < levelData.size(); xx++) {
            String line = levelData.get(xx);
            LineOfMaze lineOfMaze = new LineOfMaze();
            for (int yy = 0; yy < w; yy++) {
                char c = line.charAt(yy);

                switch (c) {

                    case '1':
                        Wall wall = new Wall(yy * 40, xx * 40,constant);

                        lineOfMaze.addElement(yy, wall);

                        break;
                    case '0':
                        dot = new Dot(yy * 40, xx * 40,false,constant);
                        lineOfMaze.addElement(yy, dot);
                        break;
                    case '2':
                        dot = new Dot(yy * 40, xx * 40, true,constant);
                        lineOfMaze.addElement(yy, dot);
                        break;
                    case '3':
                        Empty empty = new Empty(yy * 40, xx * 40,constant);
                        lineOfMaze.addElement(yy, empty);
                        break;
                }
                System.out.println(lineOfMaze.getLineOfItems().size());
            }
            maze.addLine(xx, lineOfMaze);

        }
    }

    public void closeDoor() {

        maze.getMaze().get(9).getLineOfItems().replace(18, new Wall(720, 360,constant));
        maze.getMaze().get(9).getLineOfItems().replace(19, new Wall(760, 360,constant));
        maze.getMaze().get(9).getLineOfItems().replace(20, new Wall(800, 360,constant));

    }

    public void openDoor() {

        maze.getMaze().get(9).getLineOfItems().replace(18, new Empty(720, 360,constant));
        maze.getMaze().get(9).getLineOfItems().replace(19, new Empty(760, 360,constant));
        maze.getMaze().get(9).getLineOfItems().replace(20, new Empty(800, 360,constant));

    }

    public void render(Graphics g) {

        List<Map.Entry> listOfDots = listOfDots();

        for (Map.Entry listOfDot : listOfDots) {
            if (listOfDot.getValue() instanceof Dot) {
                Dot dot = (Dot) listOfDot.getValue();
                dot.render(g);
            }
        }

    }

    public List<Map.Entry> listOfDots() {
        List<Map.Entry> listOfDots = maze.getMaze().entrySet().stream()
                .flatMap(row -> row.getValue().getLineOfItems().entrySet().stream())
                .filter(t -> (t.getValue() instanceof Dot))
                .collect(Collectors.toList());

        listOfDots.size();
        return listOfDots;
    }
}

