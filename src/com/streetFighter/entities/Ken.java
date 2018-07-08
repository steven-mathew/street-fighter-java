package com.streetFighter.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.streetFighter.gfx.Animation;
import com.streetFighter.gfx.Assets;
import com.streetFighter.main.Game;
import com.streetFighter.main.states.GameState;
import com.streetFighter.main.states.State;

public class Ken extends Creature {

	private int health;
	private int velX, velY;
	private Game game;

	// STATES

	// basic movement
	private final int IDLING      = 0;
	private final int PARRYING_R  = 1; 
	private final int PARRYING_L  = 2;
	private final int CROUCHING   = 3;
	private final int JUMPING        = 9;
	private final int FRONT_FLIPPING = 10;
	private final int BACK_FLIPPING  = 11;

	// ground attacks
	private final int ATTACKING_N4 = 4;
	private final int ATTACKING_N5 = 5;
	private final int ATTACKING_N1 = 6;
	private final int ATTACKING_N2 = 7;

	// crouch attacks
	private final int ATTACKING_C_N4 = 8;

	// air attacks: A for air
	private final int ATTACKING_A_N4  = 12;
	private final int ATTACKING_A_N5  = 13;
	private final int ATTACKING_A_N1  = 14;	

	// hurting anims
	private final int HURTING = 15;

	private final int DUMMY = 19;

	// platformer
	private final int GRAV = 1;
	private final int TERMINAL_VELOCITY = 2;
	private final int JUMP_SPEED = -10;

	private boolean[] anims = new boolean[20];

	// movement animations
	private Animation idle;
	private Animation parry_f, parry_b;
	private Animation crouch;
	private Animation jump, front_flip, back_flip;

	// attack animations
	private Animation attack_N4, attack_N5, attack_N1, attack_N2;

	// crouch attack animations
	private Animation attack_C_N4;

	// air attacks animation
	private Animation attack_A_N4, attack_A_N5, attack_A_N1;

	// ground hurt
	private Animation hurting_G;

	// cooldowns
	private boolean hurting;
	private long lastTimer;

	// random generator
	Random rand;


	public Ken(Game game, float x, float y) {
		super(x, y);
		// initialise game in constuctor to access vars
		this.game = game;

		rand = new Random();

		health = 100;
		hurting = false;

		// movement anims
		idle        = new Animation(100, Assets.idle1);
		parry_f     = new Animation(100, Assets.parry_f1);
		parry_b     = new Animation(100, Assets.parry_b1);
		crouch      = new Animation(100, Assets.crouch1);
		jump 	    = new Animation(85, Assets.jump1);
		front_flip  = new Animation(120, Assets.back_flip1);
		back_flip   = new Animation(120, Assets.front_flip1);

		// ground attack anims
		attack_N4   = new Animation(75, Assets.punch1);
		attack_N5   = new Animation(100, Assets.quick_punch1);
		attack_N1   = new Animation(75, Assets.upper_kick1);
		attack_N2   = new Animation(100, Assets.kick_low1);

		// crouch attack
		attack_C_N4 = new Animation(100, Assets.crouch_punch1);

		// air attacks
		attack_A_N4 = new Animation(100, Assets.air_punch1);
		attack_A_N5 = new Animation(100, Assets.punch_down1);
		attack_A_N1 = new Animation(100, Assets.air_kick1);		

		// hurting anim
		hurting_G   = new Animation(100, Assets.hit_stand_back1);

	}

	@Override
	public void tick() {

		// tick mvmt
		idle.tick();
		crouch.tick();

		// update anims
		parry_b.tick();		
		parry_f.tick();	

		if (anims[ATTACKING_N4])
			attack_N4.tick();

		if (anims[ATTACKING_N5])
			attack_N5.tick();

		if (anims[ATTACKING_N1])
			attack_N1.tick();

		if (anims[ATTACKING_N2])
			attack_N2.tick();

		if (anims[ATTACKING_C_N4])
			attack_C_N4.tick();

		if (anims[ATTACKING_A_N4])
			attack_A_N4.tick();

		if (anims[ATTACKING_A_N5])
			attack_A_N5.tick();

		if (anims[ATTACKING_A_N1])
			attack_A_N1.tick();

		if (y == 280){
			// if press left, move left
			if (game.getKeyManager().left1 && !game.getKeyManager().up1) {
				velX = -2;

				// reset and init true to state
				handleAnims(PARRYING_L);
			}
			// if press right, move forward
			else if (game.getKeyManager().right1 && !game.getKeyManager().up1) {
				velX = 2;

				// reset and init true to state
				handleAnims(PARRYING_R);
			} 
			//if pressing only up
			else if (game.getKeyManager().up1 && !game.getKeyManager().right1 && !game.getKeyManager().left1 && !anims[JUMPING]) {
				// jump
				velY = JUMP_SPEED - 2;
				y-=1;
				jump.index = 0;
			}
			// if pressing up, right
			else if (game.getKeyManager().up1 && game.getKeyManager().right1 && !game.getKeyManager().left1 && !anims[FRONT_FLIPPING]) {
				// jump diagonally to the right
				velY = JUMP_SPEED;
				velX = 2;
				y-=1;
				front_flip.index = 0;				
			}
			// if pressing up, left
			else if (game.getKeyManager().up1 && !game.getKeyManager().right1 && game.getKeyManager().left1 && !anims[BACK_FLIPPING]) {
				// jump diagonally to the left
				velY = JUMP_SPEED;
				velX = -2;
				y-=1;
				back_flip.index = 0;	
			}
			// if press down, crouch
			else if (!game.getKeyManager().right1 && !game.getKeyManager().left1 && game.getKeyManager().down1){

				// set hor, vertical speed to 0
				velX = 0;
				velY = 0;

				handleAnims(CROUCHING);

				if (game.getKeyManager().N4) {
					velX = 0;
					velY = 0;

					anims[CROUCHING] = false;

					// reset and init true to state
					if (checkIfRunning()) {
						handleAnims(ATTACKING_C_N4);
					}

				}

				resetAnim(attack_C_N4, ATTACKING_C_N4);

			}
			else if (game.getKeyManager().N4 && !anims[CROUCHING]){
				// set hor, vertical speed to 0
				velX = 0;
				velY = 0;

				// reset and init true to state
				if (checkIfRunning())
					handleAnims(ATTACKING_N4);
			} else if (game.getKeyManager().N5 && !anims[CROUCHING]){
				// set hor, vertical speed to 0
				velX = 0;
				velY = 0;

				// reset punch 2
				if (checkIfRunning())
					handleAnims(ATTACKING_N5);
			} else if (game.getKeyManager().N1 && !anims[CROUCHING]){
				// set hor, vertical speed to 0
				velX = 0;
				velY = 0;

				// reset punch
				if (checkIfRunning())
					handleAnims(ATTACKING_N1);
			} else if (game.getKeyManager().N2 && !anims[CROUCHING]){
				// set hor, vertical speed to 0
				velX = 0;
				velY = 0;

				// reset 
				if (checkIfRunning())
					handleAnims(ATTACKING_N2);
			} else {

				velX = 0;
				velY = 0;

				anims[CROUCHING] = false;
				anims[PARRYING_L] = false;
				anims[PARRYING_R] = false;
				anims[JUMPING] = false;
				anims[FRONT_FLIPPING] = false;
				anims[BACK_FLIPPING] = false;

				if (anims[DUMMY]) {
					handleAnims(IDLING);
				}	

			}
		}// otherwise, player is in air
		else {

			anims[PARRYING_R] = false;
			anims[PARRYING_L] = false;

			// update frames
			jump.tick();
			front_flip.tick();
			back_flip.tick();

			// if moving left
			if (velX < 0) {
				// back flip
				handleAirAttacks(back_flip, BACK_FLIPPING);
				// if moving right
			} else if (velX > 0) {
				// front flip
				handleAirAttacks(front_flip, FRONT_FLIPPING);
				// otherwise
			} else {
				// jump vertically
				handleAirAttacks(jump, JUMPING);
			}
		}

		// if attacking, stop moving
		if (anims[ATTACKING_N4] || anims[ATTACKING_N5] || anims[ATTACKING_N1] || anims[ATTACKING_N2]) {
			velX = 0;
			velY = 0;
		}

		// reset ground attack
		resetAnim(attack_N4, ATTACKING_N4);
		resetAnim(attack_N5, ATTACKING_N5);
		resetAnim(attack_N1, ATTACKING_N1);
		resetAnim(attack_N2, ATTACKING_N2);

		// reset air attacks
		resetAnim(attack_A_N4, ATTACKING_A_N4);
		resetAnim(attack_A_N5, ATTACKING_A_N5);
		resetAnim(attack_A_N1, ATTACKING_A_N1);

		// if on the floor...
		if (y == 280) {
			// if attacking...
			if (anims[ATTACKING_A_N4] || anims[ATTACKING_A_N5] || anims[ATTACKING_A_N1]) {
				anims[ATTACKING_A_N4] = false;
				anims[ATTACKING_A_N5] = false;
				anims[ATTACKING_A_N1] = false;
			}
		}

		collisions();		        	

		// if hurting.. tick anims
		if (hurting) {	
			hurting_G.tick();

			// if 400ms has passed, then the anim is complete
			if (System.currentTimeMillis() - lastTimer > 400) {
				// set anim to false, hurting to false, and inc timer
				anims[HURTING] = false;
				hurting = false;
				lastTimer += 400;
			}
		}

		// increment x by horizontal speed
		x += velX;

		// if in air, fall
		if (y < 280)
			fall();

		// if player clips through floor, set y to floor
		if (y > 280)
			y = 280;


		// check edges of screen
		checkWalls();

	}
	
	public void checkWalls(){
		
		// if x < 0...
		if (x < 0){
			// do not go past left edge
			x = 0;
		}

		// if x > 0
		if (x > Game.WIDTH * 2){
			// do not go past right edge
			x = Game.WIDTH * 2;
		}
	}

	public void fall(){

		// update y speed
		velY += GRAV;

		// if greater than max speed, just equal max speed
		if (velY > TERMINAL_VELOCITY){
			velY = TERMINAL_VELOCITY;
		} 

		// if moving left...		
		if (velX < 0) {
			// backflip
			if (back_flip.getFrame() >= 2 && back_flip.getFrame() <= 3) {
				velY = 0;
			}
		}

		// if moving right...
		if (velX > 0) {
			// front flip
			if (front_flip.getFrame() >= 2 && front_flip.getFrame() <= 3) {
				velY = 0;
			}
		}

		// if horizontally still...
		if (velX == 0) {
			// jump anims
			if (jump.getFrame() >= 3 && jump.getFrame() <= 4) {
				velY = 0;
			}
		}

		// update y by y-speed
		y += velY;


	}

	public void collisions() {

		if (game.getGameState().getRyuAttackBounds().intersects(getHitBounds())) {

			if (!hurting) {
				hurting = true;
				lastTimer = System.currentTimeMillis();
				handleAnims(HURTING);
				health-=2;
			}

			try {
				TimeUnit.MILLISECONDS.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} 

	}

	@Override
	public void render(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;	

		// when hit, vibrate randomly
		if (hurting) {
			int k = rand.nextInt(3);	
			g2d.translate(-k, k);
		}

		// draw shadow

		g.setColor(new Color(0,0,0, 125));
		g.fillOval((int) x - 61, 188 * Game.SCALE, 64, 16);

		// drawing ken

		if(anims[PARRYING_R])
			g.drawImage(getCurrentAnimFrame(), (int) (x + 9), (int) (y - 3), -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(),null);	

		else if (anims[PARRYING_L])
			g.drawImage(getCurrentAnimFrame(), (int) (x + 4), (int) y, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(),null);	

		else if (anims[CROUCHING])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) y + 36, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(),null);	

		else if (anims[JUMPING])
			g.drawImage(getCurrentAnimFrame(), (int) x - 3, (int) y - 20, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[BACK_FLIPPING])
			g.drawImage(getCurrentAnimFrame(), (int) x - 15, (int) y - 15, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[FRONT_FLIPPING])
			g.drawImage(getCurrentAnimFrame(), (int) x - 15, (int) y - 15, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(),null);

		else if (anims[ATTACKING_N4])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) (y + 3), -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[ATTACKING_N5])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) (y + 3), -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[ATTACKING_N1])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) (y - 3), -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[ATTACKING_N2])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) (y - 4), -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[ATTACKING_C_N4])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) y + 37, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);	

		else if (anims[HURTING])
			g.drawImage(getCurrentAnimFrame(), (int) x + 20, (int) y + 2, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);	

		else if (anims[ATTACKING_A_N4])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) y - 10, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[ATTACKING_A_N5])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) y - 10, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[ATTACKING_A_N1])
			g.drawImage(getCurrentAnimFrame(), (int) x - 4, (int) y - 5, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else 
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) y, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);	


		// draw hitboxes

/*		g.setColor(Color.WHITE);
		g.drawRect(getHitBounds().x, getHitBounds().y, getHitBounds().width, getHitBounds().height);

		g.setColor(Color.RED);
		g.drawRect(getAttackBounds().x, getAttackBounds().y, getAttackBounds().width, getAttackBounds().height);*/
		
	}

	private BufferedImage getCurrentAnimFrame() {

		// get animation of each frame
		
		if (anims[PARRYING_R]) 
			return parry_b.getCurrentFrame();

		else if (anims[PARRYING_L])  
			return parry_f.getCurrentFrame();

		else if (anims[CROUCHING])   
			return crouch.getCurrentFrame();

		else if (anims[JUMPING]) 
			return jump.getCurrentFrame();

		else if (anims[FRONT_FLIPPING]) 
			return front_flip.getCurrentFrame();

		else if (anims[BACK_FLIPPING]) 
			return back_flip.getCurrentFrame();

		else if (anims[ATTACKING_N4]) 
			return attack_N4.getCurrentFrame();

		else if (anims[ATTACKING_N5]) 
			return attack_N5.getCurrentFrame();

		else if (anims[ATTACKING_N1]) 
			return attack_N1.getCurrentFrame();

		else if (anims[ATTACKING_N2]) 
			return attack_N2.getCurrentFrame();

		else if (anims[ATTACKING_C_N4]) 
			return attack_C_N4.getCurrentFrame();

		else if (anims[ATTACKING_A_N4])
			return attack_A_N4.getCurrentFrame();

		else if (anims[ATTACKING_A_N5]) 
			return attack_A_N5.getCurrentFrame();

		else if (anims[ATTACKING_A_N1]) 
			return attack_A_N1.getCurrentFrame();

		else if (anims[HURTING]) 
			return hurting_G.getCurrentFrame();

		else                         
			return idle.getCurrentFrame();

	}

	public Rectangle getHitBounds() {
		// get attack hit boxes for each attack
		if (anims[CROUCHING])
			return new Rectangle((int) x - 60, (int) y + 30, 60, 80);
		else if (anims[ATTACKING_C_N4])
			return new Rectangle((int) x - 60, (int) y + 30, 60, 80);


		return new Rectangle((int) x - 60, (int) y, 60, 110);	
	}

	public Rectangle getAttackBounds() {

		// draw rectangles for each attack
		if (anims[ATTACKING_N4] && attack_N4.index >= 3 && attack_N4.index <= 4)
			return new Rectangle((int) x - 110, (int) y + 10, 60, 30);

		if (anims[ATTACKING_N5] && attack_N5.index >= 1 && attack_N5.index <= 2)
			return new Rectangle((int) x - 100, (int) y + 10, 60, 30);

		if (anims[ATTACKING_N1] && attack_N1.index >= 3 && attack_N1.index <= 6)
			return new Rectangle((int) x - 140, (int) y + 30, 90, 50);

		if (anims[ATTACKING_N2] && attack_N2.index >= 1 && attack_N2.index <= 3)
			return new Rectangle((int) x - 120, (int) y , 70, 50);
		
		if (anims[ATTACKING_A_N4] && attack_A_N4.index >= 1 && attack_A_N4.index <= 3)
			return new Rectangle((int) x - 80, (int) y + 30, 50, 30);
		
		if (anims[ATTACKING_A_N5] && attack_A_N5.index >= 1 && attack_A_N5.index <= 3)
			return new Rectangle((int) x - 80, (int) y + 20 , 70, 30);
		
		if (anims[ATTACKING_A_N1] && attack_A_N1.index >= 1 && attack_A_N1.index <= 3)
			return new Rectangle((int) x - 120, (int) y + 20, 70, 50);

		if (anims[ATTACKING_C_N4] && attack_C_N4.index >= 0 && attack_C_N4.index <= 1)
			return new Rectangle((int) x - 90, (int) y + 40, 60, 30);


		return new Rectangle((int) x - 60, (int) y, 0, 0);
	}

	public void handleAnims(int unchanged){

		// make all anims false...
		for (int i = 0; i < 19; i++) {
			anims[i] = false;
		}

		// except active anim
		anims[unchanged] = true;

	}

	public void handleAirAttacks(Animation anim, int index) {

		// if g, h, b while in air... set all anims to false except called anim
		if (game.getKeyManager().N4) {
			handleAnims(ATTACKING_A_N4);
		} else if (game.getKeyManager().N5) {
			handleAnims(ATTACKING_A_N5);
		} else if (game.getKeyManager().N1) {
			handleAnims(ATTACKING_A_N1);
		} else if (checkIfRunning()){
			// update anim and set all to false
			anim.tick();
			handleAnims(index);
		}

	}

	public boolean checkIfRunning() {

		// counter
		int i = 0;

		// traverse through anims
		for (boolean b : anims) {
			// if false, increment counter
			if (b == false)
				i++;
		}
		// if entire array is false, return false
		if (i == 19) {
			return false;
		}

		// otherwise, true
		return true;
	}

	public void resetAnim(Animation anim, int frame) {
		if (anim.hasPlayedOnce()) {			
			anim.setPlayed();
			anims[frame] = false;
		}

	}

	public int getHealth() {
		return health;
	}

	public int getKenX() {
		return (int) x;
	}
}
