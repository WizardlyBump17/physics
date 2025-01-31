package com.wizardlybump17.physics.graphics.two.panel.object;

import com.wizardlybump17.physics.two.physics.Physics;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import org.jetbrains.annotations.NotNull;

public class FallingBallPhysics extends Physics {

    private final @NotNull PanelObject panelObject;

    public FallingBallPhysics(@NotNull PanelObject panelObject) {
        super((PhysicsObject) panelObject.getObject());
        this.panelObject = panelObject;
    }

    public @NotNull PanelObject getPanelObject() {
        return panelObject;
    }

    @Override
    public void tick() {
        super.tick();
        if (panelObject.isSelected()) {
            setAcceleration(Vector2D.ZERO);
            setVelocity(Vector2D.ZERO);
        } else
            setAcceleration(GRAVITY_VECTOR);
    }
}
