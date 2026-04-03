package kahan;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Scoreboard {
    private static final String FILE_NAME = "leaderboard.csv";
    List<String[]> leaderboard = new ArrayList<>();

    public void csv(boolean win, String name, int time, int mines, int rows, int cols) {
	    leaderboard.clear();
	    boolean nameExists = false;
	    int score = Math.min(1000, (int) Math.round(200 * mines * Math.sqrt(rows * cols) / time));
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
	    String line;
        while ((line = reader.readLine()) != null) {
            String[] cells = line.split(",");
            leaderboard.add(cells);
            }
    } catch (IOException e) {
        e.printStackTrace();
        
    }
    if (win) {
        
          for (String[] entry : leaderboard) {
              if (entry[0].equalsIgnoreCase(name)) {
                    nameExists = true;
                    int existingScore = Integer.parseInt(entry[1]);
                    existingScore += score;
                    entry[1] = String.valueOf(existingScore);

                    if (Integer.parseInt(entry[2]) > score) {
                        entry[2] = String.valueOf(score);
                    }
                }
         }
                if (!nameExists) {
                    leaderboard.add(new String[] {name, String.valueOf(score), String.valueOf(score)});

                }
                
            }
    else {
	    for (String[] entry : leaderboard) {
            if (entry[0].equalsIgnoreCase(name)) {
                nameExists = true;
                int existingScore = Integer.parseInt(entry[1]);
                existingScore = Math.max(0, existingScore - 100);
                entry[1] = String.valueOf(existingScore);
            }
     }
            if (!nameExists) {
                leaderboard.add(new String[] {name, "0", "0"});

            }
    }
    leaderboard.sort((a, b) -> a[0].compareToIgnoreCase(b[0]));
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
        for (String[] entry : leaderboard) {
            writer.write(entry[0] + "," + entry[1] + "," + entry[2]);
    		writer.newLine();
    	}
    }
    catch(IOException e) {
    	e.printStackTrace();
    }
        
}
    public void viewcsv() {
        List<String[]> rows = new ArrayList<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            while((line = reader.readLine())!=null){
                if (!line.trim().isEmpty()) {
                    String[] cells = line.split(",");
                    if (cells.length >= 3 && !cells[0].equalsIgnoreCase("NAME")) {
                        rows.add(cells);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        rows.sort((a,b)->a[0].compareToIgnoreCase(b[0]));

        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = new DefaultTableModel(new Object[] {"Rank", "Name", "Total Score", "Max Score"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            for (int i = 0; i < rows.size(); i++) {
                String[] entry = rows.get(i);
                model.addRow(new Object[] {i + 1, entry[0], entry[1], entry[2]});
            }

            JTable table = new JTable(model);
            table.setFillsViewportHeight(true);
            JScrollPane scrollPane = new JScrollPane(table);

            JFrame frame = new JFrame("Leaderboard");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLocationRelativeTo(null);
            frame.add(scrollPane);
            frame.setVisible(true);
        });
    }
}