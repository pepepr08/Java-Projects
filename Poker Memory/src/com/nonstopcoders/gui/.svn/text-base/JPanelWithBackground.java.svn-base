package com.nonstopcoders.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Adds a background image to the game's window.
 * 
 */
public class JPanelWithBackground extends JPanel {

	private static final long serialVersionUID = -860911155033907776L;
	private Image backgroundImage;

	/**
	 * Some code to initialize the background image. Here, we use the
	 * constructor to load the image. This can vary depending on the use case of
	 * the panel.
	 * 
	 * @param fileName
	 *            the image's file name
	 */
	public JPanelWithBackground(String fileName) {

		try {
			backgroundImage = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// Draw the background image.
		g.drawImage(backgroundImage, 0, 0, null);
	}
}