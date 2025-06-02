package com.wizardlybump17.physics.three.registry;

import com.wizardlybump17.physics.registry.MapRegistry;
import com.wizardlybump17.physics.three.container.ShapesGroupContainer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BaseObjectContainerRegistry extends MapRegistry<UUID, ShapesGroupContainer> {

    public BaseObjectContainerRegistry() {
        super(UUID.class, ShapesGroupContainer.class);
    }

    @Override
    public @NotNull UUID extractKey(@NotNull ShapesGroupContainer value) {
        return value.getId();
    }
}
