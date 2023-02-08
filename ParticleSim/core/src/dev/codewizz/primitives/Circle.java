package dev.codewizz.primitives;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.rigidbody.Rigidbody2D;

public class Circle {

	private float radius = 1.0f;
	private Rigidbody2D body = null;
	
	public Circle(float x, float y, float r) {
		this.body = new Rigidbody2D();
		this.body.setPosition(new Vector2(x, y));
		this.radius = r;
	}
	
	public float getRadius() {
		return this.radius;
	}
	
	public Vector2 getCenter() {
		return body.getPosition();
	}
}
