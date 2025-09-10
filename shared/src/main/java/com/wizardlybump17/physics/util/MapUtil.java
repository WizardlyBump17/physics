package com.wizardlybump17.physics.util;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public final class MapUtil {

    private MapUtil() {
    }

    public static <K, V> @NotNull Map<K, V> fromCollection(@NotNull Supplier<Map<K, V>> mapSupplier, @NotNull Collection<V> collection, @NotNull Function<V, K> keyExtractor) {
        Map<K, V> map = mapSupplier.get();
        for (V value : collection)
            map.put(keyExtractor.apply(value), value);
        return map;
    }

    public static <K, V> @NotNull Map<K, V> fromCollection(@NotNull Collection<V> collection, @NotNull Function<V, K> keyExtractor) {
        return fromCollection(HashMap::new, collection, keyExtractor);
    }

    public static <K, V> @NotNull Map<K, V> replaceValues(@NotNull Supplier<Map<K, V>> mapSupplier, @NotNull Map<K, V> map, @NotNull Function<V, V> modifier) {
        Map<K, V> newMap = mapSupplier.get();
        map.forEach((k, v) -> newMap.put(k, modifier.apply(v)));
        return newMap;
    }

    public static <K, V> @NotNull Map<K, V> replaceValues(@NotNull Map<K, V> map, @NotNull Function<V, V> modifier) {
        return replaceValues(HashMap::new, map, modifier);
    }

    public static <K, V> @NotNull Map<K, V> replaceValuesDirect(@NotNull Map<K, V> map, @NotNull Function<V, V> modifier) {
        map.replaceAll((k, v) -> modifier.apply(v));
        return map;
    }
}
