package kahan;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class CustomLayout extends JFrame implements ActionListener{
    JButton[][] buttons;
    int matrix[][];
    boolean check[][];
    int rowSize;
    int colSize;
    String name;
    boolean flagMode = false;
    JPanel but;
    JPanel header;
    JButton flagbut;
    Color originalColor;
    Timer timer;
    JLabel timeLabel;
    int seconds = 0;
    private boolean isRevealing = false;
    ImageIcon icon0 = scaleIcon(new ImageIcon("0.png"));
	ImageIcon icon1 = scaleIcon(new ImageIcon("1.png"));
	ImageIcon icon2 = scaleIcon(new ImageIcon("2.png"));
	ImageIcon icon3 = scaleIcon(new ImageIcon("3.png"));
	ImageIcon icon4 = scaleIcon(new ImageIcon("4.png"));
	ImageIcon icon5 = scaleIcon(new ImageIcon("5.png"));
	ImageIcon icon6 = scaleIcon(new ImageIcon("6.png"));
	ImageIcon icon7 = scaleIcon(new ImageIcon("7.png"));
	ImageIcon icon8 = scaleIcon(new ImageIcon("8.png"));
	ImageIcon flag = scaleIcon(new ImageIcon("flag.png"));
    Image mine = new ImageIcon("mine.png").getImage();

	private ImageIcon scaleIcon(ImageIcon icon) {
		if (icon.getImage() != null) {
			java.awt.Image scaled = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
			return new ImageIcon(scaled);
		}
		return icon;
	}

    CustomLayout() {
    	name = JOptionPane.showInputDialog(null, "Enter your name", "");
        String rows = JOptionPane.showInputDialog(null, "Enter number of rows", "5");
        String colums = JOptionPane.showInputDialog(null, "Enter number of columns", "5");
        String minesStr = JOptionPane.showInputDialog(null, "Enter number of mines", "5");
        
        if (rows == null& colums == null) {
            return;
        }
        
            rowSize = Integer.parseInt(rows);
            colSize = Integer.parseInt(colums);
            if (rowSize < 3 || rowSize > 20 || colSize < 3 || colSize > 20) {
                JOptionPane.showMessageDialog(null, "Grid size must be between 3 and 20");
                return;
            }

        
        buttons = new JButton[rowSize][colSize];
        matrix = new int[rowSize][colSize];
        check = new boolean[rowSize][colSize];

        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 800);
        this.setLayout(new BorderLayout());
        
        but = new JPanel();
		but.setLayout(new GridLayout(rowSize, colSize, 5, 5));
        but.setBackground(new Color(255, 255, 255));
        this.add(but);

        header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(new Color(90, 90, 90));
        header.setPreferredSize(new Dimension(100,100));
        this.add(header, BorderLayout.NORTH);

        flagbut = new JButton();
        flagbut.setPreferredSize(new Dimension(80, 40));
        flagbut.setBackground(new Color(255, 255, 0));
        flagbut.setIcon(flag);
        flagbut.addActionListener(this);
        header.add(flagbut, BorderLayout.EAST);

        timeLabel = new JLabel("0s");
        timeLabel.setPreferredSize(new Dimension(60,40));
        timeLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        timeLabel.setBackground(Color.white);
        timeLabel.setOpaque(true);
        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        timeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        header.add(timeLabel, BorderLayout.WEST);
        timer = new Timer(1000, e -> {
            seconds++;
            timeLabel.setText(seconds + "s");
            if (seconds >= 100) {
                timeLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
            }
        });
        timer.start();

        for (int r = 0; r < rowSize; r++) {
            for (int c = 0; c < colSize; c++) {
                buttons[r][c] = new JButton();
                buttons[r][c].setActionCommand(r + "," + c);
                buttons[r][c].addActionListener(this);
                buttons[r][c].setOpaque(true);
                buttons[r][c].setHorizontalAlignment(javax.swing.JButton.CENTER);
                buttons[r][c].setVerticalAlignment(javax.swing.JButton.CENTER);
                but.add(buttons[r][c]);
            }
        }
        originalColor = buttons[0][0].getBackground();
        Random rand = new Random();
        
        int minesToPlace = Integer.parseInt(minesStr);
        for (int m = 0; m < minesToPlace; m++) {
            int rr = rand.nextInt(rowSize);
            int rc = rand.nextInt(colSize);

            while (matrix[rr][rc] == -1) {
                rr = rand.nextInt(rowSize);
                rc = rand.nextInt(colSize);
            }
            matrix[rr][rc] = -1;

            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr == 0 && dc == 0) continue;
                    int nr = rr + dr;
                    int nc = rc + dc;
                    if (nr >= 0 && nr < rowSize && nc >= 0 && nc < colSize && matrix[nr][nc] != -1) {
                        matrix[nr][nc] += 1;
                    }
                }
            }
        }
		
        this.setVisible(true);
    }
    public void disable() {
  	  for (int r=0;r<rowSize;r++) {
  		  for (int c=0;c<colSize;c++) {
  			  buttons[r][c].setEnabled(false);
  		  }
  	  }
    }
    public void reveal(){
    	Image newImage = mine.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImage);


        for (int r=0; r<rowSize; r++) {
            for (int c=0; c<colSize; c++) {
                if (matrix[r][c] == -1) {
                    buttons[r][c].setBackground(new java.awt.Color(255, 0, 0));
                    buttons[r][c].setIcon(newIcon);
                } else if (matrix[r][c] >= 0) {
                    buttons[r][c].setBackground(new java.awt.Color(192, 192, 192));
                }
            }
        }
    }
    public void open(int r,int c) {
  		for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int nr = r + dr;
                int nc = c + dc;
                if (nr >= 0 && nr < rowSize&& nc >= 0 && nc < colSize && matrix[nr][nc] != -1) {
                	check[nr][nc] = true;
                	buttons[nr][nc].doClick();
                }
            }
        }
  	}
    @Override
    public void actionPerformed(ActionEvent e) {
          if (e.getSource() == flagbut) {
            if (flagMode) {
                flagMode = false;
                flagbut.setBackground(new Color(255, 255, 0));

            }
            else {
                flagMode=true;
                flagbut.setBackground(new Color(255, 0, 0));
            }
        }
        String cmd = e.getActionCommand();
        if (cmd != null && cmd.contains(",")) {
            String[] parts = cmd.split(",");
            int r = Integer.parseInt(parts[0]);
            int c = Integer.parseInt(parts[1]);
 			buttons[r][c].setBackground(new java.awt.Color(192, 192, 192));
 			if (flagMode && matrix[r][c] != -2) {
                if (check[r][c]) {
                    buttons[r][c].setIcon(null);
                    buttons[r][c].setBackground(originalColor);
                    check[r][c] = false;
                } else{
 				buttons[r][c].setIcon(flag);
 				check[r][c]=true;
                }
 			}
 			else if (!flagMode) {
 			switch (matrix[r][c]) {
 				case 0: buttons[r][c].setIcon(icon0); 
 							if (matrix[r][c]!=-2) {
                            if (matrix[r][c] !=-2) {
                                matrix[r][c] = -2;
                                if (!isRevealing) {
                                    isRevealing = true;
                                    timer.stop();
                                }
 							    open(r,c);
                                if (isRevealing) {
                                    isRevealing = false;
                                    timer.start();
                                }
 						}
                    }
 						break;
 				case 1: buttons[r][c].setIcon(icon1); matrix[r][c] = -2; break;
 				case 2: buttons[r][c].setIcon(icon2); matrix[r][c] = -2; break;
 				case 3: buttons[r][c].setIcon(icon3); matrix[r][c] = -2; break;
 				case 4: buttons[r][c].setIcon(icon4); matrix[r][c] = -2; break;
 				case 5: buttons[r][c].setIcon(icon5); matrix[r][c] = -2; break;
 				case 6: buttons[r][c].setIcon(icon6); matrix[r][c] = -2; break;
 				case 7: buttons[r][c].setIcon(icon7); matrix[r][c] = -2; break;
 				case 8: buttons[r][c].setIcon(icon8); matrix[r][c] = -2; break;
 			}
 			  if (matrix[r][c] == -1) {
                    timer.stop();
 	                new youLose();
 	                reveal();
 	                disable();
 			}
            }
 			
 			boolean allRevealed = true;
 			for (int r1 = 0; r1 < rowSize; r1++) {
 				for (int c1 = 0; c1 < colSize; c1++) {
 					if (matrix[r1][c1] >= 0) {
 						allRevealed = false;
 						break;
 					}
 				}
 				if (!allRevealed) break;
 			}
 			if (allRevealed) {
                timer.stop();
 				new youWin();
                reveal();
                disable();
 				
 			}
        }
    }
  }