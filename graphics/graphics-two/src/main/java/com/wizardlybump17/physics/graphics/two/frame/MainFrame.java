package com.wizardlybump17.physics.graphics.two.frame;

import com.wizardlybump17.physics.graphics.two.panel.object.ObjectsPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MainFrame extends JFrame {

    private final @NotNull ObjectsPanel objectsPanel;

    public MainFrame(@NotNull String title) {
        super(title);
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(objectsPanel = new ObjectsPanel());
    }

    public @NotNull ObjectsPanel getObjectsPanel() {
        return objectsPanel;
    }
}
