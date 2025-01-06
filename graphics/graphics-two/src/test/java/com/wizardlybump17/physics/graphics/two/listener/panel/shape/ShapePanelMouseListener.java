package com.wizardlybump17.physics.graphics.two.listener.panel.shape;

import com.wizardlybump17.physics.graphics.two.panel.object.ObjectsPanel;
import com.wizardlybump17.physics.graphics.two.panel.object.PanelObject;
import com.wizardlybump17.physics.two.Engine;
import com.wizardlybump17.physics.two.position.Vector2D;
import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShapePanelMouseListener extends MouseAdapter {

    private final @NotNull ObjectsPanel panel;

    public ShapePanelMouseListener(@NotNull ObjectsPanel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseClicked(@NotNull MouseEvent event) {
        Engine.getScheduler().schedule(() -> {
            Vector2D point = new Vector2D(event.getX(), event.getY());

            boolean success = false;
            for (PanelObject shape : panel.getShapes().values()) {
                boolean selected = shape.getShape().hasPoint(point);
                shape.setSelected(selected);

                if (!selected)
                    continue;

                PanelObject previous = panel.getSelectedShape();
                if (previous != null && previous.getId() != shape.getId())
                    previous.setSelected(false);

                panel.setSelectedShape(shape);
                success = true;
                break;
            }

            if (!success)
                panel.setSelectedShape(null);
        });
    }

    public @NotNull ObjectsPanel getPanel() {
        return panel;
    }
}
