package com.nonstopcoders.utility;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Music thread. Plays the background music in the game.
 */
public class Music implements Runnable {

	private String soundFileName;
	private boolean done;

	public Music(String soundFileName) {
		this.soundFileName = soundFileName;
	}

	public void run() {
		while (!done)
			play(soundFileName);
	}

	public void play(String soundFileName) {

		int BUFFER_SIZE = 64 * 1024; // [Note: Equivalent to 64 KB]
		SourceDataLine soundLine = null;

		// Establish audio stream
		try {

			// Initial setup
			File soundFile = new File(soundFileName);
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(soundFile);
			AudioFormat audioFormat = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class,
					audioFormat);
			soundLine = (SourceDataLine) AudioSystem.getLine(info);
			soundLine.open(audioFormat);
			soundLine.start();
			// ------------------------------

			// Play music
			int nBytesRead = 0;
			byte[] sampledData = new byte[BUFFER_SIZE];
			while (nBytesRead != -1) {
				// Check if music is interrupted
				try {
					Thread.sleep(370); // Less than 370 may cause concurrency
					// issues
				} catch (InterruptedException e) {
					done = true;
					return;
				}
				// ------------------------------
				nBytesRead = audioInputStream.read(sampledData, 0,
						sampledData.length);
				if (nBytesRead >= 0) {
					// Writes audio data to the mixer via this source data
					// line.
					soundLine.write(sampledData, 0, nBytesRead);
				}
			}
			// ------------------------------
		} catch (UnsupportedAudioFileException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		} finally {
			if (soundLine != null) {
				soundLine.drain();
				soundLine.close();
			}
		}
	}
}