package com.wizardlybump17.physics.two.test;

import com.wizardlybump17.physics.two.position.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VectorTests {

    @Test
    void testAdd() {
        Assertions.assertEquals(new Vector2D(10, 10), Vector2D.ZERO.add(10, 10));
        Assertions.assertEquals(new Vector2D(10, 10), Vector2D.ZERO.add(new Vector2D(10, 10)));
        Assertions.assertEquals(new Vector2D(5, -7), new Vector2D(-10, -45).add(15, 38));
        Assertions.assertEquals(new Vector2D(5, -7), new Vector2D(-10, -45).add(new Vector2D(15, 38)));
    }

    @Test
    void testSubtract() {
        Assertions.assertEquals(new Vector2D(0, 0), new Vector2D(10, 10).subtract(10, 10));
        Assertions.assertEquals(new Vector2D(0, 0), new Vector2D(10, 10).subtract(new Vector2D(10, 10)));
        Assertions.assertEquals(new Vector2D(6, -13), new Vector2D(10, 10).subtract(4, 23));
        Assertions.assertEquals(new Vector2D(6, -13), new Vector2D(10, 10).subtract(new Vector2D(4, 23)));
    }

    @Test
    void testMultiply() {
        Assertions.assertEquals(new Vector2D(8, 6), new Vector2D(4, 3).multiply(2, 2));
        Assertions.assertEquals(new Vector2D(8, 6), new Vector2D(4, 3).multiply(new Vector2D(2, 2)));
        Assertions.assertEquals(new Vector2D(8, 6), new Vector2D(4, 3).multiply(2));
        Assertions.assertEquals(new Vector2D(15, 49), new Vector2D(5, 7).multiply(3, 7));
        Assertions.assertEquals(new Vector2D(15, 49), new Vector2D(5, 7).multiply(new Vector2D(3, 7)));
    }

    @Test
    void testDivide() {
        Assertions.assertEquals(new Vector2D(10, 20), new Vector2D(40, 60).divide(4, 3));
        Assertions.assertEquals(new Vector2D(10, 20), new Vector2D(40, 60).divide(new Vector2D(4, 3)));
        Assertions.assertEquals(new Vector2D(10, 20), new Vector2D(40, 80).divide(4));
        Assertions.assertEquals(new Vector2D(45, 30), new Vector2D(90, 60).divide(2, 2));
        Assertions.assertEquals(new Vector2D(45, 30), new Vector2D(90, 60).divide(2, 2));
        Assertions.assertEquals(new Vector2D(45, 30), new Vector2D(90, 60).divide(2));
    }

    @Test
    void testMidpoint() {
        Assertions.assertEquals(new Vector2D(5, 0), Vector2D.ZERO.midpoint(10, 0));
        Assertions.assertEquals(new Vector2D(5, 0), Vector2D.ZERO.midpoint(new Vector2D(10, 0)));
        Assertions.assertEquals(new Vector2D(7, 7), Vector2D.ZERO.midpoint(14, 14));
        Assertions.assertEquals(new Vector2D(7, 7), Vector2D.ZERO.midpoint(new Vector2D(14, 14)));
    }

    @Test
    void testLength() {
        Assertions.assertEquals(22.360679774997898, new Vector2D(10, 20).length());
        Assertions.assertEquals(8.602325267042627, new Vector2D(5, 7).length());
        Assertions.assertEquals(99.64436762808022, new Vector2D(52, 85).length());
    }
}
