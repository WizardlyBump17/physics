package com.wizardlybump17.physics.graphics.two.renderer;

import lombok.NonNull;

public interface Renderer<T> {

    void render(@NonNull T value);
}
