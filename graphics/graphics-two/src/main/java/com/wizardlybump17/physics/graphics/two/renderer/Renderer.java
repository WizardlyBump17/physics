package com.wizardlybump17.physics.graphics.two.renderer;

import lombok.NonNull;

import java.awt.*;

public interface Renderer<T> {

    void render(@NonNull Graphics graphics, @NonNull T value);
}
