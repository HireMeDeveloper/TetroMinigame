package me.hiremedev.tetrisplugin.display.util;

import org.bukkit.Location;

import java.awt.geom.Point2D;

public class PointMath {

    public static Point2D pixelAdd(Point2D point1, Point2D point2){
        return new Point2D.Double(point1.getX() + point2.getX(), point1.getY() + point2.getY());
    }
    public static Point2D pixelSubtract(Point2D point1, Point2D point2){
        return new Point2D.Double(point1.getX() - point2.getX(), point1.getY() - point2.getY());
    }

    public static Location locationFromPoint(Location origin, Axis axis,  Point2D point, int offset ){
        return switch (axis){
            case NORTH -> new Location(
                    origin.getWorld(),
                    origin.getX() + point.getX(),
                    origin.getY() + point.getY(),
                    origin.getZ() + offset
            );
            case SOUTH -> new Location(
                    origin.getWorld(),
                    origin.getX() - point.getX(),
                    origin.getY() + point.getY(),
                    origin.getZ() - offset
            );
            case EAST -> new Location(
                    origin.getWorld(),
                    origin.getX() - offset,
                    origin.getY() + point.getY(),
                    origin.getZ() + point.getX()
            );
            case WEST -> new Location(
                    origin.getWorld(),
                    origin.getX() + offset,
                    origin.getY() + point.getY(),
                    origin.getZ() - point.getX()
            );
        };
    }
}
