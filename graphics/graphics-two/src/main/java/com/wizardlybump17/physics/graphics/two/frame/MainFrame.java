package com.wizardlybump17.physics.graphics.two.frame;

import com.wizardlybump17.physics.graphics.two.panel.object.ObjectsPanel;
import com.wizardlybump17.physics.graphics.two.panel.object.PanelObject;
import com.wizardlybump17.physics.two.physics.Physics;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import lombok.Getter;
import lombok.NonNull;

import javax.swing.*;

@Getter
public class MainFrame extends JFrame {

    private final @NonNull ObjectsPanel objectsPanel;
    private long lastTick = System.currentTimeMillis();

    public MainFrame(@NonNull String title) {
        super(title);
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(objectsPanel = new ObjectsPanel());
    }

    public void tick() {
        for (PanelObject panelObject : objectsPanel.getShapes().values()) {
            PhysicsObject object = panelObject.getObject();

            Physics physics = object.getPhysics();
            if (panelObject.isSelected()) {
                physics.setAcceleration(Vector2D.ZERO);
                physics.setVelocity(Vector2D.ZERO);
            } else
                physics.setAcceleration(new Vector2D(0, 0.1));

            object.tick((System.currentTimeMillis() - lastTick) / 1000.0);
        }

        repaint();

        lastTick = System.currentTimeMillis();
    }
}
