package com.wizardlybump17.physics.graphics.two;

import com.wizardlybump17.physics.graphics.two.frame.MainFrame;
import com.wizardlybump17.physics.graphics.two.panel.shape.ShapesPanel;
import com.wizardlybump17.physics.graphics.two.renderer.shape.RectangleRenderer;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Rectangle;

public class Main {

    public static void main(String[] args) {
        MainFrame frame = new MainFrame("2D test");
        frame.setVisible(true);

        ShapesPanel shapesPanel = frame.getShapesPanel();
        shapesPanel.addShape(new Rectangle(Vector2D.ZERO, new Vector2D(100, 100)), new RectangleRenderer());
        shapesPanel.addShape(new Rectangle(Vector2D.ZERO, new Vector2D(100, 100)), new RectangleRenderer());
        shapesPanel.addShape(new Rectangle(Vector2D.ZERO, new Vector2D(100, 100)), new RectangleRenderer());
    }
}
