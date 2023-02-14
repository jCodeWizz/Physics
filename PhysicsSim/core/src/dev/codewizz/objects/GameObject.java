package dev.codewizz.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.physics2D.Rigidbody;
import dev.codewizz.physics2D.collision.Collider;

public abstract class GameObject {

	protected Rigidbody rigidbody;
	protected Collider collider;
	
	public GameObject(float x, float y) {
	}
	
	public abstract void update(float dt);
	public abstract void render(SpriteBatch b);
	
	public Rigidbody getRigidbody() {
		return this.rigidbody;
	}
	
	public Collider getCollider() {
		return this.collider;
	}
	
}
