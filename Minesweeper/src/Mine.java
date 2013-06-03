import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Mine
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
		
		Graphics2D g1 = (Graphics2D) g2;
		g1.setColor(color);
		
		Ellipse2D.Double ellipse = new Ellipse2D.Double(xLeft, yTop+8, 20, 20);
		g1.fill(ellipse);
		Line2D.Double segment1 = new Line2D.Double(xLeft, yTop+8, xLeft+20, yTop+28);
		g1.draw(segment1);
		Line2D.Double segment2 = new Line2D.Double(xLeft+20, yTop+8, xLeft, yTop+28);
		g1.draw(segment2);
		Line2D.Double segment3 = new Line2D.Double(xLeft+10, yTop+5, xLeft+10, yTop+31);
		g1.draw(segment3);
		Line2D.Double segment4 = new Line2D.Double(xLeft-3, yTop+18, xLeft+23, yTop+18);
		g1.draw(segment4);
	}
}