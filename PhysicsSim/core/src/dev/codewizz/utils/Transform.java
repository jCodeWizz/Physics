package dev.codewizz.utils;

import com.badlogic.gdx.math.Vector2;

public class Transform {

	public float x, y, sin, cos;
	
	public Transform(Vector2 pos, float angleDegrees) {
		this.x = pos.x;
		this.y = pos.y;
		
		this.sin = (float)Math.sin(angleDegrees);
		this.cos = (float)Math.cos(angleDegrees);
	}
	
	public Transform(float x, float y, float angleDegrees) {
		this.x = x;
		this.y = y;
		
		this.sin = (float)Math.sin(angleDegrees);
		this.cos = (float)Math.cos(angleDegrees);
	}
	
	public Transform() {
		this(0f, 0f, 0f);
	}
}
