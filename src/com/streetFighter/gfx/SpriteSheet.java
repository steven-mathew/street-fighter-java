package com.streetFighter.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	private BufferedImage sheet;
	
	/**
	 * @param sheet
	 * 		send image to be cropped
	 */
	
	public SpriteSheet(BufferedImage sheet) {
		this.sheet = sheet;
	}
	
	/**
	 * @param width
	 * 		get width of subimage
	 * @param height
	 * 		get height of subimage
	 * @param x
	 * 		x position of subimage
	 * @param y
	 * 		y position of subimage
	 * @return
	 * 		returns a BufferedImage to calling class
	 */
	
	public BufferedImage crop(int width, int height, int x, int y) {
		return sheet.getSubimage(x, y, width, height);
	}

}
