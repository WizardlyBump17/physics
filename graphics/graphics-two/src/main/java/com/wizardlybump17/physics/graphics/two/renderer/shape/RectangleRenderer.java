package com.wizardlybump17.physics.graphics.two.renderer.shape;

import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Rectangle;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class RectangleRenderer implements ShapeRenderer<Rectangle> {

    private @NonNull Color color;

    public RectangleRenderer(@NonNull Color color) {
        this.color = color;
    }

    public RectangleRenderer() {
        color = Color.RED;
    }

    @Override
    public void render(@NonNull Graphics graphics, @NonNull Rectangle value) {
        Vector2D min = value.getMin();
        graphics.setColor(color);
        graphics.fillRect((int) min.x(), (int) min.y(), (int) value.getWidth(), (int) value.getHeight());
    }
}
