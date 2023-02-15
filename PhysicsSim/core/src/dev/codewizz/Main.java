package dev.codewizz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import dev.codewizz.objects.Box;
import dev.codewizz.objects.Circle;
import dev.codewizz.objects.GameObject;
import dev.codewizz.physics2D.World;
import dev.codewizz.utils.Utils;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Main extends ApplicationAdapter {
	
	public static ShapeDrawer shapeDrawer;
	private TextureRegion WHITE;
	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private World world;
	
	//private Color[] colors;

	@Override
	public void create () {
		world = new World();
		spriteBatch = new SpriteBatch();
		WHITE = new TextureRegion(new Texture("white.png"));
		shapeDrawer = new ShapeDrawer(spriteBatch, WHITE);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(1000, 1000 * (h / w));

		camera.update();
		
		float angle = 45f;
		
		GameObject box1 = new Box(0, -260, 1000f, 20f, Utils.getRandomColor());
		GameObject box2 = new Box(0, -260, 1000f, 20f, Utils.getRandomColor());
		
		box1.getRigidbody().setRotation(angle);
		box2.getRigidbody().setRotation(-angle);
		
		box1.getRigidbody().setStatic(true);
		box2.getRigidbody().setStatic(true);
		
		world.addObject(box1);
		world.addObject(box2);
		
		for(int i = 0; i < 50; i++) {
			if(Utils.RANDOM.nextBoolean()) {
				world.addObject(new Circle(Utils.RANDOM.nextInt(1000) - 500, Utils.RANDOM.nextInt(1000) - 500, Utils.RANDOM.nextInt(10) + 10f, Utils.getRandomColor()));
			} else {
				world.addObject(new Box(Utils.RANDOM.nextInt(1000) - 500, Utils.RANDOM.nextInt(1000) - 500, Utils.RANDOM.nextInt(10) + 10f, Utils.RANDOM.nextInt(10) + 10f, Utils.getRandomColor()));
			}
		}
	}
	
	

	@Override
	public void render () {
		ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1f);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			world.getObject(0).addForce(new Vector2(-50f * world.getObject(0).getRigidbody().getMass(), 0));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			world.getObject(0).addForce(new Vector2(50f * world.getObject(0).getRigidbody().getMass(), 0));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			world.getObject(0).addForce(new Vector2(0, 50f * world.getObject(0).getRigidbody().getMass()));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			world.getObject(0).addForce(new Vector2(0, -50f * world.getObject(0).getRigidbody().getMass()));
		}
		
		
		world.update(Gdx.graphics.getDeltaTime());
		
		spriteBatch.begin();
		
		world.render(spriteBatch);
		
		spriteBatch.end();
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
	}
}
