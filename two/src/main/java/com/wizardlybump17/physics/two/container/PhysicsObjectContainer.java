package com.wizardlybump17.physics.two.container;

import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.tick.Ticker;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PhysicsObjectContainer implements Ticker {

    private final @NotNull Map<Integer, PhysicsObject> objects = new HashMap<>();
    private long lastTick;

    @Override
    public void run() {
        for (PhysicsObject object : objects.values())
            object.tick((System.currentTimeMillis() - lastTick) / 1000.0);

        lastTick = System.currentTimeMillis();
    }

    public @NotNull Map<Integer, PhysicsObject> getObjects() {
        return Map.copyOf(objects);
    }

    public long getLastTick() {
        return lastTick;
    }

    public void addObject(@NotNull PhysicsObject object) {
        objects.put(object.getId(), object);
    }

    public void removeObject(int id) {
        objects.remove(id);
    }

    public boolean hasObject(int id) {
        return objects.containsKey(id);
    }
}
