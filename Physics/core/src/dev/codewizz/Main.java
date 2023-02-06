package dev.codewizz;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
	
	private ArrayList<Solid> solids = new ArrayList<Solid>();
	
	private SpriteBatch batch;
	private Ball ball;
	private Ball ball2;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		solids.add(new Solid(0, 0, Gdx.graphics.getWidth(), 50));
		solids.add(new Solid(0, Gdx.graphics.getHeight() - 50, Gdx.graphics.getWidth(), 50));
		solids.add(new Solid(0, 0, 50, Gdx.graphics.getHeight()));
		solids.add(new Solid(Gdx.graphics.getWidth() - 50, 0, 50, Gdx.graphics.getHeight()));
		solids.add(new Solid(Gdx.graphics.getWidth()/2-25, 0, 50, Gdx.graphics.getHeight()/2));
		
		
		
		ball = new Ball(500, 500);
		ball2 = new Ball(1500, 400);
	}

	@Override
	public void render () {
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		
		ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
		batch.begin();
		ball.update(Gdx.graphics.getDeltaTime(), solids);
		ball2.update(Gdx.graphics.getDeltaTime(), solids);
		
		
		for(Solid solid : solids) {
			solid.render(batch);
		}
		
		ball.render(batch);
		ball2.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
