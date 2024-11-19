package com.wizardlybump17.physics.graphics.two.listener.panel.shape;

import com.wizardlybump17.physics.graphics.two.panel.object.ObjectsPanel;
import com.wizardlybump17.physics.graphics.two.panel.object.PanelObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShapePanelMouseMotionListener extends MouseAdapter {

    private final @NotNull ObjectsPanel panel;

    public ShapePanelMouseMotionListener(@NotNull ObjectsPanel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseDragged(@NotNull MouseEvent event) {
        PanelObject panelObject = panel.getSelectedShape();
        if (panelObject != null)
            panelObject.getObject().teleport(new Vector2D(event.getX(), event.getY()));
    }

    public @NotNull ObjectsPanel getPanel() {
        return panel;
    }
}
