package dev.codewizz.physics2D;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.Main;
import dev.codewizz.objects.GameObject;
import dev.codewizz.physics2D.collision.AABB;
import dev.codewizz.physics2D.collision.CollisionResult;
import dev.codewizz.physics2D.collision.Collisions;

public class World {

	public static final float minBodySize = 0.1f * 0.1f;
	public static final float maxBodySize = 640f * 640f;

	public static final float minDensity = 0.2f;
	public static final float maxDensity = 21.4f;

	public List<GameObject> objects = new CopyOnWriteArrayList<>();
	public List<CollisionResult> results = new CopyOnWriteArrayList<>();
	public List<Vector2> points = new CopyOnWriteArrayList<>();

	public World() {

	}

	public void update(float dt, int iterations) {
		points.clear();
		for (int it = 0; it < iterations; it++) {

			for (GameObject object : objects) {
				object.update(dt, iterations);
			}

			for (int i = 0; i < objects.size() - 1; i++) {
				Rigidbody b1 = objects.get(i).getRigidbody();
				AABB aabb1 = b1.getAABB();

				for (int j = i + 1; j < objects.size(); j++) {

					Rigidbody b2 = objects.get(j).getRigidbody();
					AABB aabb2 = b2.getAABB();

					if (b1.isStatic() && b2.isStatic())
						continue;

					if (!Collisions.AABBAndAABB(aabb1, aabb2))
						continue;

					CollisionResult result = Collisions.testCollision(b1.getObject().getCollider(), b2.getObject().getCollider());
					
					if (result.isIntersecting()) {
						seperateBodies(b1, b2, result);
						
						results.add(result);
						if (result.getContactPoints() != null) {
							points.addAll(result.getContactPoints());
						}
					}
				}
			}

			for (CollisionResult result : results) {
				resolveCollision(result.getBodyA().getRigidbody(), result.getBodyB().getRigidbody(), result.getNormal(),
						result.getDepth());
			}
			results.clear();

		}

		for (GameObject object : objects) {
			if (object.getRigidbody().getAABB().maxY < -200f && !object.getRigidbody().isStatic()) {
				this.removeObject(object);
			}
		}
	}
	
	private void broadPhase() {
		
	}
	
	private void narrowPhase() {
		
	}
	
	private void seperateBodies(Rigidbody b1, Rigidbody b2, CollisionResult result) {
		Vector2 normal = result.getNormal();
		float depth = result.getDepth();

		if (b1.isStatic()) {
			b2.move(new Vector2(normal).scl(depth));
		} else if (b2.isStatic()) {
			b1.move(new Vector2(normal).scl(-depth));
		} else {
			b1.move(new Vector2(normal).scl(-depth / 2f));
			b2.move(new Vector2(normal).scl(depth / 2f));
		}
	}

	public void resolveCollision(Rigidbody a, Rigidbody b, Vector2 normal, float depth) {

		Vector2 relativeVelocity = new Vector2(b.getLinearVelocity()).sub(a.getLinearVelocity());

		if (relativeVelocity.dot(normal) > 0f) {
			return;
		}

		float e = Math.min(a.getRestitution(), b.getRestitution());

		float j = -(1f + e) * relativeVelocity.dot(normal);
		j /= a.getInvMass() + b.getInvMass();

		Vector2 impulse = normal.scl(j);

		a.getLinearVelocity().sub(new Vector2(impulse).scl(a.getInvMass()));
		b.getLinearVelocity().add(new Vector2(impulse).scl(b.getInvMass()));
	}

	public void render(SpriteBatch b) {
		for (GameObject object : objects) {
			object.render(b);
		}

		for (Vector2 point : points) {
			Main.shapeDrawer.filledCircle(point, 2f, Color.RED);
		}
	}

	public void addObject(GameObject object) {
		objects.add(object);
	}

	public void removeObject(GameObject object) {
		objects.remove(object);
		object.destroy();
	}

	public GameObject getObject(int index) {
		return objects.get(index);
	}
}
