package me.hiremedev.tetrisplugin.database;


import me.hiremedev.tetrisplugin.display.screens.ScreenStyle;
import me.hiremedev.tetrisplugin.display.util.Axis;
import org.bukkit.Location;

public class SystemDocument {
    private String name;
    private Axis axis;
    private Location origin;
    private int HighScore;
    private String hsName;
    private ScreenStyle style;

    public SystemDocument(String name, Axis axis, Location origin, int highScore, String hsName, ScreenStyle style) {
        this.name = name;
        this.axis = axis;
        this.origin = origin;
        HighScore = highScore;
        this.hsName = hsName;
        this.style = style;
    }


    public String getName() {
        return name;
    }

    public Axis getAxis() {
        return axis;
    }

    public Location getOrigin() {
        return origin;
    }

    public int getHighScore() {
        return HighScore;
    }

    public String getHsName() {
        return hsName;
    }

    public ScreenStyle getStyle() {
        return style;
    }
}
