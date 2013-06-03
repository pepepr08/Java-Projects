package com.nonstopcoders.level;

import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.nonstopcoders.game.Card;
import com.nonstopcoders.gui.ScoreCounterLabel;
import com.nonstopcoders.gui.TurnsTakenCounterLabel;
import com.nonstopcoders.utility.Leaderboard;
import com.nonstopcoders.utility.Sound;

public class RankTrioLevel extends EqualPairLevel {

	// TRIO LEVEL: The goal is to find, on each turn, three cards with the same
	// rank

	public RankTrioLevel(TurnsTakenCounterLabel validTurnTime,
			ScoreCounterLabel scoreCounter, JFrame mainFrame) {
		super(validTurnTime, scoreCounter, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Trio Level");
		cardsToTurnUp = 3;
		cardsPerRow = 10;
		rowsPerGrid = 5;
		totalUniqueCards = cardsPerRow * rowsPerGrid;
		cardsFaceDown = totalUniqueCards;
		this.leaderboard = new Leaderboard(this.getMode());

	}

	@Override
	protected void makeDeck() {
		// In Trio level the grid consists of distinct cards, no repetitions
		ImageIcon cardIcon[] = this.loadCardIcons();

		// back card
		ImageIcon backIcon = cardIcon[TotalCardsPerDeck];

		int cardsToAdd[] = new int[getRowsPerGrid() * getCardsPerRow()];
		for (int i = 0; i < (getRowsPerGrid() * getCardsPerRow()); i++) {
			cardsToAdd[i] = i;
		}

		// randomize the order of the deck
		this.randomizeIntArray(cardsToAdd);

		// make each card object
		for (int i = 0; i < cardsToAdd.length; i++) {
			// number of the card, randomized
			int num = cardsToAdd[i];
			// make the card object and add it to the panel
			String rank = cardNames[num].substring(0, 1);
			String suit = cardNames[num].substring(1, 2);
			this.grid.add(new Card(this, cardIcon[num], backIcon, num, rank,
					suit));
		}
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
			Card otherCard1 = (Card) this.turnedCardsBuffer.get(0);
			Card otherCard2 = (Card) this.turnedCardsBuffer.get(1);

			if ((card.getRank().equals(otherCard1.getRank()))
					&& (card.getRank().equals(otherCard2.getRank()))) {
				// Three cards match, so remove them from the list (they will
				// remain face up)
				this.turnedCardsBuffer.clear();

				// Increases the score depending on the rank of the trio
				int rankValue = card.getRankValue();
				this.scoreCounter.increment(100 + 3 * rankValue);
				cardsFaceDown -= this.cardsToTurnUp;
				Sound.WINNING_HAND.play();
				
				// Check if game is over
				// ---------------------------------
				if (cardsFaceDown == 14) {
					// Determine if a high score was obtained and end game
					card.turnUp();
					card.setIcon(card.getFaceIcon());
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

	protected String getMode() {
		// TODO Auto-generated method stub
		return "TrioMode";
	}
}
