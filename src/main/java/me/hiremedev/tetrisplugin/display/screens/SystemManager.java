package me.hiremedev.tetrisplugin.display.screens;

import me.hiremedev.tetrisplugin.display.types.TetrominoeSystem;
import org.bukkit.Bukkit;

import java.util.HashMap;

public class SystemManager {
    private HashMap<String, TetrominoeSystem> systems;

    public SystemManager() {
        systems = new HashMap<>();
    }

    public void registerSystem(TetrominoeSystem system){
        systems.put(system.getName(), system);
        Bukkit.getLogger().info("added %s succesfully.".formatted(system.getName()));
    }

    public void removeScreen(TetrominoeSystem system){
        systems.remove(system.getName(),system);
    }

    public HashMap<String, TetrominoeSystem> getSystems() {
        return systems;
    }
}
