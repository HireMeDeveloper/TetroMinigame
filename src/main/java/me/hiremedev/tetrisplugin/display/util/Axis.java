package me.hiremedev.tetrisplugin.display.util;

import org.bukkit.entity.Player;

public enum Axis {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static Axis getYaw(Player player) {
        float yaw = player.getLocation().getYaw();
        yaw = (yaw % 360 + 360) % 360;
        if (yaw > 135 || yaw < -135) {
            return Axis.NORTH;
        } else if (yaw < -45) {
            return Axis.EAST;
        } else if (yaw > 45) {
            return Axis.WEST;
        } else {
            return Axis.SOUTH;
        }
    }
}
