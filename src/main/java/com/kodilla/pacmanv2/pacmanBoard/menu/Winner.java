package com.kodilla.pacmanv2.pacmanBoard.menu;

import java.awt.*;

public class Winner {

    private String winner= "YOU WIN!!";


    public void paint(Graphics g) {


            Font stringFont = new Font("Arial", Font.BOLD, 64);
            g.setFont(stringFont);
            g.setColor(Color.RED);
            g.drawString(winner, 600, 200);


    }
}
