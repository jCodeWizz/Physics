package dev.codewizz;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.primitives.Circle;

public class Collisions {

	
	
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
