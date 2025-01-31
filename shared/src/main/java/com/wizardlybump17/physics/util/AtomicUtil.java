package com.wizardlybump17.physics.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

public final class AtomicUtil {

    public static <T> void set(@NotNull AtomicReference<T> reference, @Nullable T newValue) {
        T value = reference.get();
        while (!reference.compareAndSet(value, newValue))
            value = reference.get();
    }
}
