package dev.codewizz;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
	
	private ArrayList<Hydrogen> particles = new ArrayList<>();
	private ArrayList<Solid> solids = new ArrayList<>();
	private SpriteBatch batch;
	
	private List<CollisionManifold> collisions;
	private List<Hydrogen> bodies1;
	private List<Hydrogen> bodies2;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		collisions = new ArrayList<>();
		bodies1 = new ArrayList<>();
		bodies2 = new ArrayList<>();
		
		particles.add(new Hydrogen(500, 600));
		particles.add(new Hydrogen(500, 300));
		
		solids.add(new Solid(0, 0, Gdx.graphics.getWidth(), 50));
		solids.add(new Solid(0, Gdx.graphics.getHeight() - 50, Gdx.graphics.getWidth(), 50));
		solids.add(new Solid(0, 0, 50, Gdx.graphics.getHeight()));
		solids.add(new Solid(Gdx.graphics.getWidth() - 50, 0, 50, Gdx.graphics.getHeight()));
	}
	
	public void update(float dt) {
		bodies1.clear();
		bodies2.clear();
		collisions.clear();
		
		int size = particles.size();
		for(int i = 0; i < size; i++) {
			for(int j = i; j < size; j++) {
				if(i == j) continue;
				
				CollisionManifold result = new CollisionManifold();
				Hydrogen r1 = particles.get(i);
				Hydrogen r2 = particles.get(j);
				
				result = Collisions.findCollisionFeatures(r1, r2);
				
				if(result != null && result.isColliding()) {
					bodies1.add(r1);
					bodies2.add(r2);
					collisions.add(result);
				}
			}
		}
		
		
		
		
		
		for(Hydrogen p : particles) {
			p.update(dt, solids, particles);
		}
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
		
		update(Gdx.graphics.getDeltaTime());
		
		batch.begin();
		for(Solid s : solids) {
			s.render(batch);
		}
		for(Hydrogen h : particles) {
			h.render(batch);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
