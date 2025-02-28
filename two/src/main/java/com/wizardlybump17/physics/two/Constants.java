package com.wizardlybump17.physics.two;

/**
 * <p>
 * This class holds some constants that do not fit well in other places.
 * For instance, the gravity related constants are in the {@link com.wizardlybump17.physics.two.physics.Physics} class because
 * they are appropriate there, but the {@link #TICKS_PER_SECOND} is here because there is no other place good for it.
 * </p>
 */
public final class Constants {

    public static final int TICKS_PER_SECOND = 128;
    public static final double MILLIS_PER_TICK = 1000.0 / TICKS_PER_SECOND;
    public static final double NANOS_PER_TICK = 1_000_000_000.0 / TICKS_PER_SECOND;

    private Constants() {
    }
}
