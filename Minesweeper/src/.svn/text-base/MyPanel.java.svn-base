import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MyPanel extends JPanel
{
	private static final long serialVersionUID = 3426940946811133635L;
	public final int GRID_X = 25;
	public final int GRID_Y = 25;
	public final int INNER_CELL_SIZE = 29;
	public static int DESIRED_COLUMNS = 9;
	public final int TOTAL_COLUMNS = DESIRED_COLUMNS + 1;
	public final int TOTAL_ROWS = TOTAL_COLUMNS + 1;   //Last row has only one cell

	public final int GAME_WON = 1;
	public final int GAME_LOSE = 0;

	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public String gameStatus = "";       //Win or lose, si no se a acabado el juego, el string esta vacio
	public static boolean firstClick = true;
	public static int numOfMines = 10;//(int) (DESIRED_COLUMNS*DESIRED_COLUMNS/6.4);
	public int numOfCellsLeft = (TOTAL_COLUMNS-1)*(TOTAL_COLUMNS-1) - numOfMines;
	public int numOfMinesLeft = numOfMines;

	public Color[][] coveredCellColor = new Color[TOTAL_COLUMNS][TOTAL_ROWS+1];  //sumas 1 para evitar error
	public Color[][] uncoveredCellColor = new Color[TOTAL_COLUMNS][TOTAL_ROWS];  //este array determina si la celda es una mina o no lo es

	
	//Flags and mines
	public Color[][] flagColor = new Color[TOTAL_COLUMNS][TOTAL_ROWS]; //este array determina si la celda es una mina o no lo es
	private Flag flag1 = new Flag();
	private Flag[][] flag = new Flag[TOTAL_COLUMNS][TOTAL_COLUMNS];
	public Color[][] mineColor = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	private Mine mine1 = new Mine();
	private Mine[][] mine = new Mine[TOTAL_COLUMNS][TOTAL_COLUMNS];

	//Esto se usa para escribir los numeros
	public int[][] adjacentMines= new int[TOTAL_COLUMNS + 1][TOTAL_ROWS + 1]; //sumas 1 pq cuando hay una mina al borde, le puedes sumar 1 a un bloque imaginario fuera del grid y evitas un runtime error
	public String[][] cellNum = new String[TOTAL_COLUMNS + 1][TOTAL_ROWS + 1];

	public MyPanel()
	{  
		//This is the constructor... this code runs first to initialize
		newGame();

		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1)
		{	
			//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2)
		{	
			//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3)
		{	
			//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}
		for (int x = 0; x < TOTAL_COLUMNS; x++)
		{   
			//Top row 
			coveredCellColor[x][0] = null; //null para que no se vea
			uncoveredCellColor[x][0] = null; //null para que no se vea
		}
		for (int y = 0; y < TOTAL_ROWS; y++)
		{   
			//Left column
			coveredCellColor[0][y] = null; //null para que no se vea
			uncoveredCellColor[0][y] = null; //null para que no se vea
		}
		for (int i = 1; i<TOTAL_COLUMNS; i++)
		{ 
			//dibuja las banderas/minas, para que sean visible, cambia el color de la bandera/mina.
			for (int j = 1; j<TOTAL_COLUMNS; j++)
			{
				flag[i][j] = flag1; //Si no hago esto sale un runtime error (Null pointer)
				flag[i][j].setLocation(GRID_X + i*(INNER_CELL_SIZE+1) + 5, GRID_Y + j*(INNER_CELL_SIZE+1) - 3 );
				mine[i][j] = mine1;
				mine[i][j].setLocation(GRID_X + i*(INNER_CELL_SIZE+1) + 5, GRID_Y + j*(INNER_CELL_SIZE+1) - 3 );
				mineColor[i][j] = Color.WHITE;
			}
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;

		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		//Paint the background
		Color backgroundColor = new Color(0x007FFF);
		g.setColor(backgroundColor);
		g.fillRect(x1, y1, width + 1, height + 1);

		//Draw the grid minus the bottom row (which has only one cell)
		//By default, the grid will be 10x10 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 1; y <= TOTAL_ROWS - 1; y++)
		{
			g.drawLine(x1 + GRID_X + (INNER_CELL_SIZE + 1), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 1; x <= TOTAL_COLUMNS; x++)
		{
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + (INNER_CELL_SIZE + 1), x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS - 1)));
		}

		//Draw an additional cell at the bottom left
		g.drawRect(x1 + GRID_X, y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS - 1)), INNER_CELL_SIZE + 1, INNER_CELL_SIZE + 1);

		//Paint cell colors
		for (int x = 1; x < TOTAL_COLUMNS; x++)
		{
			for (int y = 1; y < TOTAL_ROWS; y++)
			{
				if ((x == 0) || (y != TOTAL_ROWS - 1))
				{
					Color c = coveredCellColor[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
				}
			}
		}

		//paint flags
		for (int i = 1; i<TOTAL_COLUMNS; i++)
		{  
			for (int j = 1; j<TOTAL_COLUMNS; j++)
			{
				flag[i][j] = flag1;
				flag[i][j].setLocation(GRID_X + i*(INNER_CELL_SIZE+1) + 5, GRID_Y + j*(INNER_CELL_SIZE+1) - 3 );

				if (!coveredCellColor[i][j].equals(Color.RED))
				{
					flagColor[i][j] = coveredCellColor[i][j];
					flag[i][j].draw(g, flagColor[i][j]);
				}	
				else
				{
					flagColor[i][j] = Color.WHITE;
					flag[i][j].draw(g, flagColor[i][j]);
				}
			}
		}

		
		//paint mines
		for (int i = 1; i<TOTAL_COLUMNS; i++)
		{  
			for(int j = 1; j<TOTAL_COLUMNS; j++)
			{
				mineColor[i][j] = Color.WHITE;
				if(coveredCellColor[i][j].equals(Color.BLACK))
				{
					mine[i][j] = mine1;
					mine[i][j].setLocation(GRID_X + i*(INNER_CELL_SIZE+1) + 5, GRID_Y + j*(INNER_CELL_SIZE+1) - 3 );
					mine[i][j].draw(g, mineColor[i][j]);
				}

			}
		}

		//paint orange square
		g.setColor(Color.ORANGE);
		g.fillRect(x1 + GRID_X + 1, y1 + GRID_Y + (TOTAL_COLUMNS * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);

		//Esto dibuja los numeros
		for (int x=1; x<TOTAL_COLUMNS;x++)
		{
			for (int y=1; y<TOTAL_ROWS-1; y++)
			{
				g.setColor(Color.RED);
				g.drawString(cellNum[x][y], GRID_X + x*(INNER_CELL_SIZE+1) + 10, GRID_Y + y*(INNER_CELL_SIZE+1) + 20);
			}
		}

		g.setColor(Color.YELLOW);
		g.drawString("Mines left: " + String.valueOf(numOfMinesLeft), GRID_X + INNER_CELL_SIZE+1 + ((INNER_CELL_SIZE+1)*TOTAL_COLUMNS-100)/2, GRID_Y + ((INNER_CELL_SIZE+1)*TOTAL_COLUMNS) + 2*INNER_CELL_SIZE);

		Font arial = new Font("Arial", Font.PLAIN, 20);
		g.setFont(arial);
		g.drawString(gameStatus, GRID_X + INNER_CELL_SIZE+1 + ((INNER_CELL_SIZE+1)*TOTAL_COLUMNS-100)/2, GRID_Y + ((INNER_CELL_SIZE+1)*TOTAL_COLUMNS) + INNER_CELL_SIZE);
	}

	public int getGridX(int x, int y)
	{
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0)
		{   
			//To the left of the grid
			return -1;
		}
		if (y < 0)
		{   
			//Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0))
		{  
			//Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x == 0 && y == TOTAL_ROWS - 1)
		{    
			//The lower left extra cell
			return x;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2)
		{
			//Outside the rest of the grid
			return -1;
		}
		return x;
	}

	public int getGridY(int x, int y)
	{
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0)
		{   //To the left of the grid
			return -1;
		}
		if (y < 0)
		{   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0))
		{   
			//Coordinate is at an edge; not inside a cell.
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x == 0 && y == TOTAL_ROWS - 1)
		{   
			//The lower left extra cell
			return y;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2)
		{
			//Outside the rest of the grid
			return -1;
		}
		return y;
	}

	public void showAdjacentCells(int x, int y)
	{ 
		for (int j = -1; j<=1; j++)
		{
			for (int k = -1; k<=1; k++)
			{
				if (x + j < 1 || y + k < 1 || x + j == TOTAL_COLUMNS || y + k == TOTAL_COLUMNS || x==TOTAL_COLUMNS || y==TOTAL_COLUMNS || x < 1 || y < 1)
				{
					//do nothing, if the coordinate is out of the grid
				}
				else if (coveredCellColor[x + j][y + k].equals(uncoveredCellColor[x + j][y + k]))
				{
					//if the cell is already light gray, do nothing....
				}
				else if (k==0 && j==0)
				{
					//do nothing, ignora la celda donde hizo el click
					
				}	
				else if (coveredCellColor[x + j][y + k].equals(Color.RED))
				{
					//do nothing, si la celda tiene una bandera
				}
				else if (adjacentMines[x + j][y + k] != 0)
				{
					//si la celda tiene minas adjacentes...
					cellNum[x + j][y + k] = String.valueOf(adjacentMines[x + j][y + k]);
					numOfCellsLeft--;
					coveredCellColor[x + j][y + k] = uncoveredCellColor[x + j][y + k];
				}
				else
				{
					//si la celda tiene no tiene minas adjacentes...
					numOfCellsLeft--;
					coveredCellColor[x + j][y + k] = uncoveredCellColor[x + j][y + k];
					showAdjacentCells(x + j, y + k);	
				}				
			}
		}
	}

	public void showGameStatus(int status)
	{
		if (status==GAME_WON)
		{
			this.gameStatus = "You Won!!";
			if (adjacentMines[mouseDownGridX][mouseDownGridY] != 0)
			{
				cellNum[mouseDownGridX][mouseDownGridY] = String.valueOf(adjacentMines[mouseDownGridX][mouseDownGridY]);
			}
			repaint();

			int option = JOptionPane.showConfirmDialog(null, "You Won. Congratulations!!! \n Play again?", "Minesweeper!", JOptionPane.YES_NO_CANCEL_OPTION);
			if (option==JOptionPane.YES_OPTION) 
				newGame();
			else if (option==JOptionPane.NO_OPTION)
				System.exit(0);
			else
			{
				//do nothing
			}
		}
		else
		{
			for (int i=1; i<=TOTAL_COLUMNS-1; i++)
			{
				for (int j=1; j<=TOTAL_COLUMNS-1; j++)
				{
					if (uncoveredCellColor[i][j].equals(Color.BLACK))
					{
						coveredCellColor[i][j] = uncoveredCellColor[i][j] ;
						repaint();
					}
				}
			}
			this.gameStatus = "You lose!!";
			repaint();

			int option = JOptionPane.showConfirmDialog(null, "You Lose. Play again?", "Minesweeper!", JOptionPane.YES_NO_CANCEL_OPTION);
			if (option==JOptionPane.YES_OPTION) 
				newGame();
			else if (option==JOptionPane.NO_OPTION)
				System.exit(0);
			else
			{
				//do nothing
			}
		}
	}

	public void setMinesLocation(int x, int y)
	{
		//recibe las coordenadas del primer click para que nunca haya una mina ahi.
		Boolean[][] notMine = new Boolean[TOTAL_COLUMNS+1][TOTAL_ROWS+1];

		//asign all values to false
		for (int i = 0; i<=TOTAL_COLUMNS; i++)
		{
			for (int j = 0; j<=TOTAL_ROWS; j++)
			{
				notMine[i][j] = false;
			}
		}
		for (int i = -1; i<=1; i++)
		{     
			//indica las celdas que no pueden ser minas (las de alrededor del primer click)
			for (int j = -1; j<=1; j++)
			{
				notMine[x + i][y + j] = true;
			}
		}

		Random random = new Random();    
		for(int i = 1 ; i <= numOfMines; i++)
		{ 
			//asignar las celdas que son minas
			int randomX = random.nextInt(DESIRED_COLUMNS) + 1; //suma 1 para que la x nunca sea 0 y pueda ser 9
			int randomY = random.nextInt(DESIRED_COLUMNS) + 1;

			if (uncoveredCellColor[randomX][randomY] == Color.BLACK) //si la celda random ya es una mina, el 'for' correra una vez mas
				i--;
			else if (notMine[randomX][randomY])
				i--;
			else
			{
				uncoveredCellColor[randomX][randomY] = Color.BLACK;

				for (int j = -1; j<=1; j++)
				{
					for (int k = -1; k<=1; k++)
					{
						this.adjacentMines[randomX + j][randomY + k]++; //le suma '1' a las celdas alrededor de esta mina
					}
				}
			}
		}
	}

	public void newGame()
	{
		for (int i = 1; i<TOTAL_COLUMNS; i++)
		{
			for (int j =1; j<TOTAL_ROWS; j++)
			{
				cellNum[i][j] = ""; //Asigna string vacio a todo el array de strings para que no sea null.
				adjacentMines[i][j] = 0;
				uncoveredCellColor[i][j] = Color.LIGHT_GRAY;
				coveredCellColor[i][j] = Color.WHITE;
			}
		}
		firstClick= true;
		MyMouseAdapter.minesShown = false;
		numOfMinesLeft = numOfMines;
		numOfCellsLeft = (DESIRED_COLUMNS)*(DESIRED_COLUMNS) - numOfMines;
		gameStatus = "";
		mouseDownGridX = 0;
		mouseDownGridY = 0;
		x = -1;
		y = -1;
	}
}
