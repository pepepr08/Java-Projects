package rbadia.voidspace.model;

import java.awt.Rectangle;

public class EnemyBullet extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	private int bulletWidth = 8;
	private int bulletHeight = 8;
	private int speed = 8;

	public EnemyBullet(EnemyShip ship) {
		this.setLocation(ship.x + ship.width/2 - bulletWidth/2,
				ship.y + bulletHeight);
		this.setSize(bulletWidth, bulletHeight);

	}
	
	/**
	 * Return the bullet's speed.
	 * @return the bullet's speed.
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Set the bullet's speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
}
