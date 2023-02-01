package dev.codewizz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
	
	private SpriteBatch batch;
	private Ball ball;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		ball = new Ball(0, 0);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
		batch.begin();
		ball.update(Gdx.graphics.getDeltaTime());
		ball.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
