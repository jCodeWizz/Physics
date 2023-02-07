package dev.codewizz;

import com.badlogic.gdx.math.Vector2;

public class Collisions {

	public static CollisionManifold findCollisionFeatures(Hydrogen a, Hydrogen b) {
		CollisionManifold result = new CollisionManifold();
		
		float sumRadii = a.r + b.r;
		
		
		
		Vector2 distance = new Vector2(new Vector2(b.pos).sub(a.pos));
		if(distance.len() - (sumRadii * sumRadii) > 0) {
			return result;
		}
		
		float depth = Math.abs(distance.len2() - sumRadii) * 0.5f;
		Vector2 normal = new Vector2(distance);
		normal.nor();
		float distanceToPoint = a.r - depth;
		Vector2 contactPoint = new Vector2(a.pos).add(new Vector2(normal).scl(distanceToPoint));

		result = new CollisionManifold(normal, depth);
		result.addContactPoint(contactPoint);
		return result;
	}
	
}
