package com.wizardlybump17.physics.graphics.two.listener.panel.shape;

import com.wizardlybump17.physics.graphics.two.panel.object.ObjectsPanel;
import com.wizardlybump17.physics.graphics.two.panel.object.PanelObject;
import com.wizardlybump17.physics.two.intersection.Intersection;
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

    private final @NonNull ObjectsPanel panel;
    private final boolean canIntersect;

    @Override
    public void mouseDragged(@NonNull MouseEvent event) {
        PanelObject panelObject = panel.getSelectedShape();
        if (panelObject == null)
            return;

        Vector2D point = new Vector2D(event.getX(), event.getY());
        Shape shape = panelObject.getShape();

        Intersection intersection = panel.getIntersection(shape.at(point), panelObject.getId());
        if (!canIntersect && intersection.intersects())
            point = intersection.getSafePosition();

        panelObject.setShape(shape.at(point));

        panel.repaint();
    }
}
