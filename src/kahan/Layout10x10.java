package kahan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class Layout10x10 extends JFrame implements ActionListener{
    JButton[][] buttons = new JButton[10][10];
    int matrix[][] = new int[10][10];
    boolean check[][] = new boolean[10][10];
    boolean flagMode = false;
    JPanel but;
    JPanel header;
    JButton flagbut;
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
    Layout10x10(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700,700);
        this.setLayout(new BorderLayout());

        but = new JPanel();
        but.setLayout(new GridLayout(10,10,5,5));
        but.setBackground(new Color(255,255,255));
        this.add(but, BorderLayout.CENTER);

        header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(new Color(90,90,90));
        header.setPreferredSize(new Dimension(100,100));
        this.add(header, BorderLayout.NORTH);

        flagbut = new JButton();
        flagbut.setPreferredSize(new Dimension(80,40));
        flagbut.setBackground(new Color(255,255,0));
        flagbut.setIcon(flag);
        flagbut.addActionListener(this);
        header.add(flagbut, BorderLayout.CENTER);

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                buttons[r][c] = new JButton();
                buttons[r][c].setActionCommand(r + "," + c);
                buttons[r][c].addActionListener(this);
                buttons[r][c].setOpaque(true);
                buttons[r][c].setHorizontalAlignment(javax.swing.JButton.CENTER);
                buttons[r][c].setVerticalAlignment(javax.swing.JButton.CENTER);
                this.add(buttons[r][c]);
            }
        }

        Random rand = new Random();
        
        int minesToPlace = 10;
        for (int m = 0; m < minesToPlace; m++) {
            int rr = rand.nextInt(10);
            int rc = rand.nextInt(10);

            while (matrix[rr][rc] == -1) {
                rr = rand.nextInt(10);
                rc = rand.nextInt(10);
            }
            matrix[rr][rc] = -1;

            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr == 0 && dc == 0) continue;
                    int nr = rr + dr;
                    int nc = rc + dc;
                    if (nr >= 0 && nr < 10 && nc >= 0 && nc < 10 && matrix[nr][nc] != -1) {
                        matrix[nr][nc] += 1;
                    }
                }
            }
        }
		
        this.setVisible(true);

    }
    public void disable() {
  	  for (int r=0;r<10;r++) {
  		  for (int c=0;c<10;c++) {
  			  buttons[r][c].setEnabled(false);
  		  }
  	  }
    }
   public void reveal(){
	   	Image newImage = mine.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImage);


        for (int r=0; r<10; r++) {
            for (int c=0; c<10; c++) {
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
               if (nr >= 0 && nr < 10 && nc >= 0 && nc < 10 && matrix[nr][nc] != -1) {
               	check[nr][nc] = true;
               	buttons[nr][nc].doClick();
               }
           }
       }
 	}
   @Override
   public void actionPerformed(ActionEvent e) {
       if (e.getSource() == flagbut) {
           flagMode = !flagMode;
           flagbut.setBackground(flagMode ? new Color(255, 0, 0) : new Color(255, 255, 0));
           return;
       }

       String cmd = e.getActionCommand();
       if (cmd != null && cmd.contains(",")) {
           String[] parts = cmd.split(",");
           int r = Integer.parseInt(parts[0]);
           int c = Integer.parseInt(parts[1]);
			buttons[r][c].setBackground(new java.awt.Color(192, 192, 192));
			if (flagMode) {
				if (check[r][c]) {
					buttons[r][c].setIcon(null);
					check[r][c] = false;
				} else {
					buttons[r][c].setIcon(flag);
					check[r][c] = true;
				}
				return;
			}

			if (check[r][c]==false) {
				check[r][c] = true; // mark as revealed
				switch (matrix[r][c]) {
				case 0: buttons[r][c].setIcon(icon0); 
						if (matrix[r][c]!=-2) {
                            if (matrix[r][c] !=-2) {
                                matrix[r][c] = -2;
 							    open(r,c); 
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
	               new youLose();
	               reveal();
	               disable();
			}

           }
			
			boolean allRevealed = true;
			for (int r1 = 0; r1 < 10; r1++) {
				for (int c1 = 0; c1 < 10; c1++) {
					if (matrix[r1][c1] >= 0) {
						allRevealed = false;
						break;
					}
				}
				if (!allRevealed) break;
			}
			if (allRevealed) {
				new youWin();
               reveal();
               disable();
				
			}
       }
   }
 }