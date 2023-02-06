package dev.codewizz;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Solid {

	private static Texture TEXTURE = new Texture("solid.png");
	
	public Vector2 pos, size;
	
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
