package kahan;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Main {


  private static void createAndShowGUI() {
    Scoreboard scoreboard = new Scoreboard();

    JFrame frame = new JFrame("Kahan Minesweeper");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 420);
    frame.setLayout(new BorderLayout());


    JLabel title = new JLabel("Kahan Minesweeper", SwingConstants.CENTER);
    title.setOpaque(true);
    title.setBackground(new Color(15, 88, 111));
    title.setForeground(Color.WHITE);
    title.setFont(new Font("SansSerif", Font.BOLD, 28));
    frame.add(title, BorderLayout.NORTH);


    JPanel cbaground = new JPanel(new BorderLayout());
    cbaground.setBackground(Color.WHITE);
    frame.add(cbaground, BorderLayout.CENTER);


    JLabel subtitle = new JLabel("CHOOSE YOUR GAME", SwingConstants.CENTER);
    subtitle.setFont(new Font("SansSerif", Font.PLAIN, 18));
    cbaground.add(subtitle, BorderLayout.NORTH);


    JPanel gameButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 30));
    gameButtonsPanel.setOpaque(false);
    JPanel leaderboardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
    leaderboardPanel.setOpaque(false);

    JButton btn5 = new JButton("5*5");
    JButton btn10 = new JButton("10*10");
    JButton btnCustom = new JButton("Custom");
    JButton btnLeaderboard = new JButton("Leaderboard");

    Dimension btnSize = new Dimension(170, 130);
    JButton[] buttons = new JButton[] { btn5, btn10, btnCustom };
    for (JButton b : buttons) {
      b.setPreferredSize(btnSize);
      b.setBackground(Color.YELLOW);
      b.setOpaque(true);
      b.setFocusPainted(false);
      b.setFont(new Font("SansSerif", Font.PLAIN, 18));
    }
    btnLeaderboard.setPreferredSize(new Dimension(220, 55));
    btnLeaderboard.setBackground(new Color(15, 88, 111));
    btnLeaderboard.setForeground(Color.WHITE);
    btnLeaderboard.setOpaque(true);
    btnLeaderboard.setFocusPainted(false);
    btnLeaderboard.setFont(new Font("SansSerif", Font.BOLD, 18));

    btn5.addActionListener(e -> {
      Layout5x5 game = new Layout5x5();
      game.setLocationRelativeTo(null);
      frame.dispose();
    });
    btn10.addActionListener(e -> {
      Layout10x10 game = new Layout10x10();
      game.setLocationRelativeTo(null);
      frame.dispose();
    });
    btnCustom.addActionListener(e -> {
      CustomLayout game = new CustomLayout();
      game.setLocationRelativeTo(null);
      frame.dispose();
    });
    btnLeaderboard.addActionListener(e -> scoreboard.viewcsv());

    gameButtonsPanel.add(btn5);
    gameButtonsPanel.add(btn10);
    gameButtonsPanel.add(btnCustom);
    leaderboardPanel.add(btnLeaderboard);

    cbaground.add(gameButtonsPanel, BorderLayout.CENTER);
    cbaground.add(leaderboardPanel, BorderLayout.SOUTH);

    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
  public static void main(String[] args) {
      createAndShowGUI();
  }
}