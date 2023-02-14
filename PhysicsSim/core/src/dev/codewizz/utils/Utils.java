package dev.codewizz.utils;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Utils {

	public static Random RANDOM = new Random();
	
	public static Color getRandomColor() {
		float r = RANDOM.nextFloat();
		float g = RANDOM.nextFloat();
		float b = RANDOM.nextFloat();
		
		return new Color(r, g, b, 1f);
	}
	
	public static Vector2 transform(Vector2 v, Transform transform) {
		return new Vector2(
				transform.cos * v.x - transform.sin * v.y + transform.x, 
				transform.sin * v.x - transform.cos * v.y + transform.y
		);
	}
}
