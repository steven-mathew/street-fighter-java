package com.streetFighter.entities;

public abstract class Creature extends Entity {
	
	// every subclass of creature has an x,y
	public Creature(float x, float y) {
		super(x, y);
	}
}
