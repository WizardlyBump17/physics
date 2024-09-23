package com.wizardlybump17.physics.graphics.two.listener.panel.shape;

import com.wizardlybump17.physics.graphics.two.panel.shape.PanelShape;
import com.wizardlybump17.physics.graphics.two.panel.shape.ShapesPanel;
import com.wizardlybump17.physics.graphics.two.renderer.shape.ShapeRenderer;
import com.wizardlybump17.physics.two.position.Vector2D;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class ShapePanelMouseMotionListener extends MouseAdapter {

    private final @NonNull ShapesPanel panel;

    @Override
    public void mouseDragged(@NonNull MouseEvent event) {
        Map<Integer, PanelShape> shapes = panel.getShapes();

        Vector2D point = new Vector2D(event.getX(), event.getY());

        Iterator<PanelShape> iterator = shapes.values().iterator();
        PanelShape selectedShape = null;
        while (iterator.hasNext()) {
            PanelShape shape = iterator.next();
            if (!shape.isSelected())
                continue;

            if (!shape.getShape().hasPoint(point))
                break;

            iterator.remove();
            selectedShape = shape;
            break;
        }

        if (selectedShape == null)
            return;

        ShapeRenderer<?> renderer = selectedShape.getRenderer();
        PanelShape newShape = new PanelShape(selectedShape.getShape().at(point), renderer);
        newShape.setSelected(true);
        renderer.setPanelShape(newShape);
        shapes.put(newShape.getId(), newShape);

        panel.repaint();
    }
}
