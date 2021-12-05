package fr.bakaaless.ants;

public class Vector2D implements Cloneable {

    private double x;
    private double y;

    public Vector2D(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D set(final Vector2D other) {
        this.x = other.x;
        this.y = other.y;

        return this;
    }

    public Vector2D set(final Vector2D other, final double max) {
        this.x = Math.min(other.x, max);
        this.y = Math.min(other.y, max);

        return this;
    }

    public Vector2D set(final Vector2D other, final double min, final double max) {
        this.x = Math.max(Math.min(other.x, max), -max);
        this.y = Math.max(Math.min(other.y, max), -max);

        return this;
    }

    public Vector2D set(final double angle) {
        this.x = Math.cos(angle);
        this.y = Math.sin(angle);

        return this;
    }

    public double getX() {
        return this.x;
    }

    public Vector2D setX(final double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return this.y;
    }

    public Vector2D setY(final double y) {
        this.y = y;
        return this;
    }

    public Vector2D add(final double x, final double y) {
        this.x += x;
        this.y += y;

        return this;
    }

    public Vector2D add(final double x, final double y, final double max) {
        this.x += x;
        this.x = Math.min(Math.abs(this.x), Math.abs(max)) * Math.signum(this.x);
        this.y += y;
        this.y = Math.min(Math.abs(this.y), Math.abs(max)) * Math.signum(this.y);

        return this;
    }

    public Vector2D add(final Vector2D other) {
        this.x += other.x;
        this.y += other.y;

        return this;
    }

    public Vector2D add(final Vector2D other, final int max) {
        this.x += Math.min(other.x, max);
        this.y += Math.max(other.y, max);

        return this;
    }

    public Vector2D subtract(final Vector2D other) {
        this.x -= other.x;
        this.y -= other.y;

        return this;
    }

    public Vector2D multiply(final double factor) {
        this.x *= factor;
        this.y *= factor;

        return this;
    }

    private double constrainToRange(double angle, double min, double max) {
        return Math.min(Math.max(angle, min), max);
    }

    public double dot(final Vector2D other) {
        return x * other.x + y * other.y;
    }

    public double angle(final Vector2D other) {
        double dot = dot(other) / (lengthSquared() * other.lengthSquared());

        return Math.acos(dot);
    }

    public Vector2D rotateAroundZ(double angle) {
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);

        double x = angleCos * getX() - angleSin * getY();
        double y = angleSin * getX() + angleCos * getY();
        return setX(x).setY(y);
    }

    public Vector2D normalized()  {
        final double length = lengthSquared();

        this.x /= length;
        this.y /= length;

        return this;
    }

    public double length() {
        return Math.pow(this.x, 2) + Math.pow(this.y, 2);
    }

    public double lengthSquared() {
        return Math.sqrt(this.length());
    }

    @Override
    public Vector2D clone() {
        try {
            return (Vector2D) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
