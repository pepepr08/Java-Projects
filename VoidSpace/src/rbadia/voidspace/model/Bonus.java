package rbadia.voidspace.model;

import java.awt.Rectangle;

import rbadia.voidspace.main.GameLogic;

public class Bonus  extends Rectangle{

	private static final long serialVersionUID = 1L;
	
	private String bonusType = "None";
	private int bonusWidth = 15;
	private int bonusHeight = 15;
	
	public Bonus(GameLogic gameLogic, Asteroid asteroid) {
		this.setLocation(asteroid.x, asteroid.y);
		this.setSize(bonusWidth, bonusHeight);
	}

	public String getBonusType() {
		return bonusType;
	}

	public void setBonusType(String bonusType) {
		this.bonusType = bonusType;
	}
	
	public void setBonusLocation(int x, int y) {
		this.setLocation(x, y);
	}
}