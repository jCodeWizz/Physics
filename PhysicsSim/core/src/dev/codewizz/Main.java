package dev.codewizz;

import java.util.ArrayList;
import java.util.List;

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
import dev.codewizz.physics2D.Rigidbody;
import dev.codewizz.physics2D.ShapeType;
import dev.codewizz.physics2D.World;
import dev.codewizz.physics2D.collision.BoxCollider;
import dev.codewizz.physics2D.collision.CircleCollider;
import dev.codewizz.physics2D.collision.CollisionResult;
import dev.codewizz.physics2D.collision.Collisions;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Main extends ApplicationAdapter {
	
	public static ShapeDrawer shapeDrawer;
	private TextureRegion WHITE;
	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private World world;
	
	//private Color[] colors;

	private List<Rigidbody> bodies = new ArrayList<>();
	
	@Override
	public void create () {
		world = new World();
		spriteBatch = new SpriteBatch();
		WHITE = new TextureRegion(new Texture("white.png"));
		shapeDrawer = new ShapeDrawer(spriteBatch, WHITE);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(50, 50 * (h / w));

		camera.update();
		
		//colors = new Color[20];
		
		//for(int i = 0; i < 20; i++) {
		//	Vector2 pos = new Vector2(Utils.RANDOM.nextInt((int)camera.viewportWidth)  - (int)camera.viewportWidth/2, Utils.RANDOM.nextInt((int)camera.viewportHeight) - (int)camera.viewportHeight/2);
		//	colors[i] = Utils.getRandomColor();
		//
		//	Rigidbody body = Rigidbody.createBox(pos, 1f, 1f, 2f, false, 0.4f);
		//	bodies.add(body);
		//}
		
		world.addObject(new Circle(5, 5, 2f));
		world.addObject(new Box(12, 5, 4f, 4f));
		world.addObject(new Circle(18, 5, 2f));
	}
	
	

	@Override
	public void render () {
		ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1f);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			world.getObject(0).move(new Vector2(-0.2f, 0));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			world.getObject(0).move(new Vector2(0.2f, 0));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			world.getObject(0).move(new Vector2(0, 0.2f));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			world.getObject(0).move(new Vector2(0, -0.2f));
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
