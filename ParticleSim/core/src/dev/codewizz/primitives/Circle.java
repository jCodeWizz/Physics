package dev.codewizz.primitives;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.Main;

public class Circle extends Shape {

	private float radius = 1.0f;
	private boolean fill = false;
	
	public Circle(float x, float y, float r) {
		this.rigidbody.setTransform(new Vector2(x, y));
		this.radius = r;
	}
	
	public Circle(float x, float y, float r, boolean fill) {
		this.rigidbody.setTransform(new Vector2(x, y));
		this.radius = r;
		this.fill = fill;
	}
	
	public Circle(float x, float y, float r, Color color) {
		this.rigidbody.setTransform(new Vector2(x, y));
		this.radius = r;
		this.color = color;
	}
	
	public Circle(float x, float y, float r, Color color, boolean fill) {
		this.rigidbody.setTransform(new Vector2(x, y));
		this.radius = r;
		this.fill = fill;
		this.color = color;
	}
	
	@Override
	public void render(SpriteBatch b) {
		if(fill) {
			Main.drawer.filledCircle(this.rigidbody.getPosition().x, this.rigidbody.getPosition().y, radius, color);
		} else {
			Main.drawer.circle(this.rigidbody.getPosition().x, this.rigidbody.getPosition().y, radius);
		}
	}
	
	public void setRadius(float r) {
		this.radius = r;
	}
	
	public float getRadius() {
		return this.radius;
	}
	
	public Vector2 getCenter() {
		return rigidbody.getPosition();
	}
}
