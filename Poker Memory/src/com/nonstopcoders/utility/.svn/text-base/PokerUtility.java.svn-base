package com.nonstopcoders.utility;

import java.util.Vector;

import com.nonstopcoders.game.Card;

/**
 * Used mainly by the PokerHands utility.
 */
public class PokerUtility {

	public static int cardsRankSum(Vector<Card> turnedCardsBuffer) {

		Card otherCard1 = (Card) turnedCardsBuffer.get(0);
		Card otherCard2 = (Card) turnedCardsBuffer.get(1);
		Card otherCard3 = (Card) turnedCardsBuffer.get(2);
		Card otherCard4 = (Card) turnedCardsBuffer.get(3);
		Card otherCard5 = (Card) turnedCardsBuffer.get(4);

		int sumOfRanks = otherCard1.getRankValue() + otherCard2.getRankValue()
				+ otherCard3.getRankValue() + otherCard4.getRankValue()
				+ otherCard5.getRankValue();

		return sumOfRanks;

	}

	public static boolean turnedCardsInSequence(Vector<Card> turnedCardsBuffer) {
		// Verify that the sorted cards are in a natural number sequence
		// (Example: 2, 3, 5, 6 and 7 are not in the required sequence, but
		// 10, J, Q, K and A are)
		int i;
		for (i = 0; i < turnedCardsBuffer.size() - 1; i++)
			if ((turnedCardsBuffer.get(i).getRankValue() + 1) != (turnedCardsBuffer
					.get(i + 1).getRankValue())) {
				break;
			}

		if (i == 4)
			return true;
		else
			return false;
	}

	public static boolean atLeastTwoDistinctSuits(Vector<Card> turnedCardsBuffer) {
		// Verify that the turned cards have at least two distinct suits
		for (int i = 0; i < turnedCardsBuffer.size() - 2; i++)
			if (!turnedCardsBuffer.get(i).getSuit()
					.equals(turnedCardsBuffer.get(i + 1).getSuit())) {
				return true;
			}
		return false;
	}

	public static boolean isFlush(Vector<Card> turnedCardsBuffer) {

		Card otherCard1 = (Card) turnedCardsBuffer.get(0);
		Card otherCard2 = (Card) turnedCardsBuffer.get(1);
		Card otherCard3 = (Card) turnedCardsBuffer.get(2);
		Card otherCard4 = (Card) turnedCardsBuffer.get(3);
		Card otherCard5 = (Card) turnedCardsBuffer.get(4);

		if ((otherCard5.getSuit().equals(otherCard1.getSuit()))
				&& (otherCard5.getSuit().equals(otherCard2.getSuit()))
				&& (otherCard5.getSuit().equals(otherCard3.getSuit()))
				&& (otherCard5.getSuit().equals(otherCard4.getSuit())))
			return true;
		return false;
	}

	public static boolean isFullHouse(Vector<Card> turnedCardsBuffer) {

		if ((turnedCardsBuffer.get(0).getRank()
				.equals(turnedCardsBuffer.get(1).getRank())
				&& turnedCardsBuffer.get(0).getRank()
						.equals(turnedCardsBuffer.get(2).getRank()) && turnedCardsBuffer
				.get(3).getRank().equals(turnedCardsBuffer.get(4).getRank()))
				|| (turnedCardsBuffer.get(2).getRank()
						.equals(turnedCardsBuffer.get(3).getRank())
						&& turnedCardsBuffer.get(2).getRank()
								.equals(turnedCardsBuffer.get(4).getRank()) && turnedCardsBuffer
						.get(0).getRank()
						.equals(turnedCardsBuffer.get(1).getRank())))
			return true;

		System.out.println("not full house");
		return false;
	}

	public static boolean isFourOfAKind(Vector<Card> turnedCardsBuffer) {

		int i;
		for (i = 0; i < 3; i++)
			if (!turnedCardsBuffer.get(i).getRank()
					.equals(turnedCardsBuffer.get(i + 1).getRank()))
				break;

		if (i == 3)
			return true;

		else
			for (i = 1; i < 4; i++)
				if (!turnedCardsBuffer.get(i).getRank()
						.equals(turnedCardsBuffer.get(i + 1).getRank()))
					break;
		if (i == 4)
			return true;

		return false;
	}

	public static boolean isThreeOfAKind(Vector<Card> turnedCardsBuffer) {

		if ((turnedCardsBuffer.get(0).getRank()
				.equals(turnedCardsBuffer.get(1).getRank()) && turnedCardsBuffer
				.get(0).getRank().equals(turnedCardsBuffer.get(2).getRank()))
				|| (turnedCardsBuffer.get(1).getRank()
						.equals(turnedCardsBuffer.get(2).getRank()) && turnedCardsBuffer
						.get(1).getRank()
						.equals(turnedCardsBuffer.get(3).getRank()))
				|| (turnedCardsBuffer.get(2).getRank()
						.equals(turnedCardsBuffer.get(3).getRank()) && turnedCardsBuffer
						.get(2).getRank()
						.equals(turnedCardsBuffer.get(4).getRank())))
			return true;

		System.out.println("not three of a kind");
		return false;
	}

	public static boolean isTwoPair(Vector<Card> turnedCardsBuffer) {

		if (turnedCardsBuffer.get(0).getRank()
				.equals(turnedCardsBuffer.get(1).getRank())) {
			if (turnedCardsBuffer.get(2).getRank()
					.equals(turnedCardsBuffer.get(3).getRank())) {
				System.out.println("two pair");
				return true;
			} else if (turnedCardsBuffer.get(3).getRank()
					.equals(turnedCardsBuffer.get(4).getRank())) {
				System.out.println("two pair");
				return true;
			}
		}
		if (turnedCardsBuffer.get(1).getRank()
				.equals(turnedCardsBuffer.get(2).getRank())
				&& turnedCardsBuffer.get(3).getRank()
						.equals(turnedCardsBuffer.get(4).getRank()))
			return true;

		System.out.println("not two pair");
		return false;
	}

	public static boolean isOnePair(Vector<Card> turnedCardsBuffer) {

		if (turnedCardsBuffer.get(0).getRank()
				.equals(turnedCardsBuffer.get(1).getRank())
				|| turnedCardsBuffer.get(1).getRank()
						.equals(turnedCardsBuffer.get(2).getRank())
				|| turnedCardsBuffer.get(2).getRank()
						.equals(turnedCardsBuffer.get(3).getRank())
				|| turnedCardsBuffer.get(3).getRank()
						.equals(turnedCardsBuffer.get(4).getRank()))
			return true;

		return false;
	}
}