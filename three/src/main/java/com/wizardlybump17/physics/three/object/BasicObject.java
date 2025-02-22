package com.wizardlybump17.physics.three.object;

import com.wizardlybump17.physics.three.container.BaseObjectContainer;
import com.wizardlybump17.physics.three.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class BasicObject extends BaseObject {

    public BasicObject(@NotNull Shape shape, @NotNull BaseObjectContainer container) {
        super(shape, container);
    }

    @Override
    public void tick() {
    }
}
