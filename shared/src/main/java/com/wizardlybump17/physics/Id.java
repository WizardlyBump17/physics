package com.wizardlybump17.physics;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record Id(@NotNull String namespace, @NotNull String key) {

    public static final @NotNull Id GENERIC = new Id("Generic", "Generic");

    @Override
    public @NotNull String toString() {
        return namespace + ":" + key;
    }

    public static @NotNull Id random() {
        return new Id(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }
}
