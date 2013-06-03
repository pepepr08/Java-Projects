package rbadia.voidspace.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import rbadia.voidspace.model.Ship;

/**
 * Handles user input events.
 */
public class InputHandler implements KeyListener{
	private boolean leftIsPressed;
	private boolean rightIsPressed;
	private boolean downIsPressed;
	private boolean upIsPressed;
	private boolean spaceIsPressed;
	private boolean shiftIsPressed;

	private long lastBulletTime;

	private GameLogic gameLogic;

	/**
	 * Create a new input handler
	 * @param gameLogic the game logic handler
	 */
	public InputHandler(GameLogic gameLogic){
		this.gameLogic = gameLogic;
	}

	/**
	 * Handle user input after screen update.
	 * @param gameScreen he game screen
	 */
	public void handleInput(GameScreen gameScreen){
		GameStatus status = gameLogic.getStatus();
		if(!status.isGameOver() && !status.isNewShip() && !status.isGameStarting() 
				&& status.isGameStarted() && !status.isGamePaused()){
			// fire bullet if space is pressed

			if(spaceIsPressed && !status.isGameStarting() && !status.isLevelStarting()){
				// fire only up to 5 bullets per second
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastBulletTime) > 1000/5 && !gameLogic.getStatus().isRapidFire()){
					lastBulletTime = currentTime;
					gameLogic.fireBullet();
				}
				else if ((currentTime - lastBulletTime) > 1000/10 && gameLogic.getStatus().isRapidFire()) {
					lastBulletTime = currentTime;
					gameLogic.fireBullet();
				}
			}

			Ship ship = gameLogic.getShip();

			if(shiftIsPressed){
				ship.setSpeed(ship.getDefaultSpeed() * 2);
			}

			if(upIsPressed){
				moveShipUp(ship);
			}

			if(downIsPressed){
				moveShipDown(ship, gameScreen.getHeight());
			}

			if(leftIsPressed){
				moveShipLeft(ship);
			}

			if(rightIsPressed){
				moveShipRight(ship, gameScreen.getWidth());
			}
		}
	}

	/**
	 * Move the ship up
	 * @param ship the ship
	 */
	private void moveShipUp(Ship ship){
		if(ship.getY() - ship.getSpeed() >= 0){
			ship.translate(0, -ship.getSpeed());
		}
	}

	/**
	 * Move the ship down
	 * @param ship the ship
	 */
	private void moveShipDown(Ship ship, int screenHeight){
		if(ship.getY() + ship.getSpeed() + ship.height < screenHeight){
			ship.translate(0, ship.getSpeed());
		}
	}

	/**
	 * Move the ship left
	 * @param ship the ship
	 */
	private void moveShipLeft(Ship ship){
		if(ship.getX() - ship.getSpeed() + ship.width/2 +3 >= 0){
			ship.translate(-ship.getSpeed(), 0);
		}
		else
			ship.translate(gameLogic.getGameScreen().getWidth(), 0);

	}

	/**
	 * Move the ship right
	 * @param ship the ship
	 */
	private void moveShipRight(Ship ship, int screenWidth){
		if(ship.getX() + ship.getSpeed() + ship.width/2 < screenWidth){
			ship.translate(ship.getSpeed(), 0);
		}
		else
			ship.translate(-screenWidth, 0);

	}

	/**
	 * Handle a key input event.
	 */
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			this.upIsPressed = true;
			break;
		case KeyEvent.VK_DOWN:
			this.downIsPressed = true;
			break;
		case KeyEvent.VK_LEFT:
			this.leftIsPressed = true;
			break;
		case KeyEvent.VK_RIGHT:
			this.rightIsPressed = true;
			break;
		case KeyEvent.VK_SPACE:
			GameStatus status = gameLogic.getStatus();
			if(!status.isGameStarted() && !status.isGameOver() 
					&& !status.isGameStarting() && !status.isYouWin()){
				// new game
				lastBulletTime = System.currentTimeMillis();
				leftIsPressed = false;
				rightIsPressed = false;
				downIsPressed = false;
				upIsPressed = false;
				spaceIsPressed = false;
				gameLogic.newGame();
			}
			else{
				this.spaceIsPressed = true;
			}
			break;
		case KeyEvent.VK_SHIFT:
			this.shiftIsPressed = true;
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(1);
			break;
		case KeyEvent.VK_CONTROL:
			gameLogic.getStatus().setShipsLeft(3);
			break;
		case KeyEvent.VK_ENTER:
			GameStatus status1 = gameLogic.getStatus();
			if(!status1.isGameStarted() && !status1.isGameOver() 
					&& !status1.isGameStarting()  && !status1.isYouWin()){
				JOptionPane.showMessageDialog(null, gameLogic.getStatus().getHowToPlay(),
						"VoidSpace: How To Play", JOptionPane.INFORMATION_MESSAGE);
			}
			break;
		case KeyEvent.VK_P:
			gameLogic.playPauseSound();
			if (gameLogic.getStatus().isGamePaused()) {
				gameLogic.getStatus().setGamePaused(false);
				gameLogic.playMusic();
			}
			else {
				gameLogic.getStatus().setGamePaused(true);
				gameLogic.pauseMusic();

			}
			break;

		}
		e.consume();
	}

	/**
	 * Handle a key release event.
	 */
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			this.upIsPressed = false;
			break;
		case KeyEvent.VK_DOWN:
			this.downIsPressed = false;
			break;
		case KeyEvent.VK_LEFT:
			this.leftIsPressed = false;
			break;
		case KeyEvent.VK_RIGHT:
			this.rightIsPressed = false;
			break;
		case KeyEvent.VK_SPACE:
			this.spaceIsPressed = false;
			break;
		case KeyEvent.VK_SHIFT:
			this.shiftIsPressed = false;
			Ship ship = gameLogic.getShip(); 
			if(gameLogic.getStatus().isGameStarted())
				ship.setSpeed(ship.getDefaultSpeed());
			break;
		}
		e.consume();

	}

	public void keyTyped(KeyEvent e) {
		// not used
	}
}
