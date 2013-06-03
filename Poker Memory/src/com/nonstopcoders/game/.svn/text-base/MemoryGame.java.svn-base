package com.nonstopcoders.game;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.nonstopcoders.gui.JPanelWithBackground;
import com.nonstopcoders.gui.ScoreCounterLabel;
import com.nonstopcoders.gui.TurnsTakenCounterLabel;
import com.nonstopcoders.level.ComboLevel;
import com.nonstopcoders.level.EqualPairLevel;
import com.nonstopcoders.level.FlushLevel;
import com.nonstopcoders.level.RankTrioLevel;
import com.nonstopcoders.level.StraightLevel;
import com.nonstopcoders.utility.Leaderboard;
import com.nonstopcoders.utility.Music;
import com.nonstopcoders.utility.Sound;

/**
 * Manages the game levels, including sound and music, and instantiates a frame.
 * 
 */
public class MemoryGame implements ActionListener {

	public static boolean DEBUG = true;
	private JFrame mainFrame; // top level window
	private Container mainContentPane; // frame that holds card field and turn
	// counter
	private TurnsTakenCounterLabel turnCounterLabel;
	private ScoreCounterLabel scoreCounter;
	private GameLevel difficulty;
	private Thread backgroundMusic = null;
	private Leaderboard leaderboard;

	/**
	 * Make a JMenuItem, associate an action command and listener, add to menu
	 */
	private static void newMenuItem(String text, JMenu menu,
			ActionListener listener) {
		JMenuItem newItem = new JMenuItem(text);
		newItem.setActionCommand(text);
		newItem.addActionListener(listener);
		menu.add(newItem);
	}

	/**
	 * Default constructor loads card images, makes window
	 * 
	 * @throws IOException
	 */
	public MemoryGame() throws IOException {

		// Make toplevel window
		this.mainFrame = new JFrame("The Nonstop Coders Poker");
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setSize(900, 700);
		this.mainFrame.setResizable(false);
		this.mainContentPane = this.mainFrame.getContentPane();
		this.mainContentPane.setLayout(new BoxLayout(this.mainContentPane,
				BoxLayout.PAGE_AXIS));

		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		this.mainFrame.setJMenuBar(menuBar);

		// Game menu
		JMenu gameMenu = new JMenu("Memory");
		menuBar.add(gameMenu);
		newMenuItem("Leaderboard", gameMenu, this);
		newMenuItem("Exit", gameMenu, this);

		// Difficulty menu
		JMenu difficultyMenu = new JMenu("New Game");
		menuBar.add(difficultyMenu);
		newMenuItem("Equal Pair", difficultyMenu, this);
		newMenuItem("Same Rank Trio", difficultyMenu, this);
		newMenuItem("Flush", difficultyMenu, this);
		newMenuItem("Straight", difficultyMenu, this);
		newMenuItem("Combo", difficultyMenu, this);

		// Help menu
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		newMenuItem("How To Play", helpMenu, this);
		newMenuItem("Turn Up All Cards", helpMenu, this);
		newMenuItem("Turn Down All Cards", helpMenu, this);
		newMenuItem("About", helpMenu, this);

		// this.leaderBoard = new ScoreLeaderBoard("EasyMode");
	}

	/**
	 * Handles menu events. Necessary for implementing ActionListener.
	 * 
	 * @param e
	 *            object with information about the event
	 */
	public void actionPerformed(ActionEvent e) {
		dprintln("actionPerformed " + e.getActionCommand());
		try {
			if (e.getActionCommand().equals("Equal Pair"))
				newGame("equal");
			else if (e.getActionCommand().equals("Same Rank Trio"))
				newGame("trio");
			else if (e.getActionCommand().equals("Flush"))
				newGame("flush");
			else if (e.getActionCommand().equals("Straight"))
				newGame("straight");
			else if (e.getActionCommand().equals("Combo"))
				newGame("combo");
			else if (e.getActionCommand().equals("How To Play"))
				showInstructions();
			else if (e.getActionCommand().equals("About"))
				showAbout();
			else if (e.getActionCommand().equals("Turn Up All Cards"))
				turnUpAllCards();
			else if (e.getActionCommand().equals("Turn Down All Cards"))
				turnDownAllCards();
			else if (e.getActionCommand().equals("Leaderboard"))
				showLeaderboard();
			else if (e.getActionCommand().equals("Exit"))
				System.exit(0);
		} catch (IOException e2) {
			e2.printStackTrace();
			throw new RuntimeException("IO ERROR");
		}
	}

	/**
	 * Prints debugging messages to the console
	 * 
	 * @param message
	 *            the string to print to the console
	 */
	static public void dprintln(String message) {
		if (DEBUG)
			System.out.println(message);
	}

	public JPanel showCardDeck() {
		// make the panel to hold all of the cards
		JPanel panel = new JPanel(new GridLayout(difficulty.getRowsPerGrid(),
				difficulty.getCardsPerRow()));

		// this set of cards must have their own manager
		this.difficulty.makeDeck();

		for (int i = 0; i < difficulty.getGrid().size(); i++) {
			panel.add(difficulty.getGrid().get(i));
		}

		panel.setOpaque(false);
		return panel;
	}

	/**
	 * Prepares a new game (first game or non-first game)
	 * 
	 * @throws IOException
	 */
	public void newGame(String difficultyMode) throws IOException {
		// reset the turn counter to zero
		this.turnCounterLabel = new TurnsTakenCounterLabel();
		this.scoreCounter = new ScoreCounterLabel();

		// make a new card field with cards, and add it to the window
		if (difficultyMode.equalsIgnoreCase("equal")) {
			this.difficulty = new EqualPairLevel(this.turnCounterLabel,
					this.scoreCounter, this.mainFrame);
			playMusic("bin/music/equal_pair.wav");
			Sound.EQUAL_PAIR.play();
		}

		else if (difficultyMode.equalsIgnoreCase("trio")) {
			this.difficulty = new RankTrioLevel(this.turnCounterLabel,
					this.scoreCounter, this.mainFrame);
			playMusic("bin/music/rank_trio.wav");
			Sound.RANK_TRIO.play();
		} else if (difficultyMode.equalsIgnoreCase("flush")) {
			this.difficulty = new FlushLevel(this.turnCounterLabel,
					this.scoreCounter, this.mainFrame);
			playMusic("bin/music/flush.wav");
			Sound.FLUSH.play();
		} else if (difficultyMode.equalsIgnoreCase("straight")) {
			this.difficulty = new StraightLevel(this.turnCounterLabel,
					this.scoreCounter, this.mainFrame);
			playMusic("bin/music/straight.wav");
			Sound.STRAIGHT.play();
		} else if (difficultyMode.equalsIgnoreCase("combo")) {
			this.difficulty = new ComboLevel(this.turnCounterLabel,
					this.scoreCounter, this.mainFrame);
			playMusic("bin/music/combo.wav");
			Sound.COMBO.play();
		}

		else {
			throw new RuntimeException("Illegal Game Level Detected");
		}

		this.turnCounterLabel.reset();
		this.scoreCounter.reset();

		// clear out the content pane (removes turn counter label and card
		// field)
		this.mainContentPane.removeAll();

		JPanelWithBackground panel = new JPanelWithBackground(
				"./images/background.jpg");
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS)); // new
		// GridLayout(3,1));
		panel.add(showCardDeck());
		panel.add(this.scoreCounter);
		panel.add(this.turnCounterLabel);
		this.mainContentPane.add(panel);

		// show the window (in case this is the first game)
		this.mainFrame.setVisible(true);
	}

	/**
	 * Creates a Music thread and executes it with a given audio file.
	 * 
	 * @param soundFileName
	 *            the path of the audio file (Precondition: soundFileName must
	 *            conform to the Java Sound API accepted formats. In general,
	 *            .au and .wav files are used)
	 */
	private void playMusic(String soundFileName) {

		// Check for null
		if (backgroundMusic == null) {
			backgroundMusic = new Thread(new Music(soundFileName));
			backgroundMusic.start();
			return;
		}

		// Otherwise, play music normally
		if (backgroundMusic.isAlive())
			backgroundMusic.interrupt();
		backgroundMusic = new Thread(new Music(soundFileName));
		backgroundMusic.start();
	}

	/**
	 * Shows an instructional dialog box to the user
	 */
	private void showInstructions() {
		dprintln("MemoryGame.showInstructions()");

		String instructions = "How To Play\r\r\n\n";
		if (difficulty.getMode().equalsIgnoreCase("mediummode")) {
			instructions += "EQUAL PAIR\r\n"
					+ "------------------------------------------------------------------\n"
					+ "The game consists of 8 pairs of cards.  At the start of the game,\r\n"
					+ "every card is face down.  The object is to find all the pairs and\r\n"
					+ "turn them face up.\r\n"
					+ "\r\n"
					+ "Click on two cards to turn them face up. If the cards are the \r\n"
					+ "same, then you have discovered a pair.  The pair will remain\r\n"
					+ "turned up.  If the cards are different, they will flip back\r\n"
					+ "over automatically after a short delay.  Continue flipping\r\n"
					+ "cards until you have discovered all of the pairs.  The game\r\n"
					+ "is won when all cards are face up.\n"
					+ "------------------------------------------------------------------\n";

		}

		else if (difficulty.getMode().equalsIgnoreCase("triomode")) {
			instructions += "SAME RANK TRIO\r\n"
					+ "------------------------------------------------------------------\n"

					+ "The game consists of a grid of distinct cards.  At the start of the game,\r\n"
					+ "every card is face down.  The object is to find all the trios \r\n"
					+ "of cards with the same rank and turn them face up.\r\n"
					+ "\r\n"
					+ "Click on three cards to turn them face up. If the cards have the \r\n"
					+ "same rank, then you have discovered a trio.  The trio will remain\r\n"
					+ "turned up.  If the cards are different, they will flip back\r\n"
					+ "over automatically after a short delay.  Continue flipping\r\n"
					+ "cards until you have discovered all of the trios.  The game\r\n"
					+ "is won when all cards are face up."
					+ "\n------------------------------------------------------------------";

		}

		else if (difficulty.getMode().equalsIgnoreCase("flushmode")) {
			instructions += "FLUSH\r\n"
					+ "------------------------------------------------------------------\n"
					+ "The game consists of a grid of distinct cards.  At the start of the game,\r\n"
					+ "every card is face down. The player will uncover five cards. The object is to \r\n"
					+ "uncover five cards with the same suit. Click on five cards to turn them up. If\r\n"
					+ "the cards have the same suit, the cards will remain turned up. If the cards \r\n"
					+ "are different, they will flip back over automatically after a short delay. \r\n"
					+ "Continue flipping cards until all the cards are turned up.  The game\r\n"
					+ "is won when all possible flushes are face up."
					+ "\n------------------------------------------------------------------";

		}

		else if (difficulty.getMode().equalsIgnoreCase("straightmode")) {
			instructions += "STRAIGHT\r\n"
					+ "------------------------------------------------------------------\n"
					+ "The game consists of a grid of distinct cards. At the start of the game,\r\n"
					+ "every card is face down. The player will uncover five cards. A winning hand\r\n"
					+ "consists of obtaining a sequence out of those five cards. There must be\r\n"
					+ "at least two distinct suits in that hand. Continue flipping cards until all\r\n"
					+ "the cards are turned up. The game is won when all possible straight flushes\r\n"
					+ "are face up."
					+ "\n------------------------------------------------------------------";
		}

		else {
			instructions += "COMBO\r\n"
					+ "------------------------------------------------------------------\n"
					+ "The game consists of a grid of distinct cards. At the start of the game,\r\n"
					+ "every card is face down. The player will try to form the best poker hands\r\n"
					+ "possible. The higher the hand, the bigger the chances of finishing with a winning\r\n"
					+ "top score."
					+ "\n------------------------------------------------------------------";
		}

		if (!difficulty.getMode().equalsIgnoreCase("combomode"))
			instructions += "\r\n\nTry to win the game in the fewest number of turns!";

		JOptionPane.showMessageDialog(this.mainFrame, instructions,
				"How To Play", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Shows a dialog box with the current level's high scores 
	 */
	private void showLeaderboard() {
		dprintln("MemoryGame.showLeaderBoard()");
		this.leaderboard = new Leaderboard(difficulty.getMode());

		String leaderboardText = "";
		if (difficulty.getMode().equalsIgnoreCase("mediummode")) {
			leaderboardText += "EQUAL PAIR Leaderboard\r\n"
					+ "------------------------------------------------------------------\n"
					+ leaderboard.getHighScore()
					+ "\n------------------------------------------------------------------\n";

		}

		else if (difficulty.getMode().equalsIgnoreCase("triomode")) {
			leaderboardText += "SAME RANK TRIO Leaderboard\r\n"
					+ "------------------------------------------------------------------\n"
					+ leaderboard.getHighScore()
					+ "------------------------------------------------------------------\n";

		}

		else if (difficulty.getMode().equalsIgnoreCase("flushmode")) {
			leaderboardText += "FLUSH Leaderboard\r\n"
					+ "------------------------------------------------------------------\n"
					+ leaderboard.getHighScore()
					+ "------------------------------------------------------------------\n";

		}

		else if (difficulty.getMode().equalsIgnoreCase("straightmode")) {
			leaderboardText += "STRAIGHT Leaderboard\r\n"
					+ "------------------------------------------------------------------\n"
					+ leaderboard.getHighScore()
					+ "------------------------------------------------------------------\n";
		}

		else {
			leaderboardText += "COMBO Leaderboard\r\n"
					+ "------------------------------------------------------------------\n"
					+ leaderboard.getHighScore()
					+ "------------------------------------------------------------------\n";
		}

		JOptionPane.showMessageDialog(this.mainFrame, leaderboardText,
				"The Nonstop Coders Poker || Leaderboard", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Shows a dialog box with information about the program
	 */
	private void showAbout() {
		dprintln("MemoryGame.showAbout()");
		final String ABOUTTEXT = "Game Customized at UPRM. Originally written by Mike Leonhard.";

		JOptionPane.showMessageDialog(this.mainFrame, ABOUTTEXT,
				"About Memory Game", JOptionPane.PLAIN_MESSAGE);
	}

	protected void turnUpAllCards() {
		for (int i = 0; i < difficulty.totalUniqueCards; i++) {
			difficulty.getGrid().get(i).setIcon(difficulty.getGrid().get(i).getFaceIcon());
		}
	}

	protected void turnDownAllCards() {
		for (int i = 0; i < difficulty.totalUniqueCards; i++) {
			if (!difficulty.getGrid().get(i).isFaceUp()) // if card wasn't turned up by the cheat
				difficulty.getGrid().get(i).setIcon(difficulty.getGrid().get(i).getBackIcon());
		}
	}

}