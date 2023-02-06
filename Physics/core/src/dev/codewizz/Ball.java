package dev.codewizz;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ball {

	private final float MASS = 20f;
	private final float FwC = 0.01f;
	private final float DRAGC = 5f;
	private final float BOUNCEC = 500f;
	private static final Texture TEXTURE = new Texture(Gdx.files.internal("ball.png"));
	private static final BitmapFont font = new BitmapFont();
	
	
	public boolean grounded = false;
	
	private Vector2 pos;
	private Vector2 speed;
	private Vector2 acc;
	private Vector2 size;
	
	private Vector2 mousePos1;
	private Vector2 mousePos2;
	
	private Sprite sprite;

	private ArrayList<Vector2> forces;

	public Ball(float x, float y) {
		pos = new Vector2(x, y);
		size = new Vector2(64, 64);
		speed = new Vector2();
		acc = new Vector2();

		forces = new ArrayList<Vector2>();
		
		sprite = new Sprite(TEXTURE);
		sprite.setSize(size.x, size.y);
		sprite.setOrigin(size.x/2f, size.y/2f);
	}

	public void update(float dt, ArrayList<Solid> solids) {

		if(Gdx.input.isButtonPressed(0) && mousePos1 == null) {
			mousePos1 = new Vector2(Gdx.input.getX(), Gdx.input.getY());
		}
		if(!Gdx.input.isButtonPressed(0) && mousePos2 == null && mousePos1 != null) {
			mousePos2 = new Vector2(Gdx.input.getX(), Gdx.input.getY());
			Vector2 delta = mousePos2.sub(mousePos1);
			delta.scl(MASS * DRAGC);
			delta.x *= -1;
			forces.add(delta);
			
			
			mousePos1 = null;
			mousePos2 = null;
		}
		
		
		
		
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

		if (!move(solids)) {

		}
		
	}

	/*
	 * RETURNS FALSE IF *NOT* HIT ANYTHING
	 */

	public boolean move(ArrayList<Solid> solids) {
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

			for (Solid solid : solids) {
				if (solid.getBounds().intersects(getBoundsVertical())) {
					hitVertical = true;
					pos.y -= stepY;
					applyBounceForce(true);
				}
				if (solid.getBounds().intersects(getBoundsHorizontal())) {
					hitHorizontal = true;
					pos.x -= stepX;
					applyBounceForce(false);
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

	public Rectangle getBounds() {
		return new Rectangle((int) pos.x, (int) pos.y, (int) size.x, (int) size.y);
	}

	public Rectangle getBoundsVertical() {
		return new Rectangle((int) pos.x + 5, (int) pos.y, (int) size.x - 10, (int) size.y);
	}

	public Rectangle getBoundsHorizontal() {
		return new Rectangle((int) pos.x, (int) pos.y + 5, (int) size.x, (int) size.y - 10);
	}

	public void render(SpriteBatch b) {
		sprite.setPosition(pos.x, pos.y);
		sprite.draw(b);
		
		font.draw(b, "POSITION: { " + (int)pos.x + " , " + (int)pos.y + " }", 10, Gdx.graphics.getHeight() - 50);
		font.draw(b, "SPEED: { " + (int)speed.x + " , " + (int)speed.y + " }", 10, Gdx.graphics.getHeight() - 75);
		font.draw(b, "ACCELARATION: { " + (int)acc.x + " , " + (int)acc.y + " }", 10, Gdx.graphics.getHeight() - 100);
	}
}
