package com.wizardlybump17.physics.two.test;

import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Rectangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RectangleTests {

    @Test
    void testIntersects() {
        Assertions.assertTrue(new Rectangle(Vector2D.ZERO, new Vector2D(1, 1)).intersects(new Rectangle(new Vector2D(-1, -1), Vector2D.ZERO)));
        Assertions.assertTrue(new Rectangle(Vector2D.ZERO, new Vector2D(2, 2)).intersects(new Rectangle(new Vector2D(-1, -1), new Vector2D(1, 1))));
        Assertions.assertTrue(new Rectangle(new Vector2D(1, 0), new Vector2D(2, 2)).intersects(new Rectangle(new Vector2D(-1, -1), new Vector2D(3, 3))));
    }

    @Test
    void testDoesNotIntersects() {
        Assertions.assertFalse(new Rectangle(Vector2D.ZERO, new Vector2D(1, 1)).intersects(new Rectangle(new Vector2D(-1, -1), new Vector2D(-0.5, -0.5))));
        Assertions.assertFalse(new Rectangle(new Vector2D(1, 1), new Vector2D(2, 2)).intersects(new Rectangle(new Vector2D(-1, -1), Vector2D.ZERO)));
        Assertions.assertFalse(new Rectangle(new Vector2D(5, 5), new Vector2D(10, 10)).intersects(new Rectangle(Vector2D.ZERO, new Vector2D(1, -10))));
    }
}
