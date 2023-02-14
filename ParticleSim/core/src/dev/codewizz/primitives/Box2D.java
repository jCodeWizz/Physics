package dev.codewizz.primitives;



import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.JMath;
import dev.codewizz.Main;
import dev.codewizz.rigidbody.Rigidbody2D;

public class Box2D extends Shape {

	private Vector2 size = new Vector2();
	private Vector2 halfSize = new Vector2();
	private boolean fill = false;
	
	public Box2D() {
		this.halfSize = new Vector2(size).scl(0.5f);
	}
	
	public Box2D(Vector2 min, Vector2 max) {
		this.size = new Vector2(max).sub(min);
		this.halfSize = new Vector2(size).scl(0.5f);
		this.rigidbody.setTransform(new Vector2(min).add(halfSize));
	}
	
	public Box2D(Vector2 min, Vector2 max, Color color) {
		this.size = new Vector2(max).sub(min);
		this.halfSize = new Vector2(size).scl(0.5f);
		this.rigidbody.setTransform(new Vector2(min).add(halfSize));
		this.color = color;
	}
	
	public Box2D(Vector2 min, Vector2 max, boolean fill) {
		this.fill = fill;
		this.size = new Vector2(max).sub(min);
		this.halfSize = new Vector2(size).scl(0.5f);
		this.rigidbody.setTransform(new Vector2(min).add(halfSize));
	}
	
	public Box2D(Vector2 min, Vector2 max, Color color, boolean fill) {
		this.fill = fill;
		this.size = new Vector2(max).sub(min);
		this.halfSize = new Vector2(size).scl(0.5f);
		this.rigidbody.setTransform(new Vector2(min).add(halfSize));
		this.color = color;
	}
	
	@Override
	public void render(SpriteBatch b) {
		if(fill) {
			Main.drawer.filledPolygon(getPolygon());
		} else {
			Main.drawer.polygon(getPolygon());
		}
		
	}
	
	public Polygon getPolygon() {
		Vector2[] v = getVertices();
		
		float[] f = {
			v[0].x, v[0].y,
			v[1].x, v[1].y,
			v[2].x, v[2].y,
			v[3].x, v[3].y,
		};
		
		return new Polygon(f);
	}
	
	
	public Vector2 getLocalMin() {
		return new Vector2(this.rigidbody.getPosition()).sub(this.halfSize);
	}
	
	public Vector2 getLocalMax() {
		return new Vector2(this.rigidbody.getPosition()).add(this.halfSize);
	}
	
	public Vector2 getHalfSize() {
		return this.halfSize;
	}
	
	public Vector2[] getVertices() {
		Vector2 min = getLocalMin();
		Vector2 max = getLocalMax();
		
		Vector2[] vertices = {
			new Vector2(min.x, min.y), new Vector2(min.x, max.y),
			new Vector2(max.x, max.y), new Vector2(max.x, min.y)
		};
		
		if(rigidbody.getRotation() != 0.0f) {
			for(Vector2 vert : vertices) {
				JMath.rotate(vert, this.rigidbody.getRotation(), this.rigidbody.getPosition());
			}
		}
		
		return vertices;
	}
	
	public Rigidbody2D getRigidbody() {
		return this.rigidbody;
	}
}
