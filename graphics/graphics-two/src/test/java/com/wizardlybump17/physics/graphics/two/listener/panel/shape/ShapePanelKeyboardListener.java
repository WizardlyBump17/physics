package com.wizardlybump17.physics.graphics.two.listener.panel.shape;

import com.wizardlybump17.physics.graphics.two.panel.object.ObjectsPanel;
import com.wizardlybump17.physics.two.Engine;
import com.wizardlybump17.physics.two.object.BaseObject;
import com.wizardlybump17.physics.two.shape.rotating.RotatingPolygon;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ShapePanelKeyboardListener extends KeyAdapter {

    private final @NotNull ObjectsPanel panel;

    public ShapePanelKeyboardListener(@NotNull ObjectsPanel panel) {
        this.panel = panel;
    }

    public @NotNull ObjectsPanel getPanel() {
        return panel;
    }

    @Override
    public void keyPressed(@NotNull KeyEvent event) {
        handleKey(event.getKeyCode());
    }

    @Override
    public void keyTyped(@NotNull KeyEvent event) {
        handleKey(event.getKeyCode());
    }

    public void handleKey(int key) {
        List<BaseObject> rotatingPolygons = panel.getRotatingPolygons();
        BaseObject rotatingPolygon1 = rotatingPolygons.get(0);
        BaseObject rotatingPolygon2 = rotatingPolygons.get(1 % rotatingPolygons.size());

        switch (key) {
            case KeyEvent.VK_SPACE -> Engine.getScheduler().schedule(panel::regenerate);

            case KeyEvent.VK_A -> Engine.getScheduler().schedule(() -> rotatingPolygon1.setShape(((RotatingPolygon) rotatingPolygon1.getShape()).addRotation(-3)));
            case KeyEvent.VK_D -> Engine.getScheduler().schedule(() -> rotatingPolygon1.setShape(((RotatingPolygon) rotatingPolygon1.getShape()).addRotation(3)));

            case KeyEvent.VK_LEFT -> Engine.getScheduler().schedule(() -> rotatingPolygon2.setShape(((RotatingPolygon) rotatingPolygon2.getShape()).addRotation(-3)));
            case KeyEvent.VK_RIGHT -> Engine.getScheduler().schedule(() -> rotatingPolygon2.setShape(((RotatingPolygon) rotatingPolygon2.getShape()).addRotation(3)));
        }
    }
}
