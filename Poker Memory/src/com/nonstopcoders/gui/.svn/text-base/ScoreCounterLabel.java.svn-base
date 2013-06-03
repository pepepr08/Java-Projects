package com.nonstopcoders.gui;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Shows the score on the game window
 */
public class ScoreCounterLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	// data fields
	private int score = 0;
	private String DESCRIPTION = "Score: ";

	public ScoreCounterLabel() {
		super();
		reset();
	}

	public void setDifficultyModeLabel(String difficultyMode) {
		DESCRIPTION = "Score: ";
		setHorizontalTextPosition(JLabel.LEFT);
	}

	public int getScore() {
		return this.score;
	}

	/**
	 * Update the text label with the current counter value
	 */
	private void update() {

		setForeground(Color.WHITE);
		setText(DESCRIPTION + Integer.toString(this.score));
		setHorizontalTextPosition(JLabel.LEFT);
	}

	/**
	 * Default constructor, starts counter at 0
	 */

	/**
	 * Increments the counter and updates the text label
	 */
	public void increment(int points) {
		this.score = this.score + points;
		update();
	}

	/**
	 * Resets the counter to zero and updates the text label
	 */
	public void reset() {
		this.score = 0;
		update();
	}

}