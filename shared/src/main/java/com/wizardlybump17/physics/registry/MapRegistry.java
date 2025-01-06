package com.wizardlybump17.physics.registry;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

public abstract class MapRegistry<K, V> implements Registry<K, V> {

    private final @NotNull Map<K, V> map = createInitialMap();
    private final @NotNull Class<K> keyType;
    private final @NotNull Class<V> valueType;

    public MapRegistry(@NotNull Class<K> keyType, @NotNull Class<V> valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }

    protected @NotNull Map<K, V> createInitialMap() {
        return new HashMap<>();
    }

    @Override
    public void register(@NotNull K key, @NotNull V value) {
        map.put(key, value);
    }

    @Override
    public @NotNull Optional<V> get(@NotNull K key) {
        return Optional.ofNullable(map.get(key));
    }

    @Override
    public boolean hasKey(@NotNull K key) {
        return map.containsKey(key);
    }

    @Override
    public void unregisterKey(@NotNull K key) {
        map.remove(key);
    }

    @Override
    public @NotNull Set<K> getKeys() {
        return Set.copyOf(map.keySet());
    }

    @Override
    public @NotNull Collection<V> getValues() {
        return List.copyOf(map.values());
    }

    @Override
    public @NotNull Class<K> getKeyType() {
        return keyType;
    }

    @Override
    public @NotNull Class<V> getValueType() {
        return valueType;
    }

    @Override
    public void forEach(@NotNull BiConsumer<K, V> consumer) {
        map.forEach(consumer);
    }
}
