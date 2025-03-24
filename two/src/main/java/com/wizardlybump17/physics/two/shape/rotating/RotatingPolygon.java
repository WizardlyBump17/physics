package com.wizardlybump17.physics.two.shape.rotating;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RotatingPolygon extends Shape {

    private final @NotNull Vector2D center;
    private final @NotNull List<Vector2D> points;
    private final double rotation;
    private final @NotNull List<Vector2D> rotatedPoints;

    public RotatingPolygon(@NotNull Vector2D center, @NotNull List<Vector2D> points, double rotation) {
        this.center = center;
        this.points = points;
        this.rotation = rotation;
        this.rotatedPoints = points.stream()
                .map(point -> point.rotate(rotation).add(center))
                .toList();
    }

    @Override
    public @NotNull Vector2D getPosition() {
        return center;
    }

    @Override
    public double getArea() {
        return 0;
    }

    @Override
    public double getPerimeter() {
        return 0;
    }

    @Override
    public boolean intersects(@NotNull Shape other) {
        return false;
    }

    @Override
    public boolean hasPoint(@NotNull Vector2D point) {
        return hasPoint(point.x(), point.y());
    }

    @Override
    public boolean hasPoint(double x, double y) {
//        return true;
        return RotatingPolygon.overlaps(this, new RotatingPolygon(new Vector2D(x, y), List.of(new Vector2D(-0.1, -0.1), new Vector2D(0.1, 0.1)), 0));
    }

    @Override
    public @NotNull Shape at(@NotNull Vector2D position) {
        Vector2D subtract = center.subtract(position);
        return new RotatingPolygon(
                position,
                points.stream()
//                        .map(point -> point.subtract(subtract))
                        .toList(),
                rotation
        );
    }

    @Override
    public @NotNull Intersection intersect(@NotNull Shape other) {
        return Intersection.EMPTY;
    }

    @Override
    public @NotNull Vector2D getClosestPoint(@NotNull Vector2D origin) {
        return origin;
    }

    @Override
    public @NotNull Vector2D getClosestPoint(@NotNull Shape shape) {
        return center;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }

    public @NotNull List<Vector2D> getPoints() {
        return points;
    }

    public @NotNull List<Vector2D> getRotatedPoints() {
        return rotatedPoints;
    }

    public @NotNull RotatingPolygon addRotation(double toAdd) {
        System.out.println(rotation + toAdd);
        return new RotatingPolygon(
                center,
                points,
                rotation + toAdd
        );
    }

    public static boolean overlaps(@NotNull RotatingPolygon poly1, @NotNull RotatingPolygon poly2) {
        RotatingPolygon temp1 = poly1;
        RotatingPolygon temp2 = poly2;

        for (int i = 0; i < 2; i++) {
            if (i == 1) {
                temp1 = poly2;
                temp2 = poly1;
            }

            for (int a = 0; a < temp1.rotatedPoints.size(); a++) {
                int b = (a + 1) % temp1.rotatedPoints.size();
                Vector2D projection = new Vector2D(
                        -(temp1.rotatedPoints.get(b).y() - temp1.rotatedPoints.get(a).y()),
                        temp1.rotatedPoints.get(b).x() - temp1.rotatedPoints.get(a).x()
                );

                double minR1 = Double.POSITIVE_INFINITY;
                double maxR1 = Double.NEGATIVE_INFINITY;
                for (int p = 0; p < temp1.rotatedPoints.size(); p++) {
                    double q = temp1.rotatedPoints.get(p).dot(projection);
                    minR1 = Math.min(minR1, q);
                    maxR1 = Math.max(maxR1, q);
                }

                double minR2 = Double.POSITIVE_INFINITY;
                double maxR2 = Double.NEGATIVE_INFINITY;
                for (int p = 0; p < temp2.rotatedPoints.size(); p++) {
                    double q = temp2.rotatedPoints.get(p).dot(projection);

                    minR2 = Math.min(minR2, q);
                    maxR2 = Math.max(maxR2, q);
                }

                boolean b1 = maxR2 >= minR1;
                boolean b2 = maxR1 >= minR2;
                if (!(b1 && b2))
                    return false;
            }
        }

        return true;
    }

    public static RotatingPolygon resolveOverlap(@NotNull RotatingPolygon poly1, @NotNull RotatingPolygon poly2) {
        RotatingPolygon temp1 = poly1;
        RotatingPolygon temp2 = poly2;

        double overlap = Double.POSITIVE_INFINITY;

        for (int i = 0; i < 2; i++) {
            if (i == 1) {
                temp1 = poly2;
                temp2 = poly1;
            }

            for (int a = 0; a < temp1.rotatedPoints.size(); a++) {
                int b = (a + 1) % temp1.rotatedPoints.size();
                Vector2D projection = new Vector2D(
                        -(temp1.rotatedPoints.get(b).y() - temp1.rotatedPoints.get(a).y()),
                        temp1.rotatedPoints.get(b).x() - temp1.rotatedPoints.get(a).x()
                );

                double minR1 = Double.POSITIVE_INFINITY;
                double maxR1 = Double.NEGATIVE_INFINITY;
                for (int p = 0; p < temp1.rotatedPoints.size(); p++) {
                    double q = temp1.rotatedPoints.get(p).dot(projection);
                    minR1 = Math.min(minR1, q);
                    maxR1 = Math.max(maxR1, q);
                }

                double minR2 = Double.POSITIVE_INFINITY;
                double maxR2 = Double.NEGATIVE_INFINITY;
                for (int p = 0; p < temp2.rotatedPoints.size(); p++) {
                    double q = temp2.rotatedPoints.get(p).dot(projection);

                    minR2 = Math.min(minR2, q);
                    maxR2 = Math.max(maxR2, q);
                }

                overlap = Math.min(Math.min(maxR1, maxR2) - Math.max(minR1, minR2), overlap);

                if (!(maxR2 >= minR1 && maxR1 >= minR2))
                    return null;
            }
        }

        Vector2D d = new Vector2D(poly2.center.x() - poly1.center.x(), poly2.center.y() - poly1.center.y());
        double s = d.length();
        return new RotatingPolygon(
                poly1.center.subtract(overlap * d.x() / s, overlap * d.y() / s),
                poly1.points,
                poly1.rotation
        );
    }
}
