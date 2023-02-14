package dev.codewizz.forces;

import dev.codewizz.rigidbody.Rigidbody2D;

public interface ForceGenerator {
	public void updateForce(Rigidbody2D body, float dt);
}
