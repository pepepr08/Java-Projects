package com.nonstopcoders.level;

import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.nonstopcoders.game.Card;
import com.nonstopcoders.gui.ScoreCounterLabel;
import com.nonstopcoders.gui.TurnsTakenCounterLabel;
import com.nonstopcoders.utility.Leaderboard;
import com.nonstopcoders.utility.PokerHands;
import com.nonstopcoders.utility.Sound;

/**
 * The Combo level. See help menu on game for more info.
 * 
 * @author Abner L. Coimbre & Jose F. Martinez
 * 
 */
public class ComboLevel extends StraightLevel {

	// COMBO LEVEL
	public ComboLevel(TurnsTakenCounterLabel validTurnTime,
			ScoreCounterLabel scoreCounter, JFrame mainFrame) {
		super(validTurnTime, scoreCounter, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Combo Level");
		this.leaderboard = new Leaderboard(this.getMode());

	}

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

			// Determine score
			int score = PokerHands.checkHand(turnedCardsBuffer);
			card.turnUp();
			card.setIcon(card.getFaceIcon());
			setScore(score, card, getPokerHand(score));
			// ------------------
		}
		Card.resetState(); // Updates the Card class' firstTime variable
		return true;
	}

	/**
	 * Returns the current poker hand.
	 * 
	 * @param score
	 *            the score obtained
	 * @return hand the poker hand's name
	 */
	protected String getPokerHand(int score) {

		String hand = null;
		if (score > 9000) {
			hand = "Royal Flush";
			Sound.ROYAL_FLUSH.play();
		} else if (score > 6000) {
			hand = "Straight Flush";
			Sound.STRAIGHT_FLUSH.play();
		} else if (score > 4000) {
			hand = "Four of a Kind";
			Sound.FOUR_OF_A_KIND.play();
		} else if (score > 3000) {
			hand = "Full House";
			Sound.FULL_HOUSE.play();
		} else if (score > 1000) {
			hand = "Straight";
			Sound.STRAIGHT_HAND.play();
		} else if (score > 700) {
			hand = "Flush";
			Sound.FLUSH_HAND.play();
		} else if (score > 400) {
			hand = "Three of a Kind";
			Sound.THREE_OF_A_KIND.play();
		} else if (score > 150) {
			hand = "Two Pair";
			Sound.TWO_PAIR.play();
		} else if (score > 50) {
			hand = "One Pair";
			Sound.ONE_PAIR.play();
		} else {
			hand = "High Card";
			Sound.HIGH_CARD.play();
		}

		return hand;

	}

	public void setScore(int score, Card card, String hand) {
		Sound.COMBO_DIALOG1.play();
		boolean userMadeChoice = false;
		while (!userMadeChoice) {
			int decision = JOptionPane.showOptionDialog(null, "Poker Hand: "
					+ hand + "\nPoints worth: " + Integer.toString(score)
					+ "\nDo you want to keep the hand?",
					"The Nonstop Coders Poker", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, new String[] { "No",
							"Yes" }, "default");
			if (decision == 1) // 1 means 'Yes'
			{
				Sound.COMBO_DIALOG2.play();
				userMadeChoice = true;
				this.scoreCounter.increment(score);
				Sound.WINNING_HAND.play();
				this.turnedCardsBuffer.clear();

				cardsFaceDown -= cardsToTurnUp;
				if (cardsFaceDown == 0) {
					try {
						leaderboard
								.checkHighScore(this.scoreCounter.getScore());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					card.setAsLastCard();
				}

			} else if (decision == 0) // 0 means 'No'
			{
				Sound.WRONG_HAND.play();
				this.scoreCounter.increment(-50);
				this.turnDownTimer.start();
				userMadeChoice = true;
			} else // user pressed the close button
			{
			}
		}
	}

	protected String getMode() {
		return "ComboMode";
	}
}