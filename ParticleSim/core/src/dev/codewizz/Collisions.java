package dev.codewizz;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.primitives.AABB;
import dev.codewizz.primitives.Box2D;
import dev.codewizz.primitives.Circle;
import dev.codewizz.primitives.Ray2D;
import dev.codewizz.primitives.RaycastResult;
import dev.codewizz.primitives.Shape;
import dev.codewizz.rigidbody.IntersectionDetector2D;

public class Collisions {

	public static CollisionManifold findCollisionFeatures(Shape a, Shape b) {
		if(a instanceof Circle && b instanceof Circle) {
			return findCollisionFeatures((Circle)a, (Circle)b);
		}
		if(a instanceof Circle && b instanceof AABB) {
			return findCollisionFeatures((AABB)b, (Circle)a);
		}
		if(a instanceof AABB && b instanceof Circle) {
			return findCollisionFeatures((AABB)a, (Circle)b);
		}
		if(a instanceof Circle && b instanceof Box2D) {
			return findCollisionFeatures((Box2D)b, (Circle)a);
		}
		if(a instanceof Box2D && b instanceof Circle) {
			return findCollisionFeatures((Box2D)a, (Circle)b);
		}
		
		return null;
	}
	
	public static CollisionManifold findCollisionFeatures(AABB a, Circle b) {
		CollisionManifold result = new CollisionManifold();

		RaycastResult rayResult = new RaycastResult();

		// SEND RAY FROM CIRCLE CENTER IN DIRECTION OF VELOCITY
		Ray2D ray = new Ray2D(new Vector2(b.getCenter()), new Vector2(b.getRigidbody().getVelocity()));
		
		IntersectionDetector2D.raycast(a, ray, rayResult);
	
		Vector2 normal = new Vector2(-rayResult.getNormal().x, -rayResult.getNormal().y);
		
		// CHECK IF AABB AND CIRCLE ARE COLLIDING
		if(IntersectionDetector2D.AABBAndCircle(a, b)) {
			
			
			
			// MAKE RESULT, DEPTH ISNT USED IN PHYSICSSYSTEM
			if(rayResult.isHit()) {
				result = new CollisionManifold(normal, -1.0f);
				result.addContactPoint(rayResult.getPoint());
			} 
		}
		
		return result;
	}
	
	public static CollisionManifold findCollisionFeatures(Box2D a, Circle b) {
		CollisionManifold result = new CollisionManifold();

		// CHECK IF AABB AND CIRCLE ARE COLLIDING
		if(IntersectionDetector2D.circleAndBox2D(b, a)) {
			RaycastResult rayResult = new RaycastResult();

			// SEND RAY FROM CIRCLE CENTER IN DIRECTION OF VELOCITY
			Ray2D ray = new Ray2D(new Vector2(b.getCenter()), new Vector2(b.getRigidbody().getVelocity()));
			
			IntersectionDetector2D.raycast(a, ray, rayResult);
		
			Vector2 normal = new Vector2(rayResult.getNormal().x, -rayResult.getNormal().y);
			
			// MAKE RESULT, DEPTH ISNT USED IN PHYSICSSYSTEM
			if(rayResult.isHit()) {
				result = new CollisionManifold(normal, -1.0f);
				result.addContactPoint(rayResult.getPoint());
			} 
		}
		
		if(result.isColliding()) {
			/*
			Ray2D ray = new Ray2D(new Vector2(b.getCenter()), new Vector2(0, -1));
			RaycastResult rayResult = new RaycastResult();
			
			IntersectionDetector2D.raycast(a, ray, rayResult);
			
			Vector2 d = new Vector2(rayResult.getPoint()).sub(b.getCenter());
			
			float depth = b.getRadius() - d.len();
			
			b.getRigidbody().getPosition().add(new Vector2(0, -depth));
			*/
		}
		
		return result;
	}
	
	public static CollisionManifold findCollisionFeatures(Circle a, Circle b) {
		CollisionManifold result = new CollisionManifold();
		
		float sumRadii = a.getRadius() + b.getRadius();
		
		
		
		Vector2 distance = new Vector2(b.getCenter()).sub(a.getCenter());
		if(distance.len2() - (sumRadii * sumRadii) > 0) {
			return result;
		}
		
		float depth = Math.abs(distance.len() - sumRadii) * 0.5f;
		Vector2 normal = new Vector2(distance);
		normal.nor();
		float distanceToPoint = a.getRadius() - depth;
		Vector2 contactPoint = new Vector2(a.getCenter()).add(new Vector2(normal).scl(distanceToPoint));

		result = new CollisionManifold(normal, depth);
		result.addContactPoint(contactPoint);
		
		return result;
	}
	
}
