package com.nonstopcoders.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Leaderboard {

	File inputFile;
	PrintWriter out;
	String mode;

	public Leaderboard(String mode) {
		this.mode = mode;
	}

	public void checkHighScore(int score) throws FileNotFoundException {
		String filename = "leaderboard/" + mode + ".txt";
		this.inputFile = new File(filename);
		Scanner in = new Scanner(this.inputFile);
		PrintWriter out = new PrintWriter(new File("leaderboard/temp"));

		int lineNumber = 1;
		// Read the input and write the output
		boolean highScoreAlreadyStored = false;
		while (lineNumber <= 10 && in.hasNext()) {
			int num = in.nextInt();
			String name = in.next();
			int tempScore = in.nextInt();
			String line = (num + "\t" + name + "\t" + tempScore);

			if (score < tempScore || highScoreAlreadyStored) {
				if (highScoreAlreadyStored)
					out.println(lineNumber + "\t" + name + "\t" + tempScore);
				else
					out.println(line);
			}

			else if (score >= tempScore || !in.hasNext()) {
				Sound.WIN.play();
				String userName = JOptionPane
						.showInputDialog("New High Score,  Congrats!!!\n Enter your name:");
				userName = userName.replace(" ", "_");
				out.println(Integer.toString(lineNumber) + "\t" + userName
						+ "\t" + score);

				if (lineNumber != 10) {
					lineNumber++;
					out.println(lineNumber + "\t" + name + "\t" + tempScore);
				}
				highScoreAlreadyStored = true;
			}
			lineNumber++;
		}

		in.close();
		out.close();

		if (highScoreAlreadyStored) {
			File temp = new File("leaderboard/temp");
			Scanner read = new Scanner(temp);
			PrintWriter write = new PrintWriter(this.inputFile);

			while (read.hasNextLine()) {
				write.println(read.nextLine());
			}
			read.close();
			write.close();
			temp.delete();
		}
	}

	/**
	 * Experimental method. Not yet official or permanent. For now, it should
	 * return a string to be used by the showLeaderBoard() method of the
	 * MemoryGame class.
	 * 
	 * @return highScores a string containing the level's list of high scores
	 * @throws FileNotFoundException
	 */
	public String getHighScore() {
		String leaderboard = "\n                            #  Name" +
				"                          Score    \n\n";

		String filename = "leaderboard/" + mode + ".txt";
		File file = new File(filename);
		Scanner in;

		try {
			in = new Scanner(file);
		}
		catch (FileNotFoundException e) {
			return null;
		}

		int num = 1;
		while (in.hasNext()) {
			in.nextInt();
			String name = in.next();
			String score = Integer.toString(in.nextInt());


			while (name.length() < 20)
				name = name + "-";

			leaderboard = leaderboard + "                            " + num+ "-" + name + score + "\n";
			num++;
		}

		return leaderboard;
	}
}