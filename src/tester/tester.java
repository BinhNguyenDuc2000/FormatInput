package tester;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 * Testing if output file has enough records.
 * @author Binh.NguyenDuc2000@gmail.com
 *
 */
public class tester {
	
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("Output/output.txt")));
			long lines = 0l;
			try {
				while (reader.readLine()!=null) {
					lines++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			reader.close();
			System.out.print(lines);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
