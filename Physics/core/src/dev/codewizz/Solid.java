package dev.codewizz;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Solid {

	private static final Sprite TEXTURE = new Sprite(new Texture(Gdx.files.internal("solid.png")));
	
	private Vector2 pos;
	private Vector2 size;
	
	public Solid(float x, float y, float w, float h) {
		pos = new Vector2(x, y);
		size = new Vector2(w, h);
	}
	
	public void render(SpriteBatch b) {
		b.draw(TEXTURE, pos.x, pos.y, size.x, size.y);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)pos.x, (int)pos.y, (int)size.x, (int)size.y);
	}
}
