package kahan;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class CustomLayout extends JFrame implements ActionListener{
    JButton[][] buttons;
    int matrix[][];
    int rowSize;
    int colSize;
    ImageIcon icon0 = scaleIcon(new ImageIcon("0.png"));
	ImageIcon icon1 = scaleIcon(new ImageIcon("1.png"));
	ImageIcon icon2 = scaleIcon(new ImageIcon("2.png"));
	ImageIcon icon3 = scaleIcon(new ImageIcon("3.png"));
	ImageIcon icon4 = scaleIcon(new ImageIcon("4.png"));
	ImageIcon icon5 = scaleIcon(new ImageIcon("5.png"));
	ImageIcon icon6 = scaleIcon(new ImageIcon("6.png"));
	ImageIcon icon7 = scaleIcon(new ImageIcon("7.png"));
	ImageIcon icon8 = scaleIcon(new ImageIcon("8.png"));

	private ImageIcon scaleIcon(ImageIcon icon) {
		if (icon.getImage() != null) {
			java.awt.Image scaled = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
			return new ImageIcon(scaled);
		}
		return icon;
	}

    CustomLayout() {
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
        

        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 700);
        this.setLayout(new GridLayout(rowSize, colSize, 5, 5));
        this.setTitle("Minesweeper " + rowSize + "x" + colSize);
        for (int r = 0; r < rowSize; r++) {
            for (int c = 0; c < colSize; c++) {
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
    public void reveal(){

        Image oldImage = new ImageIcon("mine.png").getImage();
        Image newImage = oldImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
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
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd != null && cmd.contains(",")) {
            String[] parts = cmd.split(",");
            int r = Integer.parseInt(parts[0]);
            int c = Integer.parseInt(parts[1]);
			buttons[r][c].setBackground(new java.awt.Color(192, 192, 192));
			switch (matrix[r][c]) {
				case 0: buttons[r][c].setIcon(icon0); break;
				case 1: buttons[r][c].setIcon(icon1); break;
				case 2: buttons[r][c].setIcon(icon2); break;
				case 3: buttons[r][c].setIcon(icon3); break;
				case 4: buttons[r][c].setIcon(icon4); break;
				case 5: buttons[r][c].setIcon(icon5); break;
				case 6: buttons[r][c].setIcon(icon6); break;
				case 7: buttons[r][c].setIcon(icon7); break;
				case 8: buttons[r][c].setIcon(icon8); break;
			}
            if (matrix[r][c] == -1) {
                new youLose();
                reveal();
            }
			matrix[r][c] = -2;
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
				new youWin();
                reveal();
				
			}
        }
    }}