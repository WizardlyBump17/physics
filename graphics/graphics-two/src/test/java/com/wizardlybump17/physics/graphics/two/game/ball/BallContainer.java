package com.wizardlybump17.physics.graphics.two.game.ball;

import com.wizardlybump17.physics.graphics.two.game.ball.ball.Ball;
import com.wizardlybump17.physics.graphics.two.game.ball.platform.Platform;
import com.wizardlybump17.physics.two.container.BasicBaseObjectContainer;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BallContainer extends BasicBaseObjectContainer {

    private @NotNull Ball ball;
    private @NotNull Platform platform;

    public BallContainer(@NotNull UUID id) {
        super(id);
        init();
    }

    protected void init() {
        addObject(ball = new Ball(this, getObjectsCount(), new Circle(new Vector2D(250, 250), 12.5)));
        addObject(platform = new Platform(this, getObjectsCount(), new Rectangle(new Vector2D(250, 450), 40, 10)));
    }

    public @NotNull Ball getBall() {
        return ball;
    }

    public void setBall(@NotNull Ball ball) {
        this.ball = ball;
    }

    public @NotNull Platform getPlatform() {
        return platform;
    }

    public void setPlatform(@NotNull Platform platform) {
        this.platform = platform;
    }
}
