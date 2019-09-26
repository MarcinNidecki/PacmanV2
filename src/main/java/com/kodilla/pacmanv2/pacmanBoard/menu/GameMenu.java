package com.kodilla.pacmanv2.pacmanBoard.menu;

import com.kodilla.pacmanv2.GameInit;
import com.kodilla.pacmanv2.ImagePanel;

import javax.swing.*;
import java.awt.*;

public class GameMenu extends JComponent {

    private ImagePanel panel;
    private RankingMenu rankingMenu;

    public GameMenu(GameInit game, RankingMenu rankingMenu) {
        this.rankingMenu = rankingMenu;

        java.net.URL imgURL3 = GameInit.class.getResource("/assets/textures/gameMenu.png");
        ImageIcon icon2 = new ImageIcon(imgURL3);
        Image image = icon2.getImage();
        panel = new ImagePanel(image);
        panel.setPreferredSize(new Dimension(261, 226));


        java.net.URL imgURL = GameInit.class.getResource("/assets/textures/exit.png");
        ImageIcon exitIcon = new ImageIcon(imgURL);
        JButton exit = new JButton("", exitIcon);
        exit.addActionListener(e -> System.exit(0));
        exit.setOpaque(false);
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        exit.setVisible(true);

        java.net.URL imgURL4 = GameInit.class.getResource("/assets/textures/ranking.png");
        ImageIcon rankingIcon = new ImageIcon(imgURL4);
        JButton ranking = new JButton("", rankingIcon);
        ranking.addActionListener(e ->  showRanking());
        ranking.setOpaque(false);
        ranking.setBorderPainted(false);
        ranking.setContentAreaFilled(false);
        ranking.setVisible(true);


        java.net.URL imgURL2 = GameInit.class.getResource("/assets/textures/newGame.png");
        ImageIcon startGameIcon = new ImageIcon(imgURL2);
        JButton newGame = new JButton("", startGameIcon);
        newGame.addActionListener(e -> game.resetGame());
        newGame.setOpaque(false);

        newGame.setBorderPainted(false);
        newGame.setContentAreaFilled(false);

        newGame.setVisible(true);
        panel.setLayout(new GridLayout(4, 1));

        panel.add(Box.createRigidArea(new Dimension(0, 0)));
        panel.add(newGame);
        panel.add(ranking);
        panel.add(exit);
        panel.setVisible(false);
    }

    public ImagePanel getPanel() {
        return panel;
    }


    public void showMenu() {
        panel.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        g.drawOval(100, 100, 200, 200);
        g.setColor(Color.YELLOW);

    }
    void showRanking() {
        rankingMenu.getPanel().setVisible(true);
        panel.setVisible(false);
    }

}
