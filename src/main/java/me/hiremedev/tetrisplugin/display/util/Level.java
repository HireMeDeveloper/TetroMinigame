package me.hiremedev.tetrisplugin.display.util;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Level {
    _1(1, NamedTextColor.GREEN,  0, 13),
    _2(2, NamedTextColor.GREEN,  2, 10),
    _3(3, NamedTextColor.BLUE,  6, 8),
    _4(4, NamedTextColor.BLUE,  12, 7),
    _5(5, NamedTextColor.LIGHT_PURPLE,  24, 6),
    _6(6, NamedTextColor.LIGHT_PURPLE,  40, 5),
    _7(7, NamedTextColor.YELLOW,  56, 4),
    _8(8, NamedTextColor.YELLOW,  72, 3),
    _9(9, NamedTextColor.RED,  88, 2),
    _10(10, NamedTextColor.DARK_RED,  104, 1);



    private NamedTextColor color;
    private long period;
    private int lineRequirement;
    private int value;

    Level(int value, NamedTextColor color, int lineRequirement, long period) {
        this.color = color;
        this.period = period;
        this.lineRequirement = lineRequirement;
        this.value = value;
    }

    public static Level getLevelFromClears(int clears){
        var levels = Arrays.stream(values())
                .filter((level) -> clears > level.getLineRequirement())
                .collect(Collectors.toList());
        var level = (clears < 2)? Level._1 : levels.get(levels.size() - 1);
        Bukkit.getLogger().info("Got Level: %s from clears %s".formatted(level.getValue(), clears));
        return level;
    }

    public static Level getLevelFromValue(int value){
        var item = Arrays.stream(values())
                .filter((level) -> value == level.getValue())
                .collect(Collectors.toList());
        return item.get(0);
    }

    public NamedTextColor getColor() {
        return color;
    }

    public long getPeriod() {
        return period;
    }

    public int getLineRequirement() {
        return lineRequirement;
    }

    public int getValue() {
        return value;
    }
}
