package me.hiremedev.tetrisplugin.database;

import me.hiremedev.tetrisplugin.display.screens.ScreenStyle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDoc {
    private UUID uuid;
    private int credits;
    private HashMap<ScreenStyle, Boolean> styles;

    public PlayerDoc(UUID uuid, int credits, HashMap<ScreenStyle, Boolean> styles) {
        this.uuid = uuid;
        this.credits = credits;
        this.styles = styles;
    }

    public int getCredits(){
        return credits;
    }

    public UUID getUuid() {
        return uuid;
    }

    public HashMap<ScreenStyle, Boolean> getStyles() {
        return styles;
    }
}
