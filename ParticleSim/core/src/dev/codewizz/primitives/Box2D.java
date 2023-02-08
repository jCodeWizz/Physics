package dev.codewizz.primitives;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.rigidbody.Rigidbody2D;

public class Box2D {

	private Vector2 size = new Vector2();
	private Vector2 halfSize = new Vector2();
	private Rigidbody2D rigidbody = null;
	
	public Box2D() {
		this.halfSize = new Vector2(size).scl(0.5f);
	}
	
	public Box2D(Vector2 min, Vector2 max) {
		this.size = new Vector2(max).sub(min);
		this.halfSize = new Vector2(size).scl(0.5f);
	}
	
	public Vector2 getMin() {
		return new Vector2(this.rigidbody.getPosition()).sub(this.halfSize);
	}
	
	public Vector2 getMax() {
		return new Vector2(this.rigidbody.getPosition()).add(this.halfSize);
	}
	
	public Vector2[] getVertices() {
		Vector2 min = getMin();
		Vector2 max = getMax();
		
		Vector2[] vertices = {
			new Vector2(min.x, min.y), new Vector2(min.x, max.y),
			new Vector2(max.x, min.y), new Vector2(max.x, max.y)
		};
		
		if(rigidbody.getRotation() != 0.0f) {
			for(Vector2 vert : vertices) {
				//JMath.rotate(vert, this.rigidbody.getPosition(), this.rigidbody.getRotation());
			}
		}
		
		return vertices;
	}
	
	public Rigidbody2D getRigidbody() {
		return this.rigidbody;
	}
	
}
