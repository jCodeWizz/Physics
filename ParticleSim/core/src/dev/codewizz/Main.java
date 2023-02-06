package dev.codewizz;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
	
	private ArrayList<Hydrogen> particles = new ArrayList<>();
	private ArrayList<Solid> solids = new ArrayList<>();
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		particles.add(new Hydrogen(500, 500));
		
		solids.add(new Solid(0, 0, Gdx.graphics.getWidth(), 50));
		solids.add(new Solid(0, Gdx.graphics.getHeight() - 50, Gdx.graphics.getWidth(), 50));
		solids.add(new Solid(0, 0, 50, Gdx.graphics.getHeight()));
		solids.add(new Solid(Gdx.graphics.getWidth() - 50, 0, 50, Gdx.graphics.getHeight()));
	}
	
	public void update(float dt) {
		for(Hydrogen p : particles) {
			for(Solid s : solids) {
				Vector2 closePoint = new Vector2(Utils.a(p.pos.x, s.pos.x + s.size.x, s.pos.x), Utils.a(p.pos.y, s.pos.y + s.size.y, s.pos.y));
				
				if(Vector2.dst(p.pos.x, p.pos.y, closePoint.x, closePoint.y) < p.r) {
					// COLLIDING
				}
			}
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
