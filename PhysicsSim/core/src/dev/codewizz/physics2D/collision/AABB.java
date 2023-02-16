package dev.codewizz.physics2D.collision;

import com.badlogic.gdx.math.Vector2;

public class AABB {

	public Vector2 min, max;
	
	public AABB(Vector2 min, Vector2 max) {
		this.min = min;
		this.max = max;
	}
	
	public AABB(float x, float y, float x2, float y2) {
		this.min = new Vector2(x, y);
		this.max = new Vector2(x2, y2);
	}
	
	
}
