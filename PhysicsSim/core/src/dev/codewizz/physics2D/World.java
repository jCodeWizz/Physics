package dev.codewizz.physics2D;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.objects.GameObject;
import dev.codewizz.physics2D.collision.BoxCollider;
import dev.codewizz.physics2D.collision.CircleCollider;
import dev.codewizz.physics2D.collision.CollisionResult;
import dev.codewizz.physics2D.collision.Collisions;

public class World {

	public static final float minBodySize = 0.01f * 0.01f;
	public static final float maxBodySize = 64f * 64f;

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

				CollisionResult result = Collisions.testCollision(b1.getObject().getCollider(), b2.getObject().getCollider());
				if (result.isIntersecting()) {
					Vector2 normal = result.getNormal();

					float depth = result.getDepth();
					if(i % 2 == 0) {
						b1.move(new Vector2(normal).scl(-depth / 2f));
						b2.move(new Vector2(normal).scl(depth / 2f));
					} else {
						b2.move(new Vector2(normal).scl(depth / 2f));
						b1.move(new Vector2(normal).scl(-depth / 2f));
					}
				}
			}
		}

		for (GameObject object : objects) {
			object.update(dt);
		}
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
