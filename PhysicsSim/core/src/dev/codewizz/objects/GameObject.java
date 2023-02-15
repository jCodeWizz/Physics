package dev.codewizz.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.physics2D.Rigidbody;
import dev.codewizz.physics2D.collision.Collider;
import dev.codewizz.utils.Debug;

public abstract class GameObject {

	protected Rigidbody rigidbody;
	protected Collider collider;
	
	public GameObject(float x, float y) {
	}
	
	public void update(float dt) {
		this.rigidbody.update(dt);
	}
	public abstract void render(SpriteBatch b);
	
	public Rigidbody getRigidbody() {
		return this.rigidbody;
	}
	
	public Collider getCollider() {
		return this.collider;
	}
	
	public void move(Vector2 a) {
		this.rigidbody.move(a);
	}
	
	public void addForce(Vector2 force) {
		this.rigidbody.addForce(force);
	}
	
}