package com.streetFighter.main.states;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.streetFighter.main.Game;

public abstract class State {

	// init State var
	private static State currState = null;
	
	// setter for State
	public static void setState(State state) {
		currState = state;
	}
	
	// getter for State
	public static State getState() {
		return currState;
	}
	
	// init Game var
	protected Game game;
	
	// constructor
	public State(Game game) {
		this.game = game;
	}
	
	// nethods that all extended classes will share...
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void music();
		
	// hitbox methods
	public abstract Rectangle getRyuHitBounds();	
	public abstract Rectangle getRyuAttackBounds();	
	public abstract Rectangle getKenHitBounds();	
	public abstract Rectangle getKenAttackBounds();	
	public abstract int getRyuX();	
	public abstract int getKenX();	
}
