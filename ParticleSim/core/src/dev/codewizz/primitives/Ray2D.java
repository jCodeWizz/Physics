package dev.codewizz.primitives;

import com.badlogic.gdx.math.Vector2;

public class Ray2D {
	
	private Vector2 origin;
	private Vector2 direction;
	
	public Ray2D(Vector2 origin, Vector2 direction) {
		this.origin = origin;
		this.direction = direction;
		this.direction.nor();
	}
	
	public Vector2 getOrigin() {
		return this.origin;
	}
	
	public Vector2 getDirection() {
		return this.direction;
	}
}
