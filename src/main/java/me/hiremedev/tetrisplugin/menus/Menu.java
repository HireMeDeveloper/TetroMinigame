package me.hiremedev.tetrisplugin.menus;

import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Menu {
    private JavaPlugin plugin;
    private int rows;
    private Component title;

    private HashMap<Integer, MenuItem> items;
}
