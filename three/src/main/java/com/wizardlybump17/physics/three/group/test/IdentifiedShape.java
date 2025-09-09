package com.wizardlybump17.physics.three.group.test;

import com.wizardlybump17.physics.Id;
import com.wizardlybump17.physics.three.shape.Shape;
import org.jetbrains.annotations.NotNull;

public record IdentifiedShape(@NotNull Id id, @NotNull Shape shape) {
}
