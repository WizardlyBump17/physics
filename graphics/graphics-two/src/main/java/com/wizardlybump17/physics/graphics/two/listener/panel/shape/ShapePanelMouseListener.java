package com.wizardlybump17.physics.graphics.two.listener.panel.shape;

import com.wizardlybump17.physics.graphics.two.panel.shape.PanelShape;
import com.wizardlybump17.physics.graphics.two.panel.shape.ShapesPanel;
import com.wizardlybump17.physics.two.position.Vector2D;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@RequiredArgsConstructor
@Getter
public class ShapePanelMouseListener extends MouseAdapter {

    private final @NonNull ShapesPanel panel;

    @Override
    public void mouseClicked(@NonNull MouseEvent event) {
        Vector2D point = new Vector2D(event.getX(), event.getY());

        boolean success = false;
        for (PanelShape shape : panel.getShapes().values()) {
            boolean selected = shape.getShape().hasPoint(point);
            shape.setSelected(selected);

            if (!selected)
                continue;

            PanelShape previous = panel.getSelectedShape();
            if (previous != null)
                previous.setSelected(false);

            panel.setSelectedShape(shape);
            success = true;
            break;
        }

        if (!success)
            panel.setSelectedShape(null);
        panel.repaint();
    }
}
