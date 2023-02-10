package dev.codewizz.primitives;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.Main;

public class AABB extends Shape {
	
	private Vector2 size = new Vector2();
	private Vector2 halfSize = new Vector2();
	private boolean fill = false;
	
	public AABB() {
		this.halfSize = new Vector2(size).scl(0.5f);
	}
	
	public AABB(Vector2 min, Vector2 max) {
		this.size = new Vector2(max).sub(min);
		this.halfSize = new Vector2(size).scl(0.5f);
		this.rigidbody.setPosition(new Vector2(min).add(halfSize));
	}
	
	public AABB(Vector2 min, Vector2 max, Color color) {
		this.size = new Vector2(max).sub(min);
		this.halfSize = new Vector2(size).scl(0.5f);
		this.rigidbody.setPosition(new Vector2(min).add(halfSize));
		this.color = color;
	}
	
	public AABB(Vector2 min, Vector2 max, Color color, boolean fill) {
		this.size = new Vector2(max).sub(min);
		this.halfSize = new Vector2(size).scl(0.5f);
		this.rigidbody.setPosition(new Vector2(min).add(halfSize));
		this.color = color;
		this.fill = fill;
	}
	
	public AABB(Vector2 min, Vector2 max, boolean fill) {
		this.fill = fill;
		this.size = new Vector2(max).sub(min);
		this.halfSize = new Vector2(size).scl(0.5f);
		this.rigidbody.setPosition(new Vector2(min).add(halfSize));
	}
	
	@Override
	public void render(SpriteBatch b) {
		if(fill) {
			Main.drawer.filledRectangle(this.rigidbody.getPosition().x - halfSize.x, this.rigidbody.getPosition().y - halfSize.y, this.size.x, this.size.y, color);
		} else {
			Main.drawer.rectangle(this.rigidbody.getPosition().x- halfSize.x, this.rigidbody.getPosition().y- halfSize.y, this.size.x, this.size.y, color);
		}
	}
	
	public Vector2 getMin() {
		return new Vector2(this.rigidbody.getPosition()).sub(this.halfSize);
	}
	
	public Vector2 getMax() {
		return new Vector2(this.rigidbody.getPosition()).add(this.halfSize);
	}
}
