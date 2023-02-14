package dev.codewizz;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import dev.codewizz.physics2D.Rigidbody;
import dev.codewizz.physics2D.ShapeType;
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
	
	//private Color[] colors;

	private List<Rigidbody> bodies = new ArrayList<>();
	
	@Override
	public void create () {
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
		
		bodies.add(Rigidbody.createCircle(new Vector2(5, 10), 1f, 0.2f, false, 0.5f));
		bodies.add(Rigidbody.createBox(new Vector2(12, 10), 2f, 2f, 0.2f, false, 0.5f));
		bodies.add(Rigidbody.createCircle(new Vector2(18, 10), 1f, 0.2f, false, 0.5f));

	}
	
	

	@Override
	public void render () {
		ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1f);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
	
		spriteBatch.begin();
		
		//for(int i = 0; i < colors.length; i++) {
		//	Rigidbody body = bodies.get(i);
		//	//shapeDrawer.circle(body.getPosition().x, body.getPosition().y, 1f, JoinType.SMOOTH);
		//	shapeDrawer.filledRectangle(body.getPosition().x, body.getPosition().y, 1f, 1f, colors[i]);
		//}
		
		for(int i = 0; i < bodies.size(); i++) {
			Rigidbody b1 = bodies.get(i);
			
			if(b1.getType() == ShapeType.Box) {
				Vector2[] v = ((BoxCollider) b1.getObject().getCollider()).getTransformedVertices();
				shapeDrawer.filledRectangle(v[0].x, v[0].y, 2f, 2f, b1.getRotation());
			} else {
				shapeDrawer.filledCircle(b1.getPosition().x, b1.getPosition().y, 1f, Color.BLUE);
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			bodies.get(0).move(new Vector2(-0.2f, 0));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			bodies.get(0).move(new Vector2(0.2f, 0));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			bodies.get(0).move(new Vector2(0, 0.2f));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			bodies.get(0).move(new Vector2(0, -0.2f));
		}
		
		
		
		
		
		
		for(int i = 0; i < bodies.size() - 1; i++) {
			Rigidbody b1 = bodies.get(i);
			
			for(int j = i+1; j < bodies.size(); j++) {
				
				Rigidbody b2 = bodies.get(j);
				
				if(b1.getType() == ShapeType.Box && b2.getType() == ShapeType.Circle) {
					CollisionResult result = Collisions.circleAndPolygon(((CircleCollider)b2.getObject().getCollider()), ((BoxCollider)b1.getObject().getCollider()));
					
					if(result.isIntersecting()) {
						Vector2 normal = result.getNormal();

						float depth = result.getDepth();
						b1.move(new Vector2(normal).scl(depth / 2f));
						b2.move(new Vector2(normal).scl(-depth / 2f));
					}
				} else if(b2.getType() == ShapeType.Box && b1.getType() == ShapeType.Circle) {
					CollisionResult result = Collisions.circleAndPolygon(((CircleCollider)b1.getObject().getCollider()), ((BoxCollider)b2.getObject().getCollider()));
					
					if(result.isIntersecting()) {
						Vector2 normal = result.getNormal();

						float depth = result.getDepth();
						b1.move(new Vector2(normal).scl(-depth / 2f));
						b2.move(new Vector2(normal).scl(depth / 2f));
					}
				}
				
				
				
				/*
				CollisionResult result = Collisions.circleAndCircle(b1.getPosition(), b1.getRadius(), b2.getPosition(), b2.getRadius());
				
				if(result.isIntersecting()) {
					Vector2 normal = result.getNormal();
					float depth = result.getDepth();
					b1.move(new Vector2(normal).scl(-depth / 2f));
					b2.move(new Vector2(normal).scl(depth / 2f));
				}
				*/
			}
		}
		
		spriteBatch.end();
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
	}
	
}
