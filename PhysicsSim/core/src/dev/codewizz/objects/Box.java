package dev.codewizz.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.Main;
import dev.codewizz.physics2D.Rigidbody;
import dev.codewizz.physics2D.collision.BoxCollider;

public class Box extends GameObject {

	private float w, h;
	private Color color;
	
	public Box(float x, float y, float w, float h) {
		super(x, y);

		this.w = w;
		this.h = h;
		this.color = Color.WHITE;
		
		this.collider = new BoxCollider(this, w, h);
		this.rigidbody = Rigidbody.createBox(new Vector2(x, y), w, h, 0.2f, false, 0.2f);
	}
	
	public Box(float x, float y, float w, float h, Color color) {
		this(x, y, w, h);
		this.color = color;
	}

	@Override
	public void update(float dt) {
		
	}

	@Override
	public void render(SpriteBatch b) {
		Main.shapeDrawer.filledRectangle(rigidbody.getPosition().x, rigidbody.getPosition().y, w, h, color);
	}
}
