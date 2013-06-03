import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter
{
	public static boolean minesShown = false;
	public void mousePressed(MouseEvent e)
	{
		switch (e.getButton())
		{
		case 1: //Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame))
			{
				c = c.getParent();
				if (c == null)
				{
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:	//Right mouse button
			c = e.getComponent();
			while (!(c instanceof JFrame))
			{
				c = c.getParent();
				if (c == null)
				{
					return;
				}
			}
			myFrame = (JFrame) c;
			myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			myInsets = myFrame.getInsets();
			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			x = e.getX();
			y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}

	
	public void mouseReleased(MouseEvent e)
	{
		Component c = e.getComponent();
		while (!(c instanceof JFrame))
		{
			c = c.getParent();
			if (c == null)
			{
				return;
			}
		}
		JFrame myFrame = (JFrame)c;
		MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
		Insets myInsets = myFrame.getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanel.x = x;
		myPanel.y = y;
		int gridX = myPanel.getGridX(x, y);
		int gridY = myPanel.getGridY(x, y);

		if (myPanel.gameStatus.equals(""))
		{
			switch (e.getButton())
			{
			case 1:		//Left mouse button
				if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1))
				{
					//Had pressed outside
					//Do nothing
				} 
				else if ((gridX == -1) || (gridY == -1))
				{
					//Is releasing outside
					//Do nothing
				} 
				else if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY))
				{
					//Released the mouse button on a different cell where it was pressed
					//Do nothing
				} 
				else
				{
					//Released the mouse button on the same cell where it was pressed
					if ((gridX == 0) && (gridY == 0))
					{
						//On the left and top cell
					}
					else if ((gridX == 0) && (gridY == myPanel.TOTAL_ROWS - 1))
					{
						//On the left and bottom cell
						if (!minesShown && !MyPanel.firstClick)
						{
							//shows all mines
							for (int i=1; i<=myPanel.TOTAL_COLUMNS-1; i++)
							{
								for (int j=1; j<=myPanel.TOTAL_COLUMNS-1; j++)
								{
									if (myPanel.uncoveredCellColor[i][j].equals(Color.BLACK) && !myPanel.coveredCellColor[i][j].equals(Color.RED))
									{
										//If the cell is a mine and is not flagged...
										myPanel.coveredCellColor[i][j] = myPanel.uncoveredCellColor[i][j] ;
										myPanel.repaint();
									}
								}
							}
							minesShown = true;
						}
						else
						{
							for (int i=1; i<=myPanel.TOTAL_COLUMNS-1; i++)
							{
								for (int j=1; j<=myPanel.TOTAL_COLUMNS-1; j++)
								{
									if (myPanel.coveredCellColor[i][j].equals(Color.BLACK))
									{
										//If the cell is a mine and is not flagged...
										myPanel.coveredCellColor[i][j] = Color.WHITE ;
										myPanel.repaint();
									}
								}
							}
							minesShown = false;
						}
					}
					else if (gridX == 0)
					{
						//On the leftmost column wish is invisible
					}
					else if (gridY == 0)
					{
						//On the top row wish is invisible
					}
					else
					{
						//On the grid other than on the left column and on the top row:
						if (myPanel.firstClick)
						{
							myPanel.setMinesLocation(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
							myPanel.firstClick=false;
						}
						if (myPanel.coveredCellColor[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.RED))
						{
							//if the cell has a flag, do nothing...
						}
						else if(myPanel.uncoveredCellColor[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.BLACK))
						{
							//Si la celda es una mina, ense–a el mensaje: El usuario pierde el juego
							myPanel.showGameStatus(myPanel.GAME_LOSE);
						}
						else
						{ 
							//Si la celda no es una mina
							if (!myPanel.coveredCellColor[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(myPanel.uncoveredCellColor[myPanel.mouseDownGridX][myPanel.mouseDownGridY]))
							{
								//if the cell is not uncovered
								myPanel.coveredCellColor[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = myPanel.uncoveredCellColor[myPanel.mouseDownGridX][myPanel.mouseDownGridY];
								myPanel.numOfCellsLeft--;

								if (myPanel.numOfCellsLeft==0)
								{
									//String userName = JOptionPane.showInputDialog(null, "What is your name?");
									myPanel.coveredCellColor[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = myPanel.uncoveredCellColor[myPanel.mouseDownGridX][myPanel.mouseDownGridY];
									myPanel.showGameStatus(myPanel.GAME_WON);

								}
								//Si la celda  tiene minas adjacentes, escribe el nœmero de minas adjacentes
								if (myPanel.adjacentMines[myPanel.mouseDownGridX][myPanel.mouseDownGridY] != 0)
								{
									myPanel.cellNum[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = String.valueOf(myPanel.adjacentMines[myPanel.mouseDownGridX][myPanel.mouseDownGridY]);
								}
								else
								{ //si la celda no tiene minas adjacentes, hacer recursion
									myPanel.showAdjacentCells(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
									if (myPanel.numOfCellsLeft==0)
									{
										myPanel.showGameStatus(myPanel.GAME_WON);
									}
								}
							}
						}
					}
				}
				myPanel.repaint();
				break;
			case 3:		//Right mouse button
				if (((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) && ((gridX == -1) || (gridY == -1)))
				{
					//Had pressed and released outside
					//					Random generator = new Random();					
					//					Color newColor = null;
					//										boolean checkColor = true;
					//										for (int i=1; i<=9; i++) {
					//											for (int j=1; j<=9; j++){
					//					
					//												while(checkColor) {
					//													switch (generator.nextInt(3)) {
					//					
					//													case 0:	
					//														newColor = new Color(0xD2B48C);
					//														if (myPanel.coveredCellColor[i][j].equals(newColor)){}
					//														else 
					//															checkColor= false;
					//														break;
					//													case 1:
					//														newColor = Color.YELLOW;
					//														if (myPanel.coveredCellColor[i][j].equals(newColor)){}
					//														else
					//															checkColor= false;
					//														break;
					//													case 2:
					//														newColor = Color.GREEN;
					//														if (myPanel.coveredCellColor[i][j].equals(newColor)){}
					//														else
					//															checkColor= false;
					//														break;
					//					
					//													}
					//													myPanel.coveredCellColor[i][j] = newColor;
					//													myPanel.repaint();
					//					
					//												}
					//												checkColor=true;
					//											}
					//										}
				}
				else if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY))
				{
					//Released the right button on a different cell where it was pressed
					//Do nothing
				} 
				else
				{
					//Released the right button on the same cell where it was pressed
					if ((gridX == 0) && (gridY == 0))
					{
						//On the left and top cell

					}
					else if ((gridX == 0) && (gridY == myPanel.TOTAL_ROWS - 1))
					{
						//On the left and bottom cell wish is invisible... do nothing 
					}
					else if (gridX == 0)
					{
						//On the leftmost column wish is invisible
					}
					else if (gridY == 0)
					{
						//On the top row wish is invisible

					}
					else
					{
						//On the grid other than on the left column and on the top row:
						if (!myPanel.firstClick) {
							if (!myPanel.coveredCellColor[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.RED))
							{ 
								//si la celda no es roja
								if (!myPanel.coveredCellColor[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.LIGHT_GRAY))
								{ 
									//si la celda no esta descubierta
									myPanel.coveredCellColor[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.RED;
									myPanel.numOfMinesLeft--;
									myPanel.flagColor[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.WHITE;
									myPanel.repaint();
								}
							}
							else {
								myPanel.coveredCellColor[myPanel.mouseDownGridX][myPanel.mouseDownGridY]= Color.WHITE;
								myPanel.numOfMinesLeft++;
								myPanel.cellNum[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = "";
								myPanel.repaint();
							}
						}
					}
				}
				break;
			default:    //Some other button (2 = Middle mouse button, etc.)
				//Do nothing
				break;
			}
		}
	}
}