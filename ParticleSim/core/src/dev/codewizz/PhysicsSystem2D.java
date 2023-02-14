package dev.codewizz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.forces.AirResistance;
import dev.codewizz.forces.ForceRegistry;
import dev.codewizz.forces.Gravity2D;
import dev.codewizz.forces.Normal2D;
import dev.codewizz.primitives.Box2D;
import dev.codewizz.primitives.Circle;
import dev.codewizz.primitives.Shape;
import dev.codewizz.rigidbody.Rigidbody2D;

public class PhysicsSystem2D {

	private ForceRegistry forceRegistry;
	private Gravity2D gravity;
	private Normal2D normal;
	private AirResistance airResistance;
	
	private List<Shape> shapes;
	private List<Shape> shape1;
	private List<Shape> shape2;
	private List<CollisionManifold> collisions;
	
	private float fixedUpdate;
	private int impulseIterations = 6;
	
	public PhysicsSystem2D(float fixedUpdateDt, Vector2 gravity) {
		this.forceRegistry = new ForceRegistry();
		this.gravity = new Gravity2D(gravity);
		this.normal = new Normal2D(gravity);
		this.airResistance = new AirResistance();
		
		this.shapes = new ArrayList<>();
		this.shape1 = new ArrayList<>();
		this.shape2 = new ArrayList<>();
		this.collisions = new ArrayList<>();
		
		this.fixedUpdate = fixedUpdateDt;
		
		
	}
	
	public void update(float dt) {
		fixedUpdate();
	}
	
	public void fixedUpdate() {
		shape1.clear();
		shape2.clear();
		collisions.clear();
		
		for(int i = 0; i < shapes.size(); i++) {
			Shape s = shapes.get(i);
			
			if(s instanceof Circle) {
				Circle c = (Circle)s;
				Vector2 pos = c.getRigidbody().getPosition();
				
				if(pos.x - c.getRadius() <= 0 || pos.x + c.getRadius() >= Gdx.graphics.getWidth()) {
					c.getRigidbody().getPosition().sub(new Vector2(c.getRigidbody().getVelocity()).scl(Gdx.graphics.getDeltaTime()));
					c.getRigidbody().getVelocity().scl(-1, 1);
				}
				if(pos.y - c.getRadius() <= 0 || pos.y + c.getRadius() >= Gdx.graphics.getHeight()) {
					c.getRigidbody().getPosition().sub(new Vector2(c.getRigidbody().getVelocity()).scl(Gdx.graphics.getDeltaTime()));
					c.getRigidbody().getVelocity().scl(1, -1);
				}
			}
		}
		
		Collections.sort(shapes);
		int size = shapes.size();
		for(int i = 0; i < size; i++) {
			for(int j = i; j < size; j++) {
				if(i == j) continue;
				
				CollisionManifold result = new CollisionManifold();
				Shape s1 = shapes.get(i);
				s1.getRigidbody().setGrounded(false);
				Shape s2 = shapes.get(j);
				s2.getRigidbody().setGrounded(false);
				
				if(!(s1.getRigidbody().hasInfiniteMass() && s2.getRigidbody().hasInfiniteMass())) {
					result = Collisions.findCollisionFeatures(s1, s2);
				}
				
				if(result != null && result.isColliding()) {
					shape1.add(s1);
					shape2.add(s2);
					collisions.add(result);
					
					s1.getRigidbody().setGrounded(true);
					
					if(s1.getRigidbody().getVelocity().len2() < 100) {
						s1.getRigidbody().getVelocity().x = 0;
						s1.getRigidbody().getVelocity().y = 0;
					}
					s2.getRigidbody().setGrounded(true);
					
					if(s2.getRigidbody().getVelocity().len2() < 100) {
						s2.getRigidbody().getVelocity().x = 0;
						s2.getRigidbody().getVelocity().y = 0;
					}
				}
				
			}
		}
		
		
		forceRegistry.updateForces(fixedUpdate);
		
		for(int k = 0; k < impulseIterations; k++) {
			for (int i = 0; i < collisions.size(); i++) {
				int jSize = collisions.get(i).getContactPoints().size();
				for(int j = 0; j < jSize; j++) {
					Shape s1 = shape1.get(i);
					Shape s2 = shape2.get(i);
					CollisionManifold m = collisions.get(i);
					
					if(!s2.getRigidbody().hasInfiniteMass()) {
						applyImpulse(s1.getRigidbody(), s2.getRigidbody(), m);
					} else {
						s1.getRigidbody().getPosition().y = ((Box2D) s2).getLocalMax().y + ((Circle) s1).getRadius();
						//s1.getRigidbody().getVelocity().scl(1, -1);
						applyImpulse(s1.getRigidbody(), s2.getRigidbody(), m);
						
						
						
						
					}
					
					
				}
			}
		}
		
		for(int i = 0; i < shapes.size(); i++) {
			shapes.get(i).getRigidbody().physicsUpdate(fixedUpdate);
		}
	}
	
	private void applyImpulse(Rigidbody2D a, Rigidbody2D b, CollisionManifold m) {
		
		
		// LINEAR VELOCITY
		float invMass1 = a.getInverseMass();
		float invMass2 = b.getInverseMass();
		float invMassSum = invMass1 + invMass2;

		if (invMassSum == 0f) {
			return;
		}

		// RELATIVE VELOCITY
		Vector2 relativeVel = new Vector2(b.getVelocity()).sub(a.getVelocity());
		Vector2 relativeNormal = new Vector2(m.getNormal()).nor();

		if (relativeVel.dot(relativeNormal) > 0.0f) {
			return;
		}

		float e = Math.min(a.getCor(), b.getCor());
		float numerator = (-(1.0f + e) * relativeVel.dot(relativeNormal));
		float j = numerator / invMassSum;

		if (m.getContactPoints().size() > 0 && j != 0.0f) {
			j /= (float) m.getContactPoints().size();
		}

		Vector2 impulse = new Vector2(relativeNormal).scl(j);
		a.setVelocity(new Vector2(a.getVelocity()).add(new Vector2(impulse).scl(invMass1).scl(-1)));
		b.setVelocity(new Vector2(b.getVelocity()).add(new Vector2(impulse).scl(invMass2).scl(1)));
		return;
	}

	public void addShape(Shape shape, boolean simulate) {
		this.shapes.add(shape);
		if(simulate) {
			this.forceRegistry.add(shape.getRigidbody(), gravity);
			this.forceRegistry.add(shape.getRigidbody(), airResistance);
			this.forceRegistry.add(shape.getRigidbody(), normal);
		}
	}
}
