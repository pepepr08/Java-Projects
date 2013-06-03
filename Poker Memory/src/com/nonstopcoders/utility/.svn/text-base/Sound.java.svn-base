package com.nonstopcoders.utility;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Sound enum that easily calls method for playing, pausing, looping, and
 * stopping sound effects.
 */
public enum Sound {

	EQUAL_PAIR("./sound/match_the_cards.wav"), RANK_TRIO(
			"./sound/rank_trio_voice.wav"), FLUSH("./sound/flush_voice.wav"), STRAIGHT(
			"./sound/straight_voice.wav"), COMBO("./sound/combo_voice.wav"), COMBO_DIALOG1(
			"./sound/combo_dialog1.wav"), COMBO_DIALOG2(
			"./sound/combo_dialog2.wav"), HIGH_CARD("./sound/high_card.wav"), ONE_PAIR(
			"./sound/one_pair.wav"), TWO_PAIR("./sound/two_pair.wav"), THREE_OF_A_KIND(
			"./sound/three_of_a_kind.wav"), FULL_HOUSE("./sound/full_house.wav"), FOUR_OF_A_KIND(
			"./sound/four_of_a_kind.wav"), STRAIGHT_HAND(
			"./sound/straight_hand.wav"), FLUSH_HAND("./sound/flush_hand.wav"), STRAIGHT_FLUSH(
			"./sound/straight_flush.wav"), ROYAL_FLUSH(
			"./sound/royal_flush.wav"), CLICK("./sound/click.wav"), TURN_CARD_UP(
			"./sound/turn_card_up.wav"), TURN_CARD_DOWN(
			"./sound/turn_card_down.wav"), WINNING_HAND(
			"./sound/winning_hand.wav"), WRONG_HAND("./sound/wrong_hand.wav"), WIN(
			"./sound/win.wav"), GAME_OVER("./sound/game_over.wav");

	private Clip clip;

	Sound(String fileName) {
		try {
			// Open an audio input stream.
			URL url = this.getClass().getClassLoader().getResource(fileName);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			// Get a sound clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		if (clip.isRunning())
			clip.stop();
	}

	public void play() {
		if (clip.isRunning())
			clip.stop();
		clip.setFramePosition(0); // Rewind
		clip.start();
	}

	public void play(int count) {
		if (clip.isRunning())
			clip.stop();
		clip.setFramePosition(0);
		if (count == -1)
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		else
			clip.loop(count);
	}

	// Preload sound files (useful for avoiding the
	// initial pause of the play() method)
	public static void init() {
		values();
	}
}