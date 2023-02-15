package dev.codewizz.physics2D;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.objects.GameObject;
import dev.codewizz.physics2D.collision.CollisionResult;
import dev.codewizz.physics2D.collision.Collisions;

public class World {

	public static final float minBodySize = 0.1f * 0.1f;
	public static final float maxBodySize = 640f * 640f;

	public static final float minDensity = 0.2f;
	public static final float maxDensity = 21.4f;

	public List<GameObject> objects = new CopyOnWriteArrayList<>();

	public World() {

	}

	public void update(float dt) {
		for (int i = 0; i < objects.size() - 1; i++) {
			Rigidbody b1 = objects.get(i).getRigidbody();

			for (int j = i + 1; j < objects.size(); j++) {

				Rigidbody b2 = objects.get(j).getRigidbody();

				if(b1.isStatic() && b2.isStatic())
					continue;
				
				CollisionResult result = Collisions.testCollision(b1.getObject().getCollider(), b2.getObject().getCollider());
				if (result.isIntersecting()) {
					Vector2 normal = result.getNormal();
					float depth = result.getDepth();
					
					if(b1.isStatic()) {
						b2.move(new Vector2(normal).scl(depth));
					} else if(b2.isStatic()) {
						b1.move(new Vector2(normal).scl(-depth));
					} else {
						b1.move(new Vector2(normal).scl(-depth / 2f));
						b2.move(new Vector2(normal).scl(depth / 2f));
					}
					
					
					
					
					
					

					resolveCollision(b1, b2, normal, depth);
				}
			}
		}

		for (GameObject object2 : objects) {
			object2.update(dt);
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
	}

	public void addObject(GameObject object) {
		objects.add(object);
	}

	public void removeObject(GameObject object) {
		objects.remove(object);
	}

	public GameObject getObject(int index) {
		return objects.get(index);
	}
}
