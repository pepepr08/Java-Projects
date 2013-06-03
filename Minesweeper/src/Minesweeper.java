import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Minesweeper
{
	public static void main(String[] args)
	{
		
		JOptionPane.showMessageDialog( null , "\n                        Welcome to Minesweeper!!! \n\n-Uncover all squares without mines to win the game. " +
				"\n\n-The first click is always safe. \n\n-Right-click to put a flag on a square you think may contain a mine. \n\n-The numbers represent " +
				"the amount of adjacent mines to the square. \n\n-The yellow box shows/hides the mines. (CHEAT)", "Minesweeper!", JOptionPane.INFORMATION_MESSAGE);


		int gameDifficulty = JOptionPane.showOptionDialog(null, "Select game difficulty.", "Minesweeper!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, 
				new String[]{"Hard(16x16)", "Easy(9x9)"}, "default");

		
		if (gameDifficulty == 1) //1 means easy
		{
			MyPanel.DESIRED_COLUMNS = 9;
			MyPanel.numOfMines = 10;
		}
		else if (gameDifficulty == 0) //0 means hard
		{
			MyPanel.DESIRED_COLUMNS = 16;
			MyPanel.numOfMines = 40;
		}
		else //user pressend the close button
		{
			System.exit(0);	
		}

		JFrame myFrame = new JFrame("Minesweeper! by 'The Runtime Errors'");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setLocation(450, 150);

		MyPanel myPanel = new MyPanel();
		myPanel = new MyPanel();
		myFrame.add(myPanel);		
		myFrame.setSize((myPanel.GRID_X + myPanel.INNER_CELL_SIZE)*2 + (myPanel.TOTAL_COLUMNS-1)*myPanel.INNER_CELL_SIZE + 20, (myPanel.GRID_Y+myPanel.INNER_CELL_SIZE)*2 + (myPanel.TOTAL_COLUMNS-1)*myPanel.INNER_CELL_SIZE + 70);

		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		myFrame.addMouseListener(myMouseAdapter);
		myFrame.setVisible(true);
	}
}
