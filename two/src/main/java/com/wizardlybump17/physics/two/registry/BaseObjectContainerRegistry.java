package com.wizardlybump17.physics.two.registry;

import com.wizardlybump17.physics.registry.MapRegistry;
import com.wizardlybump17.physics.two.container.BaseObjectContainer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BaseObjectContainerRegistry extends MapRegistry<UUID, BaseObjectContainer> {

    public BaseObjectContainerRegistry() {
        super(UUID.class, BaseObjectContainer.class);
    }

    @Override
    public @NotNull UUID extractKey(@NotNull BaseObjectContainer value) {
        return value.getId();
    }
}
