package dev.codewizz.forces;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.rigidbody.Rigidbody2D;

public class Normal2D implements ForceGenerator {

	private Vector2 gravity;
	
	public Normal2D(Vector2 force) {
		this.gravity = new Vector2(force);
	}
	
	@Override
	public void updateForce(Rigidbody2D body, float dt) {
		// F = M * A;
		
		if(body.isGrounded()) {
			body.addForce(new Vector2(gravity).scl(-body.getMass()));
		}
	}
}