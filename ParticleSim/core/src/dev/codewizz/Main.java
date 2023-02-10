package dev.codewizz;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import dev.codewizz.primitives.AABB;
import dev.codewizz.primitives.Shape;
import dev.codewizz.rigidbody.Rigidbody2D;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Main extends ApplicationAdapter {
	
	private static TextureRegion WHITE_TEX;
	public static ShapeDrawer drawer;
	
	private SpriteBatch batch;
	
	private List<CollisionManifold> collisions;
	private List<Rigidbody2D> bodies1;
	private List<Rigidbody2D> bodies2;
	private List<Shape> shapes;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		WHITE_TEX = new TextureRegion(new Texture("white.png"));
		drawer = new ShapeDrawer(batch, WHITE_TEX);
		
		collisions = new ArrayList<>();
		bodies1 = new ArrayList<>();
		bodies2 = new ArrayList<>();
		shapes = new ArrayList<>();
		
		addShapes();
	}
	
	public void addShapes() {
		shapes.add(new AABB(new Vector2(1, 0), new Vector2(Gdx.graphics.getWidth(), 30), Color.GOLDENROD, true));
		
		
		shapes.add(new AABB(new Vector2(1, 0), new Vector2(30, Gdx.graphics.getHeight()), Color.GOLDENROD, true));
		
		
		shapes.add(new AABB(new Vector2(1, Gdx.graphics.getHeight()-30), new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), Color.GOLDENROD, true));
		
		
		shapes.add(new AABB(new Vector2(Gdx.graphics.getWidth(), 0), new Vector2(Gdx.graphics.getWidth()-30, Gdx.graphics.getHeight()), Color.GOLDENROD, true));
		
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
		for(Shape shape : shapes) {
			shape.render(batch);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
