package dev.codewizz.forces;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.rigidbody.Rigidbody2D;

public class AirResistance implements ForceGenerator {

	public static final float Cw = 1f;
	
	public AirResistance() {
	}
	
	@Override
	public void updateForce(Rigidbody2D body, float dt) {
		// Fresistance = A * V * 0.5 * Cw * rho
		
		body.addForce(new Vector2(-body.getVelocity().x * body.getArea() * 0.5f * Cw, -body.getVelocity().y * body.getArea() * 0.5f * Cw));
	}
}