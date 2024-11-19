package com.wizardlybump17.physics.graphics.two.renderer;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public interface Renderer<T> {

    void render(@NotNull Graphics graphics, @NotNull T value);
}
