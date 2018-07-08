package com.streetFighter.gfx;

import java.awt.image.BufferedImage;

public class Animation {

	// delcare and init vars
	public int rate, index;
	private long lastTime, timer;
	private BufferedImage[] frames;
	
	private boolean playedOnce = false;
	
	public Animation(int rate, BufferedImage[] frames) {

		// inits class vars
		this.rate = rate;
		this.frames = frames;
		
		index = 0;
		// ms passed since start of program
		lastTime = System.currentTimeMillis();
		
	}
	
	public void tick() {
		
		// time passed since current tick method and previously called tick method
		timer += System.currentTimeMillis() - lastTime;
		// reset
		lastTime = System.currentTimeMillis();
			
		// if timer > rate per frame...
		if (timer > rate) {
			// increment index (i.e. go to next frame)
			index++;
			timer = 0;
				
			// if last frame, go to first frame
			if (index == frames.length) {
				index = 0;
				// then the animation has played once...
				playedOnce = true;
			}
		}
		
	}
	
	// GETTERS AND SETTERS:
	
	// gets index of frame of animation
	public int getFrame() {
		return index;
	}
	
	// gets image of current frame
	public BufferedImage getCurrentFrame() {
		return frames[index];
	}
	
	// ask if has been played...
	public boolean hasPlayedOnce() {
		return playedOnce;
	}
	
	// set played once to false...
	public boolean setPlayed() {
		return playedOnce = false;
	}
	
}
