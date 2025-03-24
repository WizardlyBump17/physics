package com.wizardlybump17.physics.graphics.two.renderer.shape;

import com.wizardlybump17.physics.graphics.two.panel.object.PanelObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.rotating.RotatingPolygon;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

public class RotatingPolygonRenderer implements ShapeRenderer<RotatingPolygon> {

    public static final int HIGHLIGHT_SIZE = 3;

    private static final @NotNull Color COLOR = Color.YELLOW;
    private static final @NotNull Color HIGHLIGHT_COLOR = Color.BLACK;
    private PanelObject panelObject;
    private static final @NotNull Color INTERSECTING_COLOR = new Color(128, 128, 0);

    @Override
    public void render(@NotNull Graphics graphics, @NotNull RotatingPolygon value) {
        List<Vector2D> points = value.getRotatedPoints();
        int[] xPoints = new int[points.size()];
        int[] yPoints = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            Vector2D point = points.get(i);
            xPoints[i] = (int) point.x();
            yPoints[i] = (int) point.y();
        }

        if (panelObject.isSelected()) {
            graphics.setColor(HIGHLIGHT_COLOR);
            graphics.fillPolygon(xPoints, yPoints, points.size());
        }

        graphics.setColor(panelObject.hasIntersections() ? INTERSECTING_COLOR : COLOR);
        graphics.fillPolygon(xPoints, yPoints, points.size());
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
