package com.wizardlybump17.physics.graphics.two.listener.panel.shape;

import com.wizardlybump17.physics.graphics.two.panel.object.ObjectsPanel;
import com.wizardlybump17.physics.graphics.two.panel.object.PanelObject;
import com.wizardlybump17.physics.two.position.Vector2D;
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
        if (panelObject != null)
            panelObject.getObject().teleport(new Vector2D(event.getX(), event.getY()));
    }
}
