package dev.codewizz.rigidbody;

import com.badlogic.gdx.math.Vector2;

public class Rigidbody2D {
	
	private Vector2 position;
	private Vector2 linearVelocity;
	private Vector2 forceAccum;
	
	private float angularVelocity = 0.0f;
	private float linearDamping = 0.0f;
	private float angularDamping = 0.0f;
	
	private boolean fixedRotation = false;
	
	
	private float rotation = 0.0f;
	private float mass = 0.0f;
	private float inverseMass = 0.0f;
	private float area = 1f;
	private boolean grounded = false;
	
	private float cor = 1.0f;
	
	public Rigidbody2D() {
		position = new Vector2();
		linearVelocity = new Vector2();
		forceAccum = new Vector2();
	}
	
	public void physicsUpdate(float dt) {
		if(this.mass == 0.0f) return;
		
		// CALCULATE LINEAR VELOCITY
		Vector2 acceleration = new Vector2(forceAccum).scl(this.inverseMass);
		linearVelocity.add(acceleration.scl(dt));
		
		// UPDATE THE LINEAR POSITION
		this.position.add(new Vector2(linearVelocity).scl(dt));
		
		clearAccumulators();
	}
	
	public void clearAccumulators() {
		this.forceAccum.x = 0;
		this.forceAccum.y = 0;
	}
	
	public void setTransform(Vector2 position, float rotation) {
		this.position.set(position);
		this.rotation = rotation;
	}
	
	public void setTransform(Vector2 position) {
		this.position.set(position);
	}
	
	public Vector2 getPosition() {
		return position;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public void rotate(float rotation) {
		this.rotation+=rotation;
	}
	
	public void setVelocity(Vector2 velocity) {
		this.linearVelocity.set(velocity);
	}
	
	public Vector2 getVelocity() {
		return linearVelocity;
	}

	public float getMass() {
		return mass;
	}
	
	public float getInverseMass() {
		return inverseMass;
	}

	public void setMass(float mass) {
		this.mass = mass;
		if(this.mass != 0.0f) {
			this.inverseMass = 1.0f / mass;
		}
	}
	
	public float getArea() {
		return this.area;
	}
	
	public void setArea(float area) {
		this.area = area;
	}
	
	public boolean hasInfiniteMass() {
		return this.mass == 0.0f;
	}
	
	public void addForce(Vector2 force) {
		this.forceAccum.add(force);
	}

	public float getCor() {
		return cor;
	}

	public boolean isGrounded() {
		return this.grounded;
	}
	
	public void setGrounded(boolean a) {
		this.grounded = a;
	}
	
	public void setCor(float cor) {
		this.cor = cor;
	}
}
