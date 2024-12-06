package model;

import java.util.ArrayList;

public class Vec2D {
    private int x;
    private int y;

    public Vec2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vec2D add(Vec2D v) {
        return new Vec2D(x + v.getX(), y + v.getY());
    }

    public Vec2D substract(Vec2D v) {
        return new Vec2D(x - v.getX(), y - v.getY());
    }

    public Vec2D multiply(Vec2D v) {
        return new Vec2D(x * v.getX(), y * v.getY());
    }

    public Vec2D multiply(int i) {
        return new Vec2D(x*i,y*i);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Vec2D && other != null) {
            Vec2D v = (Vec2D) other;
            return x == v.getX() && y == v.getY();
        }else{
            return false;
        }

    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(x);
        result = 31 * result + Integer.hashCode(y);
        return result;
    }

    @Override
    public String toString() {
        return "Vec2D{" + "x=" + x + ", y=" + y + '}';
    }


}
