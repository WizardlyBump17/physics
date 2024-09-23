package com.wizardlybump17.physics.graphics.two.frame;

import com.wizardlybump17.physics.graphics.two.panel.ShapesPanel;
import lombok.Getter;
import lombok.NonNull;

import javax.swing.*;

@Getter
public class MainFrame extends JFrame {

    private final @NonNull ShapesPanel shapesPanel;

    public MainFrame(@NonNull String title) {
        super(title);
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(shapesPanel = new ShapesPanel());
    }
}
