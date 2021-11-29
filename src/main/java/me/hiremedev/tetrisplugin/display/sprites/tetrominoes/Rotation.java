package me.hiremedev.tetrisplugin.display.sprites.tetrominoes;

import me.hiremedev.tetrisplugin.display.sprites.RotationDirection;

public enum Rotation {
    ROT_0,
    ROT_90,
    ROT_180,
    ROT_270;

    public static Rotation getRotation(Rotation rotation, RotationDirection direction){
        return  switch (rotation){
            case ROT_0 -> (direction == RotationDirection.CLOCKWISE) ? Rotation.ROT_90 : Rotation.ROT_270;
            case ROT_90 -> (direction == RotationDirection.CLOCKWISE) ? Rotation.ROT_180 : Rotation.ROT_0;
            case ROT_180 -> (direction == RotationDirection.CLOCKWISE) ? Rotation.ROT_270 : Rotation.ROT_90;
            case ROT_270 -> (direction == RotationDirection.CLOCKWISE) ? Rotation.ROT_0 : Rotation.ROT_180;
        };
    }
}
