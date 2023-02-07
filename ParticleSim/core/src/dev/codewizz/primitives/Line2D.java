package dev.codewizz.primitives;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Line2D {
    private Vector2 from;
    private Vector2 to;
    private Vector3 color;
    private int lifetime;

    public Line2D(Vector2 from, Vector2 to) {
        this.from = from;
        this.to = to;
    }

    public Line2D(Vector2 from, Vector2 to, Vector3 color, int lifetime) {
        this.from = from;
        this.to = to;
        this.color = color;
        this.lifetime = lifetime;
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

    public Vector3 getColor() {
        return color;
    }
}