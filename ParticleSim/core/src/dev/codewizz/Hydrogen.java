package dev.codewizz;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Hydrogen {

	private static Texture TEXTURE = new Texture("Hydrogen.png");
	private final float MASS = 20f;
	private final float FwC = 0.01f;
	private final float BOUNCEC = 500f;
	
	public Vector2 pos;
	public float r = 16f;
	
	public boolean grounded = false;
	
	private Vector2 speed;
	private Vector2 acc;
	private ArrayList<Vector2> forces;
	
	public Hydrogen(float x, float y) {
		pos = new Vector2(x, y);
		speed = new Vector2();
		acc = new Vector2();

		forces = new ArrayList<Vector2>();
	}
	
	public void update(float dt, ArrayList<Solid> solids, ArrayList<Hydrogen> p) {
		Vector2 totalForce = new Vector2();

		forces.add(new Vector2(0, MASS * -9.81f));
		if(grounded)
			forces.add(new Vector2(0, MASS * 9.81f));
		
		forces.add(new Vector2(-0.5f * FwC * speed.x * 32f * 32f * (float)Math.PI * 1.2f, -0.5f * FwC * speed.y * 32f * 32f * (float)Math.PI * 1.2f));
		
		if(grounded) {
			// APLY FRICTION
			forces.add(new Vector2(0, 0));
		}

		for (Vector2 force : forces) {
			totalForce.add(force);
		}
		forces.clear();

		// Fres = MASS * Acc -> Fres/MASS = Acc
		acc.x = totalForce.x / MASS;
		acc.y = totalForce.y / MASS;

		speed.x += acc.x * dt;
		speed.y += acc.y * dt;
		
		if (!move(solids, p)) {

		}
	}
	
	public boolean move(ArrayList<Solid> solids, ArrayList<Hydrogen> p) {
		final float STEP_SIZE = 10f;

		boolean hitVertical = false;
		boolean hitHorizontal = false;

		float stepX = speed.x / STEP_SIZE;
		float stepY = speed.y / STEP_SIZE;

		for (int i = 0; i < (int) STEP_SIZE; i++) {

			if (!hitVertical) {
				pos.y += stepY;
			}

			if (!hitHorizontal) {
				pos.x += stepX;
			}
			 
			for (Hydrogen h : p) {
				if(!h.equals(this)) {
					float distance = Vector2.dst(pos.x, pos.y, h.pos.x, h.pos.y);
					
					if(distance <= r + h.r) {
						// do some impulse shit lolmfao
						Vector2 normal = pos.sub(h.pos).nor();
						Vector2 relVel = speed.sub(h.speed);
						float sepVel = Vector2.dot(relVel.x, relVel.y, normal.x, normal.y);
						float new_sepVel = -sepVel;
						Vector2 sepVelVec = normal.scl(new_sepVel);
						
						speed = speed.add(sepVelVec);
						h.speed = h.speed.add(sepVelVec.scl(-1));
						
					}
				}
			}

			for (Solid solid : solids) {
				Vector2 closePoint = new Vector2(Utils.a(pos.x, solid.pos.x + solid.size.x, solid.pos.x), Utils.a(pos.y, solid.pos.y + solid.size.y, solid.pos.y));

				float distance = Vector2.dst(pos.x, pos.y, closePoint.x, closePoint.y);
				
				if(distance <= r) {
					boolean vertical = closePoint.y == solid.pos.y || closePoint.y == solid.pos.y + solid.size.y;
					
					if(vertical) {
						hitVertical = true;
						pos.y -= stepY;
						applyBounceForce(true);
					} else {
						hitHorizontal = true;
						pos.x -= stepX;
						applyBounceForce(false);
					}
				}
			}
		}

		grounded = hitVertical;
		
		return false;
	}
	
	public void applyBounceForce(boolean vertical) {
		if (vertical) {
			forces.add(new Vector2(0, BOUNCEC * 4f * -speed.y));
		} else {
			forces.add(new Vector2(BOUNCEC * 4f * -speed.x, 0));
		}
	}
	
	public void render(SpriteBatch b) {
		b.draw(TEXTURE, pos.x - r, pos.y - r, r * 2, r * 2);
	}
}
