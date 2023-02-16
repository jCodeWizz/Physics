package dev.codewizz.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
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
		this.rigidbody = Rigidbody.createBox(this, new Vector2(x, y), this.w, this.h, 0.5f, false, 0.5f);
	}
	
	public Box(float x, float y, float w, float h, Color color) {
		this(x, y, w, h);
		this.color = color;
	}

	@Override
	public void update(float dt, int iterations) {
		super.update(dt, iterations);
	}

	@Override
	public void render(SpriteBatch b) {
		Vector2[] pos = ((BoxCollider) collider).getTransformedVertices();
		
		Main.shapeDrawer.filledPolygon(getPolygon());
		
		if(!this.rigidbody.isStatic()) {
			Main.shapeDrawer.line(pos[0], pos[1], color);
			Main.shapeDrawer.line(pos[1], pos[2], color);
			Main.shapeDrawer.line(pos[2], pos[3], color);
			Main.shapeDrawer.line(pos[3], pos[0], color);
			Main.shapeDrawer.filledCircle(((BoxCollider) collider).getCenter(), 2f, Color.BLACK);
			
			
			Vector2 vec = new Vector2(pos[1]).sub(pos[0]);
			Vector2 vec2 = vec.scl(0.5f);
			
			
			Main.shapeDrawer.line(((BoxCollider) collider).getCenter(), new Vector2(((BoxCollider) collider).getCenter()).add(vec2), Color.BLACK);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.Z) && this.rigidbody.isStatic()) {
			this.rigidbody.rotate(0.05f);
		}
		
		
		
		
		
		//Main.shapeDrawer.filledRectangle(pos.x, pos.y, w, h, color);
	}
	
	public Polygon getPolygon() {
		Vector2[] pos = ((BoxCollider) collider).getTransformedVertices();
		float[] v = {
				pos[0].x, pos[0].y,	
				pos[1].x, pos[1].y,	
				pos[2].x, pos[2].y,	
				pos[3].x, pos[3].y
		};
		return new Polygon(v);
	}
}
