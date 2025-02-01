package com.wizardlybump17.physics.graphics.two.game.ball;

import com.wizardlybump17.physics.two.container.BasicBaseObjectContainer;
import com.wizardlybump17.physics.two.object.BaseObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BallContainer extends BasicBaseObjectContainer {

    public BallContainer(@NotNull UUID id) {
        super(id);
        init();
    }

    protected void init() {
        addObject(new BaseObject(this, 0, new Circle(new Vector2D(250, 250), 50)));
        addObject(new BaseObject(this, 1, new Circle(new Vector2D(250, 0), 50)));
    }
}
