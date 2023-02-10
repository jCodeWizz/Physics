package dev.codewizz.primitives;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.rigidbody.Rigidbody2D;

public abstract class Shape {
	
	protected Rigidbody2D rigidbody;
	protected boolean fill = false;
	protected Color color;
	
	public Shape() {
		color = Color.WHITE;
		rigidbody = new Rigidbody2D();
	}
	
	public abstract void render(SpriteBatch b);
	
	public Rigidbody2D getRigidbody() {
		return this.rigidbody;
	}
}
