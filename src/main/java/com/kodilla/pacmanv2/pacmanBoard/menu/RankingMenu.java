package com.kodilla.pacmanv2.pacmanBoard.menu;

import com.kodilla.pacmanv2.GameInit;
import com.kodilla.pacmanv2.ImagePanel;

import javax.swing.*;
import java.awt.*;

public class RankingMenu extends JComponent {

    private ImageIcon  startGameIcon;
    private ImagePanel panel;
    private JButton newGame;
    private JTextArea textArea = new JTextArea(60, 60);

    private com.kodilla.pacmanv2.pacmanBoard.statistic.Ranking ranking;
    public RankingMenu(GameInit game) {


        ranking = new com.kodilla.pacmanv2.pacmanBoard.statistic.Ranking();
        ranking.readRanking();
        String text = ranking.RankingTop10ToString();
        textArea.setMargin(new Insets(10,50,10,10));
        textArea.setForeground(Color.YELLOW);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setText(text);

        java.net.URL imgURL3 = GameInit.class.getResource("/assets/textures/showRanking.png");
        ImageIcon icon2 = new ImageIcon(imgURL3);
        Image image = icon2.getImage();
        panel = new ImagePanel(image);
        panel.setPreferredSize(new Dimension(400, 400));



        java.net.URL imgURL2 = GameInit.class.getResource("/assets/textures/newGame.png");
        startGameIcon = new ImageIcon(imgURL2);
        newGame = new JButton("", startGameIcon);
        newGame.setOpaque(false);
        newGame.setBorderPainted(false);
        newGame.setContentAreaFilled(false);
        newGame.setVisible(true);
        newGame.addActionListener(e -> game.resetGame());

        panel.setLayout(new GridLayout(4, 1));
        panel.add(Box.createRigidArea(new Dimension(0, 0)));
        //
        panel.add(textArea);
        panel.add(newGame);


        panel.setVisible(false);
    }

    public ImagePanel getPanel() {
        return panel;
    }

    public void setPanel(ImagePanel panel) {
        this.panel = panel;
    }

    public JButton getB() {
        return newGame;
    }

    public void setB(JButton b) {
        this.newGame = b;
    }

    public void showMenu() {
        panel.setVisible(true);
    }

    public void newGameButton() {

    }

    public void paintComponent(Graphics g) {
        g.drawOval(100, 100, 400, 400);
        g.setColor(Color.YELLOW);

    }


}
