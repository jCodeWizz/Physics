package dev.codewizz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
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
		
		GameObject box = new Box(0, -150f, 500f, 75);
		box.getRigidbody().setStatic(true);
		world.addObject(box);
	}
	
	

	@Override
	public void render () {
		ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1f);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		if(Gdx.input.isButtonJustPressed(0)) {
			float w = Utils.RANDOM.nextInt(15) + 15;
			float h = Utils.RANDOM.nextInt(15) + 15;
			
			Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			
			pos = camera.unproject(new Vector3(pos.x, pos.y, 0));
			world.addObject(new Box(pos.x, pos.y, w, h, Utils.getRandomColor()));
			
		}
		
		if(Gdx.input.isButtonJustPressed(1)) {
			float r = Utils.RANDOM.nextInt(15) + 15;
			r *= 0.5f;
			
			Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			
			pos = camera.unproject(new Vector3(pos.x, pos.y, 0));
			world.addObject(new Circle(pos.x, pos.y, r, Utils.getRandomColor()));
			
		}
		
		
		world.update(Gdx.graphics.getDeltaTime(), 8);
		
		spriteBatch.begin();
		
		world.render(spriteBatch);
		
		spriteBatch.end();
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
	}
}
