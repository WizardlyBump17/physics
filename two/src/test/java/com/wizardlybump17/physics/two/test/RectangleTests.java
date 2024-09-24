package com.wizardlybump17.physics.two.test;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.intersection.rectangle.RectangleToRectangleIntersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Rectangle;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RectangleTests {

    @Test
    void testIntersects() {
        Assertions.assertTrue(new Rectangle(Vector2D.ZERO, new Vector2D(1, 1)).intersects(new Rectangle(new Vector2D(-1, -1), new Vector2D(1.0001, 1.0001))));
        Assertions.assertTrue(new Rectangle(Vector2D.ZERO, new Vector2D(2, 2)).intersects(new Rectangle(new Vector2D(-1, -1), new Vector2D(1.0001, 1.0001))));
        Assertions.assertTrue(new Rectangle(new Vector2D(1, 0), new Vector2D(2, 2)).intersects(new Rectangle(new Vector2D(-1, -1), new Vector2D(3, 3))));
        Assertions.assertTrue(new Rectangle(Vector2D.ZERO, new Vector2D(10, 10)).intersects(new Rectangle(new Vector2D(-4, 4), new Vector2D(16, 8))));
    }

    @Test
    void testDoesNotIntersects() {
        Assertions.assertFalse(new Rectangle(Vector2D.ZERO, new Vector2D(1, 1)).intersects(new Rectangle(new Vector2D(-1, -1), new Vector2D(-0.5, -0.5))));
        Assertions.assertFalse(new Rectangle(new Vector2D(1, 1), new Vector2D(2, 2)).intersects(new Rectangle(new Vector2D(-1, -1), Vector2D.ZERO)));
        Assertions.assertFalse(new Rectangle(new Vector2D(5, 5), new Vector2D(10, 10)).intersects(new Rectangle(Vector2D.ZERO, new Vector2D(1, -10))));
    }

    @Test
    void testHasPoints() {
        Assertions.assertTrue(new Rectangle(Vector2D.ZERO, new Vector2D(1, 1)).hasPoint(Vector2D.ZERO));
        Assertions.assertTrue(new Rectangle(new Vector2D(1, 1), new Vector2D(10, 10)).hasPoint(new Vector2D(2, 4)));
        Assertions.assertTrue(new Rectangle(new Vector2D(-6, -9), new Vector2D(5, 9)).hasPoint(Vector2D.ZERO));
    }

    @Test
    void testDoesNotHavePoints() {
        Assertions.assertFalse(new Rectangle(Vector2D.ZERO, new Vector2D(1, 1)).hasPoint(new Vector2D(-1, -1)));
        Assertions.assertFalse(new Rectangle(new Vector2D(1, 1), new Vector2D(10, 10)).hasPoint(new Vector2D(11, 11)));
        Assertions.assertFalse(new Rectangle(new Vector2D(-6, -9), new Vector2D(5, 9)).hasPoint(new Vector2D(5.1, 9)));
    }

    @Test
    void testIntersections() {
        Assertions.assertEquals(
                new RectangleToRectangleIntersection(
                        create(Vector2D.ZERO, new Vector2D(10, 10)),
                        create(new Vector2D(5, 5), new Vector2D(15, 15)),
                        create(new Vector2D(5, 5), new Vector2D(10, 10))
                ),
                create(Vector2D.ZERO, new Vector2D(10, 10)).intersect(create(new Vector2D(5, 5), new Vector2D(15, 15)))
        );
        Assertions.assertEquals(
                new RectangleToRectangleIntersection(
                        create(Vector2D.ZERO, new Vector2D(10, 10)),
                        create(new Vector2D(5, 5), new Vector2D(7, 7)),
                        create(new Vector2D(5, 5), new Vector2D(7, 7))
                ),
                create(Vector2D.ZERO, new Vector2D(10, 10)).intersect(create(new Vector2D(5, 5), new Vector2D(7, 7)))
        );
        Assertions.assertEquals(
                new RectangleToRectangleIntersection(
                        create(new Vector2D(-10, 10), Vector2D.ZERO),
                        create(new Vector2D(5, 5), new Vector2D(-15, 15)),
                        create(new Vector2D(0, 5), new Vector2D(-10, 10))
                ),
                create(Vector2D.ZERO, new Vector2D(-10, 10)).intersect(create(new Vector2D(5, 5), new Vector2D(-15, 15)))
        );
        Assertions.assertEquals(
                new RectangleToRectangleIntersection(
                        create(new Vector2D(-10, 7), new Vector2D(0, 10)),
                        create(new Vector2D(5, 5), new Vector2D(-15, 15)),
                        create(new Vector2D(-10, 7), new Vector2D(0, 10))
                ),
                create(new Vector2D(-10, 7), new Vector2D(0, 10)).intersect(create(new Vector2D(5, 5), new Vector2D(-15, 15)))
        );
    }

    @Test
    void testNoIntersections() {
        Assertions.assertEquals(
                Intersection.EMPTY,
                create(Vector2D.ZERO, new Vector2D(10, 10)).intersect(create(new Vector2D(10, 10), new Vector2D(15, 15)))
        );
        Assertions.assertEquals(
                Intersection.EMPTY,
                create(new Vector2D(-10, -10), Vector2D.ZERO).intersect(create(new Vector2D(1, 54), new Vector2D(75, 41)))
        );
        Assertions.assertEquals(
                Intersection.EMPTY,
                create(new Vector2D(-9, -4), Vector2D.ZERO).intersect(create(new Vector2D(0, 0.1), new Vector2D(75, 41)))
        );
    }

    static @NonNull Rectangle create(@NonNull Vector2D min, @NonNull Vector2D max) {
        return new Rectangle(min, max);
    }
}