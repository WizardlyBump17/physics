package com.wizardlybump17.physics.graphics.two.game.ball;

import com.wizardlybump17.physics.graphics.two.game.ball.ball.Ball;
import com.wizardlybump17.physics.two.container.BasicBaseObjectContainer;
import com.wizardlybump17.physics.two.object.BaseObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BallContainer extends BasicBaseObjectContainer {

    private @NotNull Ball ball;

    public BallContainer(@NotNull UUID id) {
        super(id);
        init();
    }

    protected void init() {
        addObject(ball = new Ball(this, 0, new Circle(new Vector2D(250, 250), 12.5)));
        addObject(new BaseObject(this, 1, new Circle(new Vector2D(250, 0), 50)));
    }

    public @NotNull Ball getBall() {
        return ball;
    }

    public void setBall(@NotNull Ball ball) {
        this.ball = ball;
    }
}
