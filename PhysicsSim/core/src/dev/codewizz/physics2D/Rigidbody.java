package dev.codewizz.physics2D;

import com.badlogic.gdx.math.Vector2;

import dev.codewizz.objects.GameObject;
import dev.codewizz.utils.Debug;
import dev.codewizz.utils.JMath;

public class Rigidbody {

	private GameObject object;
	
	private Vector2 position;
	private Vector2 linearVelocity;
	private Vector2 force;

	private float mass;
	private float density;
	private float restitution;
	private float rotation;
	private float rotationalVelocity;
	private float area;
	
	private boolean isStatic;
	private float invMass;

	private ShapeType shapeType;
	public boolean transformRequired;
	
	private Rigidbody(GameObject object, Vector2 position, float density, float mass, float restitution, float area, boolean isStatic, ShapeType shapeType) {
		this.object = object;
		this.position = position;
		this.linearVelocity = new Vector2();
		this.force = new Vector2();
		this.isStatic = isStatic;
		
		
		this.rotation = 0f;
		this.rotationalVelocity = 0f;
		
		this.density = density;
		this.mass = mass;
		this.restitution = restitution;
		this.area = area;
		
		if(!this.isStatic) {
			this.invMass = 1f / this.mass;
		} else {
			this.invMass = 0.0f;
		}
		
		this.shapeType = shapeType;
		
		
		this.transformRequired = true;
	}
	
	public void update(float dt) {
		
		this.addForce(new Vector2(0, -98.1f * mass));
		
		
		
		Vector2 acceleration = new Vector2(force).scl(1f/mass * dt);
		this.linearVelocity.add(new Vector2(acceleration));
		this.move(new Vector2(linearVelocity).scl(dt));
		
		this.rotation += this.rotationalVelocity * dt;
		
		this.force = new Vector2();
		
		
	}
	
	public void move(Vector2 amount) {
		this.position.add(amount);
		this.transformRequired = true;
	}
	
	public void moveTo(Vector2 pos) {
		this.position.set(pos);
		this.transformRequired = true;
	}
	
	public void addForce(Vector2 force) {
		if(!isStatic) {
			this.force.add(force);
		}
	}
	
	public void rotate(float amount) {
		this.rotation += amount;
		if(this.rotation > 360) {
			this.rotation -= 360;
		}
		this.transformRequired = true;
	}
	
	public void setRotation(float amount) {
		this.rotation = amount;
		if(this.rotation > 360) {
			this.rotation -= 360;
		}
		this.transformRequired = true;
	}
	
	public static Rigidbody createCircle(GameObject object, Vector2 position, float radius, float density, boolean isStatic, float restitution) {
		Rigidbody body = null;
		
		if(radius <= 0) { Debug.error("RADIUS {" + radius + "} IS NEGATIVE"); return null; }
		
		float area = radius * radius * (float)Math.PI;
		
		if(area > World.maxBodySize || area < World.minBodySize) { Debug.error("AREA {" + area + "} IS OUT OF BOUNDS"); return null; }
		if(density > World.maxDensity || density < World.minDensity) { Debug.error("DENSITY {" + density + "} IS OUT OF BOUNDS"); return null; }
		
		
		restitution = JMath.clamp(restitution, 0f, 1f);
		
		// mass = area * depth * density
		float mass = area * density;

		body = new Rigidbody(object, position, density, mass, restitution, area, isStatic, ShapeType.Circle);
		return body;
	}
	
	public static Rigidbody createBox(GameObject object, Vector2 position, float w, float h, float density, boolean isStatic, float restitution) {
		Rigidbody body = null;
		
		
		float area = w * h;
		if(area > World.maxBodySize || area < World.minBodySize) { Debug.error("AREA {" + area + "} IS OUT OF BOUNDS"); return null; }
		if(density > World.maxDensity || density < World.minDensity) { Debug.error("DENSITY {" + density + "} IS OUT OF BOUNDS"); return null; }
		
		restitution = JMath.clamp(restitution, 0f, 1f);
		
		// mass = area * depth * density
		float mass = area * density;
		
		body = new Rigidbody(object, position, density, mass, restitution, area, isStatic, ShapeType.Box);
		return body;
	}
	
	public GameObject getObject() {
		return this.object;
	}
	
	public Vector2 getPosition() {
		return this.position;
	}
	
	public float getRotation() {
		return this.rotation;
	}
	
	public ShapeType getType() {
		return this.shapeType;
	}
	
	public float getMass() {
		return this.mass;
	}
	
	public Vector2 getLinearVelocity() {
		return linearVelocity;
	}

	public Vector2 getForce() {
		return force;
	}

	public float getDensity() {
		return density;
	}

	public float getRestitution() {
		return restitution;
	}

	public float getRotationalVelocity() {
		return rotationalVelocity;
	}

	public float getArea() {
		return area;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public ShapeType getShapeType() {
		return shapeType;
	}

	public boolean isTransformRequired() {
		return transformRequired;
	}

	public float getInvMass() {
		if(isStatic) {
			return 0.0f;
		}
		
		
		return invMass;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
}