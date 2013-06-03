package rbadia.voidspace.main;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Container for game flags and/or status variables.
 */
public class GameStatus {
	// game flags
	private boolean gameStarted = false;
	private boolean gameStarting = false;
	private boolean levelStarting = false;
	private boolean gameOver = false;
	private boolean newLevel = false;
	private boolean youWin = false;
	private boolean GamePaused = false;
	private boolean starsCreated = false;
	private final int WINNING_LEVEL = 10;

	//bonus
	private boolean activeBonus = false;
	private boolean bonusAvailable = false;
	private boolean rapidFire = false;
	private boolean activeShield = false;
	private boolean destroyAll = false;
	private boolean nukeDelay = false;
	private String bonus = "None";

	//  variables
	private boolean newShip;
	private boolean newAsteroid;
	private boolean[] newEnemyShip = new boolean[100];
	private boolean[] aliveEnemyShip = new boolean[100];
	private int[] totalBullets = new int[100];
	private int[] enemyBulletsFired = new int[100];
	private int initialHighScore;


	private long asteroidsDestroyed = 0;
	private long asteroidsDestroyedByUser = 0;
	private long shieldActivatedTime = 0;
	private long score = 0;
	private int level = 1;
	private int shipsLeft;
	private Rectangle[] enemyExplosionLoc = new Rectangle[100];
	private PrintWriter out;

	private final String HOW_TO_PLAY = 
			"\n                             VoidSpace!!\n" +
					"\n-Destroy (4 + LevelNumber) asteroids to reach the next level.\n" +
					"-Finish level 10 to win the game!!\n" +
					"-There is an enemy ship in levels 2 to 10.\n" +
					"-Some asteroids has a bonus when destroyed.\n" +
					"-Four possible bonuses: Rapid Fire, Extra Life\n 		Shield and Nuke.\n\n" +
					"" +
					"Enjoy the Game!!!\n\n\n";

	public GameStatus() {
		for (int i = 0; i < 100; i++){
			totalBullets[i] = 3;
			enemyBulletsFired[i] = 0;
		}

		String filename = "leaderboard/highScore.txt";
		File inputFile = new File(filename);
		Scanner in;
		try {
			in = new Scanner(inputFile);
			setInitialHighScore(in.nextInt());

		} catch (FileNotFoundException e) {
			System.out.println("No file error.");
		}
	}

	/**
	 * Indicates if the game has already started or not.
	 * @return if the game has already started or not
	 */
	public synchronized boolean isGameStarted() {
		return gameStarted;
	}

	public synchronized void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	/**
	 * Indicates if the game is starting ("Get Ready" message is displaying) or not.
	 * @return if the game is starting or not.
	 */
	public synchronized boolean isGameStarting() {
		return gameStarting;
	}

	public synchronized void setGameStarting(boolean gameStarting) {
		this.gameStarting = gameStarting;
	}

	/**
	 * Indicates if the game has ended and the "Game Over" message is displaying.
	 * @return if the game has ended and the "Game Over" message is displaying.
	 */
	public synchronized boolean isGameOver() {
		return gameOver;
	}

	public synchronized void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	/**
	 * Indicates if a new ship should be created/drawn.
	 * @return if a new ship should be created/drawn
	 */
	public synchronized boolean isNewShip() {
		return newShip;
	}

	public synchronized void setNewShip(boolean newShip) {
		this.newShip = newShip;
	}	

	public synchronized boolean isNewEnemyShip(int i) {
		return this.newEnemyShip[i];
	}

	public synchronized void setNewEnemyShip(boolean x, int i) {
		this.newEnemyShip[i] = x;
	}	
	public synchronized void setAllNewEnemyShip(boolean x) {
		for (int i = 0; i<100; i++)
			this.newEnemyShip[i] = x;
	}

	/**
	 * Indicates if a new asteroid should be created/drawn.
	 * @return if a new asteroid should be created/drawn
	 */
	public synchronized boolean isNewAsteroid() {
		return newAsteroid;
	}

	public synchronized void setNewAsteroid(boolean newAsteroid) {
		this.newAsteroid = newAsteroid;
	}

	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */
	public synchronized long getAsteroidsDestroyed() {
		return asteroidsDestroyed;
	}

	public synchronized void setAsteroidsDestroyed (long asteroidsDestroyed) {
		this.asteroidsDestroyed = asteroidsDestroyed;
	}
	public synchronized long getAsteroidsDestroyedByUser() {
		return asteroidsDestroyedByUser;
	}

	public synchronized void setAsteroidsDestroyedByUser (long asteroidsDestroyedByUser) {
		this.asteroidsDestroyedByUser = asteroidsDestroyedByUser;
	}

	/**
	 * Returns the number ships/lives left.
	 * @return the number ships left
	 */
	public synchronized int getShipsLeft() {
		return shipsLeft;
	}

	public synchronized void setShipsLeft(int shipsLeft) {
		this.shipsLeft = shipsLeft;
	}

	public synchronized long getScore(){
		return score;
	}

	public synchronized void setScore(long score) {
		this.score = score;
	}
	public synchronized int getLevel() {
		return level;
	}

	public synchronized void setLevel(int level) {
		this.level = level;
	}

	public synchronized boolean isNewLevel() {
		return newLevel;
	}

	public synchronized void setNewLevel(boolean x) {
		this.newLevel = x;
	}
	public synchronized boolean isAliveEnemyShip(int i) {
		return aliveEnemyShip[i];
	}

	public synchronized void setAliveEnemyShip(boolean x, int i) {
		this.aliveEnemyShip[i] = x;
	}
	public synchronized void setAliveAllEnemyShips(boolean x) {
		for (int i = 0; i < 100; i++)
			this.aliveEnemyShip[i] = x;
	}

	public boolean isLevelStarting() {
		return this.levelStarting;
	}

	public void setLevelStarting(boolean levelStarting) {
		this.levelStarting = levelStarting;
	}

	public int getEnemyBulletsFired(int i) {
		return enemyBulletsFired[i];
	}

	public void setEnemyBulletsFired(int n, int i) {
		this.enemyBulletsFired[i] = n;
	}

	public int getTotalBullets(int i) {
		return totalBullets[i];
	}

	public void setTotalBullets(int n, int i) {
		this.totalBullets[i] = n;
	}

	public boolean isActiveBonus() {
		return activeBonus;
	}

	public void setActiveBonus(boolean activeBonus) {
		this.activeBonus = activeBonus;
	}

	public boolean isRapidFire() {
		return rapidFire;
	}

	public void setRapidFire(boolean rapidFire) {
		this.rapidFire = rapidFire;
	}

	public boolean isBonusAvailable() {
		return bonusAvailable;
	}

	public void setBonusAvailable(boolean bonusAvailable) {
		this.bonusAvailable = bonusAvailable;
	}

	/**
	 * Indicates if the game has been won and the "You Win!!!" message is displaying.
	 * @return if the game has won and the "You Win!!!" message is displaying.
	 */
	public synchronized boolean isYouWin() {
		return youWin;
	}

	public synchronized void setYouWin(boolean youWin) {
		this.youWin = youWin;
	}

	public String getHowToPlay() {
		return HOW_TO_PLAY;
	}

	public boolean isStarsCreated() {
		return starsCreated;
	}

	public void setStarsCreated(boolean starsCreated) {
		this.starsCreated = starsCreated;
	}

	public boolean isActiveShield() {
		return activeShield;
	}

	public void setActiveShield(boolean activeShield) {
		this.activeShield = activeShield;
	}

	public boolean isDestroyAll() {
		return destroyAll;
	}

	public void setDestroyAll(boolean destroyAll) {
		this.destroyAll = destroyAll;
	}

	public long getShieldActivatedTime() {
		return shieldActivatedTime;
	}

	public void setShieldActivatedTime(long shieldActivatedTime) {
		this.shieldActivatedTime = shieldActivatedTime;
	}

	public boolean isNukeDelay() {
		return nukeDelay;
	}

	public void setNukeDelay(boolean nukeDelay) {
		this.nukeDelay = nukeDelay;
	}

	public Rectangle getEnemyExplosionLoc(int i) {
		return enemyExplosionLoc[i];
	}

	public void setEnemyExplosionLoc(Rectangle enemyExplosionLoc, int i) {
		this.enemyExplosionLoc[i] = enemyExplosionLoc;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public int getWinningLevel() {
		return WINNING_LEVEL;
	}

	public boolean isGamePaused() {
		return GamePaused;
	}

	public void setGamePaused(boolean gamePaused) {
		GamePaused = gamePaused;
	}

	public void setInitialHighScore(int initialHighScore) {
		this.initialHighScore = initialHighScore;
	}

	public int getInitialHighScore() {
		return initialHighScore;
	}

	public boolean checkHighScore() {
		if (initialHighScore < score) {
			try {
				initialHighScore = (int) score;
				out = new PrintWriter(new File("leaderboard/highScore.txt"));
				out.println(score);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			return true;
		}
		return false;
	}




}
