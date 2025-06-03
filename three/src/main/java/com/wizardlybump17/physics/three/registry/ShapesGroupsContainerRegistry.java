package com.wizardlybump17.physics.three.registry;

import com.wizardlybump17.physics.registry.MapRegistry;
import com.wizardlybump17.physics.three.container.ShapesGroupsContainer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ShapesGroupsContainerRegistry extends MapRegistry<UUID, ShapesGroupsContainer> {

    public ShapesGroupsContainerRegistry() {
        super(UUID.class, ShapesGroupsContainer.class);
    }

    @Override
    public @NotNull UUID extractKey(@NotNull ShapesGroupsContainer value) {
        return value.getId();
    }
}
