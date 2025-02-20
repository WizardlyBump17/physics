package com.wizardlybump17.physics.three;

import com.wizardlybump17.physics.three.registry.BaseObjectContainerRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Engine {

    private static BaseObjectContainerRegistry objectContainerRegistry;

    private Engine() {
    }

    public static BaseObjectContainerRegistry getObjectContainerRegistry() {
        return objectContainerRegistry;
    }

    public static void setObjectContainerRegistry(@NotNull BaseObjectContainerRegistry objectContainerRegistry) {
        assertNotSet(Engine.objectContainerRegistry, "object container registry");
        Engine.objectContainerRegistry = objectContainerRegistry;
    }

    private static void assertNotSet(@Nullable Object object, @NotNull String what) {
        if (object != null)
            throw new IllegalStateException("The " + what + " is already set.");
    }
}
