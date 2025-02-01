package com.wizardlybump17.physics.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * <p>
 * A method annotated with this annotation means that method plays a role in the setup process of the object.
 * Generally if this annotation is present, there is a constructor annotated with the {@link NeedsSetup} annotation.
 * </p>
 *
 * @see NeedsSetup
 */
@Target(ElementType.METHOD)
public @interface SetupMethod {
}
