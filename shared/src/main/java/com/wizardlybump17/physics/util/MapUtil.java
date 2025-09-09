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
}
