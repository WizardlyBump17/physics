package com.wizardlybump17.physics.two.container;

import com.wizardlybump17.physics.two.object.BaseObject;
import com.wizardlybump17.physics.two.tick.Ticker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BaseObjectContainer implements Ticker {

    private final @NotNull Map<Integer, BaseObject> objects = new HashMap<>();
    private long lastTick;

    @Override
    public void run() {
        for (BaseObject object : objects.values())
            object.tick((System.currentTimeMillis() - lastTick) / 1000.0);

        lastTick = System.currentTimeMillis();
    }

    public @NotNull Map<Integer, BaseObject> getObjects() {
        return Map.copyOf(objects);
    }

    public long getLastTick() {
        return lastTick;
    }

    public void addObject(@NotNull BaseObject object) {
        objects.put(object.getId(), object);
    }

    public void removeObject(int id) {
        objects.remove(id);
    }

    public boolean hasObject(int id) {
        return objects.containsKey(id);
    }

    public @Nullable BaseObject getObject(int id) {
        return objects.get(id);
    }
}
