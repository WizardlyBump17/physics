package com.wizardlybump17.physics.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

public final class AtomicUtil {

    /**
     * <p>
     * Sets the value of the {@link AtomicReference} to be the given new value.
     * It will keep trying to set the value while the {@link AtomicReference#get()} differs from the actual value held by the {@link AtomicReference}.
     * </p>
     * <p>
     * This method will return the previous value held by the {@link AtomicReference}.
     * </p>
     *
     * @param reference the {@link AtomicReference} to change
     * @param newValue  the new value to be set
     * @param <T>       the type of the value
     * @return the old value held by the {@link AtomicReference}
     */
    public static <T> T set(@NotNull AtomicReference<T> reference, @Nullable T newValue) {
        T value = reference.get();
        T oldValue = null;
        while (!reference.compareAndSet(value, newValue)) {
            oldValue = value;
            value = reference.get();
        }
        return oldValue == null ? value : oldValue;
    }
}
