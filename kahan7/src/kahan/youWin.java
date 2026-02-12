package kahan;

import java.awt.*;
import javax.swing.*;

public class youWin {
    JFrame frame;
    
    public youWin() {
        frame = new JFrame("You Win");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,400);
        frame.setVisible(true);

        JPanel background = new JPanel();
        JPanel textPanel = new JPanel();
        JLabel text = new JLabel("Congratulations! :)", SwingConstants.CENTER);
        JLabel bodytext = new JLabel("You Win!",SwingConstants.CENTER);

        background.setBackground(new Color(21,96,130));
        background.setLayout(new BorderLayout());
        frame.add(background, BorderLayout.CENTER);

        text.setOpaque(true);
        text.setBackground(Color.yellow);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("SansSerif", Font.BOLD, 48));

        textPanel.setSize(50,100);
        textPanel.setBackground(Color.yellow);
        frame.add(textPanel,BorderLayout.NORTH);
        textPanel.add(text,BorderLayout.CENTER);

        bodytext.setOpaque(true);
        bodytext.setBackground(new Color(21,96,130));
        bodytext.setForeground(Color.BLACK);
        bodytext.setFont(new Font("SansSerif", Font.BOLD, 55));
        background.add(bodytext,BorderLayout.CENTER);
    }
}