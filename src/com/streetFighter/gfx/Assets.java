package com.streetFighter.gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Assets {

	
	// ============================================ SPRITE SHEETS: RYU ============================================
	
	
	// basic movement
	public static BufferedImage[] 	idle            = new BufferedImage[6], 
									parry_f         = new BufferedImage[8], 
									parry_b         = new BufferedImage[8],
									crouch          = new BufferedImage[1];

	// ground attack
	public static BufferedImage[] 	punch           = new BufferedImage[6], 
									quick_punch     = new BufferedImage[3],
									crouch_punch    = new BufferedImage[3],
									crouch_attack   = new BufferedImage[8],
									uppercut        = new BufferedImage[8];

	// ground kick
	public static BufferedImage[]   kick_low        = new BufferedImage[5],
									upper_kick      = new BufferedImage[9];

	// air attack
	public static BufferedImage[]   air_punch       = new BufferedImage[6],
									air_kick        = new BufferedImage[5],
									punch_down      = new BufferedImage[4];
	// jumps
	public static BufferedImage[]   back_flip       = new BufferedImage[8],
									front_flip      = new BufferedImage[8],
									jump            = new BufferedImage[11];

	// hurt
	public static BufferedImage[]   crouch_hit      = new BufferedImage[2],
									crouch_hit_back = new BufferedImage[4],
									hit_stand       = new BufferedImage[2],
									hit_stand_back  = new BufferedImage[4],
									knockback       = new BufferedImage[4],
									recover         = new BufferedImage[5];
	// misc.
	public static BufferedImage[]   win             = new BufferedImage[10],
									dead            = new BufferedImage[1];
	

	// ============================================ SPRITE SHEETS: KEN ============================================

	
	// basic movement
	public static BufferedImage[] 	idle1            = new BufferedImage[6], 
									parry_f1         = new BufferedImage[8], 
									parry_b1         = new BufferedImage[8],
									crouch1          = new BufferedImage[1];

	// ground attack
	public static BufferedImage[] 	punch1           = new BufferedImage[8], 
									quick_punch1     = new BufferedImage[4],
									crouch_punch1    = new BufferedImage[3],
									crouch_attack1   = new BufferedImage[5],
									uppercut1        = new BufferedImage[8];

	// ground kick
	public static BufferedImage[]   kick_low1        = new BufferedImage[5],
									upper_kick1      = new BufferedImage[10];

	// air attack
	public static BufferedImage[]   air_punch1       = new BufferedImage[7],
									air_kick1        = new BufferedImage[6],
									punch_down1      = new BufferedImage[4];
	// jumps
	public static BufferedImage[]   back_flip1       = new BufferedImage[8],
									front_flip1      = new BufferedImage[8],
									jump1            = new BufferedImage[11];

	// hurt
	public static BufferedImage[]   crouch_hit1      = new BufferedImage[2],
									crouch_hit_back1 = new BufferedImage[4],
									hit_stand1       = new BufferedImage[2],
									hit_stand_back1  = new BufferedImage[4],
									knockback1       = new BufferedImage[4],
									recover1         = new BufferedImage[5];
	// misc.
	public static BufferedImage[]   win1             = new BufferedImage[10],
									dead1            = new BufferedImage[1];
	

	/**
	 * @description 
	 * 		pre-loads sprite sheet and crops the image as it stores into BufferedImage array
	 */

	public static void init() throws IOException {

		// ============================================ SPRITE SHEETS: RYU ============================================

		SpriteSheet ss_crouch          = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/crouch.png"));
		SpriteSheet ss_crouch_punch    = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/crouch_punch.png"));
		SpriteSheet ss_hit_stand_back  = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/hit_stand_b.png"));
		SpriteSheet ss_idle            = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/idle.png"));
		SpriteSheet ss_kick_low        = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/kick_low.png"));
		SpriteSheet ss_parry_back      = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/parry_b.png"));
		SpriteSheet ss_parry_front     = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/parry_f.png"));
		SpriteSheet ss_punch           = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/punch.png"));
		SpriteSheet ss_quick_punch     = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/quick_punch.png"));
		SpriteSheet ss_upper_kick      = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/upper_kick.png"));
		SpriteSheet ss_jump			   = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/jump.png"));
		SpriteSheet ss_front_flip      = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/front_flip.png"));
		SpriteSheet ss_back_flip      = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/back_flip.png"));
		SpriteSheet ss_air_punch       = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/air_punch.png"));
		SpriteSheet ss_air_kick        = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/air_kick.png"));
		SpriteSheet ss_punch_down      = new SpriteSheet(ImageLoader.loadImage("/textures/ryu/punch_down.png"));

		// ============================================ SPRITE CROPPING ============================================

		// loops through frame slices and stores in array

		// basic movement:
		for (int i = 0; i < 6; i++) 
			idle[i] = ss_idle.crop(57, 106, 57 * i, 0);

		for (int i = 0; i < 8; i++) 
			parry_f[i] = ss_parry_front.crop(70, 110, 70 * i, 0);

		for (int i = 0; i < 8; i++) 
			parry_b[i] = ss_parry_back.crop(70, 108, 70 * i, 0);

		crouch[0] = ss_crouch.crop(54, 73, 0, 0);
			
		jump[0] = ss_jump.crop(70, 154, 0, 0);
		jump[1] = ss_jump.crop(70, 154, 0, 0);
		jump[2] = ss_jump.crop(70, 154, 0, 0);
		
		for (int i = 3; i < 11; i++) 
			jump[i] = ss_jump.crop(70, 154, 70 * (i - 2), 0);
		
		
		for (int i = 0; i < 8; i++) 
			front_flip[i] = ss_front_flip.crop(88, 129, 88 * i, 0);
		
		for (int i = 0; i < 8; i++) 
			back_flip[i] = ss_back_flip.crop(88, 129, 88 * i, 0);
		
		// ground attack:
		for (int i = 0; i < 6; i++) 
			punch[i] = ss_punch.crop(101, 102, 101 * i, 0);

		for (int i = 0; i < 3; i++) 
			quick_punch[i] = ss_quick_punch.crop(94, 102, 94 * i, 0);

		// crouch attack	
		for (int i = 0; i < 3; i++) 
			crouch_punch[i] = ss_crouch_punch.crop(86, 72, 86 * i, 0);	
				
		// air attacks				
		for (int i = 0; i < 6; i++) 
			air_punch[i] = ss_air_punch.crop(83, 95, 83 * i, 0);
		
		for (int i = 0; i < 4; i++) 
			punch_down[i] = ss_punch_down.crop(75, 90, 75 * i, 0);
		
		for (int i = 0; i < 5; i++) 
			air_kick[i] = ss_air_kick.crop(99, 94, 99 * i, 0);
				
		// ground kick
		for (int i = 0; i < 5; i++) 
			kick_low[i] = ss_kick_low.crop(115, 111, 115 * i, 0);

		for (int i = 0; i < 9; i++) 
			upper_kick[i] = ss_upper_kick.crop(110, 111, 110 * i, 0);

		// hurting anims
  		for (int i = 0; i < 4; i++) 
			hit_stand_back[i] = ss_hit_stand_back.crop(77, 104, 77 * i, 0);

		// ============================================ SPRITE SHEETS: KEN ============================================

		SpriteSheet ss_crouch1          = new SpriteSheet(ImageLoader.loadImage("/textures/ken/crouch.png"));
		SpriteSheet ss_crouch_punch1    = new SpriteSheet(ImageLoader.loadImage("/textures/ken/crouch_punch.png"));;
		SpriteSheet ss_hit_stand_back1  = new SpriteSheet(ImageLoader.loadImage("/textures/ken/hit_stand_b.png"));
		SpriteSheet ss_idle1            = new SpriteSheet(ImageLoader.loadImage("/textures/ken/idle.png"));
		SpriteSheet ss_kick_low1        = new SpriteSheet(ImageLoader.loadImage("/textures/ken/kick_low.png"));
		SpriteSheet ss_parry_back1      = new SpriteSheet(ImageLoader.loadImage("/textures/ken/parry_b.png"));
		SpriteSheet ss_parry_front1     = new SpriteSheet(ImageLoader.loadImage("/textures/ken/parry_f.png"));
		SpriteSheet ss_punch1           = new SpriteSheet(ImageLoader.loadImage("/textures/ken/punch.png"));
		SpriteSheet ss_quick_punch1     = new SpriteSheet(ImageLoader.loadImage("/textures/ken/quick_punch.png"));
		SpriteSheet ss_upper_kick1      = new SpriteSheet(ImageLoader.loadImage("/textures/ken/upper_kick.png"));
		SpriteSheet ss_jump1			= new SpriteSheet(ImageLoader.loadImage("/textures/ken/jump.png"));
		SpriteSheet ss_front_flip1      = new SpriteSheet(ImageLoader.loadImage("/textures/ken/front_flip.png"));
		SpriteSheet ss_back_flip1       = new SpriteSheet(ImageLoader.loadImage("/textures/ken/back_flip.png"));
		SpriteSheet ss_air_punch1       = new SpriteSheet(ImageLoader.loadImage("/textures/ken/air_punch.png"));
		SpriteSheet ss_air_kick1        = new SpriteSheet(ImageLoader.loadImage("/textures/ken/air_kick.png"));
		SpriteSheet ss_punch_down1      = new SpriteSheet(ImageLoader.loadImage("/textures/ken/punch_down.png"));

		// ============================================ SPRITE CROPPING ============================================

		// loops through frame slices and stores in array ...

		// basic movement:

		for (int i = 0; i < 6; i++) 
			idle1[i] = ss_idle1.crop(57, 106, 57 * i, 0);

		for (int i = 0; i < 8; i++) 
			parry_f1[i] = ss_parry_front1.crop(70, 110, 70 * i, 0);

		for (int i = 0; i < 8; i++) 
			parry_b1[i] = ss_parry_back1.crop(70, 110, 70 * i, 0);

		crouch1[0] = ss_crouch1.crop(54, 73, 0, 0);

		// aerial moves 
		
		jump1[0] = ss_jump1.crop(61, 124, 0, 0);
		jump1[1] = ss_jump1.crop(61, 124, 0, 0);
		jump1[2] = ss_jump1.crop(61, 124, 0, 0);
		
		for (int i = 3; i < 11; i++) 
			jump1[i] = ss_jump1.crop(61, 124, 61 * (i - 2), 0);
		
	
		for (int i = 0; i < 8; i++) 
			front_flip1[i] = ss_front_flip1.crop(83, 125, 83 * i, 0);
		
		for (int i = 0; i < 8; i++) 
			back_flip1[i] = ss_back_flip1.crop(83, 125, 83 * i, 0);
		
		// ground attacks

		for (int i = 0; i < 8; i++) 
			punch1[i] = ss_punch1.crop(103, 103, 103 * i, 0);

		for (int i = 0; i < 4; i++) 
			quick_punch1[i] = ss_quick_punch1.crop(95, 102, 95 * i, 0);

		for (int i = 0; i < 3; i++) 
			crouch_punch1[i] = ss_crouch_punch1.crop(89, 72, 89 * i, 0);

		// ground kick

		for (int i = 0; i < 5; i++) 
			kick_low1[i] = ss_kick_low1.crop(118, 105, 118 * i, 0);

		for (int i = 0; i < 10; i++) 
			upper_kick1[i] = ss_upper_kick1.crop(135, 108, 135 * i, 0);
		
		// air attacks				
		for (int i = 0; i < 7; i++) 
			air_punch1[i] = ss_air_punch1.crop(84, 95, 84 * i, 0);
				
		for (int i = 0; i < 4; i++) 
			punch_down1[i] = ss_punch_down1.crop(68, 88, 68 * i, 0);
				
		for (int i = 0; i < 6; i++) 
			air_kick1[i] = ss_air_kick1.crop(106, 83, 106 * i, 0);

		// hurt
		
		for (int i = 0; i < 4; i++) 
			hit_stand_back1[i] = ss_hit_stand_back1.crop(79, 104, 79 * i, 0);

	}
}
