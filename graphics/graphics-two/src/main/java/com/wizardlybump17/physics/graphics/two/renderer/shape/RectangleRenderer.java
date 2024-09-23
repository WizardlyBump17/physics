package com.wizardlybump17.physics.graphics.two.renderer.shape;

import com.wizardlybump17.physics.graphics.two.panel.shape.PanelShape;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Rectangle;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class RectangleRenderer implements ShapeRenderer<Rectangle> {

    private @NonNull Color color;
    private @NonNull Color highlightColor;
    private @NonNull PanelShape panelShape;

    public RectangleRenderer(@NonNull Color color, @NonNull Color highlightColor) {
        this.color = color;
        this.highlightColor = highlightColor;
    }

    public RectangleRenderer() {
        color = Color.RED;
        highlightColor = Color.BLACK;
    }

    @Override
    public void render(@NonNull Graphics graphics, @NonNull Rectangle value) {
        Vector2D min = value.getMin();
        graphics.setColor(color);

        int x = (int) min.x();
        int y = (int) min.y();
        int width = (int) value.getWidth();
        int height = (int) value.getHeight();

        graphics.fillRect(x, y, width, height);

        if (!panelShape.isSelected())
            return;

        graphics.setColor(highlightColor);
        graphics.drawRect(x - 20, y - 20, width + 20, height + 20);
    }
}
