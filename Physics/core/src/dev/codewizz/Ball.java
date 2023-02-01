package dev.codewizz;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ball {

	private final float MASS = 20f;
	
	private Vector2 pos;
	private Vector2 speed;
	private Vector2 acc;
	
	private ArrayList<Vector2> forces;
	
	public Ball(float x, float y) {
		pos = new Vector2(x, y);
		speed = new Vector2();
		acc = new Vector2();
		
		forces = new ArrayList<Vector2>();
	}
	
	public void update(float dt) {
		Vector2 totalForce = new Vector2();
		
		forces.add(new Vector2(0, MASS * 9.81f));
		
		
		for(Vector2 force : forces) {
			totalForce.add(force);
		}
		
		// Fres = MASS * Acc -> Fres/MASS = Acc
		acc.x = totalForce.x / MASS;
		acc.y = totalForce.y / MASS;
		
		speed.x += acc.x * dt;
		speed.y += acc.y * dt;
		
		pos.add(speed);
	}
	
	public void render(SpriteBatch b) {
		
	}
}
