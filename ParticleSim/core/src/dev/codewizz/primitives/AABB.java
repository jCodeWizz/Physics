package dev.codewizz.primitives;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.rigidbody.Rigidbody2D;

public class AABB {
	
	private Vector2 center = new Vector2();
	private Vector2 size = new Vector2();
	private Vector2 halfSize = new Vector2();
	private Rigidbody2D rigidbody = null;
	
	public AABB() {
		this.halfSize = new Vector2(size).scl(0.5f);
	}
	
	public AABB(Vector2 min, Vector2 max) {
		this.size = new Vector2(max).sub(min);
		this.halfSize = new Vector2(size).scl(0.5f);
	}
	
	public Vector2 getMin() {
		return new Vector2(this.rigidbody.getPosition()).sub(this.halfSize);
	}
	
	public Vector2 getMax() {
		return new Vector2(this.rigidbody.getPosition()).add(this.halfSize);
	}
}
