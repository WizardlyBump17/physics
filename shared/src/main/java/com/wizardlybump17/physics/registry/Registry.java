package com.wizardlybump17.physics.registry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

public interface Registry<K, V> {

    void register(@NotNull K key, @NotNull V value);

    default void register(@NotNull V value) {
        register(extractKey(value), value);
    }

    @NotNull Optional<V> get(@NotNull K key);

    boolean hasKey(@NotNull K key);

    default boolean hasValue(@NotNull V value) {
        return hasKey(extractKey(value));
    }

    void unregisterKey(@NotNull K key);

    default void unregisterValue(@NotNull V value) {
        unregisterKey(extractKey(value));
    }

    @Unmodifiable
    @NotNull Set<K> getKeys();

    @Unmodifiable
    @NotNull Collection<V> getValues();

    @NotNull Class<K> getKeyType();

    @NotNull Class<V> getValueType();

    @NotNull K extractKey(@NotNull V value);

    void forEach(@NotNull BiConsumer<K, V> consumer);
}
