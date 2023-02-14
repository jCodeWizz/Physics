package dev.codewizz.physics2D.collision;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class CollisionResult {

	private boolean intersecting = false;
	private float depth = 0;
	private List<Vector2> contactPoints;
	private Vector2 normal;
	
	public CollisionResult() {
		contactPoints = new ArrayList<>();
		normal = new Vector2();
	}
	
	public void reset() {
		intersecting = false;
		depth = 0;
		contactPoints.clear();
		normal = new Vector2();
	}
	
	public void set(float depth, Vector2 normal, Vector2... points) {
		intersecting = true;
		this.depth = depth;
		this.normal = normal;
		for(int i = 0; i < points.length; i++) {
			contactPoints.add(points[i]);
		}
	}

	public boolean isIntersecting() {
		return intersecting;
	}

	public float getDepth() {
		return depth;
	}

	public List<Vector2> getContactPoints() {
		return contactPoints;
	}

	public Vector2 getNormal() {
		return normal;
	}
}
