package rbadia.voidspace.sounds;

import java.applet.Applet;
import java.applet.AudioClip;

import rbadia.voidspace.main.GameScreen;

/**
 * Manages and plays the game's sounds.
 */
public class SoundManager {
	private static final boolean SOUND_ON = true;

	private AudioClip shipExplosionSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/shipExplosion.wav"));
	private AudioClip bulletSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/laser.wav"));

	private AudioClip shipDestroyedSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/shipFade.wav"));

	private AudioClip backGroundMusic = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/starfox.wav"));

	private AudioClip enemyBulletSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/laser3.wav"));

	private AudioClip enemyShipDestroyedSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/enemyShipExplosion.wav"));

	private AudioClip enemyShipGettingHitSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/enemyShipHit.wav"));

	private AudioClip NukeExplosionSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/NukeSound.wav"));

	private AudioClip LifeUpSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/LifeUp.wav"));

	private AudioClip ShieldUpSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/ShieldUp.wav"));
	private AudioClip LevelUpSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/LevelUp.wav"));
	private AudioClip PauseSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/Pause.wav"));
	private Thread a;

	/**
	 * Plays sound for bullets fired by the ship.
	 */
	public void playBulletSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					bulletSound.play();
				}
			}).start();
		}
	}

	/**
	 * Plays sound for bullets fired by the enemy ship.
	 */
	public void playEnemyBulletSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					enemyBulletSound.play();
				}
			}).start();
		}
	}

	/**
	 * Plays sound for ship explosions.
	 */
	public void playShipExplosionSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					shipDestroyedSound.play();
				}
			}).start();
		}
	}

	/**
	 * Plays sound for asteroid explosions.
	 */
	public void playAsteroidExplosionSound(){
		// play sound for asteroid explosions
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					shipExplosionSound.play();
				}
			}).start();

		}
	}

	public void playBackGroundMusic(){
		//play sound for background music
		if(SOUND_ON){
			a = new Thread(new Runnable(){
				public void run(){
					backGroundMusic.loop();
				}				
			});
			a.start();
		}
	}

	public void stopBackgroundMusic() {
		if(SOUND_ON) {
			a = new Thread(new Runnable(){
				public void run(){
					backGroundMusic.stop();
				}				
			});
			a.start();
		}
	}

	public void playEnemyShipExplosionSound(){
		// play sound for asteroid explosions
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					enemyShipDestroyedSound.play();
				}
			}).start();

		}
	}

	public void playEnemyShipHitSound(){
		// play sound for asteroid explosions
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					enemyShipGettingHitSound.play();
				}
			}).start();

		}
	}

	public void playNukeSound(){
		// play sound for asteroid explosions
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					NukeExplosionSound.play();
				}
			}).start();

		}
	}

	public void playLifeUpSound(){
		// play sound for asteroid explosions
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					LifeUpSound.play();
				}
			}).start();

		}
	}

	public void playShieldUpSound(){
		// play sound for asteroid explosions
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					ShieldUpSound.play();
				}
			}).start();

		}
	}   
	public void playLevelUpSound(){
		// play sound for asteroid explosions
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					LevelUpSound.play();
				}
			}).start();

		}
	}
	public void playPauseSound(){
		// play sound for asteroid explosions
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					PauseSound.play();
				}
			}).start();

		}
	}



}
