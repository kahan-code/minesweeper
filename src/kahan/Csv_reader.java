package kahan;
import java.io.*;
public class Csv_reader {
	Csv_reader(){
		try(BufferedReader br = new BufferedReader(new FileReader("Leaderbard.csv"))){
			String line;
			while ((line = br.readLine())!=null){
				String[] values = line.split(",");
				System.out.println(values[1]);
			}
		}
		catch(IOException e) {e.printStackTrace();}
	}
}
