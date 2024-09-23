package com.wizardlybump17.physics.graphics.two.renderer.shape;

import com.wizardlybump17.physics.graphics.two.panel.shape.PanelShape;
import com.wizardlybump17.physics.graphics.two.renderer.Renderer;
import com.wizardlybump17.physics.two.shape.Shape;
import lombok.NonNull;

public interface ShapeRenderer<S extends Shape> extends Renderer<S> {

    @NonNull PanelShape getPanelShape();

    void setPanelShape(@NonNull PanelShape panelShape);
}
