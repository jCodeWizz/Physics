package dev.codewizz;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Hydrogen {

	private static Texture TEXTURE = new Texture("Hydrogen.png");
	
	public Vector2 pos;
	public float r = 16f;
	
	public Hydrogen(float x, float y) {
		pos = new Vector2(x, y);
	}
	
	public void render(SpriteBatch b) {
		b.draw(TEXTURE, pos.x - r, pos.y - r, r * 2, r * 2);
	}
	
	
	
}
