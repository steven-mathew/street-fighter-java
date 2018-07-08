package com.streetFighter.main;

/**
 * Street Fighter
 * This program attempts to recreate the classic fighter game, Street Fighter. 
 * New concepts: Canvas, implements [...], abstract classes, Threads for Runnables, Image Loader, packagaes, super constructors
 * @author Steven Mathew
 * 15 June 2018 
 */

import java.io.File;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.streetFighter.gfx.Assets;
import com.streetFighter.gfx.ImageLoader;
import com.streetFighter.gfx.SpriteSheet;
import com.streetFighter.main.states.GameState;
import com.streetFighter.main.states.State;
import com.streetFighter.managers.KeyManager;

@SuppressWarnings({ "unused", "serial" })
public class Game extends Canvas implements Runnable {
	
	// declare constants
	public static final String TITLE  = "Street Fighter II";
	public static final int    WIDTH  = 256;
	public static final int    HEIGHT = 224;
	public static final int    SCALE  = 2;
	
	// tick variables
	public boolean running = false;
	public int tickCount = 0;
	
	// graphics
	private Graphics g;
	
	// states
	private State menuState;
	private State gameState;

	// input
	private KeyManager keyManager;
	
	// init jFrame
	private JFrame frame;
	
	// init scanner, and random
	private Scanner sc;
	private Random rand;
	
	// int map
	private int map = 0;
	
	public Game() {
	
		// init frame properties
		frame = new JFrame(TITLE);
		frame.setSize(WIDTH * SCALE, HEIGHT * SCALE);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		
		frame.setUndecorated(false);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		setFocusable(false);
				
		// user input
		keyManager = new KeyManager();
		frame.addKeyListener(keyManager);
		
		// pack everything
		frame.pack();
	}
		
	/**
	 * @see ex_1.png in project dir for explanation of game updating
	 * @see ex_2.png [...] 							of game state managing
	 */

	public synchronized void start() throws IOException {
		
		// the program is running...
		running = true;		
		
		// pre-load assets
		Assets.init();
			
		// states
		gameState = new GameState(this);
		State.setState(gameState);
		
		// print out title to user
		System.out.println(" ________  _________  ________  _______   _______  _________        ________ ___  ________  ___  ___  _________  _______   ________          ___  ___");
		System.out.println("|\\   ____\\|\\___   ___|\\   __  \\|\\  ___ \\ |\\  ___ \\|\\___   ___\\     |\\  _____|\\  \\|\\   ____\\|\\  \\|\\  \\|\\___   ___|\\  ___ \\ |\\   __  \\        |\\  \\|\\  \\ ");
		System.out.println("\\ \\  \\___|\\|___ \\  \\_\\ \\  \\|\\  \\ \\   __/|\\ \\   __/\\|___ \\  \\_|     \\ \\  \\__/\\ \\  \\ \\  \\___|\\ \\  \\\\\\  \\|___ \\  \\_\\ \\   __/|\\ \\  \\|\\  \\       \\ \\  \\ \\  \\  ");
		System.out.println(" \\ \\_____  \\   \\ \\  \\ \\ \\   _  _\\ \\  \\_|/_\\ \\  \\_|/__  \\ \\  \\       \\ \\   __\\\\ \\  \\ \\  \\  __\\ \\   __  \\   \\ \\  \\ \\ \\  \\_|/_\\ \\   _  _\\       \\ \\  \\ \\  \\  ");
		System.out.println("  \\|____|\\  \\   \\ \\  \\ \\ \\  \\\\  \\\\ \\  \\_|\\ \\ \\  \\_|\\ \\  \\ \\  \\       \\ \\  \\_| \\ \\  \\ \\  \\|\\  \\ \\  \\ \\  \\   \\ \\  \\ \\ \\  \\_|\\ \\ \\  \\\\  \\|       \\ \\  \\ \\  \\ ");
		System.out.println("    ____\\_\\  \\   \\ \\__\\ \\ \\__\\\\ _\\\\ \\_______\\ \\_______\\  \\ \\__\\       \\ \\__\\   \\ \\__\\ \\_______\\ \\__\\ \\__\\   \\ \\__\\ \\ \\_______\\ \\__\\\\ _\\        \\ \\__\\ \\__\\");
		System.out.println("   |\\_________\\   \\|__|  \\|__|\\|__|\\|_______|\\|_______|   \\|__|        \\|__|    \\|__|\\|_______|\\|__|\\|__|    \\|__|  \\|_______|\\|__|\\|__|        \\|__|\\|__|");
		System.out.println("   \\|_________|                                                                                                                                           ");
		
		// print map selection
		System.out.println("===========================");		
		System.out.println("WELCOME! MAP SELECTION...");		
		System.out.println("1. Forest\n2. Main\n3. Ryu");		
		System.out.println("===========================");		

		// instantiate scanner, and randomizer and init vars
		sc = new Scanner(System.in);	
		rand = new Random();
		int choice = 0;
		
		// ask user for input
		System.out.println("\nWould you like to select your map or choose a random map?");
		
		// while user doesn't make valid choice, keep asking for choice between map/random
		do {
			try {
				System.out.println("Please enter 1 (to select) or 2 (for random).");
				choice = sc.nextInt();
			} catch (Exception e) {
				System.err.println("That is not an integer, warrior.");
				sc = new Scanner(System.in);
			}
		} while (choice < 1 || choice > 2);
		
		// if choice is 1, then choose map
		if(choice == 1) {
			System.out.println("What map would you like?");
			do {
				try {
					// output to user
					System.out.println("Please enter an integer from 1-3.");
					// store var in map
					map = sc.nextInt();
									
				} catch(Exception e) {
					System.err.println("That is not an integer, warrior.");
					sc = new Scanner(System.in);
				}
				
				System.out.println(map);
				
			} while (map < 1 || map > 3);
		// if choice is 2, then randomly select a map
		} else if (choice == 2) {
			map = rand.nextInt(3) + 1;
		}
		
		// thread this class
		new Thread(this).start();
	}
	
	public synchronized void stop() {
		// if program is stopped, running is false
		running = false;
	}
	
	/**
	 * Minecraft: Notch's game loop
	 * @link https://stackoverflow.com/questions/18283199/java-main-game-loop
	 */
	
	public void run() {
		
		// init vars
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000.0 / 60.0;
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		// while the program is running....
		while (running) {
			
			// get the current system time
			long now = System.nanoTime();
			// find delta by taking difference between now and last
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			
			// can render each frame...
			boolean canRender = true;
			
			// if ratio is greater than one, meaning...
			while (delta >= 1) {
				/* if the current time - last / n, where n
				   can be any real number is greater than 1
			    update the game...*/
				ticks++;
				tick();
				delta--;
				canRender = true;
			}
			
			// sleep program so that not to many frames are produced (reduce lag)
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// if can render...
			if (canRender) {
				// increment frames and render
				frames++;
				render();

			}
			
			// if one second has passed...
			if (System.currentTimeMillis() - lastTimer > 1000) {
				// increment last timer, output frames to user
				lastTimer += 1000;
				System.out.println(ticks + " ticks, " + frames + " frames");
				frames = 0;
				ticks = 0;
			}						
		}
		
	}
	
	public void tick() {
		
		// update keyboard input
		keyManager.tick();
		
		// if current state exist, then update the game
		if (State.getState() != null) {
			
			// increment tick count and get state of program
			tickCount++;
			State.getState().tick();

		}
       
	}
	
	
	public void render() {
		
		BufferStrategy bs = getBufferStrategy();
		
		// create a double buffering strategy
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		// create temp white rect that fills screen
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		/* ALL DRAWING HERE */
		
		// init maps
		ImageIcon ryuStage    = new ImageIcon("ryu_stage.png");
		ImageIcon mainStage   = new ImageIcon("main_stage.gif");
		ImageIcon forestStage = new ImageIcon("forest_stage.gif");
		
		
		// decision making: if player chooses a map (as def'd in start() method), draw that map 
		if (map == 1)
			g.drawImage(forestStage.getImage(), -900, -220, forestStage.getIconWidth() * 2, forestStage.getIconHeight() * 2,null);

		if (map == 2)
			g.drawImage(mainStage.getImage(), -67, -67, null);
	
		if (map == 3)
			g.drawImage(ryuStage.getImage(), -212, 30, ryuStage.getIconWidth() * Game.SCALE, ryuStage.getIconHeight() * Game.SCALE,null);
		
		
		// if current state exist, then render		
		if (State.getState() != null) {		
			tickCount++;
			State.getState().render(g);	
		}
					
		/* ALL DRAWING HERE */
		
		g.dispose();
		bs.show();
	}	
	
	public static void main(String[] args) throws IOException {
		Game game = new Game();
		game.start();
	}
	
	// GETTERS AND SETTERS
	
	/**
	 * @description 
	 * 	   gets key presses of user
	*/
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	/**
	 * @description 
	 * 	   gets current game state
	*/
	public State getGameState() {
		return gameState;
	}
}
