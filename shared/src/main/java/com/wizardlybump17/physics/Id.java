package com.wizardlybump17.physics;

import org.jetbrains.annotations.NotNull;

public record Id(@NotNull String namespace, @NotNull String key) {

    @Override
    public @NotNull String toString() {
        return namespace + ":" + key;
    }
}
