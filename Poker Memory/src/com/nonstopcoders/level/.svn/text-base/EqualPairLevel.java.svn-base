package com.nonstopcoders.level;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

import com.nonstopcoders.game.Card;
import com.nonstopcoders.gui.ScoreCounterLabel;
import com.nonstopcoders.gui.TurnsTakenCounterLabel;
import com.nonstopcoders.utility.Leaderboard;
import com.nonstopcoders.utility.Sound;

/**
 * The first level. Match the cards. See help menu on game for more info.
 * 
 */
public class EqualPairLevel extends EasyLevel {

	protected int cardsFaceDown;

	public EqualPairLevel(TurnsTakenCounterLabel validTurnTime,
			ScoreCounterLabel scoreCounter, JFrame mainFrame) {
		super(validTurnTime, scoreCounter, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Medium Level");
		cardsFaceDown = this.totalUniqueCards;
		this.leaderboard = new Leaderboard(this.getMode());

	}

	@Override
	protected boolean addToTurnedCardsBuffer(Card card) {
		this.turnedCardsBuffer.add(card);
		Sound.TURN_CARD_UP.play();
		if (this.turnedCardsBuffer.size() == getCardsToTurnUp()) {
			// there are two cards faced up
			// record the player's turn
			this.turnsTakenCounter.increment();
			// get the other card (which was already turned up)
			Card otherCard = (Card) this.turnedCardsBuffer.get(0);
			// the cards match, so remove them from the list (they will remain
			// face up)
			if (otherCard.getNum() == card.getNum()) {
				this.turnedCardsBuffer.clear();
				this.scoreCounter.increment(50);
				cardsFaceDown -= this.cardsToTurnUp;
				Sound.WINNING_HAND.play();
				
				// Check if game is over
				// ---------------------------------
				if (cardsFaceDown == 0) {
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
			// the cards do not match, so start the timer to turn them down
			else {
				this.turnDownTimer.start();
				this.scoreCounter.increment(-5);
				Sound.WRONG_HAND.play();
			}

		}
		Card.resetState(); // Updates the Card class' firstTime variable
		return true;
	}

	@Override
	protected boolean turnUp(Card card) {
		// the card may be turned
		if (this.turnedCardsBuffer.size() < getCardsToTurnUp()) {
			return this.addToTurnedCardsBuffer(card);
		}
		// there are already the number of EasyMode (two face up cards) in the
		// turnedCardsBuffer
		return false;
	}

	@Override
	protected String getMode() {
		// TODO Auto-generated method stub
		return "MediumMode";
	}

}
