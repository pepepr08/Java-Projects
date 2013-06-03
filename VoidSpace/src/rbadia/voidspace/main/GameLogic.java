package rbadia.voidspace.main;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Bonus;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Handles general game logic and status.
 */
public class GameLogic {
	private GameScreen gameScreen;
	private GameStatus status;
	private SoundManager soundMan;
	private Ship ship;
	private List<Asteroid> asteroids;
	private List<Bullet> bullets;
	private List<EnemyShip> enemyShips;
	private Bonus bonus;
	private List<Rectangle> asteroidsExplosionsLoc;

	Random rand;

	/**
	 * Create a new game logic handler
	 * @param gameScreen the game screen
	 */
	public GameLogic(GameScreen gameScreen){
		this.gameScreen = gameScreen;

		rand = new Random();

		// initialize game status information
		status = new GameStatus();

		// initialize the sound manager
		soundMan = new SoundManager();
		soundMan.playBackGroundMusic();

		// init some variables
		bullets = new ArrayList<Bullet>();
		asteroids = new ArrayList<Asteroid>();
		asteroidsExplosionsLoc = new ArrayList<Rectangle>();

	}

	/**
	 * Returns the game status
	 * @return the game status 
	 */
	public GameStatus getStatus() {
		return status;
	}

	public SoundManager getSoundMan() {
		return soundMan;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	/**
	 * Prepare for a new game.
	 */
	public void newGame(){

		status.setGameStarting(true);
		// init game variables
		bullets = new ArrayList<Bullet>();
		asteroids = new ArrayList<Asteroid>();
		enemyShips = new ArrayList<EnemyShip>();
		enemyShips.add(new EnemyShip(gameScreen));

		status.setShipsLeft(3);
		status.setGameOver(false);
		status.setYouWin(false);
		status.setAsteroidsDestroyed(0);
		status.setAsteroidsDestroyedByUser(0);
		status.setNewAsteroid(false);
		status.setLevel(1);
		status.setAliveAllEnemyShips(true);
		asteroidsExplosionsLoc.clear();

		setBonusOff();

		// init the ship and the asteroid
		newShip(gameScreen);
		newAsteroid(gameScreen);

		// prepare game screen
		gameScreen.doNewGame();

		// delay to display "Get Ready" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameStarting(false);
				status.setGameStarted(true);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	/**
	 * Prepare for a new game.
	 */
	public void newLevel(){
		status.setLevelStarting(true);
		status.setNewLevel(false);
		setBonusOff();

		if (status.getLevel()%10 == 0)
			enemyShips.add(new EnemyShip(gameScreen));
		
		for(int i = 0; i < enemyShips.size(); i++) {
			EnemyShip enemyShip = enemyShips.get(i);
			enemyShip.setLives(3);
			enemyShip.getEnemyBullets().clear();
			enemyShip.setLocation(rand.nextInt( (int)((gameScreen.getWidth()) - enemyShip.getWidth())), -20);
			enemyShip.setInitialEntrance(true);
		}

		// init game variables
		bullets = new ArrayList<Bullet>();
		asteroids = new ArrayList<Asteroid>();

		status.setAliveAllEnemyShips(true);
		status.setAllNewEnemyShip(true);
		status.setLevelStarting(true);



		// init the asteroid
		if (status.getLevel() < 3)
			for (int i = 0; i < 2*status.getLevel() - 1; i++)
				newAsteroid(gameScreen);
		else
			for (int i = 0; i < 2 + status.getLevel(); i++)
				newAsteroid(gameScreen);

		// delay to display "Level: X" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setAliveAllEnemyShips(true);
				status.setLevelStarting(false);
				status.setAllNewEnemyShip(true);				
				asteroidsExplosionsLoc.clear();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	/**
	 * Check game or level ending conditions.
	 */
	public void checkConditions(){
		// check game over conditions
		if(!status.isGameOver() && status.isGameStarted()){
			if(status.getShipsLeft() == 0) {
				gameOver();
			}
			//			if(status.getLevel() > status.getWinningLevel() && !status.isNukeDelay())
			//				youWin();
			if(status.isNewLevel()) {
				newLevel();
				soundMan.playLevelUpSound();

			}
		}
	}

	/**
	 * Actions to take when the game is over.
	 */
	public void gameOver(){
		status.setGameStarted(false);
		status.setGameOver(true);
		gameScreen.doGameOver();

		// delay to display "Game Over" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameOver(false);
				if (status.checkHighScore()){
				JOptionPane.showMessageDialog(null, "\t\t\tCongratulations!!\nYou got a new HighScore.",
							"VoidSpace: How To Play", JOptionPane.INFORMATION_MESSAGE);
				}
				status.setScore(0);

			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	/**
	 * Actions to take when the you win the game.
	 */
	public void youWin(){
		status.setGameStarted(false);
		status.setYouWin(true);
		status.setScore(0);
		gameScreen.doYouWin();

		// delay to display "YOU WIN!!!" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setYouWin(false);

			}
		});
		timer.setRepeats(false);
		timer.start();
	}


	/**
	 * Fire a bullet from ship.
	 */
	public void fireBullet(){
		Bullet bullet = new Bullet(ship);
		bullets.add(bullet);
		soundMan.playBulletSound();
	}

	/**
	 * Move a bullet once fired.
	 * @param bullet the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveBullet(Bullet bullet){
		if(bullet.getY() - bullet.getSpeed() >= 0){
			bullet.translate(0, -bullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}

	/**
	 * Create a new ship (and replace current one).
	 */
	public Ship newShip(GameScreen screen){
		this.ship = new Ship(screen);
		return ship;
	}

	/**
	 * Create a new ship (and replace current one).
	 */
	public List<EnemyShip> newEnemyShip(GameScreen screen){
		this.enemyShips.add(new EnemyShip(screen));
		this.enemyShips.add(new EnemyShip(screen));
		this.enemyShips.get(enemyShips.size()-1).setSpeed(status.getLevel()/5 + 1);

		return this.enemyShips;
	}

	/**
	 * Create a new asteroid.
	 */
	public List<Asteroid> newAsteroid(GameScreen screen){
		Asteroid asteroid = new Asteroid(screen);
		asteroid.setSpeed(rand.nextInt(3)+2);
		if (rand.nextInt(5) == 0) {
			asteroid.setBonus(true); //randomly sets a bonus asteroid
		}
		this.asteroids.add(asteroid);
		return this.asteroids;
	}

	public void setBonusOff() {

		status.setBonus("None");
		status.setRapidFire(false);
		status.setActiveBonus(false);
		status.setBonusAvailable(false);
		status.setActiveShield(false);
	}

	/**
	 * Returns the ship.
	 * @return the ship
	 */
	public Ship getShip() {
		return ship;
	}	
	public List<EnemyShip> getEnemyShips() {
		return enemyShips;
	}	
	public void setEnemyShips(List<EnemyShip> enemyShips) {
		this.enemyShips = enemyShips;
	}

	/**
	 * Returns the asteroid.
	 * @return the asteroid
	 */
	public List<Asteroid> getAsteroids() {
		return asteroids;
	}

	/**
	 * Returns the list of bullets.
	 * @return the list of bullets
	 */
	public List<Bullet> getBullets() {
		return bullets;
	}

	public Bonus getBonus() {
		return bonus;
	}

	public void setBonus(Bonus bonus) {
		this.bonus = bonus;
	}

	public List<Rectangle> getAsteroidsExplosionsLoc() {
		return asteroidsExplosionsLoc;
	}

	public void setAsteroidsExplosionsLoc(List<Rectangle> asteroidsExplosionsLoc) {
		this.asteroidsExplosionsLoc = asteroidsExplosionsLoc;
	}
	public void playMusic() {
		soundMan.playBackGroundMusic();
	}
	public void pauseMusic() {
		soundMan.stopBackgroundMusic();
	}
	public void playPauseSound() {
		soundMan.playPauseSound();
	}
}
