package com.nonstopcoders.utility;

import java.util.Vector;

import com.nonstopcoders.game.Card;

/**
 * Determines the type of poker hand and returns the corresponding score.
 */
public class PokerHands {

	public static int checkHand(Vector<Card> turnedCardsBuffer) {

		// Check if Straight
		if (PokerUtility.turnedCardsInSequence(turnedCardsBuffer)
				&& PokerUtility.atLeastTwoDistinctSuits(turnedCardsBuffer))
			return 1000 + 100 * turnedCardsBuffer.get(
					turnedCardsBuffer.size() - 1).getRankValue();

		// Check if Straight Flush or a special case (Royal Flush)
		else if (PokerUtility.turnedCardsInSequence(turnedCardsBuffer)
				&& PokerUtility.isFlush(turnedCardsBuffer))
			// Check if Royal Flush
			if (turnedCardsBuffer.get(turnedCardsBuffer.size() - 1)
					.getRankValue() == 14)
				return 10000;

			// Otherwise, it's a Straight Flush
			else
				return (4000 + 700 + PokerUtility
						.cardsRankSum(turnedCardsBuffer))
						+ (1000 + 100 * turnedCardsBuffer.get(
								turnedCardsBuffer.size() - 1).getRankValue());
		// Check if Four of a kind
		else if (PokerUtility.isFourOfAKind(turnedCardsBuffer))
			return 4500 + 100 * turnedCardsBuffer.get(2).getRankValue();

		// Check if Full House
		else if (PokerUtility.isFullHouse(turnedCardsBuffer))
			return 3500; // decidir el score luego

		// Check if Flush
		else if (PokerUtility.isFlush(turnedCardsBuffer))
			return 700 + PokerUtility.cardsRankSum(turnedCardsBuffer);

		// Check if Three of a Kind
		else if (PokerUtility.isThreeOfAKind(turnedCardsBuffer))
			return 500; // cambiar luego

		// Check if Two Pair
		else if (PokerUtility.isTwoPair(turnedCardsBuffer))
			return 200;
		// Check if one pair
		else if (PokerUtility.isOnePair(turnedCardsBuffer))
			return 100 + PokerUtility.cardsRankSum(turnedCardsBuffer); // decidir
																		// el
																		// score

		// They do not meet the above requirements
		else
			return turnedCardsBuffer.get(4).getRankValue();
	}
}