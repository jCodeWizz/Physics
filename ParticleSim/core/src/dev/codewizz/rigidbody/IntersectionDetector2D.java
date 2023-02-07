package dev.codewizz.rigidbody;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.primitives.AABB;
import dev.codewizz.primitives.Box2D;
import dev.codewizz.primitives.Circle;
import dev.codewizz.primitives.Line2D;

public class IntersectionDetector2D {

	public static boolean pointOnLine(Vector2 point, Line2D line) {
		float dy = line.getEnd().y - line.getStart().y;
		float dx = line.getEnd().x - line.getStart().x;
		float m = dy/dx;
		
		float b = line.getEnd().y - (m * line.getEnd().x);
		
		return point.y == m * point.x + b;
		
	}
	
	public static boolean pointInCircle(Vector2 point, Circle c) {
		Vector2 circleCenter = c.getcenter();
		Vector2 centerToPoint = new Vector2(point).sub(circleCenter);
		
		return centerToPoint.len2() <= c.getRadius() * c.getRadius();
	}
	
	public static boolean pointInAABB(Vector2 point, AABB box) {
		Vector2 min = box.getMin();
		Vector2 max = box.getMax();
		
		return point.x <= max.x && min.x <= point.x && point.y <= max.y && min.y <= point.y;
	}
	
	public static boolean pointInBox2D(Vector2 point, Box2D box) {
		return false;
	}
	
}
