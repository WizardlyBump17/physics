package com.wizardlybump17.physics.registry;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface Registry<K, V> {

    void register(@NotNull K key, @NotNull V value);

    void register(@NotNull V value);

    @NotNull Optional<V> get(@NotNull K key);

    boolean hasKey(@NotNull K key);

    boolean hasValue(@NotNull V value);

    void unregisterKey(@NotNull K key);

    void unregisterValue(@NotNull V value);

    @NotNull Set<K> getKeys();

    @NotNull Collection<V> getValues();

    @NotNull Class<K> getKeyType();

    @NotNull Class<V> getValueType();
}
