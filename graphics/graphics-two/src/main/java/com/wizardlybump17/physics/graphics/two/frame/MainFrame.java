package com.wizardlybump17.physics.graphics.two.frame;

import com.wizardlybump17.physics.graphics.two.panel.object.ObjectsPanel;
import com.wizardlybump17.physics.graphics.two.panel.object.PanelObject;
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
        for (PanelObject object : objectsPanel.getShapes().values())
            object.getObject().tick((System.currentTimeMillis() - lastTick) / 1000.0);
        repaint();
        lastTick = System.currentTimeMillis();
    }
}
