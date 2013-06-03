package com.nonstopcoders.level;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

import com.nonstopcoders.game.Card;
import com.nonstopcoders.gui.ScoreCounterLabel;
import com.nonstopcoders.gui.TurnsTakenCounterLabel;
import com.nonstopcoders.utility.Leaderboard;
import com.nonstopcoders.utility.Sound;

/**
 * Flush level. See help menu on game for more info.
 */
public class FlushLevel extends RankTrioLevel {

	// FLUSH LEVEL: The goal is to find, on each turn, five cards with the same
	// suit

	public FlushLevel(TurnsTakenCounterLabel validTurnTime,
			ScoreCounterLabel scoreCounter, JFrame mainFrame) {
		super(validTurnTime, scoreCounter, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Flush Level");
		cardsToTurnUp = 5;
		cardsPerRow = 10;
		rowsPerGrid = 5;
		totalUniqueCards = cardsPerRow * rowsPerGrid;
		cardsFaceDown = totalUniqueCards;
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
			// get the other card (which was already turned up)

			// Card otherCard1 = (Card) this.turnedCardsBuffer.get(0);
			// Card otherCard2 = (Card) this.turnedCardsBuffer.get(1);
			// Card otherCard3 = (Card) this.turnedCardsBuffer.get(2);
			// Card otherCard4 = (Card) this.turnedCardsBuffer.get(3);

			// Five cards match, so remove them from the list (they will
			// remain face up)
			if (isFlush() == true) {
				// Increases the score depending on the rank of the cards
				int sumOfRanks = cardsRankSum();

				this.scoreCounter.increment(700 + sumOfRanks);
				cardsFaceDown -= cardsToTurnUp;
				this.turnedCardsBuffer.clear();
				Sound.WINNING_HAND.play();
				
				// Check if game is over
				// ---------------------------------
				if (cardsFaceDown == 10) {
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
			} else {
				// The cards do not match, so start the timer to turn them down
				this.turnDownTimer.start();
				this.scoreCounter.increment(-5);
				Sound.WRONG_HAND.play();
			}
		}
		Card.resetState(); // Updates the Card class' firstTime variable
		return true;
	}

	protected boolean isFlush() {

		Card otherCard1 = (Card) this.turnedCardsBuffer.get(0);
		Card otherCard2 = (Card) this.turnedCardsBuffer.get(1);
		Card otherCard3 = (Card) this.turnedCardsBuffer.get(2);
		Card otherCard4 = (Card) this.turnedCardsBuffer.get(3);
		Card otherCard5 = (Card) this.turnedCardsBuffer.get(4);

		if ((otherCard5.getSuit().equals(otherCard1.getSuit()))
				&& (otherCard5.getSuit().equals(otherCard2.getSuit()))
				&& (otherCard5.getSuit().equals(otherCard3.getSuit()))
				&& (otherCard5.getSuit().equals(otherCard4.getSuit())))
			return true;
		return false;
	}

	protected int cardsRankSum() {

		Card otherCard1 = (Card) this.turnedCardsBuffer.get(0);
		Card otherCard2 = (Card) this.turnedCardsBuffer.get(1);
		Card otherCard3 = (Card) this.turnedCardsBuffer.get(2);
		Card otherCard4 = (Card) this.turnedCardsBuffer.get(3);
		Card otherCard5 = (Card) this.turnedCardsBuffer.get(4);

		int sumOfRanks = otherCard1.getRankValue() + otherCard2.getRankValue()
				+ otherCard3.getRankValue() + otherCard4.getRankValue()
				+ otherCard5.getRankValue();

		return sumOfRanks;

	}

	protected String getMode() {
		return "FlushMode";
	}
}