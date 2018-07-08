package com.streetFighter.managers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	private boolean[] keys;
	public boolean up, down, left, right;
	public boolean up1, down1, left1, right1;
	
	public boolean G, H, B, N;
	public boolean N1, N2, N4, N5;

	
	public KeyManager() {
		keys = new boolean[256];
	}
	
	public void tick() {
		
		// movement P1
		up    = keys[KeyEvent.VK_W];
		down  = keys[KeyEvent.VK_S];
		left  = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		
		// attack P1
		G = keys[KeyEvent.VK_G];
		H = keys[KeyEvent.VK_H];
		B = keys[KeyEvent.VK_B];
		N = keys[KeyEvent.VK_N];
		
		// movement P2
		up1    = keys[KeyEvent.VK_UP];
		down1  = keys[KeyEvent.VK_DOWN];
		left1  = keys[KeyEvent.VK_LEFT];
		right1 = keys[KeyEvent.VK_RIGHT];
		
		// attack P2
		N4 = keys[KeyEvent.VK_NUMPAD4];
		N5 = keys[KeyEvent.VK_NUMPAD5];
		N1 = keys[KeyEvent.VK_NUMPAD1];
		N2 = keys[KeyEvent.VK_NUMPAD2];
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
