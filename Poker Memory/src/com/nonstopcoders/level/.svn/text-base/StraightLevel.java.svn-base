package com.nonstopcoders.level;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

import com.nonstopcoders.game.Card;
import com.nonstopcoders.gui.ScoreCounterLabel;
import com.nonstopcoders.gui.TurnsTakenCounterLabel;
import com.nonstopcoders.utility.Leaderboard;
import com.nonstopcoders.utility.Sound;

/**
 * Straight level. See help menu on game for more info.
 * 
 */
public class StraightLevel extends FlushLevel {

	// STRAIGHT LEVEL: The goal is to find, on each turn, five cards in sequence
	// with at least two distinct suits

	private int cardsWithoutCombination;

	public StraightLevel(TurnsTakenCounterLabel validTurnTime,
			ScoreCounterLabel scoreCounter, JFrame mainFrame) {
		super(validTurnTime, scoreCounter, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Straight Level");
		cardsWithoutCombination = 10;
		this.leaderboard = new Leaderboard(this.getMode());

	}

	@Override
	protected boolean addToTurnedCardsBuffer(Card card) {
		// add the card to the list
		this.turnedCardsBuffer.add(card);
		Sound.TURN_CARD_UP.play();
		if (this.turnedCardsBuffer.size() == getCardsToTurnUp()) {
			// We are uncovering the last card in this turn
			// Record the player's turn
			this.turnsTakenCounter.increment();

			// Sort turned cards by increasing rank values
			sortTurnedCards();

			// Increase score if turned cards are in sequence and have
			// at least two distinct suits
			if (turnedCardsInSequence() && atLeastTwoDistinctSuits()) {
				this.scoreCounter.increment(1000 + 100 * this.turnedCardsBuffer
						.get(cardsToTurnUp - 1).getRankValue());

				// Special case where straight starts with 6
				if (this.turnedCardsBuffer.get(0).getRankValue() == 6)
					cardsWithoutCombination += 5;

				this.turnedCardsBuffer.clear();
				cardsFaceDown -= cardsToTurnUp;
				Sound.WINNING_HAND.play();
				
				// Check if game is over
				// ---------------------------------
				if (cardsFaceDown == cardsWithoutCombination) {
					card.turnUp();
					card.setIcon(card.getFaceIcon());
					// Determine if a high score was obtained and end game
					try {
						leaderboard
								.checkHighScore(this.scoreCounter.getScore());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					card.setAsLastCard();
				}
				// ---------------------------------
			}
			// They do not meet the above requirements
			else {
				this.turnDownTimer.start();
				this.scoreCounter.increment(-5);
				Sound.WRONG_HAND.play();
			}

		}
		Card.resetState(); // Updates the Card class' firstTime variable
		return true;
	}

	protected void sortTurnedCards() {
		// Sort in increasing order.
		// [Note: A more efficient algorithm may be implemented in the future
		// (such as merge sort)]
		for (int i = 0; i < this.turnedCardsBuffer.size() - 1; i++)
			for (int j = 0; j < (this.turnedCardsBuffer.size() - 1) - i; j++)
				if (this.turnedCardsBuffer.get(j).getRankValue() > this.turnedCardsBuffer
						.get(j + 1).getRankValue()) {
					Card temp = this.turnedCardsBuffer.get(j);
					this.turnedCardsBuffer.set(j,
							this.turnedCardsBuffer.get(j + 1));
					this.turnedCardsBuffer.set(j + 1, temp);
				}
	}

	protected boolean turnedCardsInSequence() {
		// Verify that the sorted cards are in a natural number sequence
		// (Example: 2, 3, 5, 6 and 7 are not in the required sequence, but
		// 10, J, Q, K and A are)
		int i;
		for (i = 0; i < this.turnedCardsBuffer.size() - 1; i++)
			if ((this.turnedCardsBuffer.get(i).getRankValue() + 1) != (this.turnedCardsBuffer
					.get(i + 1).getRankValue())) {
				break;
			}

		if (i == 4)
			return true;
		else
			return false;
	}

	protected boolean atLeastTwoDistinctSuits() {
		// Verify that the turned cards have at least two distinct suits
		for (int i = 0; i < this.turnedCardsBuffer.size() - 1; i++)
			if (!this.turnedCardsBuffer.get(i).getSuit()
					.equals(this.turnedCardsBuffer.get(i + 1).getSuit())) {
				return true;
			}
		return false;
	}

	protected String getMode() {
		return "StraightMode";
	}
}