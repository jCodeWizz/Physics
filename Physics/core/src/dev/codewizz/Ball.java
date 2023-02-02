package dev.codewizz;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ball {

	private final float BOUNCE_STOP = 0.1f;
	private final float MASS = 20f;
	private static final Sprite TEXTURE = new Sprite(new Texture(Gdx.files.internal("ball.png")));
	
	private Vector2 pos;
	private Vector2 speed;
	private Vector2 acc;
	private Vector2 size;
	
	private ArrayList<Vector2> forces;
	
	public Ball(float x, float y) {
		pos = new Vector2(x, y);
		size = new Vector2(64, 64);
		speed = new Vector2();
		acc = new Vector2();
		
		forces = new ArrayList<Vector2>();
	}
	
	public void update(float dt, ArrayList<Solid> solids) {
		
		Vector2 totalForce = new Vector2();
		
		forces.add(new Vector2(0, MASS * -9.81f));
		
		if(Gdx.input.isButtonPressed(0)) {
			System.out.println("APLLYING");
			forces.add(new Vector2(MASS * 5f, 0));
		}
		
		
		for(Vector2 force : forces) {
			totalForce.add(force);
		}
		forces.clear();
		
		// Fres = MASS * Acc -> Fres/MASS = Acc
		acc.x = totalForce.x / MASS;
		acc.y = totalForce.y / MASS;
		
		speed.x += acc.x * dt;
		speed.y += acc.y * dt;

		
		if(!move(solids)) {
			
		}
	}
	
	/*
	 *	RETURNS FALSE IF *NOT* HIT ANYTHING 
	 */
	
	public boolean move(ArrayList<Solid> solids) {
		final float STEP_SIZE = 10f;

		boolean hitVertical = false;
		boolean hitHorizontal = false;
		
		if(speed.x != 0) {
			float step = speed.x/STEP_SIZE;
			float a = speed.y/step;
			
			for(int i = 0; i < (int)STEP_SIZE; i++) {
				
				if(!hitVertical) {
					pos.y += a;
				}
				
				if(!hitHorizontal) {
					pos.x += step;
				}
				
				for(Solid solid : solids) {
					if(solid.getBounds().intersects(getBoundsVertical())) {
						hitVertical = true;
						pos.y -= a;
						applyBounceForce(true);
					}  
					if(solid.getBounds().intersects(getBoundsHorizontal())) {
						hitHorizontal = true;
						pos.x -= step;
						applyBounceForce(false);
					}
				}
			}
		} else {
			float a = speed.y/STEP_SIZE;
			for(int i = 0; i < (int)STEP_SIZE; i++) {
				
				if(!hitVertical) {
					pos.y += a;
				}
				
				for(Solid solid : solids) {
					if(solid.getBounds().intersects(getBoundsVertical())) {
						hitVertical = true;
						pos.y -= a;
						applyBounceForce(true);
					}  
				}
			}
		}
		
		
		return false;
	}
	
	public void applyBounceForce(boolean vertical) {
		if(vertical) {
			if(Math.abs(speed.y) < BOUNCE_STOP) {
				speed.y = 0;
			} else {
				speed.y *= -1;
			}
		} else {
			if(Math.abs(speed.x) < BOUNCE_STOP) {
				speed.x = 0;
			} else {
				speed.x *= -1;
			}
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)pos.x, (int)pos.y, (int)size.x, (int)size.y);
	}
	
	public Rectangle getBoundsVertical() {
		return new Rectangle((int)pos.x + 5, (int)pos.y, (int)size.x - 10, (int)size.y);
	}
	
	public Rectangle getBoundsHorizontal() {
		return new Rectangle((int)pos.x, (int)pos.y + 5, (int)size.x, (int)size.y - 10);
	}
	
	public void render(SpriteBatch b) {
		b.draw(TEXTURE, pos.x, pos.y, size.x, size.y);
	}
}
