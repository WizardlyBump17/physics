package com.wizardlybump17.physics.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * <p>
 * A constructor annotated with this annotation means that the object being constructed needs further setup after calling
 * the constructor. This may happen when there is a crucial field that can not be null, but it is not assigned by the constructor
 * and needs to be set with a setter method right after the constructor call.
 * </p>
 * <p>
 * Generally if this annotation is present, there is a method annotated with the {@link SetupMethod} annotation.
 * </p>
 *
 * @see SetupMethod
 */
@Target(ElementType.CONSTRUCTOR)
public @interface NeedsSetup {
}
