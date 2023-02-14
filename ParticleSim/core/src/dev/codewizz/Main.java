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

import dev.codewizz.primitives.Box2D;
import dev.codewizz.primitives.Circle;
import dev.codewizz.primitives.Shape;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Main extends ApplicationAdapter {
	
	private static TextureRegion WHITE_TEX;
	public static ShapeDrawer drawer;
	private PhysicsSystem2D physics = new PhysicsSystem2D(1.0f / 60.0f, new Vector2(0, -80));
	
	private SpriteBatch batch;
	
	private List<Shape> shapes;

	@Override
	public void create () {
		batch = new SpriteBatch();
		WHITE_TEX = new TextureRegion(new Texture("white.png"));
		drawer = new ShapeDrawer(batch, WHITE_TEX);
		
		shapes = new ArrayList<>();
		
		addShapes();
	}
	
	public void addShapes() {
		for(int i = 0; i < 15; i++) {
			//addShape(new Circle(475 + 45 * i, 500 - (i*2) * (i*2), 50, Color.WHITE, true), 0.0f, false);
		}
		
		//Circle c = new Circle(500, 450, 50, Color.RED, true);
		//c.getRigidbody().setArea(100f);
		//addShape(c, 50f, true);
		
		//Circle c1 = new Circle(100, 500, 20, Color.YELLOW, true);
		//c1.getRigidbody().setArea(20);
		//addShape(c1, 40f, true);
		
		//addShape(new Box2D(new Vector2(1, 1), new Vector2(Gdx.graphics.getWidth(), 30)), 0.0f, false);
		
		//Circle c2 = new Circle(200, 500, 20, Color.YELLOW, true);
		//c2.getRigidbody().setArea(20);
		//addShape(c2, 40f, true);
		
		//Circle c2 = new Circle(500, 700, 50, Color.BLUE, true);
		//c2.getRigidbody().setArea(20f);
		//addShape(c2, 50f, true);
		//addShape(new Circle(500, 500, 50, Color.WHITE, true), 0.0f, false);
		
		
		/*
		shapes.add(new AABB(new Vector2(1, 0), new Vector2(Gdx.graphics.getWidth(), 30), Color.GOLDENROD, true));
		shapes.get(0).getRigidbody().setMass(10f);
		shapes.add(new AABB(new Vector2(1, 0), new Vector2(30, Gdx.graphics.getHeight()), Color.GOLDENROD, true));
		shapes.get(1).getRigidbody().setMass(10f);
		shapes.add(new AABB(new Vector2(1, Gdx.graphics.getHeight()-30), new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), Color.GOLDENROD, true));
		shapes.get(2).getRigidbody().setMass(10f);
		shapes.add(new AABB(new Vector2(Gdx.graphics.getWidth(), 0), new Vector2(Gdx.graphics.getWidth()-30, Gdx.graphics.getHeight()), Color.GOLDENROD, true));
		shapes.get(3).getRigidbody().setMass(10f);
		*/
		//Box2D box = new Box2D(new Vector2(100, 100), new Vector2(200, 120));
		//box.getRigidbody().rotate(30f);
		//addShape(box, 0.0f, false);
		
		addShape(new Box2D(new Vector2(1, 1), new Vector2(Gdx.graphics.getWidth(), 30)), 0.0f, false);
	}
	
	public void addShape(Shape shape, float mass, boolean b) {
		shapes.add(shape);
		shape.getRigidbody().setMass(mass);
		physics.addShape(shape, b);
	}
	
	private Vector2 last = new Vector2();
	
	public void update(float dt) {
		if(Gdx.input.isButtonPressed(0)) {
			
			Vector2 newVect = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
			float d = Vector2.dst(last.x, last.y, newVect.x, newVect.y);
			
			if(d > 50) {
				last = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
				Circle c = new Circle(last.x, last.y, 20, Color.YELLOW, true);
				c.getRigidbody().setArea(20);
				addShape(c, 40f, true);
				
			}
			
		}
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
		
		update(Gdx.graphics.getDeltaTime());
		physics.update(Gdx.graphics.getDeltaTime());
		
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
