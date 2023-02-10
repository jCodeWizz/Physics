package dev.codewizz.primitives;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dev.codewizz.Main;

public class Line2D extends Shape {
    private Vector2 from;
    private Vector2 to;
    private int lifetime;

    public Line2D(Vector2 from, Vector2 to) {
        this.from = from;
        this.to = to;
    }
    
    public Line2D(Vector2 from, Vector2 to, Color color) {
        this.from = from;
        this.to = to;
        this.color = color;
    }

    public Line2D(Vector2 from, Vector2 to, Color color, int lifetime) {
        this.from = from;
        this.to = to;
        this.color = color;
        this.lifetime = lifetime;
    }
    
    @Override
	public void render(SpriteBatch b) {
    	Main.drawer.line(to, from, color);
	}
    
    public float lengthSquared() {
    	return new Vector2(to).sub(from).len2();
    }

    public int beginFrame() {
        this.lifetime--;
        return this.lifetime;
    }

    public Vector2 getFrom() {
        return from;
    }

    public Vector2 getTo() {
        return to;
    }

    public Vector2 getStart() {
        return this.from;
    }

    public Vector2 getEnd() {
        return this.to;
    }

    public Color getColor() {
        return color;
    }
}