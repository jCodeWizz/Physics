package dev.codewizz.rigidbody;

import com.badlogic.gdx.math.Vector2;

public class Rigidbody2D {

	private Vector2 position = new Vector2();
	private float rotation = 0.0f;
	
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public void rotate(float rotation) {
		this.rotation+=rotation;
	}
}
