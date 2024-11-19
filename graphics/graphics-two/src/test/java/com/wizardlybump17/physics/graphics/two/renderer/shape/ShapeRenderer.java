package com.wizardlybump17.physics.graphics.two.renderer.shape;

import com.wizardlybump17.physics.graphics.two.panel.object.PanelObject;
import com.wizardlybump17.physics.graphics.two.renderer.Renderer;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;

public interface ShapeRenderer<S extends Shape> extends Renderer<S> {

    @NotNull PanelObject getPanelObject();

    void setPanelObject(@NotNull PanelObject panelObject);
}
