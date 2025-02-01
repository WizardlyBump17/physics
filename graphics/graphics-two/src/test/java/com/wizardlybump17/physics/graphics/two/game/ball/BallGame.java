package com.wizardlybump17.physics.graphics.two.game.ball;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.UUID;

public class BallGame {

    private final @NotNull BallContainer objectContainer = new BallContainer(UUID.nameUUIDFromBytes("BallContainer".getBytes()));
    private final @NotNull JFrame mainFrame = new JFrame("Ball game");
    private final @NotNull BallGamePanel mainPanel = new BallGamePanel(objectContainer);

    public BallGame() {
        init();
    }

    protected void init() {
        initMainFrame();
    }

    protected void initMainFrame() {
        mainFrame.setSize(500, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);
    }

    public @NotNull BallContainer getObjectContainer() {
        return objectContainer;
    }

    public @NotNull JFrame getMainFrame() {
        return mainFrame;
    }

    public @NotNull BallGamePanel getMainPanel() {
        return mainPanel;
    }
}
