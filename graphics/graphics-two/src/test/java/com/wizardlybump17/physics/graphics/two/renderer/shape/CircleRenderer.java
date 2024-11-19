package com.wizardlybump17.physics.graphics.two.renderer.shape;

import com.wizardlybump17.physics.graphics.two.panel.object.PanelObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CircleRenderer implements ShapeRenderer<Circle> {

    public static final double HIGHLIGHT_SIZE = 6;
    public static final @NotNull Color NOT_INTERSECTING = Color.BLUE;
    public static final @NotNull Color INTERSECTING = new Color(0, 100, 150);

    private PanelObject panelObject;

    @Override
    public void render(@NotNull Graphics graphics, @NotNull Circle value) {
        Vector2D position = value.getPosition();
        double radius = value.getRadius();
        double x = position.x() - radius;
        double y = position.y() - radius;
        double diameter = radius * 2;

        if (panelObject.isSelected()) {
            graphics.setColor(Color.BLACK);
            graphics.fillOval((int) (x - HIGHLIGHT_SIZE / 2), (int) (y - HIGHLIGHT_SIZE / 2), (int) (diameter + HIGHLIGHT_SIZE), (int) (diameter + HIGHLIGHT_SIZE));
        }

        graphics.setColor(panelObject.hasIntersections() ? INTERSECTING : NOT_INTERSECTING);
        graphics.fillOval((int) x, (int) y, (int) diameter, (int) diameter);
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
