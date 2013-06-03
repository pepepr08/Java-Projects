package rbadia.voidspace.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Bonus;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyBullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Main game screen. Handles all game graphics updates and some of the game logic.
 */
public class GameScreen extends JPanel {
	private static final long serialVersionUID = 1L;


	private BufferedImage backBuffer;
	private Graphics2D g2d;

	private static final int NEW_SHIP_DELAY = 500;
	private static final int NEW_ASTEROID_DELAY = 500;
	private static final int ENEMY_EXPLOSION_DELAY = 1000;
	private static final int SHIP_RESPAWN_DELAY = 2500;
	private static final int SHIELD_DURATION = 8000;
	private static final int NUKE_DELAY = 1500;

	public int screenWidth;
	public int screenHeight;
	public static final int NUM_OF_STARS = 75;

	private long lastShipTime;
	private long lastAsteroidTime;
	private long lastNukeTime = 0;
	private int[] starX = new int[NUM_OF_STARS];
	private int[] starY = new int[NUM_OF_STARS];
	private Color[] starColor = new Color[NUM_OF_STARS];

	private Rectangle asteroidExplosion;
	private Rectangle shipExplosion;

	private JLabel shipsValueLabel;
	private JLabel destroyedValueLabel;
	private JLabel bonusValueLabel;
	private JLabel scoreValueLabel;
	private JLabel highScoreValueLabel;


	private Random rand;

	private Font originalFont;
	private Font bigFont;
	private Font biggestFont;

	private GameStatus status;
	private SoundManager soundMan;
	private GraphicsManager graphicsMan;
	private GameLogic gameLogic;


	/**
	 * This method initializes 
	 * 
	 */
	public GameScreen() {
		super();
		// initialize random number generator
		rand = new Random();
		Dimension dim = this.getToolkit().getScreenSize();
		screenWidth = dim.width - 200; //Quitar el 200 y el 150///////////
		screenHeight = dim.height - 134 - 150;
		initialize();
		// init graphics manager
		graphicsMan = new GraphicsManager();
		// init back buffer image

		backBuffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
	}

	/**
	 * Initialization method (for VE compatibility).
	 */
	private void initialize() {
		// set panel properties
		this.setSize(new Dimension(screenWidth, screenHeight));
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
	}

	/**
	 * Update the game screen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw current back buffer to the actual game screen
		g.drawImage(backBuffer, 0, 0, this);
	}

	/**
	 * Update the game screen's backbuffer image.
	 */
	public void updateScreen(){
		Ship ship = gameLogic.getShip();
		List<EnemyShip> enemyShips = gameLogic.getEnemyShips();
		List<Asteroid> asteroids = gameLogic.getAsteroids();
		List<Bullet> bullets = gameLogic.getBullets();
		Bonus bonus = null;// = new Bonus (gameLogic, new Rectangle(0,0,10,10));

		// set original font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		// erase screen
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);

		// draw random stars
		drawStars();
		// if the game is starting, draw "Get Ready" message
		if(status.isGameStarting()){
			drawGetReady();
			return;
		}

		// if the game is over, draw the "Game Over" message
		if(status.isGameOver()){
			// draw the message
			drawGameOver();

			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
			if((currentTime - lastShipTime) < NEW_SHIP_DELAY){
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
			return;
		}

		// if the game is won, draw the "You Win" message
		if(status.isYouWin()) {
			// draw the message
			drawYouWin();

			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
			if((currentTime - lastShipTime) < NEW_SHIP_DELAY){
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
			return;
		}


		// the game has not started yet
		if(!status.isGameStarted()){
			// draw game title screen
			initialMessage();
			return;
		}

		if (status.isGamePaused() && status.isGameStarted()) {
			drawPauseLabel();
			//paint enemyShips and enemy bullets
			for (int i=0; i<enemyShips.size(); i++) {
				if (status.isAliveEnemyShip(i)){
					EnemyShip enemyShip = enemyShips.get(i);
					graphicsMan.drawEnemyShip(enemyShip, g2d, this);
					for (int j = 0; j<enemyShip.getEnemyBullets().size(); j++) {
						EnemyBullet bullet = enemyShip.getEnemyBullets().get(j);
						graphicsMan.drawEnemyBullet(bullet, g2d, this);
					}
				}
			}
			//paint asteroids
			for(int i=0; i<asteroids.size(); i++){
				Asteroid asteroid = asteroids.get(i);
				if(!asteroid.isNewAsteroid()){
					graphicsMan.drawAsteroid(asteroid, g2d, this);
				}
			}
			//paint bullets
			for(int i=0; i<bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				graphicsMan.drawBullet(bullet, g2d, this);
			}
		}

		//if the level is starting, show "Level: " label, draw the last asteroid explosion
		else if (status.isLevelStarting()) {
			drawLevelLabel();
			if (!status.isNukeDelay())
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			else {
				status.setBonus("Nuke");
				for(int i=0; i<gameLogic.getAsteroidsExplosionsLoc().size(); i++){
					graphicsMan.drawAsteroidExplosion(gameLogic.getAsteroidsExplosionsLoc().get(i), g2d, this);
				}
				for (int i=0; i<enemyShips.size(); i++)
					if (enemyShips.get(i).isShipExplotion()) {
						graphicsMan.drawEnemyExplosion(status.getEnemyExplosionLoc(i), g2d, this);
					}
			}
		}

		//if the level is already started
		else {
			// draw asteroid
			for(int i=0; i<asteroids.size(); i++){
				Asteroid asteroid = asteroids.get(i);
				int xDirection=asteroid.getRandomDirection(i);

				if(!asteroid.isNewAsteroid()){
					// draw the asteroid until it reaches the bottom of the screen
					if(asteroid.getY() + asteroid.getSpeed() < this.getHeight()){
						asteroid.translate(xDirection, asteroid.getSpeed());
						graphicsMan.drawAsteroid(asteroid, g2d, this);
					}
					else {//draw the asteroid at the top of the screen again
						asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), -10);
						asteroid.setSpeed(rand.nextInt(3)+2);
					}
				}

				else {
					long currentTime = System.currentTimeMillis();
					if((currentTime - asteroid.getDestroyedTime()) > NEW_ASTEROID_DELAY){
						// draw a new asteroid
						asteroid.setDestroyedTime(currentTime);
						asteroid.setNewAsteroid(false);
						asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), -10);
						asteroid.setSpeed(rand.nextInt(3)+2);
						asteroid.setBonus(true); //randomly sets a bonus asteroid 33% CHANCE

					}
					else {						
						//draw explosion
						graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
					}
				}
			}

			// draw bullets
			for(int i=0; i<bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				graphicsMan.drawBullet(bullet, g2d, this);

				boolean remove = gameLogic.moveBullet(bullet);
				if(remove) {
					bullets.remove(i);
					i--;
				}
			}

			//draw enemy ship
			for (int i=0; i<enemyShips.size(); i++) {
				EnemyShip enemyShip = enemyShips.get(i);
				if (status.isAliveEnemyShip(i)) {
					//if the enemy is exploding
					if (enemyShip.isShipExplotion() && !status.isNukeDelay()) {
						long currentTime = System.currentTimeMillis();
						if((currentTime - enemyShip.getDestroyedTime()) > ENEMY_EXPLOSION_DELAY){
							enemyShip.setShipExplotion(false);
							status.setAliveEnemyShip(false, i);
						}
						else {
							// draw explosion
							graphicsMan.drawEnemyExplosion(new Rectangle(
									enemyShip.x,
									enemyShip.y,
									enemyShip.width,
									enemyShip.height), g2d, this);
						}
					}

					//if the enemy is not exploding
					else {
						if (!status.isNewEnemyShip(i)) {
							// draw it in its current location
							enemyShip.move();
							graphicsMan.drawEnemyShip(enemyShip, g2d, this);
						}
						else {
							status.setNewEnemyShip(false, i);
							status.setEnemyBulletsFired(status.getTotalBullets(i), i);
						}

						// Fire enemy bullets
						long currentTime = System.currentTimeMillis();
						long lastBulletTime = enemyShip.getLastBulletTime();
						if((currentTime - lastBulletTime) > 1000/5 && enemyShip.isFiringBullets()){
							enemyShip.setLastBulletTime(currentTime);
							enemyShip.fireBullet();
							soundMan.playEnemyBulletSound();
							status.setEnemyBulletsFired(status.getEnemyBulletsFired(i) + 1, i);
						}
						if (status.getEnemyBulletsFired(i) >= status.getTotalBullets(i)) {
							enemyShip.setFiringBullets(false);
							if (currentTime - enemyShip.getLastBulletTime() > 1000*enemyShip.getFireDelayTime()) {
								enemyShip.setFiringBullets(true); 
								status.setTotalBullets(rand.nextInt(3) + 1, i);
								enemyShip.setFireDelayTime(rand.nextInt(3) + 1);
								status.setEnemyBulletsFired(0, i);
							}
						}

						//check bullet-enemy collision
						for(int j=0; j<bullets.size(); j++){
							Bullet bullet = bullets.get(j);
							//if enemy has no lives left
							if (enemyShip.intersects(bullet)) {
								enemyShip.setLives(enemyShip.getLives() - 1);
								if (enemyShip.getLives() == 0){
									soundMan.playEnemyShipExplosionSound();
									enemyShip.setDestroyedTime(System.currentTimeMillis());
									enemyShip.setShipExplotion(true);
									bullets.remove(j);

									// increase score enemy destroyed count
									status.setScore(status.getScore() + 500 );
									break;
								}
								else {
									soundMan.playEnemyShipHitSound();
									bullets.remove(j);
								}
							}
						}
					}
					//move enemy bullets
					enemyShip.moveBullets();
					for (int j = 0; j<enemyShip.getEnemyBullets().size(); j++) {
						EnemyBullet bullet = enemyShip.getEnemyBullets().get(j);
						graphicsMan.drawEnemyBullet(bullet, g2d, this);

						//check if enemy bullet hits you
						if (bullet.intersects(ship) 
								&& (System.currentTimeMillis() - lastShipTime) > SHIP_RESPAWN_DELAY) {

							if (!status.isActiveShield()) {
								status.setShipsLeft(status.getShipsLeft() - 1);

								// "remove" ship
								shipExplosion = new Rectangle(
										ship.x,
										ship.y,
										ship.width,
										ship.height);
								ship.setLocation(this.getWidth() + ship.width, -ship.height);
								status.setNewShip(true);
								lastShipTime = System.currentTimeMillis();

								// play ship explosion sound
								soundMan.playShipExplosionSound();

								//erase active bonus
								status.setBonusAvailable(false);
								status.setActiveBonus(false);
								status.setBonus("None");
								status.setRapidFire(false);
							}
							enemyShip.getEnemyBullets().remove(j);
						}
					}
				}
			}
		}

		// draw ship
		if(!status.isNewShip()){
			// draw it in its current location
			graphicsMan.drawShip(ship, g2d, this);
			if (ship.getX() < 0) {
				Ship rec = new Ship(this);
				rec.setBounds(ship);
				rec.translate(screenWidth - ship.width/2, 0);
				graphicsMan.drawShip(rec, g2d, this);
			}
			else if (ship.getX() + ship.width > screenWidth- ship.width/2) {
				Ship rec = new Ship(this);
				rec.setBounds(ship);
				rec.translate(-screenWidth + ship.width/2, 0);
				graphicsMan.drawShip(rec, g2d, this);
			}
		}
		else{
			// draw a new one
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastShipTime) > NEW_SHIP_DELAY){
				lastShipTime = currentTime;
				status.setNewShip(false);
				ship = gameLogic.newShip(this);
			}
			else{
				// draw explosion
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
		}

		//check ship/enemyShip collision
		for (int i = 0; i < enemyShips.size(); i++) {
			EnemyShip enemyShip = enemyShips.get(i);
			if (enemyShip.intersects(ship) && status.isAliveEnemyShip(i)
					&& (System.currentTimeMillis() - lastShipTime) > SHIP_RESPAWN_DELAY) {

				//explode enemyShip
				soundMan.playEnemyShipExplosionSound();
				enemyShip.setDestroyedTime(System.currentTimeMillis());
				enemyShip.setShipExplotion(true);

				//explode ship
				if(!status.isActiveShield()) {
					// decrease number of ships left
					status.setShipsLeft(status.getShipsLeft() - 1);
					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(this.getWidth() + ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();

					// play ship explosion sound
					soundMan.playShipExplosionSound();

					//erase active bonus
					status.setBonusAvailable(false);
					status.setActiveBonus(false);
					status.setBonus("None");
					status.setRapidFire(false);
				}
			}
		}

		// check bullet-asteroid collisions
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			for(int j=0; j<asteroids.size(); j++){
				Asteroid asteroid = asteroids.get(j);

				if(asteroid.intersects(bullet)){
					// increase asteroids destroyed count
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);
					status.setAsteroidsDestroyedByUser(status.getAsteroidsDestroyedByUser() + 1);

					// increase score asteroid destroyed count
					status.setScore(status.getScore() + 100 );

					//check if current level is completed
					if(isLevelComplete(status)) {}
					//check if bonus
					else if (asteroid.isBonus() && !status.isActiveBonus() && !status.isBonusAvailable()) {
						bonus = new Bonus(gameLogic, asteroid);
						gameLogic.setBonus(bonus);
						status.setBonusAvailable(true);
						graphicsMan.drawBonus(bonus, g2d, this);
					}

					//"remove" asteroid
					asteroidExplosion = new Rectangle(
							asteroid.x,
							asteroid.y,
							asteroid.width,
							asteroid.height);
					asteroid.setLocation(-asteroid.width, -asteroid.height);
					asteroid.setNewAsteroid(true);
					asteroid.setDestroyedTime(System.currentTimeMillis());

					// play asteroid explosion sound
					soundMan.playAsteroidExplosionSound();

					// remove bullet
					bullets.remove(i);
					break;
				}
			}
		}

		// check ship-asteroid collisions
		for(int j=0; j<asteroids.size(); j++){
			Asteroid asteroid = asteroids.get(j);

			if(asteroid.intersects(ship) && 
					(System.currentTimeMillis() - lastShipTime) > SHIP_RESPAWN_DELAY){

				if(!status.isActiveShield()) {
					// decrease number of ships left
					status.setShipsLeft(status.getShipsLeft() - 1);
					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(this.getWidth() + ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();

					// play ship explosion sound
					soundMan.playShipExplosionSound();

					//erase active bonus
					status.setBonusAvailable(false);
					status.setActiveBonus(false);
					status.setBonus("None");
					status.setRapidFire(false);

				} 
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

				// "remove" asteroid
				asteroidExplosion = new Rectangle(
						asteroid.x,
						asteroid.y,
						asteroid.width,
						asteroid.height);
				asteroid.setLocation(-asteroid.width, -asteroid.height);
				status.setNewAsteroid(true);
				lastAsteroidTime = System.currentTimeMillis();
				asteroid.setDestroyedTime(System.currentTimeMillis());

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();
			}

			long currentTime = System.currentTimeMillis();
			if((currentTime - asteroid.getDestroyedTime()) > NEW_ASTEROID_DELAY 
					&& !asteroid.isNewAsteroid()){
			}
			else {						
				//draw explosion
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}

		//Draw Bonus if available
		if (status.isBonusAvailable() && gameLogic.getBonus()!= null) {
			graphicsMan.drawBonus(gameLogic.getBonus(), g2d, this);
			if (ship.intersects(gameLogic.getBonus())) {
				status.setBonusAvailable(false);
				status.setActiveBonus(true);
				switch(rand.nextInt(4)) {
				case 0: //Rapid Fire Bonus  25% probability
					status.setRapidFire(true);
					status.setBonus("Rapid Fire");
					break;
				case 1: //Shield Bonus  25% 
					status.setActiveShield(true);
					status.setShieldActivatedTime(System.currentTimeMillis());
					status.setBonus("Shield");
					soundMan.playShieldUpSound();
					break;
				case 2:
					//One life up  25%
					status.setShipsLeft(status.getShipsLeft() + 1);
					soundMan.playLifeUpSound();
					break;
				case 3: //Nuke Bonus 25%
					status.setDestroyAll(true);
					status.setBonus("Nuke");
					soundMan.playNukeSound();
					break;
				}
				if (status.getBonus().equalsIgnoreCase("None"))
					status.setBonus("Extra-Life");
			}
		}

		//if shield is activated
		if (status.isActiveShield()) {
			if (System.currentTimeMillis() - status.getShieldActivatedTime() < SHIELD_DURATION)
				graphicsMan.drawShield(ship, g2d, this);
			else {
				status.setActiveShield(false);
				status.setActiveBonus(false);
				status.setBonus("None");
			}
		}

		//if nuke bonus is taken
		if (status.isDestroyAll())
		{
			lastNukeTime = System.currentTimeMillis();
			status.setDestroyAll(false);
			status.setNukeDelay(true);
			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + asteroids.size());
			status.setAsteroidsDestroyedByUser(status.getAsteroidsDestroyedByUser() + asteroids.size());


			//set all asteroids locations

			for(int i=0; i<asteroids.size(); i++){
				Asteroid asteroid = asteroids.get(i);
				status.setScore(status.getScore() + 100 );
				// "remove" asteroid
				gameLogic.getAsteroidsExplosionsLoc().add(new Rectangle(
						asteroid.x,
						asteroid.y,
						asteroid.width,
						asteroid.height));
				asteroid.setLocation(-asteroid.width, -asteroid.height);
				status.setNewAsteroid(true);
			}

			for (int i = 0; i < enemyShips.size(); i++) {
				EnemyShip enemyShip = enemyShips.get(i);
				if (status.isAliveEnemyShip(i)) {
					soundMan.playAsteroidExplosionSound();
					status.setScore(status.getScore() + 500 );

					enemyShip.setShipExplotion(true);
					//set enemy explosion location
					status.setEnemyExplosionLoc(new Rectangle(
							enemyShip.x,
							enemyShip.y,
							enemyShip.width,
							enemyShip.height), i);
				}
			}
			isLevelComplete(status);

		}
		if (status.isNukeDelay()) {
			status.setBonus("Nuke");
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastNukeTime < NUKE_DELAY) {
				//destroy all asteroids
				for(int i=0; i<gameLogic.getAsteroidsExplosionsLoc().size(); i++){
					graphicsMan.drawAsteroidExplosion(gameLogic.getAsteroidsExplosionsLoc().get(i), g2d, this);
				}
				for (int i = 0; i < enemyShips.size(); i++) {
					EnemyShip enemyShip = enemyShips.get(i);

					if (enemyShip != null && enemyShip.isShipExplotion() && !isLevelComplete(status)) {
						graphicsMan.drawEnemyExplosion(status.getEnemyExplosionLoc(i), g2d, this);
						status.setAliveEnemyShip(false, i);
					}
				}
			}
			else {
				gameLogic.getAsteroidsExplosionsLoc().clear();
				for (int i = 0; i < enemyShips.size(); i++) {
					EnemyShip enemyShip = enemyShips.get(i);
					if (enemyShip != null)
						enemyShip.setShipExplotion(false);
				}
				status.setNukeDelay(false);
				status.setActiveBonus(false);
				status.setBonus("None");
			}
		}

		// update asteroids destroyed label
		destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));

		// update ships left label
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));

		// update score label
		scoreValueLabel.setText(Long.toString(status.getScore()));

		if (status.getScore() < status.getInitialHighScore())
			highScoreValueLabel.setText(Long.toString(status.getInitialHighScore()));
		else
			highScoreValueLabel.setText(Long.toString(status.getScore()));

		//update bonus label
		bonusValueLabel.setText(status.getBonus());

	}

	/**
	 * Draws the "Game Over" message.
	 */
	private void drawGameOver() {
		String gameOverStr = " GAME OVER ";
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.WHITE);
		g2d.drawString(gameOverStr, strX, strY);
	}

	/**
	 * Draws the "You win!!" message.
	 */
	private void drawYouWin(){
		String gameWinStr = "  YOU WIN!   ";
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameWinStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameWinStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.WHITE);
		g2d.drawString(gameWinStr, strX, strY);

	}

	/**
	 * Draws the initial "Get Ready!" message.
	 */
	private void drawGetReady() {
		String readyStr = "Get Ready!!";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);
	}

	private void drawPauseLabel() {
		String readyStr = "Game Paused";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);
	}

	private void drawLevelLabel() {

		String readyStr = "Level: " + status.getLevel();
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);

	}

	/**
	 * Draws the specified number of stars randomly on the game screen.
	 * @param numberOfStars the number of stars to draw
	 */
	private void drawStars() {

		int grayStars = 0;
		for(int i=0; i<NUM_OF_STARS; i++){
			if (!status.isStarsCreated()) {
				if(grayStars < NUM_OF_STARS*3/10) {
					starColor[i] = Color.GRAY; 
					grayStars++;
				}
				else
					starColor[i] = Color.DARK_GRAY;

				starX[i] = (int)(Math.random() * this.getWidth());
				starY[i] = (int)(Math.random() * this.getHeight());
			}

			// Estrella
			Polygon p2 = new Polygon();
			p2.addPoint(starX[i] + 0, starY[i] + 3);
			p2.addPoint(starX[i] + 3, starY[i] + 3);
			p2.addPoint(starX[i] + 5, starY[i] + 0);
			p2.addPoint(starX[i] + 7, starY[i] + 3);
			p2.addPoint(starX[i] + 10, starY[i] + 3);
			p2.addPoint(starX[i] + 7, starY[i] + 5);
			p2.addPoint(starX[i] + 9, starY[i] + 8);
			p2.addPoint(starX[i] + 5, starY[i] + 6);
			p2.addPoint(starX[i] + 1, starY[i] + 8);
			p2.addPoint(starX[i] + 3, starY[i] + 5);
			g2d.setColor(starColor[i]);
			g2d.fillPolygon(p2);

			if (starY[i] + 8 > this.getHeight()){
				starY[i] = 0; 
				starX[i] = (int)(Math.random() * this.getWidth());
			}
			else if (!status.isGamePaused() || !status.isGameStarted())
				starY[i] = starY[i] + 1;

		}
		status.setStarsCreated(true);

	}

	/**
	 * Display initial game title screen.
	 */
	private void initialMessage() {
		String gameTitleStr = "  Void Space  ";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > this.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2 - ascent;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start a New Game.";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);

		String howToPlayStr = "Press <Enter> for How-To-Play.";
		strWidth = fm.stringWidth(howToPlayStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(howToPlayStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);
		fm = g2d.getFontMetrics();

	}

	/**
	 * Prepare screen for game over.
	 */
	public void doGameOver(){
		shipsValueLabel.setForeground(new Color(128, 0, 0));
	}

	/**
	 * Prepare screen for you win.
	 */
	public void doYouWin(){
		shipsValueLabel.setForeground(new Color(128, 0, 0));
	}


	/**
	 * Prepare screen for a new game.
	 */
	public void doNewGame(){		
		lastAsteroidTime = -NEW_ASTEROID_DELAY;
		lastShipTime = -NEW_SHIP_DELAY;
		lastNukeTime = 0;

		bigFont = originalFont;
		biggestFont = null;

		// set labels' text
		shipsValueLabel.setForeground(Color.BLACK);
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));
		destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));
		//
		scoreValueLabel.setText(Long.toString(status.getScore()));
		highScoreValueLabel.setText(Long.toString(status.getInitialHighScore()));
		//
		bonusValueLabel.setText(status.getBonus());
	}

	/**
	 * Sets the game graphics manager.
	 * @param graphicsMan the graphics manager
	 */
	public void setGraphicsMan(GraphicsManager graphicsMan) {
		this.graphicsMan = graphicsMan;
	}

	/**
	 * Sets the game logic handler
	 * @param gameLogic the game logic handler
	 */
	public GameLogic getGameLogic() {
		return this.gameLogic;
	}
	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		this.status = gameLogic.getStatus();
		this.soundMan = gameLogic.getSoundMan();
	}

	public boolean isLevelComplete(GameStatus status) {
		if(status.getAsteroidsDestroyedByUser() >= (status.getLevel() + 4)) {
			status.setLevel(status.getLevel() + 1);
			status.setAsteroidsDestroyedByUser(0);
			status.setNewLevel(true);  
			return true;
		}
		else 
			return false;

	}

	/**
	 * Sets the label that displays the value for asteroids destroyed.
	 * @param destroyedValueLabel the label to set
	 */
	public void setDestroyedValueLabel(JLabel destroyedValueLabel) {
		this.destroyedValueLabel = destroyedValueLabel;
	}

	/**
	 * Sets the label that displays the value for ship (lives) left
	 * @param shipsValueLabel the label to set
	 */
	public void setShipsValueLabel(JLabel shipsValueLabel) {
		this.shipsValueLabel = shipsValueLabel;
	}

	public void setScoreValueLabel(JLabel scoreValueLabel){
		this.scoreValueLabel = scoreValueLabel;
	}	
	public void setHighScoreValueLabel(JLabel highScoreValueLabel){
		this.highScoreValueLabel = highScoreValueLabel;
	}

	public void setBonusValueLabel(JLabel bonusValueLabel) {
		this.bonusValueLabel = bonusValueLabel;
	}
	public void setScreenDimensions(int width, int height) {
		this.screenHeight = height;
		this.screenWidth = width;
	}

}
