import java.awt.Color;
import java.awt.Graphics;

public class Flag
{
	private int xLeft;
	private int yTop;	

	public void setLocation(int x, int y)
	{
		xLeft = x;
		yTop = y;
	}
	public void draw(Graphics g2, Color color)
	{
		
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		xPoints[0]= xLeft + 10;
		xPoints[1]= xLeft + 15;
		xPoints[2]= xLeft + 10;
		yPoints[0]= yTop + 10;
		yPoints[1]= yTop + 15;
		yPoints[2]= yTop + 20;

		g2.setColor(color);
		g2.fillPolygon(xPoints, yPoints, 3);
		g2.drawLine(xLeft + 10, yTop + 11, xLeft + 10, yTop + 25);
	}
}
