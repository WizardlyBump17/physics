package com.wizardlybump17.physics.graphics.two.renderer.shape;

import com.wizardlybump17.physics.graphics.two.panel.object.PanelObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class RectangleRenderer implements ShapeRenderer<Rectangle> {

    public static final int HIGHLIGHT_SIZE = 3;

    private static final @NotNull Color COLOR = Color.RED;
    private static final @NotNull Color HIGHLIGHT_COLOR = Color.BLACK;
    private PanelObject panelObject;
    private static final @NotNull Color INTERSECTING_COLOR = Color.ORANGE;

    @Override
    public void render(@NotNull Graphics graphics, @NotNull Rectangle value) {
        Vector2D min = value.getMin();

        int x = (int) min.x();
        int y = (int) min.y();
        int width = (int) value.getWidth();
        int height = (int) value.getHeight();

        if (panelObject.isSelected()) {
            graphics.setColor(HIGHLIGHT_COLOR);
            graphics.fillRect(x - HIGHLIGHT_SIZE, y - HIGHLIGHT_SIZE, width + HIGHLIGHT_SIZE * 2, height + HIGHLIGHT_SIZE * 2);
        }

        graphics.setColor(panelObject.hasIntersections() ? INTERSECTING_COLOR : COLOR);
        graphics.fillRect(x, y, width, height);
    }

    @Override
    public @NotNull PanelObject getPanelObject() {
        return panelObject;
    }

    @Override
    public void setPanelObject(@NotNull PanelObject panelObject) {
        this.panelObject = panelObject;
    }
}
