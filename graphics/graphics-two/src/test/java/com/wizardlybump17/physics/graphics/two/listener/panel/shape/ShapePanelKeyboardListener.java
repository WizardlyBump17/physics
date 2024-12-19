package com.wizardlybump17.physics.graphics.two.listener.panel.shape;

import com.wizardlybump17.physics.graphics.two.panel.object.ObjectsPanel;
import com.wizardlybump17.physics.two.Engine;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        if (event.getKeyCode() != KeyEvent.VK_SPACE)
            return;
        Engine.getScheduler().schedule(panel::regenerate);
    }

    @Override
    public void keyTyped(@NotNull KeyEvent event) {
        if (event.getKeyCode() != KeyEvent.VK_SPACE)
            return;
        Engine.getScheduler().schedule(panel::regenerate);
    }
}
