package dev.codewizz;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
	
	private ArrayList<Solid> solids = new ArrayList<Solid>();
	
	private SpriteBatch batch;
	private Ball ball;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		solids.add(new Solid(0, 0, Gdx.graphics.getWidth(), 50));
		solids.add(new Solid(0, Gdx.graphics.getHeight() - 50, Gdx.graphics.getWidth(), 50));
		solids.add(new Solid(0, 0, 50, Gdx.graphics.getHeight()));
		solids.add(new Solid(Gdx.graphics.getWidth() - 50, 0, 50, Gdx.graphics.getHeight()));
		
		
		
		ball = new Ball(500, 500);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
		batch.begin();
		ball.update(Gdx.graphics.getDeltaTime(), solids);
		ball.render(batch);
		
		for(Solid solid : solids) {
			solid.render(batch);
		}
		
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
