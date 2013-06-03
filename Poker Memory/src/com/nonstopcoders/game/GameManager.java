package com.nonstopcoders.game;

import java.io.IOException;

import com.nonstopcoders.utility.Sound;

/**
 * Manages the MemoryGame instance. This class effectively starts the game.
 */
public class GameManager {

	/**
	 * Poker Memory. Instantiates a MemoryGame object which starts the game.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException,
			IOException {
		// make an instance of the main game class
		MemoryGame instance = new MemoryGame();
		Sound.init();
		instance.newGame("equal");
	}
}