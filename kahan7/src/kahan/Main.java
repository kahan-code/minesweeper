package kahan;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Main {


  private static void createAndShowGUI() {
    JFrame frame = new JFrame("Kahan Minesweeper");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 420);
    frame.setLayout(new BorderLayout());


    JLabel title = new JLabel("Kahan Minesweeper", SwingConstants.CENTER);
    title.setOpaque(true);
    title.setBackground(new Color(15, 88, 111));
    title.setForeground(Color.WHITE);
    title.setFont(new Font("SansSerif", Font.BOLD, 28));
    title.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
    frame.add(title, BorderLayout.NORTH);


    JPanel outer = new JPanel(new BorderLayout());
    outer.setBackground(Color.WHITE);
    frame.add(outer, BorderLayout.CENTER);


    JLabel subtitle = new JLabel("CHOOSE YOUR GAME", SwingConstants.CENTER);
    subtitle.setFont(new Font("SansSerif", Font.PLAIN, 18));
    subtitle.setBorder(new EmptyBorder(10, 10, 10, 10));
    outer.add(subtitle, BorderLayout.NORTH);


    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 30));
    buttonsPanel.setOpaque(false);

    JButton btn5 = new JButton("5*5");
    JButton btn10 = new JButton("10*10");
    JButton btnCustom = new JButton("Custom");

    Dimension btnSize = new Dimension(200, 130);
    JButton[] buttons = new JButton[] { btn5, btn10, btnCustom };
    for (JButton b : buttons) {
      b.setPreferredSize(btnSize);
      b.setBackground(Color.YELLOW);
      b.setOpaque(true);
      b.setFocusPainted(false);
      b.setFont(new Font("SansSerif", Font.PLAIN, 18));
    }

    btn5.addActionListener(e -> {
      new Layout5x5();
      frame.dispose();
    });
    btn10.addActionListener(e -> {
      new Layout10x10();
      frame.dispose();
    });
    btnCustom.addActionListener(e -> {
      new CustomLayout();
      frame.dispose();
    });

    buttonsPanel.add(btn5);
    buttonsPanel.add(btn10);
    buttonsPanel.add(btnCustom);

    outer.add(buttonsPanel, BorderLayout.CENTER);

    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
  public static void main(String[] args) {
      createAndShowGUI();
  }
}