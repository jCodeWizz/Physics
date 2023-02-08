package dev.codewizz.rigidbody;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.JMath;
import dev.codewizz.primitives.AABB;
import dev.codewizz.primitives.Box2D;
import dev.codewizz.primitives.Circle;
import dev.codewizz.primitives.Line2D;

public class IntersectionDetector2D {

	public static boolean pointOnLine(Vector2 point, Line2D line) {
		float dy = line.getEnd().y - line.getStart().y;
		float dx = line.getEnd().x - line.getStart().x;
		if(dx == 0f) {
			return JMath.compare(point.x, line.getStart().x);
		}
		float m = dy/dx;
		
		float b = line.getEnd().y - (m * line.getEnd().x);
		
		return point.y == m * point.x + b;
		
	}
	
	public static boolean pointInCircle(Vector2 point, Circle c) {
		Vector2 circleCenter = c.getCenter();
		Vector2 centerToPoint = new Vector2(point).sub(circleCenter);
		
		return centerToPoint.len2() <= c.getRadius() * c.getRadius();
	}
	
	public static boolean pointInAABB(Vector2 point, AABB box) {
		Vector2 min = box.getMin();
		Vector2 max = box.getMax();
		
		return point.x <= max.x && min.x <= point.x && point.y <= max.y && min.y <= point.y;
	}
	
	public static boolean pointInBox2D(Vector2 point, Box2D box) {
		
		Vector2 pointLocalBoxSpace = new Vector2(point);
		JMath.rotate(pointLocalBoxSpace, box.getRigidbody().getRotation(), box.getRigidbody().getPosition());
		
		Vector2 min = box.getMin();
		Vector2 max = box.getMax();
		
		return pointLocalBoxSpace.x <= max.x && min.x <= pointLocalBoxSpace.x && pointLocalBoxSpace.y <= max.y && min.y <= pointLocalBoxSpace.y;
	}
	
	public static boolean lineAndCircle(Line2D line, Circle circle) {
		if(pointInCircle(line.getStart(), circle) || pointInCircle(line.getEnd(), circle))
			return true;
		
		Vector2 ab = new Vector2(line.getEnd()).sub(line.getStart());
		Vector2 circleCenter = circle.getCenter();
		Vector2 centerToLineStart = new Vector2(circleCenter).sub(line.getStart());
		float t = centerToLineStart.dot(ab) / ab.dot(ab);
		
		if(t < 0.0f || t > 1.0f) {
			return false;
		}
		
		Vector2 closestPoint = new Vector2(line.getStart()).add(ab.scl(t));
		
		return pointInCircle(closestPoint, circle);
	}
	
	public static boolean lineAndAABB(Line2D line, AABB box) {
		if(pointInAABB(line.getStart(), box) || pointInAABB(line.getEnd(), box)) {
			return true;
		}
		
		Vector2 unitVector = new Vector2(line.getEnd()).sub(line.getStart());
		unitVector.nor();
		unitVector.x = (unitVector.x != 0) ? 1.0f / unitVector.x : 0f;
		unitVector.y = (unitVector.y != 0) ? 1.0f / unitVector.y : 0f;
		
		Vector2 min = box.getMin();
		min.sub(line.getStart()).scl(unitVector);
		Vector2 max = box.getMax();
		max.sub(line.getStart()).scl(unitVector);
		
		float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
		float tmax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));
		
		if(tmax < 0 || tmin > tmax) {
			return false;
		}
		
		float t = (tmin < 0f) ? tmax : tmin;
		return t > 0f && t * t < line.lengthSquared();
	}
	
	public static boolean lineAndBox2D(Line2D line, Box2D box) {
		float theta = box.getRigidbody().getRotation();
		Vector2 center = box.getRigidbody().getPosition();
		Vector2 localStart = new Vector2(line.getStart());
		Vector2 localEnd = new Vector2(line.getEnd());
		
		JMath.rotate(localStart, theta, center);
		JMath.rotate(localEnd, theta, center);

		Line2D localLine = new Line2D(localStart, localEnd);
		AABB aabb = new AABB(box.getMin(), box.getMax());
		
		return lineAndAABB(localLine, aabb);
	
	}
	
}
