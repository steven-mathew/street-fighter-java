package com.streetFighter.main.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import com.streetFighter.entities.Ken;
import com.streetFighter.entities.Ryu;
import com.streetFighter.gfx.*;
import com.streetFighter.main.Game;

public class GameState extends State {
	
	// init ryu, ken
	private Ryu ryu;
	private Ken ken;
	
	// constructor
	public GameState(Game game) {
		super(game);
		
		// create instance of ryu, ken 
		ryu = new Ryu(game, 60, 280);
		ken = new Ken(game, 224 * 2, 280);
		
	}
	
	@Override
	public void tick() {
		// update hitboxes, attack boxes
		ryu.getAttackBounds();
		ryu.getHitBounds();
		
		ken.getAttackBounds();
		ken.getHitBounds();
		
		// update entire instance
		ryu.tick();
		ken.tick();
	
	}

	@Override
	public void render(Graphics g) {
				
		// get images for ui
		ImageIcon healthBar = new ImageIcon("healthBar.png");
		ImageIcon ryuFont = new ImageIcon("ryuFont.png");
		ImageIcon kenFont = new ImageIcon("kenFont.png");

		// print ui for ryu
		g.setColor(Color.yellow);
		double percentRyu = ryu.getHealth() / 100.0;	
		g.fillRect(61, 19, (int) (173 * percentRyu), 11);
		g.drawImage(ryuFont.getImage(), kenFont.getIconWidth() - 35, 40, 48, 16, null);
		
		// print ui for ken
		g.setColor(Color.yellow);
		double percentKen  = ken.getHealth() / 100.0;	
		g.fillRect(99 + 29 + 144, 19, (int) (173 * percentKen) , 11);
		g.drawImage(kenFont.getImage(), (Game.WIDTH * Game.SCALE) - 2 * kenFont.getIconWidth() + kenFont.getIconWidth() / 2 + 32, 40, 48, 16, null);
		
		// drawn last so that rect and ui could be under
		g.drawImage(healthBar.getImage(),  60, 16, (int) (healthBar.getIconWidth() * 1.2), (int) (healthBar.getIconHeight() * 1.2) ,null);

		
		// render instances
		ryu.render(g);
		ken.render(g);	
				
		
		g.setColor(Color.BLACK);
		// end game
		if (ken.getHealth() <= 0) {
			// win screen
			String ryuWin = "RYU WINS THE GAME!";
			g.fillRect(0, 0, Game.WIDTH * 2, Game.HEIGHT * 2);
			g.setColor(Color.WHITE);
			int width = g.getFontMetrics().stringWidth(ryuWin);
			g.drawString(ryuWin, Game.WIDTH - width/2, Game.HEIGHT);
		} else if (ryu.getHealth() <= 0) {
			// win screen
			String kenWin = "KEN WINS THE GAME!";
			g.fillRect(0, 0, Game.WIDTH * 2, Game.HEIGHT * 2);
			g.setColor(Color.WHITE);
			int width = g.getFontMetrics().stringWidth(kenWin);
			g.drawString(kenWin, Game.WIDTH - width/2, Game.HEIGHT);
		}
		
	}

	@Override
	public void music() {
		// empty for now because Java 8 API issues on school computers
	}
	
	// GETTERS AND SETTERS:
	
	public Rectangle getRyuHitBounds() {
		return ryu.getHitBounds();
	}
	
	public Rectangle getRyuAttackBounds() {
		return ryu.getAttackBounds();
	}
	
	public Rectangle getKenHitBounds() {
		return ken.getHitBounds();
	}
	
	public Rectangle getKenAttackBounds() {
		return ken.getAttackBounds();
	}
	
	public int getRyuX() {
		return ryu.getRyuX();
	}

	public int getKenX() {
		return ken.getKenX();
	}
	
}
