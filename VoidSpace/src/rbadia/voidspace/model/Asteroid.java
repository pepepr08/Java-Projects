package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class Asteroid extends Rectangle {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_SPEED = 4;

	private int asteroidWidth = 32;
	private int asteroidHeight = 32;
	private int speed = DEFAULT_SPEED;
	private long destroyedTime = 0;
	private boolean newAsteroid;
	private boolean bonus = false;

	private Random rand = new Random();

	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public Asteroid(GameScreen screen){
		this.setLocation(rand.nextInt(screen.getWidth() - asteroidWidth), 0);
		this.setSize(asteroidWidth, asteroidHeight);
	}
	
	public int getRandomDirection(int i) {
		int xDirection = 0;
		if (i%3 == 0)
			xDirection = 0;
		else if (i%3 == 1)
			xDirection=-1;
		else if (i%3 == 2)
			xDirection=1;
		return xDirection;
		
	}

	public int getAsteroidWidth() {
		return asteroidWidth;
	}
	public int getAsteroidHeight() {
		return asteroidHeight;
	}	
	public long getDestroyedTime() {
		return destroyedTime;
	}
	public void setDestroyedTime(long x) {
		destroyedTime = x;
	}
	public boolean isNewAsteroid() {
		return newAsteroid;
	}
	public void setNewAsteroid(boolean x) {
		newAsteroid = x;
	}
	
	/**
	 * Returns the current asteroid speed
	 * @return the current asteroid speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Set the current asteroid speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Returns the default asteroid speed.
	 * @return the default asteroid speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}

	public boolean isBonus() {
		return bonus;
	}

	public void setBonus(boolean bonus) {
		this.bonus = bonus;
	}
}
