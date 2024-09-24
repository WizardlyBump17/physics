package com.wizardlybump17.physics.graphics.two.listener.panel.shape;

import com.wizardlybump17.physics.graphics.two.panel.shape.PanelShape;
import com.wizardlybump17.physics.graphics.two.panel.shape.ShapesPanel;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Shape;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@RequiredArgsConstructor
@Getter
public class ShapePanelMouseMotionListener extends MouseAdapter {

    private final @NonNull ShapesPanel panel;
    private final boolean canIntersect;

    @Override
    public void mouseDragged(@NonNull MouseEvent event) {
        PanelShape panelShape = panel.getSelectedShape();
        if (panelShape == null)
            return;

        Vector2D point = new Vector2D(event.getX(), event.getY());
        Shape shape = panelShape.getShape();

        if (!canIntersect && panel.getIntersection(shape.at(point), panelShape.getId()).intersects())
            return;

        panelShape.setShape(shape.at(point));

        panel.repaint();
    }
}
