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
import dev.codewizz.utils.StopWatch;
import dev.codewizz.utils.Utils;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Main extends ApplicationAdapter {

	public static ShapeDrawer shapeDrawer;
	private TextureRegion WHITE;
	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private World world;
	private int iterations = 8;
	private StopWatch watch;

	private long longest = 0L;

	// private Color[] colors;

	@Override
	public void create() {
		watch = new StopWatch();
		world = new World();
		spriteBatch = new SpriteBatch();
		WHITE = new TextureRegion(new Texture("white.png"));
		shapeDrawer = new ShapeDrawer(spriteBatch, WHITE);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(1000, 1000 * (h / w));

		camera.update();

		GameObject box1 = new Box(0, -150, 600f, 50f, Utils.getRandomColor());

		box1.getRigidbody().setStatic(true);
		
		world.addObject(box1);

	}

	@Override
	public void render() {
		ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1f);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		if (Gdx.input.isButtonPressed(0)) {
			Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			Vector3 pos = camera.unproject(mousePos);

			float w = Utils.RANDOM.nextInt(20) + 15;

			Box box = new Box(pos.x, pos.y, w, w);

			world.addObject(box);
		}
		if (Gdx.input.isButtonPressed(1)) {
			Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			Vector3 pos = camera.unproject(mousePos);

			float w = Utils.RANDOM.nextInt(20) + 15;

			Circle circle = new Circle(pos.x, pos.y, w / 2f, Utils.getRandomColor());

			world.addObject(circle);

		}

		watch.start();
		world.update(Gdx.graphics.getDeltaTime(), iterations);
		watch.end();

		if (longest < watch.getTime())
			longest = watch.getTime();

		//Debug.info("TIME: " + watch.getTime());
		//Debug.info("LONGEST: " + longest);

		spriteBatch.begin();

		world.render(spriteBatch);

		spriteBatch.end();
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
	}
}
