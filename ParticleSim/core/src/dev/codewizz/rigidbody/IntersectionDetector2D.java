package dev.codewizz.rigidbody;


import com.badlogic.gdx.math.Vector2;

import dev.codewizz.JMath;
import dev.codewizz.primitives.AABB;
import dev.codewizz.primitives.Box2D;
import dev.codewizz.primitives.Circle;
import dev.codewizz.primitives.Line2D;
import dev.codewizz.primitives.Ray2D;
import dev.codewizz.primitives.RaycastResult;

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
	
	public static boolean raycast(Circle circle, Ray2D ray, RaycastResult result) {
		RaycastResult.reset(result);
		Vector2 originToCircle = new Vector2(circle.getCenter()).sub(ray.getOrigin());
		float radiusSquared = circle.getRadius() * circle.getRadius();
		float originToCircleLengthSquared = originToCircle.len2();
		
		float a = originToCircle.dot(ray.getDirection());
		float bSq = originToCircleLengthSquared - (a * a);
		
		if(radiusSquared - bSq < 0.0f) {
			return false;
		}
		
		float f = (float)Math.sqrt(radiusSquared - bSq);
		float t = 0;
		
		if(originToCircleLengthSquared < radiusSquared) {
			t = a + f;
		} else {
			t = a - f;
		}
		
		if(result != null) {
			Vector2 point = new Vector2(ray.getOrigin()).add(new Vector2(ray.getDirection()).scl(t));
			Vector2 normal = new Vector2(point).sub(circle.getCenter());
			normal.nor();
			
			result.init(point, normal, t, true);
		}
		
		return true;
	}
	
	public static boolean raycast(AABB box, Ray2D ray, RaycastResult result) {
		RaycastResult.reset(result);
		Vector2 unitVector = ray.getDirection();
		unitVector.nor();
		unitVector.x = (unitVector.x != 0) ? 1.0f / unitVector.x : 0f;
		unitVector.y = (unitVector.y != 0) ? 1.0f / unitVector.y : 0f;
		
		Vector2 min = box.getMin();
		min.sub(ray.getOrigin()).scl(unitVector);
		Vector2 max = box.getMax();
		max.sub(ray.getOrigin()).scl(unitVector);
		
		float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
		float tmax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));
		
		if(tmax < 0 || tmin > tmax) {
			return false;
		}
		
		float t = (tmin < 0f) ? tmax : tmin;
		boolean hit = t > 0f;
		if(!hit) {
			return false;
		}
		
		if(result != null) {
			Vector2 point = new Vector2(ray.getOrigin()).add(new Vector2(ray.getDirection()).scl(t));
			Vector2 normal = new Vector2(ray.getOrigin()).sub(point);
			normal.nor();
			
			result.init(point, normal, t, hit);
		}
		
		return true;
	}
	
	public static boolean raycast(Box2D box, Ray2D ray, RaycastResult result) {
		RaycastResult.reset(result);
		
		Vector2 size = box.getHalfSize();
		
		Vector2 xAxis = new Vector2(1, 0);
		Vector2 yAxis = new Vector2(0, 1);
		
		JMath.rotate(xAxis, box.getRigidbody().getRotation(), new Vector2(0, 0));
		JMath.rotate(yAxis, box.getRigidbody().getRotation(), new Vector2(0, 0));
		
		Vector2 p = new Vector2(box.getRigidbody().getPosition()).sub(ray.getOrigin());
		Vector2 f = new Vector2(xAxis.dot(p), yAxis.dot(p));

		Vector2 e = new Vector2(xAxis.dot(p), yAxis.dot(p));
		
		float[] tArr = { 0, 0, 0, 0 };
		
		for(int i = 0; i < 2; i++) {
			if(i == 0) {
				if(JMath.compare(f.x, 0)) {
					if(-e.x - size.x > 0 || -e.x + size.x < 0) {
						return false;
					}
					f.x = 0.00001f;
				}
				tArr[i * 2 + 0] = (e.x * size.x) / f.x;
				tArr[i * 2 + 1] = (e.x - size.x) / f.x;
			} else {
				if(JMath.compare(f.y, 0)) {
					if(-e.y - size.y > 0 || -e.y + size.y < 0) {
						return false;
					}
					f.y = 0.00001f;
				}
				tArr[i * 2 + 0] = (e.y * size.y) / f.y;
				tArr[i * 2 + 1] = (e.y - size.y) / f.y;
			}
		}
		
		float tmin = Math.max(Math.min(tArr[0], tArr[1]), Math.min(tArr[2], tArr[3]));
		float tmax = Math.min(Math.max(tArr[0], tArr[1]), Math.max(tArr[2], tArr[3]));
		float t = (tmin < 0f) ? tmax : tmin;
		boolean hit = t > 0f;
		if(!hit) {
			return false;
		}
		
		if(result != null) {
			Vector2 point = new Vector2(ray.getOrigin()).add(new Vector2(ray.getDirection()).scl(t));
			Vector2 normal = new Vector2(ray.getOrigin()).sub(point);
			normal.nor();
			
			result.init(point, normal, t, hit);
		}
		
		return true;
	}
	
	public static boolean circleAndLine(Circle circle, Line2D line) {
		return lineAndCircle(line, circle);
	}
	
	public static boolean cricleAndCircle(Circle c1, Circle c2) {
		Vector2 vecBetweenCenters = new Vector2(c1.getCenter()).sub(c2.getCenter());
		float radiiSum = c1.getRadius() + c2.getRadius();
		return vecBetweenCenters.len2() <= radiiSum * radiiSum;
	}
	
	public static boolean circleAndAABB(Circle circle, AABB box) {
		Vector2 min = box.getMin();
		Vector2 max = box.getMax();
		
		Vector2 closestPointToCircle = new Vector2(circle.getCenter());
		
		if(closestPointToCircle.x < min.x) {
			closestPointToCircle.x = min.x;
		} else if(closestPointToCircle.x > max.x) {
			closestPointToCircle.x = max.x;
		}
		
		if(closestPointToCircle.y < min.y) {
			closestPointToCircle.y = min.y;
		} else if(closestPointToCircle.y > max.y) {
			closestPointToCircle.y = max.y;
		}
		
		Vector2 circleToBox = new Vector2(circle.getCenter()).sub(closestPointToCircle);
		return circleToBox.len2() <= circle.getRadius() * circle.getRadius();
	}
	
	public static boolean circleAndBox2D(Circle circle, Box2D box) {
		Vector2 min = new Vector2();
		Vector2 max = new Vector2(box.getHalfSize()).scl(2.0f);
		
		Vector2 r = new Vector2(circle.getCenter()).sub(box.getRigidbody().getPosition());
		JMath.rotate(r, -box.getRigidbody().getRotation(), new Vector2(0, 0));
		Vector2 localCirclePos = new Vector2(r).add(box.getHalfSize());
		
		Vector2 closestPointToCircle = new Vector2(localCirclePos);
		
		if(closestPointToCircle.x < min.x) {
			closestPointToCircle.x = min.x;
		} else if(closestPointToCircle.x > max.x) {
			closestPointToCircle.x = max.x;
		}
		
		if(closestPointToCircle.y < min.y) {
			closestPointToCircle.y = min.y;
		} else if(closestPointToCircle.y > max.y) {
			closestPointToCircle.y = max.y;
		}
		
		Vector2 circleToBox = new Vector2(localCirclePos).sub(closestPointToCircle);
		return circleToBox.len2() <= circle.getRadius() * circle.getRadius();
	}
	
	public static boolean AABBAndCircle(AABB box, Circle circle) {
		return circleAndAABB(circle, box);
	}
	
	
	
	public static boolean AABBAndAABB(AABB b1, AABB b2) {
		Vector2 axesToTest[] = { new Vector2(0, 1), new Vector2(1, 0)};
		for(int i = 0; i < axesToTest.length; i++) {
			if(!overlapOnAxis(b1, b2, axesToTest[1])) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean AABBAndBox2D(AABB b1, Box2D b2) {
		Vector2[] axesToTest = { 
				new Vector2(0, 1), new Vector2(1, 0),
				new Vector2(0, 1), new Vector2(1, 0)		
		};
		
		JMath.rotate(axesToTest[2], b2.getRigidbody().getRotation(), new Vector2(0, 0));
		JMath.rotate(axesToTest[3], b2.getRigidbody().getRotation(), new Vector2(0, 0));
		
		for(int i = 0; i < axesToTest.length; i++) {
			if(!overlapOnAxis(b1, b2, axesToTest[1])) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean AABBAndBox2D(Box2D b1, Box2D b2) {
		Vector2[] axesToTest = { 
				new Vector2(0, 1), new Vector2(1, 0),
				new Vector2(0, 1), new Vector2(1, 0)		
		};
		
		JMath.rotate(axesToTest[0], b1.getRigidbody().getRotation(), new Vector2(0, 0));
		JMath.rotate(axesToTest[1], b1.getRigidbody().getRotation(), new Vector2(0, 0));
		JMath.rotate(axesToTest[2], b2.getRigidbody().getRotation(), new Vector2(0, 0));
		JMath.rotate(axesToTest[3], b2.getRigidbody().getRotation(), new Vector2(0, 0));
		
		for(int i = 0; i < axesToTest.length; i++) {
			if(!overlapOnAxis(b1, b2, axesToTest[1])) {
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean overlapOnAxis(AABB b1, AABB b2, Vector2 axis) {
		Vector2 interval1 = getInterval(b1, axis);
		Vector2 interval2 = getInterval(b2, axis);
		
		return ((interval2.x <= interval1.y) && (interval1.x <= interval2.y));
	}
	
	private static boolean overlapOnAxis(AABB b1, Box2D b2, Vector2 axis) {
		Vector2 interval1 = getInterval(b1, axis);
		Vector2 interval2 = getInterval(b2, axis);
		
		return ((interval2.x <= interval1.y) && (interval1.x <= interval2.y));
	}
	
	private static boolean overlapOnAxis(Box2D b1, Box2D b2, Vector2 axis) {
		Vector2 interval1 = getInterval(b1, axis);
		Vector2 interval2 = getInterval(b2, axis);
		
		return ((interval2.x <= interval1.y) && (interval1.x <= interval2.y));
	}
	
	private static Vector2 getInterval(AABB rect, Vector2 axis) {
		Vector2 result = new Vector2(0, 0);
		Vector2 min = rect.getMin();
		Vector2 max = rect.getMax();
		
		Vector2 vertices[] = {
			new Vector2(min.x, min.y), new Vector2(min.x, max.y), 
			new Vector2(max.x, min.y), new Vector2(max.x, max.y)
		};
		
		result.x = axis.dot(vertices[0]);
		result.y = result.x;
		
		for(int i = 1; i < 4; i++) {
			float projection = axis.dot(vertices[i]);
			if(projection < result.x) {
				result.x = projection;
			}
			if(projection > result.y) {
				result.y = projection;
			}
		}
		return result;
	}
	
	private static Vector2 getInterval(Box2D rect, Vector2 axis) {
		Vector2 result = new Vector2(0, 0);
		
		Vector2 vertices[] = rect.getVertices();
		
		result.x = axis.dot(vertices[0]);
		result.y = result.x;
		
		for(int i = 1; i < 4; i++) {
			float projection = axis.dot(vertices[i]);
			if(projection < result.x) {
				result.x = projection;
			}
			if(projection > result.y) {
				result.y = projection;
			}
		}
		return result;
	}
	
}
