package com.wizardlybump17.physics.graphics.two.renderer.shape;

import com.wizardlybump17.physics.graphics.two.panel.object.PanelObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Rectangle;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class RectangleRenderer implements ShapeRenderer<Rectangle> {

    public static final int HIGHLIGHT_SIZE = 3;

    private @NonNull Color color;
    private @NonNull Color highlightColor;
    private @NonNull PanelObject panelObject;
    private @NonNull Color intersectingColor;

    public RectangleRenderer() {
        color = Color.RED;
        highlightColor = Color.BLACK;
        intersectingColor = Color.ORANGE;
    }

    @Override
    public void render(@NonNull Graphics graphics, @NonNull Rectangle value) {
        Vector2D min = value.getMin();

        int x = (int) min.x();
        int y = (int) min.y();
        int width = (int) value.getWidth();
        int height = (int) value.getHeight();

        if (panelObject.isSelected()) {
            graphics.setColor(highlightColor);
            graphics.fillRect(x - HIGHLIGHT_SIZE, y - HIGHLIGHT_SIZE, width + HIGHLIGHT_SIZE * 2, height + HIGHLIGHT_SIZE * 2);
        }

        graphics.setColor(panelObject.hasIntersections() ? intersectingColor : color);
        graphics.fillRect(x, y, width, height);
    }
}
