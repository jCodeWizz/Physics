package dev.codewizz;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import dev.codewizz.primitives.AABB;
import dev.codewizz.primitives.Line2D;
import dev.codewizz.rigidbody.IntersectionDetector2D;
import dev.codewizz.rigidbody.Rigidbody2D;

public class Main extends ApplicationAdapter {
	
	private SpriteBatch batch;
	
	private List<CollisionManifold> collisions;
	private List<Rigidbody2D> bodies1;
	private List<Rigidbody2D> bodies2;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		collisions = new ArrayList<>();
		bodies1 = new ArrayList<>();
		bodies2 = new ArrayList<>();
		
		
		AABB aabb = new AABB(new Vector2(-10, -10), new Vector2(10, 10));
		Line2D line = new Line2D(new Vector2(-20, -20), new Vector2(20, 20));
		
		System.out.println(IntersectionDetector2D.lineAndAABB(line, aabb));
		
		
	}
	
	public void update(float dt) {
		bodies1.clear();
		bodies2.clear();
		collisions.clear();
		
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
		
		update(Gdx.graphics.getDeltaTime());
		
		batch.begin();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
