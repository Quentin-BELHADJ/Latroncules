package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Vec2DUnitTest {

    Vec2D vec2D;

    @BeforeEach
    void testSetUp() {
        vec2D = new Vec2D(1, 2);
    }

    @Test
    void testGetX() {
        Assertions.assertEquals(1, vec2D.getX());
    }

    @Test
    void testGetY() {
        Assertions.assertEquals(2, vec2D.getY());
    }

    @Test
    void testSetX() {
        vec2D.setX(10);
        Assertions.assertEquals(10, vec2D.getX());
    }

    @Test
    void testSetY() {
        vec2D.setY(10);
        Assertions.assertEquals(10, vec2D.getY());
    }

    @Test
    void testAdd() {
        vec2D = vec2D.add(new Vec2D(4, 2));
        Assertions.assertEquals(5, vec2D.getX());
        Assertions.assertEquals(4, vec2D.getY());
    }

    @Test
    void testSubstract() {
        vec2D = vec2D.substract(new Vec2D(4, 2));
        Assertions.assertEquals(-3, vec2D.getX());
        Assertions.assertEquals(-0, vec2D.getY());
    }

    @Test
    void testMultiply() {
        vec2D = vec2D.multiply(new Vec2D(4, 3));
        Assertions.assertEquals(4, vec2D.getX());
        Assertions.assertEquals(6, vec2D.getY());
    }

    @Test
    void testEquals() {
        Vec2D v = new Vec2D(1, 2);
        Assertions.assertEquals(vec2D, v);
        v = vec2D.add(new Vec2D(1, 3));
        Assertions.assertNotEquals(vec2D, v);
        v = vec2D.add(new Vec2D(0, 2));
        Assertions.assertNotEquals(vec2D, v);
        v = vec2D.add(new Vec2D(2, 1));
        Assertions.assertNotEquals(vec2D, v);
    }

    @Test
    void testToString(){
        Assertions.assertEquals("Vec2D.java{x=1, y=2}", vec2D.toString());
    }
}