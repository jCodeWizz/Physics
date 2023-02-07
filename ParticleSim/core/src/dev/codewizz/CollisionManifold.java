package dev.codewizz;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class CollisionManifold {

	private Vector2 normal;
	private List<Vector2> contactPoints;
	private float depth;
	private boolean isColliding;
	
	public CollisionManifold() {
		this.normal = new Vector2();
		this.depth = 0.0f;
		this.isColliding = false;
	}
	
	public CollisionManifold(Vector2 normal, float depth) {
		this.normal = normal;
		this.contactPoints = new ArrayList<>();
		this.depth = depth;
		this.isColliding = true;
	}
	
	public void addContactPoint(Vector2 point) {
		contactPoints.add(point);
	}

	public Vector2 getNormal() {
		return normal;
	}

	public void setNormal(Vector2 normal) {
		this.normal = normal;
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public boolean isColliding() {
		return isColliding;
	}

	public void setColliding(boolean isColliding) {
		this.isColliding = isColliding;
	}

	public List<Vector2> getContactPoints() {
		return contactPoints;
	}
}
