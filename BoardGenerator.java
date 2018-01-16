package core;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.*;

import core.Board;

/**
 * Classe relativa al BoardGenerator
 * @author Mina
 *
 */

public class BoardGenerator {

	private static ObjectOutputStream oos;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Board[] boardCollection = new Board[200];
		FileOutputStream fos = null;
		//Generate boards (9 dead fields each, no guarantee that in terms of 
		//duplicates).
		int valueboardGB1 = 1;
		boolean valueboardGB2 = true;
		int valueboardGB3 = 3;
		for(int i = 0; i < 200; ++i) {
			boardCollection[i] = new Board(valueboardGB1, valueboardGB2, valueboardGB3);
		}

		//Save to the file.
		
		try {
			fos = new FileOutputStream("200_boards_3.sav");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(boardCollection);
			
		
		} catch(Exception e) {
			System.err.println("Error occured during saving.");
			System.out.println("Something was wrong");
		}finally {
	           if (fos != null) {
	               try {
	                 fos.close (); 
	               } catch (java.io.IOException e3) {
	                 System.out.println("I/O Exception");
	               }
	}
		}
	}
	}
