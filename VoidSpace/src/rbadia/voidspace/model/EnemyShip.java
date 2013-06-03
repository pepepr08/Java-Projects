package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class EnemyShip extends Rectangle {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_SPEED = 1;

	private long destroyedTime = 0;
	private long lastBulletTime;
	private long shipCreatedTime=0;	
	private final long DOUBLE_SPEED_DELAY = 1;	
	private int shipWidth = 60;
	private int shipHeight = 26;
	private int speed = DEFAULT_SPEED;
	private int yLimit = 100;
	private int lives = 3;
	private int fireDelayTime = 2;
	private int randNum = 1000;
	private boolean up = false;
	private boolean down = true;
	private boolean right = true;
	private boolean left = false;
	private boolean initialEntrance;
	private boolean shipExplotion = false;
	private boolean firingBullets = true;
	private boolean doubleSpeed = false;
	private List<EnemyBullet> enemyBullets = new ArrayList<EnemyBullet>();

	private GameScreen screen;


	private Random rand = new Random();

	/**
	 * Creates a new ship at the default initial location. 
	 * @param screen the game screen
	 */
	public EnemyShip(GameScreen screen) {
		this.setShipCreatedTime(System.currentTimeMillis());
		this.setLocation(rand.nextInt(screen.getWidth() - shipWidth), -20);
		this.setSize(shipWidth, shipHeight);
		this.screen = screen;
		this.setLastBulletTime(System.currentTimeMillis());
		this.randNum = rand.nextInt(5) + 4;
	}

	/**
	 * Move the ship up
	 * @param ship the ship
	 */
	public void move() {
		
		//Change Direction Randomly
		if (( (System.currentTimeMillis()-shipCreatedTime) /1000) == randNum && !doubleSpeed) {
			this.speed = this.speed + 1; 
			
			doubleSpeed = true;
			this.setYLimit(100 + rand.nextInt(80));

			if (this.getCenterX() > screen.getGameLogic().getShip().getCenterX()) {
				right = false; 
				left = true;
			}
			else {
				right = true; 
				left = false;
			}
			down = true;
			up = false;
		}
		else if (doubleSpeed && ((System.currentTimeMillis()-shipCreatedTime)/1000) == randNum + DOUBLE_SPEED_DELAY) {
			this.speed = this.speed - 1; 
			doubleSpeed = false;
			shipCreatedTime = System.currentTimeMillis();
			this.randNum = rand.nextInt(5) + 1;
			this.setYLimit(100);
		}	

		if (initialEntrance)
			moveShipDown(this.yLimit);
		else {
			if (down)
				moveShipDown(this.yLimit);
			else if (up)
				moveShipUp();
			if (right)
				moveShipRight(screen.getWidth());
			else if (left)
				moveShipLeft();			
		}
	}	

	private void moveShipUp(){
		if(this.getY() - this.getSpeed() >= 0){
			this.translate(0, -this.getSpeed());
		}
		else {
			up = false;
			down = true;
		}
	}

	/**
	 * Move the ship down
	 * @param ship the ship
	 */
	private void moveShipDown(int limit){
		if(this.getY() + this.getSpeed() + this.height < limit){
			this.translate(0, this.getSpeed());
		}
		else {
			initialEntrance = false;
			down = false;
			up = true;
		}	
	}

	/**
	 * Move the ship left
	 * @param ship the ship
	 */
	private void moveShipLeft(){
		if(this.getX() - this.getSpeed() >= 0){
			this.translate(-this.getSpeed(), 0);
		}
		else {
			left = false;
			right = true;
		}
	}

	/**
	 * Move the ship right
	 * @param ship the ship
	 */
	private void moveShipRight(int screenWidth){
		if(this.getX() + this.getSpeed() + this.width < screenWidth){
			this.translate(this.getSpeed(), 0);
		}
		else {
			left = true;
			right = false;
		}
	}

	/**
	 * Get the default ship width
	 * @return the default ship width
	 */
	public int getShipWidth() {
		return shipWidth;
	}

	/**
	 * Get the default ship height
	 * @return the default ship height
	 */
	public int getShipHeight() {
		return shipHeight;
	}

	/**
	 * Returns the current ship speed
	 * @return the current ship speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Set the current ship speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Returns the default ship speed.
	 * @return the default ship speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}

	public long getDestroyedTime() {
		return destroyedTime;
	}
	public void setDestroyedTime(long x) {
		destroyedTime = x;
	}
	public boolean isShipExplotion() {
		return shipExplotion;
	}
	public void setShipExplotion(boolean x) {
		this.shipExplotion = x;
	}

	public List<EnemyBullet> getEnemyBullets() {
		return enemyBullets;
	}

	public void setEnemyBullets(List<EnemyBullet> enemyBullets) {
		this.enemyBullets = enemyBullets;
	}
	public void fireBullet() {
		EnemyBullet bullet = new EnemyBullet(this);
		enemyBullets.add(bullet);
		this.lastBulletTime = System.currentTimeMillis();

	}
	public void moveBullets(){

		for (int i = 0; i<enemyBullets.size(); i++) {
			EnemyBullet bullet = this.enemyBullets.get(i);
			if(bullet.getY() + bullet.getSpeed() <= screen.getHeight()){
				bullet.translate(0, bullet.getSpeed());
			}
			else {
				this.enemyBullets.remove(i);
				i--;
			}
		}
	}

	public boolean isFiringBullets() {
		return firingBullets;
	}

	public void setFiringBullets(boolean firingBullets) {
		this.firingBullets = firingBullets;
	}

	public int getFireDelayTime() {
		return fireDelayTime;
	}

	public void setFireDelayTime(int fireDelayTime) {
		this.fireDelayTime = fireDelayTime;
	}

	public long getLastBulletTime() {
		return lastBulletTime;
	}

	public void setLastBulletTime(long lastBulletTime) {
		this.lastBulletTime = lastBulletTime;
	}

	public int getYLimit() {
		return yLimit;
	}

	public void setYLimit(int yLimit) {
		this.yLimit = yLimit;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	public void setInitialEntrance(boolean b) {
		initialEntrance = b;
	}

	public long getShipCreatedTime() {
		return shipCreatedTime;
	}

	public void setShipCreatedTime(long shipCreatedTime) {
		this.shipCreatedTime = shipCreatedTime;
	}
}
