package com.wizardlybump17.physics.graphics.two.listener.panel.shape;

import com.wizardlybump17.physics.graphics.two.panel.object.ObjectsPanel;
import com.wizardlybump17.physics.graphics.two.panel.object.PanelObject;
import com.wizardlybump17.physics.two.Constants;
import com.wizardlybump17.physics.two.Engine;
import com.wizardlybump17.physics.two.object.BaseObject;
import com.wizardlybump17.physics.two.physics.Physics;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
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
        Engine.getScheduler().schedule(() -> {
            PanelObject panelObject = panel.getSelectedShape();
            if (panelObject == null)
                return;

            BaseObject object = panelObject.getObject();
            Vector2D position = object.getPosition();
            Vector2D target = new Vector2D(event.getX(), event.getY());
            if (object instanceof PhysicsObject physicsObject) {
                Physics physics = physicsObject.getPhysics();
                physics.setAcceleration(Vector2D.ZERO);
                physics.setVelocity(target.subtract(position).multiply(Constants.TICKS_PER_SECOND));
                Engine.getScheduler().schedule(() -> {
                    physics.setAcceleration(Vector2D.ZERO);
                    physics.setVelocity(Vector2D.ZERO);
                }, 1);
            } else
                object.teleport(target);
        });
    }

    public @NotNull ObjectsPanel getPanel() {
        return panel;
    }
}
