package com.wizardlybump17.physics.graphics.two.renderer.shape;

import com.wizardlybump17.physics.graphics.two.panel.shape.PanelShape;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class CircleRenderer implements ShapeRenderer<Circle> {

    public static final double HIGHLIGHT_SIZE = 6;
    public static final @NonNull Color NOT_INTERSECTING = Color.BLUE;
    public static final @NonNull Color INTERSECTING = new Color(0, 100, 150);

    private @NonNull PanelShape panelShape;

    @Override
    public void render(@NonNull Graphics graphics, @NonNull Circle value) {
        Vector2D position = value.getPosition();
        double radius = value.getRadius();
        double x = position.x() - radius;
        double y = position.y() - radius;
        double diameter = radius * 2;

        if (panelShape.isSelected()) {
            graphics.setColor(Color.BLACK);
            graphics.fillOval((int) (x - HIGHLIGHT_SIZE / 2), (int) (y - HIGHLIGHT_SIZE / 2), (int) (diameter + HIGHLIGHT_SIZE), (int) (diameter + HIGHLIGHT_SIZE));
        }

        graphics.setColor(panelShape.getIntersecting().isEmpty() ? NOT_INTERSECTING : INTERSECTING);
        graphics.fillOval((int) x, (int) y, (int) diameter, (int) diameter);
    }
}
