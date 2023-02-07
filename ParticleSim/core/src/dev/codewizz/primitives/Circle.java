package dev.codewizz.primitives;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.rigidbody.Rigidbody2D;

public class Circle {

	private float radius = 1.0f;
	private Rigidbody2D body = null;
	
	public float getRadius() {
		return this.radius;
	}
	
	public Vector2 getcenter() {
		return body.getPosition();
	}
}
