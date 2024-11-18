package com.wizardlybump17.physics.graphics.two.renderer.shape;

import com.wizardlybump17.physics.graphics.two.panel.object.PanelObject;
import com.wizardlybump17.physics.graphics.two.renderer.Renderer;
import com.wizardlybump17.physics.two.shape.Shape;
import lombok.NonNull;

public interface ShapeRenderer<S extends Shape> extends Renderer<S> {

    @NonNull
    PanelObject getPanelObject();

    void setPanelObject(@NonNull PanelObject panelObject);
}
